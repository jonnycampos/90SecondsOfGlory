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

public class Counter extends View {


    private Paint drawPaint;

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
        drawPaint.setColor(Color.BLUE);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(0, 0, 200, drawPaint);


    }
}
