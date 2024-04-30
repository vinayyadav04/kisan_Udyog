package com.example.kisan_udyog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan_udyog.sell.CustomPrediction;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EcoFragment extends Fragment{

    JSONObject data = null;

    String JSON_STRING = "{\"pomegranate\":{\"phvalue\":\"6.36137446\",\"temp\":\"22.77035608\", \"humidity\":\"91.45498527\", \"rainfall\":\"106.9659201\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/pomegranate.jfif?alt=media&token=616af232-ae37-4caa-8b9e-eb87688eb904\"},\"Tea\":{\"phvalue\":\"4.571921666\",\"temp\":\"23.49211409\", \"humidity\":\"55.50981676\", \"rainfall\":\"207.7376\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/tea.png?alt=media&token=24f931da-0253-49b4-b00a-44a2624122c2\"},\"wheat\":{\"phvalue\":\"6.276107098\",\"temp\":\"27.65509604\", \"humidity\":\"53.92601391\", \"rainfall\":\"85.77839497\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/wheat.png?alt=media&token=d51690cc-a800-435d-901b-aa22fe665da4\"},\"muskmelon\":{\"phvalue\":\"6.123802502\",\"temp\":\"29.81196601\", \"humidity\":\"90.36881284\", \"rainfall\":\"22.68766503\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/muskmelon.jfif?alt=media&token=e68df9f1-be42-4493-bd4e-bfc3c4bc36c9\"},\"mango\":{\"phvalue\":\"5.699586972\",\"temp\":\"33.56150184\", \"humidity\":\"47.67525434\", \"rainfall\":\"95.85118326\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/mango.jfif?alt=media&token=877cd8a1-9079-4cde-94f1-0bf47d5660a2\"},\"Jute\":{\"phvalue\":\"6.176860192\",\"temp\":\"24.35564134\", \"humidity\":\"88.80391021\", \"rainfall\":\"169.1168028\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/jute.jfif?alt=media&token=f865841d-59a2-462e-b455-1848d725537f\"},\"millet\":{\"phvalue\":\"6.484499631\",\"temp\":\"45.38352611\", \"humidity\":\"13.87064234\", \"rainfall\":\"38.19865734\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/millet.jfif?alt=media&token=e7e498ed-4308-4cf9-9541-c50cca1157eb\"},\"Adzuki Beans\":{\"phvalue\":\"6.984003028\",\"temp\":\"51.98960952\", \"humidity\":\"54.33930163\", \"rainfall\":\"50.34963363\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/Adzuki-Beans.jfif?alt=media&token=36b8d658-60cf-4ba4-a8ff-a1c330e56bd4\"},\"Pigeon Peas\":{\"phvalue\":\"5.611510977\",\"temp\":\"31.2192752\", \"humidity\":\"56.46868874\", \"rainfall\":\"129.2028653\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/pegion-peas.jfif?alt=media&token=be3d82b5-f9e3-42aa-9268-edd215a38893\"},\"papaya\":{\"phvalue\":\"6.761953186\",\"temp\":\"24.48620746\", \"humidity\":\"92.98254537\", \"rainfall\":\"183.49095\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/papaya.jfif?alt=media&token=279927d1-bbce-499c-82a0-beecb8f81a33\"},\"Ground Nut\":{\"phvalue\":\"6.178774026\",\"temp\":\"31.11185433\", \"humidity\":\"62.36169509\", \"rainfall\":\"118.7825932\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/groundnut.jfif?alt=media&token=12b837a0-81e7-4e96-a06d-146bc512bb37\"},\"banana\":{\"phvalue\":\"6.190757459\",\"temp\":\"26.33544853\", \"humidity\":\"76.8532006\", \"rainfall\":\"118.6858263\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/banana.jfif?alt=media&token=5c21e411-16c2-4369-939e-142b0b630f10\"},\"Coffee\":{\"phvalue\":\"6.516312148\",\"temp\":\"27.56088634\", \"humidity\":\"68.49299897\", \"rainfall\":\"167.4358075\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/cofee.jfif?alt=media&token=a01b22b7-c0ae-4e37-a11a-08971cfd186b\"},\"grapes\":{\"phvalue\":\"6.207600783\",\"temp\":\"10.89875873\", \"humidity\":\"80.01639435\", \"rainfall\":\"68.69420397\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/grapes.jfif?alt=media&token=87998d80-c4af-4d6e-8e15-be59527c1182\"},\"Kidney Beans\":{\"phvalue\":\"5.926676985\",\"temp\":\"16.43340342\", \"humidity\":\"24.24045875\", \"rainfall\":\"140.3717815\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/Kidney-Beans.jfif?alt=media&token=1d5abc79-368a-4738-8d2b-27759ff8d69a\"},\"maize\":{\"phvalue\":\"5.749914421\",\"temp\":\"22.61359953\", \"humidity\":\"63.69070564\", \"rainfall\":\"87.75953857\", \"src\":\"hhtttp....\"},\"orange\":{\"phvalue\":\"6.361671475\",\"temp\":\"19.33516809\", \"humidity\":\"91.97978938\", \"rainfall\":\"116.450422\", \"src\":\"hhtttp....\"},\"watermelon\":{\"phvalue\":\"6.429788073\",\"temp\":\"26.80750629\", \"humidity\":\"88.22874955\", \"rainfall\":\"58.79889057\", \"src\":\"hhtttp....\"},\"Tobacco\":{\"phvalue\":\"5.901895741\",\"temp\":\"26.57256307\", \"humidity\":\"62.94940697\", \"rainfall\":\"77.79486786\", \"src\":\"hhtttp....\"},\"Chickpea\":{\"phvalue\":\"6.391173589\",\"temp\":\"18.86805647\", \"humidity\":\"15.65809214\", \"rainfall\":\"88.51048983\", \"src\":\"hhtttp....\"},\"Moth Beans\":{\"phvalue\":\"4.61136408\",\"temp\":\"28.09568993\", \"humidity\":\"60.9835384\", \"rainfall\":\"33.84110759\", \"src\":\"hhtttp....\"},\"Mung Bean\":{\"phvalue\":\"27.43329405\",\"temp\":\"7.18530147\", \"humidity\":\"87.80507732\", \"rainfall\":\"54.73367631\", \"src\":\"hhtttp....\"},\"Coconut\":{\"phvalue\":\"5.860740481\",\"temp\":\"25.84726298\", \"humidity\":\"90.92669463\", \"rainfall\":\"147.8888994\", \"src\":\"hhtttp....\"},\"Peas\":{\"phvalue\":\"6.219809112\",\"temp\":\"18.38433704\", \"humidity\":\"13.627536\", \"rainfall\":\"46.1097405\", \"src\":\"hhtttp....\"},\"rice\":{\"phvalue\":\"6.502985292\",\"temp\":\"20.87974371\", \"humidity\":\"82.00274423\", \"rainfall\":\"202.9355362\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/rice.png?alt=media&token=704cc1d8-c326-4ef7-8ce2-f45371244bf7\"},\"apple\":{\"phvalue\":\"6.75020529\",\"temp\":\"24.84063998\", \"humidity\":\"60.09116626\", \"rainfall\":\"48.77790371\", \"src\":\"hhtttp....\"},\"Lentil\":{\"phvalue\":\"7.352401887\",\"temp\":\"23.66457347\", \"humidity\":\"81.69105088\", \"rainfall\":\"99.36898373\", \"src\":\"hhtttp....\"},\"Cotton\":{\"phvalue\":\"7.929240731\",\"temp\":\"27.8558593\", \"humidity\":\"76.91493565\", \"rainfall\":\"154.9011463\", \"src\":\"hhtttp....\"},\"Sugarcane\":{\"phvalue\":\"4.634975125\",\"temp\":\"26.64588548\", \"humidity\":\"70.80010239\", \"rainfall\":\"361.1434598\", \"src\":\"hhtttp....\"},\"Rubber\":{\"phvalue\":\"7.397190844\",\"temp\":\"32.47648301\", \"humidity\":\"64.34848735\", \"rainfall\":\"65.820457\", \"src\":\"hhtttp....\"},\"Black gram\":{\"phvalue\":\"3.5\",\"temp\":\"35\", \"humidity\":\"45\", \"rainfall\":\"456\", \"src\":\"hhtttp....\"}}";
    private Context mcontext = getContext();
    private static final String TAG = "EcoFragment";
    private TextView result;
    private TextView temperature, rainfall, pH, humidity, temp2,humid2, pressure;
    Interpreter interpreter;
    private ImageView srcUrl;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private int n,hu, p;
    private Button custPrediction;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eco, container, false);
        result=view.findViewById(R.id.outputModel);
        temperature=view.findViewById(R.id.tempval);
        humidity=view.findViewById(R.id.humidval);
        pressure=view.findViewById(R.id.currPressure);
        custPrediction=view.findViewById(R.id.custPre);

        pH=view.findViewById(R.id.phval);
        temp2=view.findViewById(R.id.tempReq);
        humid2=view.findViewById(R.id.humidReq);
        srcUrl=view.findViewById(R.id.imageView2);
        rainfall=view.findViewById(R.id.rainfallval);
        try {
            interpreter = new Interpreter(loadModelFile(),null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        requestLocation();
        custPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomPrediction.class);
                startActivity(intent);
            }
        });

        return view;
    }

    //========================================================PREDICTION======================================================================================
    private MappedByteBuffer loadModelFile() throws IOException{
        AssetFileDescriptor assetFileDescriptor = getActivity().getAssets().openFd("tfmodels.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel= fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length =assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset,length);
    }
    public String doInference(float a, float b, float c, float d){
        Log.d(TAG, "helllo"+a+" "+b);
        float[] input = new float[]{a, b,6,202};
        String[] Classes = new String[]{"pomegranate", "Tea", "wheat", "muskmelon", "mango", "Jute", "millet",
                "Adzuki Beans", "Pigeon Peas", "papaya", "Ground Nut", "banana", "Coffee", "grapes", "Kidney Beans",
                "maize", "orange", "watermelon", "Tobacco", "Chickpea", "Moth Beans", "Mung Bean", "Coconut", "Peas",
                "rice", "apple", "Lentil", "Cotton", "Sugarcane", "Rubber", "Black gram"};
        float[][] output = new float[1][31];
        List<Float> newOutput = new ArrayList<>();
        interpreter.run(input,output);
        for(int i=0;i<=30;i++){
            newOutput.add(output[0][i]);
        }
        Float max = Collections.max(newOutput);
        Log.d(TAG,"MAXX"+" "+newOutput);
        Log.d(TAG,"MAXX"+" "+newOutput.indexOf(max));
        return Classes[newOutput.indexOf(max)];
    }


    /////============================================================WEATHER API =============================================================================
    public void getJSON(final double lat, final double longi){
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                String str="http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+longi+"&appid=d94cf62848d1ba15929b240e90183338";
                Log.d(TAG, "helllo"+str);
                URLConnection urlConn = null;
                BufferedReader bufferedReader = null;
                try
                {
                    URL url = new URL(str);
                    urlConn = url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        stringBuffer.append(line);
                    }
                    data = new JSONObject(stringBuffer.toString());
                    final JSONObject temps = data.getJSONObject("main");
                     n=temps.getInt("temp")-273;
                    hu=temps.getInt("humidity");
                    p=temps.getInt("pressure");
                   getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d("mine", String.valueOf(n));
                            temperature.setText(String.valueOf(n));
                            humidity.setText(String.valueOf(hu));
                            pressure.setText(String.valueOf(p));
                            String f=doInference(n,hu, 6,365);
                            result.setText(f);
                            jsonParser(f);
                            //Log.d("mine", String.valueOf(hu));
                        }
                    });
                    return new JSONObject(stringBuffer.toString());
                }
                catch(Exception ex)
                {
                    Log.e("App", "yourDataTask", ex);
                    return null;
                }
                finally
                {
                    if(bufferedReader != null)
                    {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            protected void onPostExecute(JSONObject response) {
                if(response != null)
                {
                    Log.e(TAG, "GOT THE RESPONSE");
                }
            }
        }.execute();
    }


    /////=====================================================LOCATION==========================================================================================


    private void requestLocation() {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            getCurrentLocation();
        } else {
            Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
    private void getCurrentLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    getJSON(latitude, longitude);
                }
            }
        }, Looper.getMainLooper());
    }



    ///================================================================JSON PARSER====================================================
public void jsonParser(String g){
    try {
        JSONObject obj = new JSONObject(JSON_STRING);
        JSONObject dataset = obj.getJSONObject(g);
        Log.e(TAG, "ERRORS!"+" "+1);
        String temps = dataset.getString("temp");
        Log.e(TAG, "ERRORS!"+" "+2);
        String rainfalls = dataset.getString("rainfall");
        Log.e(TAG, "ERRORS!"+" "+3);
        String imgUrls =  dataset.getString("src");
        Log.e(TAG, "ERRORS!"+" "+4);
        String phvals=dataset.getString("phvalue");
        Log.e(TAG, "ERRORS!"+" "+5);
        String humiditys =dataset.getString("humidity");


        Picasso.with(getContext()).load(imgUrls).into(srcUrl);
        humid2.setText(humiditys);
        temp2.setText(temps);
        rainfall.setText(rainfalls);
        pH.setText(phvals);
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
}
