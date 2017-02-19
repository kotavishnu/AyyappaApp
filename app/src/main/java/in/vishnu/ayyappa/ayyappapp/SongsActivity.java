package in.vishnu.ayyappa.ayyappapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import in.vishnu.ayyappa.ayyappapp.util.DataObject;

public class SongsActivity extends AppCompatActivity {
    public static int [] songImages ={R.drawable.ayy1,R.drawable.ayy2,R.drawable.ayy3,R.drawable.ayy4,R.drawable.ayy5,R.drawable.ayy};
    public static String [] songNames={"Maladharanam Niyamala Toranam","Harivarasanam Viswamohanam","Baghavan Saranam","Ayyappa4","Ayyappa5","Ayyappa6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView rvSongs = (RecyclerView) findViewById(R.id.rvSongs);
        rvSongs.setHasFixedSize(true);
        LinearLayoutManager lmSongs = new LinearLayoutManager(this);
        rvSongs.setLayoutManager(lmSongs);

        rvSongs.setAdapter(new MyRecyclerViewAdapter(getSongsDataSet()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private ArrayList<DataObject> getSongsDataSet() {
        ArrayList results = new ArrayList<DataObject>();

        for (int index = 0; index < songNames.length; index++) {
            DataObject obj = new DataObject(songNames[index],"Movie ",songImages[index]);
            results.add(index, obj);
        }
        return results;
    }
}
