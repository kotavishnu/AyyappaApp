package in.vishnu.ayyappa.ayyappapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

import in.vishnu.ayyappa.ayyappapp.util.Config;


/**
 * Created by welcome on 12/22/2016.
 */

public class SplashActivity extends AppCompatActivity {
    String id,otp;

    Context ctx=this;


        // Splash screen timer
        private static int SPLASH_TIME_OUT = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splashactivity);
            MediaPlayer sp = MediaPlayer.create(getBaseContext(), R.raw.splash1);
            sp.start();
            ctx=this;
            SharedPreferences preferences=getSharedPreferences(Config.Member_ID, Context.MODE_PRIVATE);
            id=preferences.getString("memId","");
            otp=preferences.getString("otp","");
            System.out.println("id in splash"+id+"and otp"+otp);

            if(id!=null && id.trim().length()>0)
                FirebaseMessaging.getInstance().subscribeToTopic("mem"+id);

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    Intent mainIntent=null;
                    if(id==null || id.equals("")){
                        mainIntent = new Intent(SplashActivity.this, RegisterFormActivity.class);
                    } else {
                        mainIntent = new Intent(SplashActivity.this,CardViewActivity.class);
                    }
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    startActivity(mainIntent);
                    SplashActivity.this.finish();
                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }

