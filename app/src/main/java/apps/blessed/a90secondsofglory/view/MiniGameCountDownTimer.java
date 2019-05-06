package apps.blessed.a90secondsofglory.view;

import android.content.Context;
import android.os.CountDownTimer;

import apps.blessed.a90secondsofglory.FullGameState;
import apps.blessed.a90secondsofglory.R;
import apps.blessed.a90secondsofglory.sound.MediaPlayerUtils;




public class MiniGameCountDownTimer extends CountDownTimer {

    MiniGameView view;
    Context context;

    public MiniGameCountDownTimer(long millisInFuture, long countDownInterval, MiniGameView view, Context context) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.context = context;

    }

    @Override
    public void onFinish() {
        //Bye bye
        if (FullGameState.getInstance().isPlayingGame() && !view.isAnswered()) {
            MediaPlayerUtils.playSound(context, R.raw.wrong2);
            view.restartView();
        }

    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (!FullGameState.getInstance().isPlayingGame()) {
            this.cancel();
        }
        view.addMillisec();
    }
}

