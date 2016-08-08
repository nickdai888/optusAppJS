package com.optus.optusappjson;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.optus.optusappjson.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private static String URL;
    private Handler myHandler;
    //get from server
    private TransportBean transBeans[] = null;
    private CurrentStatus mStatus = CurrentStatus.FETCHING_JSON;
    private int choosedItemIndex;
    private TransUserData location;
    private ActivityMainBinding binder;

    enum CurrentStatus{
        FETCHING_JSON,
        GET_JSON_SUCCESSFUL,
        NETWORK_UNREACHABLE,
        OTHER_ERROR,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatus = CurrentStatus.FETCHING_JSON;
        URL = getString(R.string.optus_url);
        location = new TransUserData(this);

        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        location = new TransUserData(this);
        binder.setUser(location);
        binder.setHandlers(new TransEventHandler(location));

        initHandle();
        new TransportAsyncTask(myHandler).execute(URL);
    }

    private void initSpinner(){
        Spinner mSpinner = binder.spinner;
        ArrayAdapter<String> adapter;
        String names[] =  new String[transBeans.length];
        for(int i = 0; i < transBeans.length; i++) {
            names[i] =  transBeans[i].name;
        }
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }


    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            int selectedIndex = arg2;
            choosedItemIndex =  selectedIndex;
            location.setTransInfoText(getTransText());
            setLocation();
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void initHandle(){
        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                location.setErrState(false);
                switch (msg.what) {
                    case 0: //failed
                        mStatus = CurrentStatus.OTHER_ERROR;
                        location.setErrState(true);
                        break;
                    case 1: //successful
                        TransportBean trans[] = (TransportBean[])msg.obj;
                        transBeans = trans;
                        mStatus = CurrentStatus.GET_JSON_SUCCESSFUL;
                        location.setErrState(false);
                        break;
                }
                updateUI();
                super.handleMessage(msg);
            }
        };
    }

    private void updateUI(){
        switch(mStatus){
            case GET_JSON_SUCCESSFUL:
                initSpinner();
                location.setTransInfoText(getTransText());
                setLocation();
                break;
            case  OTHER_ERROR:
            case NETWORK_UNREACHABLE:
            case FETCHING_JSON:
                break;
        }
    }

    private void setLocation(){
        location.setLantitude(transBeans[choosedItemIndex].location.latitude);
        location.setLongitude(transBeans[choosedItemIndex].location.longitude);
    }

    private String getTransText(){
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
       return newContent;
    }


}
