package apps.blessed.a90secondsofglory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import apps.blessed.a90secondsofglory.view.Counter;

/**
 * Created by jacam on 25/11/2018.
 */

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewPoints = findViewById(R.id.result_points);
        textViewPoints.setText(String.valueOf(FullGameState.getInstance().getPoints()));

    }

}
