package in.eloksolutions.ayyappa.ayyappapp.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import in.eloksolutions.ayyappa.ayyappapp.MyRecyclerViewAdapter;
import in.eloksolutions.ayyappa.ayyappapp.R;
import in.eloksolutions.ayyappa.ayyappapp.beans.EventDTO;
import in.eloksolutions.ayyappa.ayyappapp.util.DataObject;
import in.eloksolutions.ayyappa.ayyappapp.util.RestServices;

public class GetContacts extends AsyncTask<String, Void, String> {
    private Context mcontext;
    private ProgressDialog progress;
    String surl;
    RecyclerView rvPadi;

    public GetContacts(Context mcontext, String surl, RecyclerView rvPadi) {
        this.mcontext = mcontext;
        this.surl=surl;
        this.rvPadi=rvPadi;
    }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(mcontext);
            progress.setMessage("Loading...");
            progress.show();
        }
        protected String doInBackground(String... urls) {
            URL url = null;
            try {
                System.out.println("Connection to url ................." + surl);
                url = new URL(surl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return RestServices.GET(url);
        }
        protected void onPostExecute(String result) {
            System.out.println("Get Events Result is "+result);
            progress.dismiss();
        }
}
