package apps.blessed.a90secondsofglory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import apps.blessed.a90secondsofglory.bo.GameMetric;
import apps.blessed.a90secondsofglory.lambda.LambdaInterface;
import apps.blessed.a90secondsofglory.lambda.RequestClass;
import apps.blessed.a90secondsofglory.lambda.ResponseClass;


/**
 * Created by jacam on 25/11/2018.
 */

public class ResultActivity extends Activity {


    Spinner spinner;
    List<String> provinces;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewPoints = findViewById(R.id.result_points);
        textViewPoints.setText(String.valueOf(FullGameState.getInstance().getPoints()) + " " + "Puntos");

        provinces = Arrays.asList(getResources().getStringArray(R.array.provinces));

        spinner = (Spinner) findViewById(R.id.result_city_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provinces, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Selecciona tu Provincia");
        spinner.setAdapter(adapter);
        spinner.setSelected(false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        String city = resolveCity();
        if (city != null) {
            spinner.setSelection(findSimilarity(city, provinces));
        }

        editText = findViewById(R.id.name_result);


        button = findViewById(R.id.button_result);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = editText.getText().toString();
                String province = provinces.get(spinner.getSelectedItemPosition());
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(province)) {
                    //Pop up warning
                } else {
                    FullGameState.getInstance().setName(name);
                    FullGameState.getInstance().setProvince(province);
                    sendMetrics();

                    //New Activity
                    Intent myIntent = new Intent(ResultActivity.this, ClassifActivity.class);
                    ResultActivity.this.startActivity(myIntent);


                }

            }
        });
    }

    private void sendMetrics() {
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
        RequestClass request = new RequestClass();
        request.setName(FullGameState.getInstance().getName());
        request.setProvince(FullGameState.getInstance().getProvince());
        request.setPoints(FullGameState.getInstance().getPoints());
        request.setMetricIndex(buildMetricIndex(request.getPoints(), request.getName()));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        request.setDate(formatter.format(date));
        request.setGameMetrics(FullGameState.getInstance().getGameMetricList().toArray(new GameMetric[FullGameState.getInstance().getGameMetricList().size()]));


        // The Lambda function invocation results in a network call.
        // Make sure it is not called from the main thread.
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return lambdaInterface.game_metric(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ResponseClass result) {
                FullGameState.getInstance().setPosition(result.getPosition());
            }
        }.execute(request);
    }

    private String buildMetricIndex(int points, String name) {
        return String.format("%05d", points) + "-" + name;
    }


    public static int findSimilarity(String word, List<String> candidatesList) {
        LevenshteinDistance distance = new LevenshteinDistance();
        int minScore=10000;
        int position = 0;
        int result = 0;
        for (String s:candidatesList) {
            int score = distance.apply(word.replaceAll("\\s","").toUpperCase(),
                    s.replaceAll("\\s","").toUpperCase());
            if (score == 0) {
                return position;
            } else if (minScore > score) {
                minScore = score;
                result = position;
            }
            position++;
        }
        return result;
    }

    private String resolveCity() {


        Location location = getLastKnownLocation();
        StringBuilder builder = new StringBuilder();
        String city = null;
        double lat = 37.016865;
        double lng = -4.558381;


        if (location != null) {
             lat = location.getLatitude();
             lng = location.getLongitude();
        }
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            city= address.get(0).getSubAdminArea();
        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }
        return city;

    }


    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {

            @SuppressLint("MissingPermission") Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String city = resolveCity();
                    spinner.setSelection(findSimilarity(city,provinces));

                } else {
                    chooseCity();
                }
                return;
            }

        }
    }

    private void chooseCity() {
    }

}