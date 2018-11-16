package apps.blessed.a90secondsofglory;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import apps.blessed.a90secondsofglory.view.CanvasGame;

public class GameActivity extends Activity {

   // CanvasGame gameA;
    CanvasGame gameB;
    CanvasGame gameC;
    CanvasGame gameD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

     //   gameA = (CanvasGame) findViewById(R.id.gamea);
        //gameB = (CanvasGame) findViewById(R.id.gameb);
        //gameC = (CanvasGame) findViewById(R.id.gamec);
        //gameD = (CanvasGame) findViewById(R.id.gamed);

        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(1000);
        anim.setRepeatCount(5);
        anim.setRepeatMode(Animation.REVERSE);
        //gameB.startAnimation(anim);
    }
}
