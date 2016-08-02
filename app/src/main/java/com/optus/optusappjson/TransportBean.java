package com.optus.optusappjson;

/**
 * Created by nick on 16/8/3.
 */

public class TransportBean {
    public int id;
    public String name;

    public Fromcentral fromcentral;
    public Location location;
    public static class Fromcentral{
        String car;
        String train;
    }

    public static class Location{
        float latitude;
        float longitude;
    }

}
