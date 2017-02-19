package in.eloksolutions.ayyappa.ayyappapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import in.eloksolutions.ayyappa.ayyappapp.asynctask.DeekshaUserTask;
import in.eloksolutions.ayyappa.ayyappapp.util.Config;
import in.eloksolutions.ayyappa.ayyappapp.util.DateAndTimePicker;

/**
 * Created by welcome on 12/15/2016.
 */




    public class DeekshaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText description;
    TextView fdate, txtdate;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.shedule);
        ImageView shedule=(ImageView) findViewById(R.id.button);
        final TextView edate=(TextView) findViewById(R.id.txtdate);
        final TextView tdate=(TextView) findViewById(R.id.fdate);
        final TextView desc=(TextView) findViewById(R.id.description);

        final Context ctx = this;
        SharedPreferences preferences = getSharedPreferences(in.eloksolutions.ayyappa.ayyappapp.util.Config.Member_ID, MODE_PRIVATE);
       final String memId=preferences.getString("memId", null);
        shedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enddate=edate.getText().toString();
                String txdate=tdate.getText().toString();
                String descrip=desc.getText().toString();
                try {
                    String result=new DeekshaUserTask(ctx,txdate,enddate,descrip,memId).execute().get();
                    SharedPreferences preferences = ctx.getSharedPreferences(Config.Member_ID, ctx.MODE_PRIVATE);
                    SharedPreferences.Editor edit;
                    edit= preferences.edit();
                    if(result!=null) {
                        String id = result;
                        edit.putString("startDate", txdate);
                        edit.putString("endDate", enddate);
                        edit.commit();
                    }
                    Intent feedbaciInt = new Intent(ctx, CardViewActivity.class);
                    ctx.startActivity(feedbaciInt);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });
            fdate = (TextView) findViewById(R.id.fdate);
            fdate.setText("" + DateFormat.format(" dd-MM-yyyy", System.currentTimeMillis()));
        txtdate = (TextView) findViewById(R.id.txtdate);
        txtdate.setText("" + DateFormat.format(" dd-MM-yyyy", System.currentTimeMillis()));
            description = (EditText) findViewById(R.id.description);

        fdate.setOnClickListener(this);
        txtdate.setOnClickListener(this);
        description.setOnClickListener(this);


}
    public void onClick(View v) {

        if (v == fdate) {
            DateAndTimePicker.datePickerDialog(this, fdate);

        }
        if (v == txtdate) {
            DateAndTimePicker.datePickerDialog(this, txtdate);
        }


    }


}



