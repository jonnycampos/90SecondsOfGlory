package apps.blessed.a90secondsofglory.shape;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import apps.blessed.a90secondsofglory.App;
import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.animation.MiniGameViewAnimation;

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

    //Animation of the progress bar
    MiniGameViewAnimation animation;



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


    public void start() {
        animation.start();
    }

    public void reset() {
        animation.cancel();
        animation.start();
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


    public MiniGameViewAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(MiniGameViewAnimation animation) {
        this.animation = animation;
    }


}
