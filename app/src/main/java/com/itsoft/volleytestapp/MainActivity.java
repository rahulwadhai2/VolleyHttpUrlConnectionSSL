package com.itsoft.volleytestapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    private String url;
    ProgressDialog dialog;
    //SslSocketFactoryMethod sslSocketFactoryMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // sslSocketFactoryMethod = new SslSocketFactoryMethod(MainActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  urlCall();
                new AsyncTaskLoadTrainingNo(MainActivity.this).execute();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void urlCall() {

        HurlStack hurlStack = new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                try {


                    httpsURLConnection.setSSLSocketFactory(SslSocketFactoryMethod.getInstance(MainActivity.this).getSSLSocketFactory());
                    httpsURLConnection.setHostnameVerifier(SslSocketFactoryMethod.getInstance(MainActivity.this).getHostnameVerifier());

                    String auth = "1" +":"+"1";

                    String base64EncodedCredentials = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
                    httpsURLConnection.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
                    httpsURLConnection.setRequestProperty("branchCode", "1002");
                    httpsURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    httpsURLConnection.setRequestProperty("Accept", "application/json; charset=utf-8");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }
        };

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading please wait");
        dialog.setCancelable(true);
        dialog.show();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Log.e("response ", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Log.e("response ", String.valueOf(error.getMessage()));
                error.printStackTrace();
            }
        });

        final RequestQueue requestQueue = Volley.newRequestQueue(this, hurlStack);

        requestQueue.add(jsonObjectRequest);
    }


    private class AsyncTaskLoadTrainingNo extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;
        String _error;
        String _result;
        Context ctx;

        public AsyncTaskLoadTrainingNo(Context ctx) {
            this._error = "";
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading training no....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                _result = HttpClientWrapper.getResponseGET(url,SslSocketFactoryMethod.getInstance(MainActivity.this).getSSLSocketFactory(),
                        SslSocketFactoryMethod.getInstance(MainActivity.this).getHostnameVerifier());
                Log.e("Response:--->" ,_result);
            } catch (Exception ex) {
                _error = ex.getMessage();
            }

            return _result;
        }

        @Override
        protected void onPostExecute(String value) {
            super.onPostExecute(value);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }
}
