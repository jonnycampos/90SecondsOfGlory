package apps.blessed.a90secondsofglory;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import apps.blessed.a90secondsofglory.view.CanvasGame;
import apps.blessed.a90secondsofglory.view.Counter;

public class GameActivity extends Activity implements GameEventsListener{

   // CanvasGame gameA;
    CanvasGame gameB;
    CanvasGame gameC;
    CanvasGame gameD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Counter counter = findViewById(R.id.counter);
        counter.setGameEventsListener(this);

    }

    @Override
    public void onTimeEnds() {
        Intent myIntent = new Intent(GameActivity.this, ResultActivity.class);
        GameActivity.this.startActivity(myIntent);
    }
}
