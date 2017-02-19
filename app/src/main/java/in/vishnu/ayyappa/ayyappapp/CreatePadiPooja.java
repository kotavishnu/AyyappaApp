package in.vishnu.ayyappa.ayyappapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import in.vishnu.ayyappa.ayyappapp.beans.EventDTO;
import in.vishnu.ayyappa.ayyappapp.helper.CreatePadiPoojaHelper;
import in.vishnu.ayyappa.ayyappapp.util.CheckInternet;
import in.vishnu.ayyappa.ayyappapp.util.Config;
import in.vishnu.ayyappa.ayyappapp.util.Validation;


public class CreatePadiPooja extends AppCompatActivity implements View.OnClickListener {
    EditText event_name, location, description;
    TextView fdate, fromTime, toDate;
    String memId,city,state,country,areaName,ownerName,pincode;

    private static final String TAG = "CreatePadiPooja";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml
        setContentView(R.layout.activity_create_padipooja);
        Button createhere=(Button) findViewById(R.id.butCreateHere);

        final Context ctx = this;
        createhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eventid=saveEventToServer();
               /* Intent padipoojaView = new Intent(ctx, PadiPoojaView.class);
                padipoojaView.putExtra("eventid",eventid);
                   startActivity(padipoojaView);*/

            }
        });

        // Initializes TransferUtility, always do this before using it.

        event_name = (EditText) findViewById(R.id.event_name);
        fdate = (TextView) findViewById(R.id.fdate);
        fdate.setText("" + DateFormat.format("dd/MM/yyyy", System.currentTimeMillis()));
        toDate = (TextView) findViewById(R.id.fromTime);
        toDate.setText("" + DateFormat.format("hh:mm a", System.currentTimeMillis()));
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        fdate.setOnClickListener(this);
        toDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == fdate) {
            DateAndTimePicker.datePickerDialog(this, fdate);
        }
        if (v == toDate) {
            showToTimePicker();
        }

    }
    private void showToTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = DateAndTimePicker.getTimePickerDialog(hour, minute, this, toDate);
        mTimePicker.show();
    }
    private void showFromTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = DateAndTimePicker.getTimePickerDialog(hour, minute, this, fromTime);
        mTimePicker.show();
    }

    private String saveEventToServer() {
       EventDTO eventDTO = buildDTOObject();
        if (checkValidation()) {
            if (CheckInternet.checkInternetConenction(CreatePadiPooja.this)) {
                CreatePadiPoojaHelper createEventHelper = new CreatePadiPoojaHelper(CreatePadiPooja.this);
                String surl = Config.SERVER_URL + "event_save.php";
                try {
                    String eventid= createEventHelper.new CreateEvent(eventDTO, surl).execute().get();
                    return eventid;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                CheckInternet.showAlertDialog(CreatePadiPooja.this, "No Internet Connection",
                        "You don't have internet connection.");
            }
        }
        return null;
    }


    private EventDTO buildDTOObject() {
        EventDTO eventDTO = new EventDTO();
        String eventname = event_name.getText().toString();
        eventDTO.setEventName(eventname);
        String s1=fdate.getText().toString()+" "+fdate.getText().toString();
       // eventDTO.settodate(s1);
        String s=toDate.getText().toString()+"-"+toDate.getText().toString();
        System.out.println(s);
        eventDTO.setFtTime(s);
        String loc = location.getText().toString();
        eventDTO.setLocation(loc);
        String desc = description.getText().toString();
        eventDTO.setDescription(desc);
        eventDTO.setOwner(memId);
        eventDTO.setOwnerName(ownerName);
        eventDTO.setCity(city);
        eventDTO.setCountry(country);
        eventDTO.setState(state);
        eventDTO.setAreaName(areaName);
        eventDTO.setPincode(pincode);
        return eventDTO;
    }
    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(description)) ret = false;
        if (!Validation.hasText(location)) ret = false;
        if (!Validation.hasText(event_name)) ret = false;
        return ret;
    }

}
