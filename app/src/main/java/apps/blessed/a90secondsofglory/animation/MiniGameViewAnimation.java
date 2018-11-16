package apps.blessed.a90secondsofglory.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import apps.blessed.a90secondsofglory.view.MiniGameView;

/**
 * Created by jacam on 04/11/2018.
 */

public class MiniGameViewAnimation extends Animation {

    private MiniGameView view;

    private int speed = 3;

    private int newTopProgressBar;

    private int countSpeed = 8000;

    public MiniGameViewAnimation(MiniGameView view, int newTop) {
        this.newTopProgressBar = newTop;
        this.view = view;

    }



    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        if (view.getProgressBar().getTop() < view.getProgressBar().getBottom())
        {
           if (countSpeed % speed == 0.0) {

                newTopProgressBar = newTopProgressBar + 1;
                view.getProgressBar().setTop(newTopProgressBar);
                view.requestLayout();
           }
           countSpeed = countSpeed - 1;
        }
    }
}
