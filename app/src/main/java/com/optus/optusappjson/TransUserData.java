package com.optus.optusappjson;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by nick on 16/8/8.
 */

public class TransUserData extends BaseObservable {
    private Context context;
    private String locationNames[];
    private String transTitleText;
    private String transInfoText;
    private String errMessage;
    private String navigationButtonName;

    private double lantitude;
    private double longitude;

    private boolean errState;

    @Bindable
    public boolean isErrState() {
        return errState;
    }

    public void setErrState(boolean errState) {
        this.errState = errState;
        notifyPropertyChanged(BR.errState);
    }


    public double getLantitude() {
        return lantitude;
    }

    public void setLantitude(double lantitude) {
        this.lantitude = lantitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public TransUserData(Context ctx) {
        this.context = ctx;
        //init 3 fixed strings ,just for showcase, actually we could config them in xml files.
        errMessage = context.getString(R.string.conn_error_msg);
        navigationButtonName= context.getString(R.string.navi_button_name);
        transTitleText = context.getString(R.string.transport_title);

    }
@Bindable
    public String getNavigationButtonName() {
        return navigationButtonName;
    }

    public void setNavigationButtonName(String navigationButtonName) {
        this.navigationButtonName = navigationButtonName;
        notifyPropertyChanged(BR.navigationButtonName);
    }

    @Bindable
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
        notifyPropertyChanged(BR.errMessage);
    }
    @Bindable
    public String[] getLocationNames() {
        return locationNames;
    }

    public void setLocationNames(String[] locationNames) {
        this.locationNames = locationNames;
        notifyPropertyChanged(BR.locationNames);
    }

    public String getTransTitleText() {
        return transTitleText;
    }

    public void setTransTitleText(String transTitleText) {
        this.transTitleText = transTitleText;
    }
    @Bindable
    public String getTransInfoText() {
        return transInfoText;
    }

    public void setTransInfoText(String transInfoText) {
        this.transInfoText = transInfoText;
        notifyPropertyChanged(BR.transInfoText);
    }
}
