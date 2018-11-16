package apps.blessed.a90secondsofglory.shape;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import apps.blessed.a90secondsofglory.App;
import apps.blessed.a90secondsofglory.R;

/**
 * Created by jacam on 04/11/2018.
 */

public class MiniGameProgressBar {

    // Position of the progress Bar
    private int left;
    private int right;
    private int top;
    private int bottom;

    // Shape where to draw the bar
    private Drawable shape;
    private int shapeResource = R.drawable.ic_mini_game_progressbar;



    public MiniGameProgressBar(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        shape = App.getContext().getResources().getDrawable(shapeResource);
        shape.setBounds(left, top, right, bottom);
    }


    public void draw(Canvas canvas) {
        shape.setBounds(left, top, right, bottom);
        shape.draw(canvas);
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public Drawable getShape() {
        return shape;
    }

    public void setShape(Drawable shape) {
        this.shape = shape;
    }

    public int getShapeResource() {
        return shapeResource;
    }

    public void setShapeResource(int shapeResource) {
        this.shapeResource = shapeResource;
    }
}
