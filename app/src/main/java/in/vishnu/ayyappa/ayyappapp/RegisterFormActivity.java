package in.vishnu.ayyappa.ayyappapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import in.vishnu.ayyappa.ayyappapp.asynctask.RegisterUserTask;
import in.vishnu.ayyappa.ayyappapp.util.CheckInternet;
import in.vishnu.ayyappa.ayyappapp.util.Config;

public class RegisterFormActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "RegisterFormActivity";
    GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 2;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Context ctx;
    String address;
    TextView etLocation;
    TextView etCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registerform);
        ctx = this;
        Button register=(Button) findViewById(R.id.butRegister);
        final TextView etName=(TextView) findViewById(R.id.etName);
        final TextView etPhoneNumber=(TextView) findViewById(R.id.etPhoneNumber);
        final TextView etEmailid=(TextView) findViewById(R.id.etEmailid);
        etLocation=(TextView) findViewById(R.id.etLocation);
        etCity=(TextView) findViewById(R.id.etCity);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        System.out.println("android version " + currentapiVersion);
       if (currentapiVersion >= android.os.Build.VERSION_CODES.M){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            Toast.makeText(this, "oncreate checking permissions", Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            Location myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (myLocation != null) {
                Toast.makeText(this, "onConnected lcations" + myLocation.getLatitude(), Toast.LENGTH_LONG);
                Log.d(TAG, "On create Latitude: onConnected " + String.valueOf(myLocation.getLatitude()));
                Log.d(TAG, "On create Latitude: onConnected " + String.valueOf(myLocation.getLongitude()));

                address = getAddress(myLocation);

                if(address!=null){
                    etLocation.setText(address);
                    etCity.setText(getCity(myLocation));
                }

            }
        }catch (Exception e){
            Log.i(TAG,"Error while getting address ",e);
        }


        final Context ctx=this;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String name=etName.getText().toString();
                 String phonenumber=etPhoneNumber.getText().toString();
                 String emailid=etEmailid.getText().toString();
                 String location=etLocation.getText().toString();
                String city=etCity.getText().toString();
                String result= null;
                try {
                    result = new RegisterUserTask(ctx,name,phonenumber,emailid,location,city).execute().get();
                    if(result.indexOf("Error")>=0){
                        Toast.makeText(ctx, "Please enter valid data", Toast.LENGTH_LONG).show();

                        return;
                    }
                    SharedPreferences preferences = ctx.getSharedPreferences(Config.Member_ID, ctx.MODE_PRIVATE);
                    SharedPreferences.Editor edit;
                    edit= preferences.edit();
                    if(!result.equals("")) {
                        String id = result;
                        edit.putString("memId", id);
                        edit.putString("name", name);
                        edit.putString("mobile", phonenumber);
                        edit.putString("email", emailid);
                        edit.putString("area_name",location);
                        edit.putString("city",city);
                        edit.commit();
                    }else{
                        CheckInternet.showAlertDialog(ctx, "Invalid Details",
                                "Error while registering the user please send feedback form with your details.");
                    }

                    Toast.makeText(ctx, "Swamy Registration Successfull", Toast.LENGTH_LONG).show();
                    Intent cardViewMain = new Intent(ctx, CardViewActivity.class);
                    ctx.startActivity(cardViewMain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Toast.makeText(this, "onConnected In Connected method", Toast.LENGTH_LONG);
        // permission has been granted, continue as usual
        // int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // if (currentapiVersion >= android.os.Build.VERSION_CODES.M){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            Toast.makeText(this, "oncreate checking permissions", Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        //}

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (myLocation != null) {
            Log.d(TAG, "onConnected Latitude: onConnected " + String.valueOf(myLocation.getLatitude()));
            Log.d(TAG, "onConnected Latitude: onConnected " + String.valueOf(myLocation.getLongitude()));

           address= getAddress(myLocation);
            if(address!=null){
              etLocation.setText(address);
              etCity.setText(getCity(myLocation));
            }
        }


    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Location", "Connection suspended");
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();

    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("In Connected method failed");
        Log.e("Location", "Connection Failed");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if(myLocation!=null) {
                        Toast.makeText(this, "permision  lcations" + myLocation.getLatitude(), Toast.LENGTH_LONG);
                        Log.d(TAG, "Request permision Latitude: permision "+String.valueOf(myLocation.getLatitude()));
                        Log.d(TAG, "Request permision Latitude: permision "+String.valueOf(myLocation.getLongitude()));
                        getAddress(myLocation);
                         myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (myLocation != null) {
                            Log.d(TAG, "Request permision Latitude: onConnected " + String.valueOf(myLocation.getLatitude()));
                            Log.d(TAG, "Request permision Latitude: onConnected " + String.valueOf(myLocation.getLongitude()));

                            address= getAddress(myLocation);
                            if(address!=null){
                                etLocation.setText(address);
                                etCity.setText(getCity(myLocation));
                            }
                        }
                    }

                    Toast.makeText(this, "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this, "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private String getAddress(Location myLocation) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    myLocation.getLatitude(),
                    myLocation.getLongitude(),
                    // In this sample, get just a single address.
                    1);
            Address address = addresses.get(0);
            StringBuilder addressFragments = new StringBuilder();
            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.append(address.getAddressLine(i)+"|");
            }
            Log.d(TAG, "address list "+addressFragments);
            return addressFragments.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCity(Location myLocation) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    myLocation.getLatitude(),
                    myLocation.getLongitude(),
                    // In this sample, get just a single address.
                    1);
            Address address = addresses.get(0);
            StringBuilder addressFragments = new StringBuilder();
            if(address!=null && address.getMaxAddressLineIndex()>=2) {
                String addr4 = address.getAddressLine(2);
                Log.i(TAG,"Address 4 "+addr4);

                String city="";
                if(addr4.indexOf(",")>0)
                    city=addr4.substring(0,addr4.indexOf(","));
                return city;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
