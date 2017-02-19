package in.eloksolutions.ayyappa.ayyappapp.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;

import in.eloksolutions.ayyappa.ayyappapp.CardViewActivity;

import in.eloksolutions.ayyappa.ayyappapp.util.CheckInternet;
import in.eloksolutions.ayyappa.ayyappapp.util.Config;
import in.eloksolutions.ayyappa.ayyappapp.util.RestServices;

public class RegisterUserTask extends AsyncTask<String, Void, String> {
        // Call after onPreExecute method
        String TAG="RegisterUserTask";
        URL url;
        String name;
    String phoneNumber;
    String emailId;
    String adress;
    String city;
    Context context;
        private ProgressDialog progress;



    public RegisterUserTask(Context context, String name, String phoneNumber, String emailId, String adress, String city){
            this.name=name;
        this.phoneNumber=phoneNumber;
        this.emailId=emailId;
        this.adress=adress;
        this.city=city;
        this.context=context;
        }
        protected void onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setMessage("Loading...");
            progress.show();
        }
        protected String doInBackground(String... urls) {
            String json = "";
            try {
                String surl = Config.SERVER_URL + "mem_save.php";
                System.out.println("Connection to url ................." + surl);
                url = new URL(surl);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", name);
                jsonObject.accumulate("mobile", phoneNumber);
                jsonObject.accumulate("email", emailId);
                jsonObject.accumulate("area_name", adress);
                jsonObject.accumulate("city", city);
                json = jsonObject.toString();
                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return RestServices.POST(url, json);
        }
    protected void onPostExecute(String result) {
        Log.i(TAG,"Response form server is "+result);
        progress.dismiss();

    }
}

