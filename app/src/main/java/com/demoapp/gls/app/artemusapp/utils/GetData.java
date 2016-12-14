package com.demoapp.gls.app.artemusapp.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.gls.app.demo.BaseActivity;
/**
 * Created by Santosh on 06-Oct-15.
 */
public class GetData extends AsyncTask {
    String url;
    String result = "";
    String callFor = "";
    String extendedUrl = "";
    BaseActivity activity;
    ProgressDialog progressDialog;

    public GetData(BaseActivity activity, String callFor, String extendedUrl){
        this.activity = activity;
        this.callFor = callFor;
        this.extendedUrl = extendedUrl;
        url = createURL(callFor);
    }

    private String createURL(String callFor) {
        url = Constants.BASE_URL;
        if(callFor==CallFor.HOME_DATA){
            url = url+URL.HOME_DATA+extendedUrl;
        }


        return url;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity,"","Loading...");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("URL ===>",url);
        try {
            result = ServerConnection.giveResponse(url,"");
        } catch (Exception e){
            Log.e("Error doInBackground",e.toString());
            e.printStackTrace();
        }
        Log.e("Response ===>",result);
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        activity.onGetResponse(result,callFor);
    }
}
