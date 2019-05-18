package apps.blessed.a90secondsofglory.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import apps.blessed.a90secondsofglory.view.MiniGameView;

/**
 * Created by jacam on 04/11/2018.
 */

public class MiniGameViewAnimation extends Animation {

    private MiniGameView view;
    //Calculated by checking output
    private static int EXECUTIONS_PER_SECOND = 54;

    private long speed;

    private int newTopProgressBar;

    private int countSpeed = 8500;

    public MiniGameViewAnimation(MiniGameView view, int newTop) {
        this.newTopProgressBar = newTop;
        this.view = view;
    }


    @Override
    public void setDuration(long durationMillis) {
        super.setDuration(durationMillis);
        //Speed calculation - It means, when the progress bar should (each "speed" executions)
        int length =  view.getProgressBar().getBottom() - view.getProgressBar().getTop();
        speed = Math.round(((EXECUTIONS_PER_SECOND * getDuration())/1000) / length);
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
