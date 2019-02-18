package apps.blessed.a90secondsofglory;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import apps.blessed.a90secondsofglory.view.CanvasGame;
import apps.blessed.a90secondsofglory.view.Counter;

public class GameActivity extends Activity implements GameEventsListener{

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Counter counter = findViewById(R.id.counter);
        counter.setGameEventsListener(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.main_theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    public void onTimeEnds() {
        Intent myIntent = new Intent(GameActivity.this, ResultActivity.class);
        GameActivity.this.startActivity(myIntent);
        mediaPlayer.stop();
    }
}
