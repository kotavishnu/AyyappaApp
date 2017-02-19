package in.vishnu.ayyappa.ayyappapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import in.vishnu.ayyappa.ayyappapp.helper.GetContacts;
import in.vishnu.ayyappa.ayyappapp.util.Config;

/**
 * Created by welcome on 1/4/2017.
 */

public class ContactActivity extends AppCompatActivity {
    Context context;
    String memId,memName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactactivity);
        SharedPreferences preferences = getSharedPreferences(Config.Member_ID, Context.MODE_PRIVATE);
        memId = preferences.getString("memId", null);
        memName= preferences.getString("memName", null);
        context=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView rvPadi = (RecyclerView) findViewById(R.id.rvContacts);
        rvPadi.setHasFixedSize(true);
        LinearLayoutManager lmPadi = new LinearLayoutManager(this);
        rvPadi.setLayoutManager(lmPadi);
        String url= in.vishnu.ayyappa.ayyappapp.util.Config.SERVER_URL+"get_contacts.php?memId="+memId;
        GetContacts getContacts=new GetContacts(context,url,rvPadi);
        getContacts.execute();

    }
    }




