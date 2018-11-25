package apps.blessed.a90secondsofglory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_menu);

        View menuTextStart = (View) findViewById(R.id.menu_text_start);
        Animation shakingAnim = AnimationUtils.loadAnimation(this, R.anim.start_anim);
        menuTextStart.startAnimation(shakingAnim);
        menuTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, GameActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
    }




}
