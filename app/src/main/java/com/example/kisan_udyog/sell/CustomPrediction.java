package com.example.kisan_udyog.sell;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kisan_udyog.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomPrediction extends AppCompatActivity {
    ImageView close,srcUrl;
    EditText temp,humid,rain,ph;
    TextView label;
    Button predict;
    float t,h,p,r;

    JSONObject data = null;
    String JSON_STRING = "{\"pomegranate\":{\"phvalue\":\"6.36137446\",\"temp\":\"22.77035608\", \"humidity\":\"91.45498527\", \"rainfall\":\"106.9659201\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/pomegranate.jfif?alt=media&token=616af232-ae37-4caa-8b9e-eb87688eb904\"},\"Tea\":{\"phvalue\":\"4.571921666\",\"temp\":\"23.49211409\", \"humidity\":\"55.50981676\", \"rainfall\":\"207.7376\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/tea.png?alt=media&token=24f931da-0253-49b4-b00a-44a2624122c2\"},\"wheat\":{\"phvalue\":\"6.276107098\",\"temp\":\"27.65509604\", \"humidity\":\"53.92601391\", \"rainfall\":\"85.77839497\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/wheat.png?alt=media&token=d51690cc-a800-435d-901b-aa22fe665da4\"},\"muskmelon\":{\"phvalue\":\"6.123802502\",\"temp\":\"29.81196601\", \"humidity\":\"90.36881284\", \"rainfall\":\"22.68766503\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/muskmelon.jfif?alt=media&token=e68df9f1-be42-4493-bd4e-bfc3c4bc36c9\"},\"mango\":{\"phvalue\":\"5.699586972\",\"temp\":\"33.56150184\", \"humidity\":\"47.67525434\", \"rainfall\":\"95.85118326\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/mango.jfif?alt=media&token=877cd8a1-9079-4cde-94f1-0bf47d5660a2\"},\"Jute\":{\"phvalue\":\"6.176860192\",\"temp\":\"24.35564134\", \"humidity\":\"88.80391021\", \"rainfall\":\"169.1168028\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/jute.jfif?alt=media&token=f865841d-59a2-462e-b455-1848d725537f\"},\"millet\":{\"phvalue\":\"6.484499631\",\"temp\":\"45.38352611\", \"humidity\":\"13.87064234\", \"rainfall\":\"38.19865734\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/millet.jfif?alt=media&token=e7e498ed-4308-4cf9-9541-c50cca1157eb\"},\"Adzuki Beans\":{\"phvalue\":\"6.984003028\",\"temp\":\"51.98960952\", \"humidity\":\"54.33930163\", \"rainfall\":\"50.34963363\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/Adzuki-Beans.jfif?alt=media&token=36b8d658-60cf-4ba4-a8ff-a1c330e56bd4\"},\"Pigeon Peas\":{\"phvalue\":\"5.611510977\",\"temp\":\"31.2192752\", \"humidity\":\"56.46868874\", \"rainfall\":\"129.2028653\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/pegion-peas.jfif?alt=media&token=be3d82b5-f9e3-42aa-9268-edd215a38893\"},\"papaya\":{\"phvalue\":\"6.761953186\",\"temp\":\"24.48620746\", \"humidity\":\"92.98254537\", \"rainfall\":\"183.49095\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/papaya.jfif?alt=media&token=279927d1-bbce-499c-82a0-beecb8f81a33\"},\"Ground Nut\":{\"phvalue\":\"6.178774026\",\"temp\":\"31.11185433\", \"humidity\":\"62.36169509\", \"rainfall\":\"118.7825932\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/groundnut.jfif?alt=media&token=12b837a0-81e7-4e96-a06d-146bc512bb37\"},\"banana\":{\"phvalue\":\"6.190757459\",\"temp\":\"26.33544853\", \"humidity\":\"76.8532006\", \"rainfall\":\"118.6858263\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/banana.jfif?alt=media&token=5c21e411-16c2-4369-939e-142b0b630f10\"},\"Coffee\":{\"phvalue\":\"6.516312148\",\"temp\":\"27.56088634\", \"humidity\":\"68.49299897\", \"rainfall\":\"167.4358075\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/cofee.jfif?alt=media&token=a01b22b7-c0ae-4e37-a11a-08971cfd186b\"},\"grapes\":{\"phvalue\":\"6.207600783\",\"temp\":\"10.89875873\", \"humidity\":\"80.01639435\", \"rainfall\":\"68.69420397\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/grapes.jfif?alt=media&token=87998d80-c4af-4d6e-8e15-be59527c1182\"},\"Kidney Beans\":{\"phvalue\":\"5.926676985\",\"temp\":\"16.43340342\", \"humidity\":\"24.24045875\", \"rainfall\":\"140.3717815\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/Kidney-Beans.jfif?alt=media&token=1d5abc79-368a-4738-8d2b-27759ff8d69a\"},\"maize\":{\"phvalue\":\"5.749914421\",\"temp\":\"22.61359953\", \"humidity\":\"63.69070564\", \"rainfall\":\"87.75953857\", \"src\":\"hhtttp....\"},\"orange\":{\"phvalue\":\"6.361671475\",\"temp\":\"19.33516809\", \"humidity\":\"91.97978938\", \"rainfall\":\"116.450422\", \"src\":\"hhtttp....\"},\"watermelon\":{\"phvalue\":\"6.429788073\",\"temp\":\"26.80750629\", \"humidity\":\"88.22874955\", \"rainfall\":\"58.79889057\", \"src\":\"hhtttp....\"},\"Tobacco\":{\"phvalue\":\"5.901895741\",\"temp\":\"26.57256307\", \"humidity\":\"62.94940697\", \"rainfall\":\"77.79486786\", \"src\":\"hhtttp....\"},\"Chickpea\":{\"phvalue\":\"6.391173589\",\"temp\":\"18.86805647\", \"humidity\":\"15.65809214\", \"rainfall\":\"88.51048983\", \"src\":\"hhtttp....\"},\"Moth Beans\":{\"phvalue\":\"4.61136408\",\"temp\":\"28.09568993\", \"humidity\":\"60.9835384\", \"rainfall\":\"33.84110759\", \"src\":\"hhtttp....\"},\"Mung Bean\":{\"phvalue\":\"27.43329405\",\"temp\":\"7.18530147\", \"humidity\":\"87.80507732\", \"rainfall\":\"54.73367631\", \"src\":\"hhtttp....\"},\"Coconut\":{\"phvalue\":\"5.860740481\",\"temp\":\"25.84726298\", \"humidity\":\"90.92669463\", \"rainfall\":\"147.8888994\", \"src\":\"hhtttp....\"},\"Peas\":{\"phvalue\":\"6.219809112\",\"temp\":\"18.38433704\", \"humidity\":\"13.627536\", \"rainfall\":\"46.1097405\", \"src\":\"hhtttp....\"},\"rice\":{\"phvalue\":\"6.502985292\",\"temp\":\"20.87974371\", \"humidity\":\"82.00274423\", \"rainfall\":\"202.9355362\", \"src\":\"https://firebasestorage.googleapis.com/v0/b/kisan-udyog-fd4a7.appspot.com/o/rice.png?alt=media&token=704cc1d8-c326-4ef7-8ce2-f45371244bf7\"},\"apple\":{\"phvalue\":\"6.75020529\",\"temp\":\"24.84063998\", \"humidity\":\"60.09116626\", \"rainfall\":\"48.77790371\", \"src\":\"hhtttp....\"},\"Lentil\":{\"phvalue\":\"7.352401887\",\"temp\":\"23.66457347\", \"humidity\":\"81.69105088\", \"rainfall\":\"99.36898373\", \"src\":\"hhtttp....\"},\"Cotton\":{\"phvalue\":\"7.929240731\",\"temp\":\"27.8558593\", \"humidity\":\"76.91493565\", \"rainfall\":\"154.9011463\", \"src\":\"hhtttp....\"},\"Sugarcane\":{\"phvalue\":\"4.634975125\",\"temp\":\"26.64588548\", \"humidity\":\"70.80010239\", \"rainfall\":\"361.1434598\", \"src\":\"hhtttp....\"},\"Rubber\":{\"phvalue\":\"7.397190844\",\"temp\":\"32.47648301\", \"humidity\":\"64.34848735\", \"rainfall\":\"65.820457\", \"src\":\"hhtttp....\"},\"Black gram\":{\"phvalue\":\"3.5\",\"temp\":\"35\", \"humidity\":\"45\", \"rainfall\":\"456\", \"src\":\"hhtttp....\"}}";
    private static final String TAG = "CustomPrediction";
    Interpreter interpreter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_prediction);
        getSupportActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .55));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        getWindow().setAttributes(params);

        temp=findViewById(R.id.temp);
        humid=findViewById(R.id.humid);
        rain=findViewById(R.id.rain);
        ph=findViewById(R.id.ph);
        predict=findViewById(R.id.predict);
        label=findViewById(R.id.label);
        srcUrl=findViewById(R.id.image);

        try {
            interpreter = new Interpreter(loadModelFile(),null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        close=findViewById(R.id.closess);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t= Float.parseFloat(temp.getText().toString());
                p= Float.parseFloat(ph.getText().toString());
                h= Float.parseFloat(humid.getText().toString());
                r= Float.parseFloat(rain.getText().toString());

                String f=doInference(t,h, p,r);
                label.setText(f);
                jsonParser(f);
            }
        });


    }


    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor assetFileDescriptor = getApplicationContext().getAssets().openFd("tfmodels.tflite");
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


    public void jsonParser(String g){
        try {
            JSONObject obj = new JSONObject(JSON_STRING);
            JSONObject dataset = obj.getJSONObject(g);
            String imgUrls =  dataset.getString("src");
            Picasso.with(getApplicationContext()).load(imgUrls).into(srcUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
