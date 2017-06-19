package com.example.hppc.business;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hp pc on 02-06-2017.
 */

public class JSONfunctions
{
    public String login(String employee_id, String pass)
    {
        InputStream is = null;
        String result = "";
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://localhost:80/test/entry.php?");

            nameValuePairs.add(new BasicNameValuePair("emailid", employee_id));
            nameValuePairs.add(new BasicNameValuePair("password", pass));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            is = entity.getContent();



        }
        catch (Exception e)
        {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // convert response to string
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }

            is.close();
            result = sb.toString();

            Log.v("log","Result: " + result);
        }
        catch (Exception e)
        {
            Log.v("log", "Error converting result " + e.toString());
        }

        return result;
    }

}
