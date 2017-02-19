package in.vishnu.ayyappa.ayyappapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
import java.util.ArrayList;

import in.vishnu.ayyappa.ayyappapp.util.DataObject;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
 
    public  class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
//        TextView dateTime;
        ImageView imageView;
 
        public DataObjectHolder(final View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
  //          dateTime = (TextView) itemView.findViewById(R.id.textView2);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imgPlay);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(LOG_TAG, "Adding Listener "+label.getText());
                    Intent chatIntent=new Intent(view.getContext(),ChatActivity.class);
                    int itemPosition = getAdapterPosition();
                    DataObject dataObject=mDataset.get(itemPosition);
                    Log.i(LOG_TAG,"Positing on item "+itemPosition+" data object "+dataObject);
                    chatIntent.putExtra("contactId",dataObject.getmText2());
                    view.getContext().startActivity(chatIntent);
                }
            });
        }
 
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
 
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
 
    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }
 
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);
 
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
 
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());
    //    holder.dateTime.setText(mDataset.get(position).getmText2());
        holder.imageView.setImageResource(mDataset.get(position).getImgResource());
    }
 
    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
 
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
 
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
 
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}