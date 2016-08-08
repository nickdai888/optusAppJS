package com.optus.optusappjson;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static String URL;
    private Handler myHandler;
    //get from server
    private TransportBean transBeans[] = null;
    private CurrentStatus mStatus = CurrentStatus.FETCHING_JSON;

    private Spinner mSpinner;
    private TextView mTextView;
    private TextView mTransTitleView;
    private Button mButton;
    private int choosedItemIndex;
// show err message
    private TextView mInfoTextView;

    enum CurrentStatus{
        FETCHING_JSON,
        GET_JSON_SUCCESSFUL,
        NETWORK_UNREACHABLE,
        OTHER_ERROR,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatus = CurrentStatus.FETCHING_JSON;
        URL = getString(R.string.optus_url);

        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0: //failed
                        mStatus = CurrentStatus.OTHER_ERROR;
                        break;
                    case 1: //successful
                        TransportBean trans[] = (TransportBean[])msg.obj;
                        transBeans = trans;
                        mStatus = CurrentStatus.GET_JSON_SUCCESSFUL;
                        break;
                }
                updateUI();
                super.handleMessage(msg);
            }
        };

        mSpinner = (Spinner)this.findViewById(R.id.spinner);
        mButton = (Button)this.findViewById(R.id.button);
        mTextView = (TextView)this.findViewById(R.id.transportInfo);
        mInfoTextView = (TextView)this.findViewById(R.id.errText);
        mTransTitleView = (TextView)this.findViewById(R.id.transportTitle);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
                intentMap.putExtra("latitude", transBeans[choosedItemIndex].location.latitude);
                intentMap.putExtra("longitude", transBeans[choosedItemIndex].location.longitude);
                startActivity(intentMap);
            }
        });
        new TransportAsyncTask(myHandler).execute(URL);
    }

    private void updateUI(){
        String errMsg = this.getString(R.string.conn_error_msg);
        switch(mStatus){
            case GET_JSON_SUCCESSFUL:
                showTransUIElements();
                updatePinner();
                break;
            case  OTHER_ERROR:
            case NETWORK_UNREACHABLE:
            case FETCHING_JSON:
                hidAllUIElements();
                mInfoTextView.setVisibility(View.VISIBLE);
                mInfoTextView.setText(errMsg);
                break;
        }
    }

    private void showTransUIElements(){
        mSpinner.setVisibility(View.VISIBLE);
        mButton.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        mTransTitleView.setVisibility(View.VISIBLE);
    }

    private void hidAllUIElements(){
        mSpinner.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);
        mTransTitleView.setVisibility(View.GONE);
        mInfoTextView.setVisibility(View.GONE);
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            int selectedIndex = arg2;
            choosedItemIndex =  selectedIndex;
            updateTextView();
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    private void updateTextView(){
        String carStr = "Car - " + transBeans[choosedItemIndex].fromcentral.car;
        String trainStr = "Train - " + transBeans[choosedItemIndex].fromcentral.train;
        String newContent = "";
        if((transBeans[choosedItemIndex].fromcentral.train == null ) ||
                (transBeans[choosedItemIndex].fromcentral.train.trim().length() == 0)){
            newContent =  carStr;
        }
        else{
            newContent  =  carStr + "\n" + trainStr;
        }
        mTextView.setText(newContent);
    }

    private void updatePinner(){
        ArrayAdapter<String> adapter;
        String names[] =  new String[transBeans.length];
        for(int i = 0; i < transBeans.length; i++) {
            names[i] =  transBeans[i].name;
        }
        mSpinner.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

  }


}
