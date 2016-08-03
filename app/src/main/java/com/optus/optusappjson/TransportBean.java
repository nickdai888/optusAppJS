package com.optus.optusappjson;

/**
 * Created by nick on 16/8/3.
 */

public class TransportBean {

    public static class Fromcentral{
        String car;
        String train;
    }

    public static class Location{
        double latitude;
        double longitude;
    }

    public int id;
    public String name;
    public Fromcentral fromcentral;
    public Location location;
}
