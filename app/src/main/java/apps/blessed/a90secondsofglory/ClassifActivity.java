package apps.blessed.a90secondsofglory;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;


import java.util.ArrayList;
import java.util.Arrays;

import apps.blessed.a90secondsofglory.adapter.ClassifItemAdapter;
import apps.blessed.a90secondsofglory.lambda.LambdaInterface;
import apps.blessed.a90secondsofglory.lambda.RequestMetricsByProvince;
import apps.blessed.a90secondsofglory.lambda.ResponseMetricByProvince;
import apps.blessed.a90secondsofglory.lambda.ResponseMetricsByProvince;
import apps.blessed.a90secondsofglory.view.CustomListView;

/**
 * Created by jacam on 25/11/2018.
 */

public class ClassifActivity extends Activity {

    private String province;
    ResponseMetricsByProvince listOfPlayers;
    CustomListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classif);

        //Province to list
        TextView textViewPoints = findViewById(R.id.classif_province);
        String province = String.valueOf(FullGameState.getInstance().getProvince());
        textViewPoints.setText(province);

        //Items list
        listView = findViewById(R.id.classif_list);
        getTopPlayers(province);





    }


    /**
     * Get from backend the top list of players in one province
     * @param province Province
     */
    private void getTopPlayers(String province) {
        // Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(), "eu-west-2:17d83e7f-30b5-4ed1-be2c-337db82ef226", Regions.EU_WEST_2);

        // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = LambdaInvokerFactory.builder().context(this.getApplicationContext()).region(Regions.EU_WEST_2).credentialsProvider(cognitoProvider).build();


        // Create the Lambda proxy object with a default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder.
        final LambdaInterface lambdaInterface = factory.build(LambdaInterface.class);

        //Build the request
        RequestMetricsByProvince request = new RequestMetricsByProvince();
        request.setProvince(province);


        // The Lambda function invocation results in a network call.
        // Make sure it is not called from the main thread.
        new AsyncTask<RequestMetricsByProvince, Void, ResponseMetricsByProvince>() {
            @Override
            protected ResponseMetricsByProvince doInBackground(RequestMetricsByProvince... params) {
                try {
                    return lambdaInterface.getMetricByProvince(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke getMetricByProvince", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ResponseMetricsByProvince result) {
                if (result == null) {
                    return;
                } else {
                    listOfPlayers = result;
                    ArrayList<ResponseMetricByProvince> listClassif =
                            new ArrayList<ResponseMetricByProvince>(Arrays.asList(listOfPlayers.getItems()));
                    if (FullGameState.getInstance().getPosition() > listOfPlayers.getCount()) {
                        ResponseMetricByProvince currentGame = new ResponseMetricByProvince();
                        currentGame.setPosition(FullGameState.getInstance().getPosition());
                        currentGame.setName(FullGameState.getInstance().getName());
                        currentGame.setProvince(FullGameState.getInstance().getProvince());
                        currentGame.setPoints(FullGameState.getInstance().getPoints());
                        currentGame.setCurrentGame(true);
                        listClassif.add(currentGame);
                    } else {
                        listClassif.get(FullGameState.getInstance().getPosition()-1).setCurrentGame(true);
                    }

                    ClassifItemAdapter classifItemAdapter = new ClassifItemAdapter(ClassifActivity.this,
                            listClassif);
                    listView.setAdapter(classifItemAdapter);
                }
            }
        }.execute(request);
    }



}