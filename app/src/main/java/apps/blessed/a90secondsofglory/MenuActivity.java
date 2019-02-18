package apps.blessed.a90secondsofglory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_menu);
        final Button button = (Button) findViewById(R.id.button_menu);
        button.setAlpha(0);


        final TextView textview = (TextView) findViewById(R.id.menu_title);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 50);
        animator.setDuration(3000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                textview.setTextSize(animatedValue);
            }

        });

        animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                final ImageView clockBottom = (ImageView) findViewById(R.id.menu_image_bottom);
                Animation animationFadeOut = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out_anim);
                animationFadeOut.setAnimationListener(new Animation.AnimationListener()
                {
                    public void onAnimationStart(Animation anim)
                    {
                    };
                    public void onAnimationRepeat(Animation anim)
                    {
                    };
                    public void onAnimationEnd(Animation anim)
                    {
                        clockBottom.setImageAlpha(0);
                    };
                });



                Animation animationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in_anim);
                animationFadeIn.setAnimationListener(new Animation.AnimationListener()
                {
                    public void onAnimationStart(Animation anim)
                    {
                    };
                    public void onAnimationRepeat(Animation anim)
                    {
                    };
                    public void onAnimationEnd(Animation anim)
                    {
                        button.setAlpha(1);
                    };
                });


                button.startAnimation(animationFadeIn);
                clockBottom.startAnimation(animationFadeOut);


            }
        });
        animator.start();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, GameActivity.class);
                MenuActivity.this.startActivity(myIntent);

            }
        });


    }




}
