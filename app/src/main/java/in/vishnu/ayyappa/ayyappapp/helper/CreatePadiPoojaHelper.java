package in.vishnu.ayyappa.ayyappapp.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.URL;

import in.vishnu.ayyappa.ayyappapp.beans.EventDTO;
import in.vishnu.ayyappa.ayyappapp.util.RestServices;

public class CreatePadiPoojaHelper {
    private Context mcontext;
    public CreatePadiPoojaHelper(Context mcontext) {
        this.mcontext = mcontext;
    }

    public class CreateEvent extends AsyncTask<String, Void, String> {
        // Call after onPreExecute method
        URL url;
        EventDTO eventDTO;
        private ProgressDialog progress;
        String surl;

        public CreateEvent(EventDTO eventDTO, String surl) {
            this.eventDTO = eventDTO;
            this.surl = surl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(mcontext);
            progress.setMessage("Loading...");
            progress.show();
        }

        protected String doInBackground(String... urls) {
            String json = "";
            try {
                System.out.println("Connection to url ................." + surl);
                url = new URL(surl);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("eventName", eventDTO.getEventName());
                jsonObject.accumulate("fromTime", "12/10/2016 12:00:00");
                jsonObject.accumulate("toTime", "12/10/2016 12:00:00");
                jsonObject.accumulate("location", eventDTO.getLocation());
                jsonObject.accumulate("desc", eventDTO.getDescription());
                jsonObject.accumulate("pincode","45454545");
                jsonObject.accumulate("owner", 1);
                jsonObject.accumulate("ownerName", "Suresh");
                jsonObject.accumulate("city","Hyderabad");
                jsonObject.accumulate("state", "TS");
                jsonObject.accumulate("areaName", "Mkj");
                json = jsonObject.toString();
                System.out.println("Json is" + json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String result= RestServices.POST(url, json);
            System.out.println("Response  is" + result);
            return result;
        }

    }
}
