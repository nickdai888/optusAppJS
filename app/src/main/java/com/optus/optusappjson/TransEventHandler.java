package com.optus.optusappjson;

import android.content.Intent;
import android.view.View;

/**
 * Created by nick on 16/8/8.
 */
public class TransEventHandler {
    private TransUserData location;

    public TransEventHandler(TransUserData location) {
        this.location = location;
    }
    public void handleNavClick(View view) {
        Intent intentMap = new Intent(view.getContext(), MapsActivity.class);
        intentMap.putExtra("latitude", location.getLantitude());
        intentMap.putExtra("longitude", location.getLongitude());
        view.getContext().startActivity(intentMap);
    }
}
