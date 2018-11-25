package apps.blessed.a90secondsofglory.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.concurrent.TimeUnit;

import apps.blessed.a90secondsofglory.FullGameState;
import apps.blessed.a90secondsofglory.GameEventsListener;
import apps.blessed.a90secondsofglory.R;

/**
 * Created by jacam on 30/09/2018.
 */

public class Counter extends View {


    private Paint drawPaint;
    private Paint eraserPaint;
    private float mCircleSweepAngle;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private RectF mCircleOuterBounds;
    private RectF mCircleInnerBounds;
    private long time;
    private GameEventsListener gameEventsListener;

    private static final float THICKNESS_SCALE = 0.01f;


    public GameEventsListener getGameEventsListener() {
        return gameEventsListener;
    }

    public void setGameEventsListener(GameEventsListener gameEventsListener) {
        this.gameEventsListener = gameEventsListener;
    }

    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CanvasGame);

        setupPaint();
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(Color.BLUE);


        eraserPaint = new Paint();
        eraserPaint.setAntiAlias(true);
        eraserPaint.setColor(Color.TRANSPARENT);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        start(90);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mBitmap.eraseColor(Color.TRANSPARENT);
            mCanvas = new Canvas(mBitmap);
        }

        super.onSizeChanged(w, h, oldw, oldh);
        updateBounds();
    }


    @Override
    protected void onDraw(Canvas canvas) {


        //Draw animated circle
        //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        drawPaint.setColor(Color.MAGENTA);
        if (mCircleSweepAngle > 0f) {
            canvas.drawArc(mCircleOuterBounds, 270, mCircleSweepAngle, true, drawPaint);
            canvas.drawOval(mCircleInnerBounds, eraserPaint);
        }

        //Draw points
        //canvas.drawBitmap(mBitmap, 0, 0, null);
        drawPaint.setTextSize(100);
        drawPaint.setStyle(Paint.Style.FILL);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.oswald);
        drawPaint.setTypeface(typeface);

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((drawPaint.descent() + drawPaint.ascent()) / 2)) ;
        drawPaint.setTextAlign(Paint.Align.CENTER);
        drawPaint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(FullGameState.getInstance().getPoints()),xPos, yPos, drawPaint);

        //Draw time
        drawPaint.setTextSize(50);
        drawPaint.setColor(Color.YELLOW);
        canvas.drawText(drawTime(time),xPos, yPos+80, drawPaint);
    }


    private String drawTime(Long milliseconds) {
        String seconds = String.format("%02d",milliseconds/1000);
        String millis = String.format("%03d",milliseconds%1000).substring(0,2);
        return seconds + ":" + millis;
    }


    public void start(int secs) {


        ValueAnimator mTimerAnimator = ValueAnimator.ofFloat(0f, 1f);
        mTimerAnimator.setDuration(TimeUnit.SECONDS.toMillis(secs));
        mTimerAnimator.setInterpolator(new LinearInterpolator());
        mTimerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawProgress((float) animation.getAnimatedValue(), animation.getCurrentPlayTime());
            }
        });
        mTimerAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                gameEventsListener.onTimeEnds();
            }
        });
        mTimerAnimator.start();
    }


    private void updateBounds() {
        final float thickness = getWidth() * THICKNESS_SCALE;
        int height = getHeight() / 2;
        int width = getWidth() / 2;
            mCircleOuterBounds = new RectF(width-200, height-200, width + 200, height + 200);
        mCircleInnerBounds = new RectF(
                mCircleOuterBounds.left + thickness,
                mCircleOuterBounds.top + thickness,
                mCircleOuterBounds.right - thickness,
                mCircleOuterBounds.bottom - thickness);

        invalidate();
    }

    private void drawProgress(float progress, long time) {
        mCircleSweepAngle = 360 * progress;
        this.time = 90000 - time;
        invalidate();
    }

//    public class TimeCountDownTimer extends CountDownTimer {
//
//        MiniGameView view;
//
//        public TimeCountDownTimer(long millisInFuture, long countDownInterval, MiniGameView view) {
//            super(millisInFuture, countDownInterval);
//            this.view = view;
//        }
//
//        @Override
//        public void onFinish() {
//            state = STATE_GAME;
//            view.setVisibility(View.GONE);
//            view.invalidate();
//            setupPaint();
//            view.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//        }
//    }
}
