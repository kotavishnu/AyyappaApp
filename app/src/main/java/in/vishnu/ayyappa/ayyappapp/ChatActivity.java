package in.vishnu.ayyappa.ayyappapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.vishnu.ayyappa.ayyappapp.beans.ChatMsg;
import in.vishnu.ayyappa.ayyappapp.beans.EventDTO;
import in.vishnu.ayyappa.ayyappapp.util.Config;
import in.vishnu.ayyappa.ayyappapp.util.Message;
import in.vishnu.ayyappa.ayyappapp.util.URLUtil;

public class ChatActivity extends AppCompatActivity {
    private List<Message> listMessages;
    private ChatArrayAdapter chatArrayAdapter;
    private EditText chatmsg;
    public static String TAG="ChatActivity.class";
    public String memId;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        Log.i(TAG,"Starting Chat version X0003 ......");
        ListView listView = (ListView) findViewById(R.id.lvChat);
        Intent intent=getIntent();
        final String contactId=intent.getStringExtra("contactId");
        Log.i(TAG,"Contact id is "+contactId);
        // listMessages = new ArrayList<>(aController.getMessageVO().getEventMessage(Integer.parseInt(id)));
        listMessages=new ArrayList<>();
        chatArrayAdapter = new ChatArrayAdapter(this, listMessages,"memId");
        listView.setAdapter(chatArrayAdapter);
        System.out.println("before click");
        ImageView sendmsg=(ImageView) findViewById(R.id.btSend);
        chatmsg=(EditText) findViewById(R.id.etMessage);
        ctx=this;
        SharedPreferences preferences=getSharedPreferences(Config.Member_ID, MODE_PRIVATE);
        memId=preferences.getString("memId", null);
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=chatmsg.getText().toString();
                if (msg != null && msg.trim().length() > 0) {
                    System.out.println("Button is clicked sending message");
                    Date d = new Date();
                    Message m = new Message("1", msg, d, "", "memId", "name");
                    listMessages.add(m);
                    chatArrayAdapter.notifyDataSetChanged();
                    chatmsg.setText("");
                    AsyncTaskRunner runner = new AsyncTaskRunner(msg,memId,Config.MEMBER_TOPIC+contactId);
                    Log.i(TAG,"Message is "+msg);
                    runner.execute(msg);

                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(message,
                new IntentFilter("in.vishnu.ayyappa.SEND_MSG"));
    }

    private BroadcastReceiver message = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("message");
            Log.d(TAG, "Received message "+msg);
            Gson gson = new Gson();
            Type type = new TypeToken<ChatMsg>() { }.getType();
            ChatMsg fromJson = gson.fromJson(msg, type);
            Log.d(TAG, "Received member "+fromJson.memId+" current user is"+memId);
            if(memId.equals(fromJson.memId)) return;
            Date d = new Date();
            Message m = new Message("1", fromJson.message, d, "", fromJson.memId, "name");
            listMessages.add(m);
            chatArrayAdapter.notifyDataSetChanged();
        }
    };

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        private String memId;
        private String gcmId;
        String msg;
        AsyncTaskRunner(String msg, String memId, String gcmId){
            this.msg=msg;
            this.memId=memId;
            this.gcmId=gcmId;
        }
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
                // Do your long operations here and return the result

            Log.i(TAG,"Message in async task is "+msg);
            String result="Server did not responded";
            try {
                result=URLUtil.postToDevice(msg,memId,gcmId);
            } catch (Exception e) {
               Log.e(TAG,"Error while sending message ",e);
            }
            // Sleeping for given time period

            return resp;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            //finalResult.setText(result);
            chatmsg.setText("");
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            chatmsg.setText("Sending...");
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
