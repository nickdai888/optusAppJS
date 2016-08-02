package com.optus.optusappjson;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;


/**
 * Created by nick on 16/8/3.
 */

public class TransportAsyncTask extends AsyncTask<String, Void, TransportBean[]> {

    private Handler mHandler;
    public TransportAsyncTask(Handler mHandler) {
        super();
        this.mHandler = mHandler;
    }

    @Override
    protected TransportBean[] doInBackground(String... params) {
        try {
            TransportBean result[] =  getJsonData(params[0]);
            Message msg = Message.obtain();
            msg.obj = result;
            msg.what = 1;
            mHandler.sendMessage(msg);
            return result;
        } catch (IOException e) {
            mHandler.sendEmptyMessage(0);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(TransportBean transBeans[]) {
        super.onPostExecute(transBeans);

    }


    private TransportBean[] getJsonData(String url) throws IOException {
        String  jsonString = readStream(new URL(url).openStream());
        TransportBean[] foos = new Gson().fromJson(jsonString, TransportBean[].class);
        return foos;
    }

    private String readStream(InputStream is) {
        InputStreamReader isr;

        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            while((line = br.readLine()) != null) {
                result += line;
            }
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
