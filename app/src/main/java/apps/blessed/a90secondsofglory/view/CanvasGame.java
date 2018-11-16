package apps.blessed.a90secondsofglory.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import apps.blessed.a90secondsofglory.R;

/**
 * Created by jacam on 30/09/2018.
 */

public class CanvasGame extends View {


    private int colorBorder;
    private int colorBackground;
    private int paddingShape;
    private String positionCircle;
    private int radiusCircle;
    private int strokeBorder;

    private int leftRelative;
    private int rightRelative;
    private int topRelative;
    private int bottomRelative;

    // defines paint and canvas
    private Paint drawPaint;

    public int getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(int colorBorder) {
        this.colorBorder = colorBorder;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public int getPaddingShape() {
        return paddingShape;
    }

    public void setPaddingShape(int paddingShape) {
        this.paddingShape = paddingShape;
    }

    public String getPositionCircle() {
        return positionCircle;
    }

    public void setPositionCircle(String positionCircle) {
        this.positionCircle = positionCircle;
    }

    public int getRadiusCircle() {
        return radiusCircle;
    }

    public void setRadiusCircle(int radiusCircle) {
        this.radiusCircle = radiusCircle;
    }

    public int getStrokeBorder() {
        return strokeBorder;
    }

    public void setStrokeBorder(int strokeBorder) {
        this.strokeBorder = strokeBorder;
    }


    public int getLeftRelative() {
        return leftRelative;
    }

    public void setLeftRelative(int leftRelative) {
        this.leftRelative = leftRelative;
    }

    public int getRightRelative() {
        return rightRelative;
    }

    public void setRightRelative(int rightRelative) {
        this.rightRelative = rightRelative;
    }

    public int getTopRelative() {
        return topRelative;
    }

    public void setTopRelative(int topRelative) {
        this.topRelative = topRelative;
    }

    public int getBottomRelative() {
        return bottomRelative;
    }

    public void setBottomRelative(int bottomRelative) {
        this.bottomRelative = bottomRelative;
    }

    public CanvasGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CanvasGame);

        this.setColorBorder(a.getInteger(R.styleable.CanvasGame_colorBorder,0));
        this.setColorBackground(a.getInteger(R.styleable.CanvasGame_colorBackground,0));
        this.setPaddingShape(a.getInteger(R.styleable.CanvasGame_paddingShape,0));
        this.setRadiusCircle(a.getInteger(R.styleable.CanvasGame_radiusCircle,0));
        this.setStrokeBorder(a.getInteger(R.styleable.CanvasGame_strokeBorder,0));
        this.setPositionCircle(a.getString(R.styleable.CanvasGame_positionCircle));

        this.setLeftRelative(0);
        this.setTopRelative(0);
        this.setRightRelative(Resources.getSystem().getDisplayMetrics().widthPixels / 2);
        this.setBottomRelative(Resources.getSystem().getDisplayMetrics().heightPixels / 2);
        setupPaint();
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(getColorBorder());
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(getStrokeBorder());
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawPaint.setColor(getColorBorder());
        drawPaint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(getLeftRelative() + getPaddingShape(),
                getTopRelative() +getPaddingShape(),
                getRightRelative()-getPaddingShape() ,
                getBottomRelative()-getPaddingShape());
        canvas.drawRect(rect, drawPaint);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setColor(Color.BLACK);
        if (getPositionCircle() != null){
            if (getPositionCircle().equals("tl"))
                canvas.drawCircle(getLeftRelative(), getTopRelative(), getRadiusCircle(), drawPaint);
            else if (getPositionCircle().equals("tr"))
                canvas.drawCircle(getRightRelative(), getTopRelative(), getRadiusCircle(), drawPaint);
            else if (getPositionCircle().equals("bl"))
                canvas.drawCircle(getLeftRelative(), getBottomRelative(), getRadiusCircle(), drawPaint);
            else if (getPositionCircle().equals("br"))
                canvas.drawCircle(getRightRelative(), getBottomRelative(), getRadiusCircle(), drawPaint);
        }

        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setColor(Color.WHITE);
        drawPaint.setTextSize(50);
        canvas.drawText("Triangles",200,200,drawPaint);

    }
}
