package com.demoapp.gls.app.artemusapp.utils;

import android.util.Log;

import com.demoapp.gls.app.artemusapp.response.Response;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class ServerConnection {
    public static final int HTTP_TIMEOUT = 2 * 60 * 1000;
    private static HttpClient mHttpClient;
    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }
    public static String executeHttpPost(String url,ArrayList<NameValuePair> postParameters)throws Exception{
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println(""+e);
                }
            }
        }
    }
    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String result = sb.toString();
            result=result.trim();
            //result= result.replaceAll("\\s+","");
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println(""+e);
                }
            }
        }
    }
    public static String giveResponse(String url,String data){
        try{
            if(data==null || data.equals("")) {
                String response = executeHttpGet(url);
                try{
                    new Gson().fromJson(response, Response.class);
                    return response;
                } catch (Exception e){
                    Response resp = new Response();
                    resp.setCode(Constants.SERVER_ERROR);
                    resp.setMessage("Error in server connection ==> "+e);
                    return new Gson().toJson(resp);
                }
            } else {
                ArrayList<NameValuePair> postParameters = new ArrayList<>();
                postParameters.add(new BasicNameValuePair("data", data));
                String response = executeHttpPost(url, postParameters);
                try{
                    new Gson().fromJson(response, Response.class);
                    return response;
                } catch (Exception e){
                    Response resp = new Response();
                    resp.setCode(Constants.SERVER_ERROR);
                    resp.setMessage("Error in server connection ==> "+e);
                    return new Gson().toJson(resp);
                }

            }
        }catch(Exception e){
            Response resp = new Response();
            resp.setCode(Constants.SERVER_ERROR);
            resp.setMessage("Error in server connection ==> "+e);
            return new Gson().toJson(resp);
        }
    }
}