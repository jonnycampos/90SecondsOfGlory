package apps.blessed.a90secondsofglory;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import apps.blessed.a90secondsofglory.sound.MediaPlayerUtils;
import apps.blessed.a90secondsofglory.view.CanvasGame;
import apps.blessed.a90secondsofglory.view.Counter;
import apps.blessed.a90secondsofglory.view.MiniGameView;

public class GameActivity extends Activity implements GameEventsListener{

    MediaPlayer mediaPlayer;
    boolean isGameReleased;
    MiniGameView my_image_view1;
    MiniGameView my_image_view2;
    MiniGameView my_image_view3;
    MiniGameView my_image_view4;
    Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullGameState.clean();
        FullGameState.getInstance().setStatus(FullGameState.STATUS_GAME);
        setContentView(R.layout.activity_game);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        my_image_view1 = findViewById(R.id.my_image_view);
        my_image_view2 = findViewById(R.id.my_image_view);
        my_image_view3 = findViewById(R.id.my_image_view);
        my_image_view4 = findViewById(R.id.my_image_view);

        counter = findViewById(R.id.counter);
        counter.setGameEventsListener(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.main_theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        isGameReleased = false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        FullGameState.getInstance().setStatus(FullGameState.STATUS_PAUSED);
        stopAndReleaseGame();
    }

    @Override
    public void onTimeEnds() {
        FullGameState.getInstance().setStatus(FullGameState.STATUS_TIME_OVER);
        stopAndReleaseGame();
        Intent myIntent = new Intent(GameActivity.this, ResultActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        GameActivity.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopAndReleaseGame();
        this.finish();
    }



    //We need to release resources
    private void stopAndReleaseGame() {

        if (!isGameReleased) {
            my_image_view1.stopAndRelease();
            my_image_view2.stopAndRelease();
            my_image_view3.stopAndRelease();
            my_image_view4.stopAndRelease();
            mediaPlayer.stop();
            mediaPlayer.release();
            counter.stopTimer();
        }
        isGameReleased = true;
    }

}
