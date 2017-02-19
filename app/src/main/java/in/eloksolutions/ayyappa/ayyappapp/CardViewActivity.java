package in.eloksolutions.ayyappa.ayyappapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import in.eloksolutions.ayyappa.ayyappapp.beans.EventDTO;
import in.eloksolutions.ayyappa.ayyappapp.beans.MemberDTO;
import in.eloksolutions.ayyappa.ayyappapp.helper.GetContacts;
import in.eloksolutions.ayyappa.ayyappapp.helper.GetEvents;
import in.eloksolutions.ayyappa.ayyappapp.helper.MoviesRecyclerViewAdapter;
import in.eloksolutions.ayyappa.ayyappapp.helper.SlidingImage_Adapter;
import in.eloksolutions.ayyappa.ayyappapp.util.Config;
import in.eloksolutions.ayyappa.ayyappapp.util.DataObject;


public class CardViewActivity extends AppCompatActivity {


    ListView lv;
    Context context;
    MyRecyclerViewAdapter mAdapter;
    MoviesRecyclerViewAdapter moviesAdapter;
    ArrayList prgmName;
    MediaPlayer mediaPlayer;
    String TAG="CardViewActivity";
    int currentTrack = 0;
    private int [] songs ={R.raw.song1,R.raw.song2,R.raw.song3};
    boolean isPlayOrPause=true;

    public static int [] contactImages ={R.drawable.chat_icon,R.drawable.chat_icon,R.drawable.chat_icon,R.drawable.chat_icon,R.drawable.chat_icon,R.drawable.chat_icon};
    public static String [] contactNames={"Contact1","Contact2","Contact 3","Contact 4","Contact 5","Contact 6"};

    public static int [] songImages ={R.drawable.ayy1,R.drawable.ayy2,R.drawable.ayy3,R.drawable.ayy4,R.drawable.ayy5,R.drawable.ayy};
    public static String [] songNames={"Maladharanam Niyamala Toranam","Harivarasanam Viswamohanam","Baghavan Saranam","Ayyappa4","Ayyappa5","Ayyappa6"};
    public static int [] moviesImages ={R.drawable.ayy1,R.drawable.ayy2,R.drawable.ayy3,R.drawable.ayy4,R.drawable.ayy5,R.drawable.ayy};
    public static String [] moviesNames={"Ayyappa Swamy Janma Rahasyam Telugu Movie 2014","Ayyappa Swamy Mahatyam Full Movie | Sarath Babu | Silk Smitha | K Vasu | KV Mahadevan","Ayyappa Telugu Full Movie Exclusive - Sai Kiran, Deekshith","Ayyappa Swamy Mahatyam | Full Length Telugu Movie | Sarath Babu, Shanmukha Srinivas","Ayyappa Deeksha Telugu Full Movie | Suman, Shivaji","Ayyappa Swamy Janma Rahasyam Telugu Full Movie"};
    RecyclerView mRecyclerView;
    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        context=this;
        SharedPreferences preferences = getSharedPreferences(Config.Member_ID, MODE_PRIVATE);
        String deekshaStartDate=preferences.getString("startDate",null);
        String deekshaEndDate=preferences.getString("endDate",null);
        String memId = preferences.getString("memId", null);

        subscribeToTopic(memId);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        moviesAdapter = new MoviesRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(moviesAdapter);

        RecyclerView rvSongs = (RecyclerView) findViewById(R.id.rvSongs);
        rvSongs.setHasFixedSize(true);
        LinearLayoutManager lmSongs = new LinearLayoutManager(this);
        rvSongs.setLayoutManager(lmSongs);
        mAdapter=new MyRecyclerViewAdapter(getSongsDataSet());
        rvSongs.setAdapter(mAdapter);

        RecyclerView rvContact = (RecyclerView) findViewById(R.id.rvContacts);
        rvContact.setHasFixedSize(true);
        LinearLayoutManager lmContact = new LinearLayoutManager(this);
        rvContact.setLayoutManager(lmContact);

        final ImageView imgDeeksha=(ImageView) findViewById(R.id.imgDeeksha);
        final TextView tvDays=(TextView) findViewById(R.id.tvDays);

        String url= Config.SERVER_URL+"get_contacts.php?memId="+memId;
        GetContacts getContacts=new GetContacts(context,url,rvContact);
        String result="";
        try {
            result=getContacts.execute().get();
            updateContactList(rvContact, result);
        } catch (Exception e) {
            Log.e(TAG,"Error while getting contaccts ",e);
        }



        if(deekshaStartDate!=null){
            Log.i(TAG,"Deeksha start date"+deekshaStartDate);
            int diff=0,noOfDays=0;
            try {
                Date startDate=(new SimpleDateFormat("dd-MM-yyyy")).parse(deekshaStartDate);
                Date endDate=(new SimpleDateFormat("dd-MM-yyyy")).parse(deekshaEndDate);
                Calendar cal=Calendar.getInstance();
                Date today=cal.getTime();
                diff=daysBetween(startDate,today)+1;
                noOfDays=daysBetween(startDate,endDate)+1;
                Log.i(TAG,"Diff date is is "+diff);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            imgDeeksha.setVisibility(View.GONE);
            tvDays.setText(diff+"th Day of "+noOfDays+" days");
        }else{
            tvDays.setText("");
            imgDeeksha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, DeekshaActivity.class));
                }
            });
        }

        songPlayerSetup();

        final ImageView createPadipooja=(ImageView) findViewById(R.id.imgCreatePadi);
        createPadipooja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, CreatePadiPooja.class));
            }
        });

        final ImageView imgInvite=(ImageView) findViewById(R.id.imgInvite);
        imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "You are invited to experience Ayyappa Swamy app.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        RecyclerView rvPadi = (RecyclerView) findViewById(R.id.rvPadi);
        rvPadi.setHasFixedSize(true);
        LinearLayoutManager lmPadi = new LinearLayoutManager(this);
        rvPadi.setLayoutManager(lmPadi);

        String eventsUrl= Config.SERVER_URL+"getevents.php";
        GetEvents getEvents=new GetEvents(context,eventsUrl,rvPadi);
        getEvents.execute();

        final ImageView ivFull=(ImageView) findViewById(R.id.ivFull);
        ivFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent songsIntent = new Intent(context,SongsActivity.class);
                startActivity(songsIntent);
            }
        });
    }

    private void subscribeToTopic(String memId) {
        if(memId==null || memId.trim().length()==0)return;
        try {
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic(Config.MEMBER_TOPIC+memId);
            // [END subscribe_topics]
        }catch (Exception ex){
            Log.e(TAG,"Error while subscribing to Topic ",ex);
        }
    }


    private void updateContactList(RecyclerView rvContact, String result) {
        if (result!=null && result.trim().length()>0) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<MemberDTO>>() { }.getType();
            List<MemberDTO> fromJson = gson.fromJson(result, type);
            ArrayList results = new ArrayList<DataObject>();
             int i=0;
            for (MemberDTO event : fromJson) {
                String memberName = event.getMemberName();
                String memberId = event.getMemberId();
                System.out.println("event name "+memberId);
                DataObject obj = new DataObject(memberName,memberId, R.drawable.day);
                results.add(i, obj);
                i++;
            }
            MyRecyclerViewAdapter mAdapter = new MyRecyclerViewAdapter(results);
            rvContact.setAdapter(mAdapter);
        }
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void songPlayerSetup() {
        MediaPlayer.OnCompletionListener mediaListener = getOnCompletionListener();
        mediaPlayer = MediaPlayer.create(this, R.raw.song1);
        mediaPlayer.setOnCompletionListener(mediaListener);
        long finalTime = mediaPlayer.getDuration();

        final TextView tvSongName = (TextView) this.findViewById(R.id.tvSongName);

        final ImageView play = (ImageView) findViewById(R.id.ibplay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlayOrPause) {
                    mediaPlayer.start();
                    int timeElapsed = mediaPlayer.getCurrentPosition();
                    Log.i(TAG, "Time elapsed " + timeElapsed);
                    play.setImageResource(R.drawable.pause1);
                    isPlayOrPause=false;
                }else {
                    mediaPlayer.pause();
                    int timeElapsed = mediaPlayer.getCurrentPosition();
                    Log.i(TAG, "Time elapsed " + timeElapsed);
                    play.setImageResource(R.drawable.play);
                    isPlayOrPause=true;
                }
            }
        });

        ImageView next = (ImageView) findViewById(R.id.ibnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.release();
                    currentTrack++;
                    currentTrack=currentTrack%(songs.length);
                    Log.i(TAG,"CURRENT Track "+currentTrack);
                    setupMediaPlayer(tvSongName,songs[currentTrack],songNames[currentTrack]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        ImageView prev = (ImageView) findViewById(R.id.ibprev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.release();;
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    currentTrack--;
                    if(currentTrack<0)currentTrack=songs.length-1;
                    currentTrack=currentTrack%(songs.length);
                    Log.i(TAG,"CURRENT Track "+currentTrack);
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://"+ context.getPackageName() + "/raw/"+songs[currentTrack]));
                    tvSongName.setText(songNames[currentTrack]);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setupMediaPlayer(TextView tvSongName,int songResourceId,String songName) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context, Uri.parse("android.resource://"+ context.getPackageName() + "/raw/"+songResourceId));
        tvSongName.setText(songName);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
 
    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < moviesImages.length; index++) {
            DataObject obj = new DataObject(moviesNames[index],"Movie ",moviesImages[index]);
            results.add(index, obj);
        }
        return results;
    }



    private ArrayList<DataObject> getSongsDataSet() {
        ArrayList results = new ArrayList<DataObject>();

        for (int index = 0; index < songImages.length; index++) {
            DataObject obj = new DataObject(songNames[index],"Movie ",songImages[index]);
            results.add(index, obj);
        }
        return results;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_feedback:
                startActivity(new Intent(this, FeedBackForm.class));
                return true;
            case R.id.action_home:
                startActivity(new Intent(this, CardViewActivity.class));
               // startActivity(new Intent(this, CarousalActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    private MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                arg0.release();
                currentTrack++;
                currentTrack=currentTrack%(songs.length);
                Log.i(TAG,"CURRENT Track "+currentTrack);
                arg0 = MediaPlayer.create(getApplicationContext(), songs[currentTrack]);
                arg0.setOnCompletionListener(this);
                arg0.start();
            }
        };
    }


}