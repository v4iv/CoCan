package com.v4ivstudio.cocan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Vaibhav Sharma on 6/24/2015.
 **/
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public String usrnm;
    protected List<ParseObject> sStatus;
    public static String objectId;


    // Provide a suitable constructor
    public PostAdapter(List<ParseObject> status) {
        sStatus = status;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ParseObject statusUpdate = sStatus.get(position);
        //Username
        usrnm = statusUpdate.getString("Username");
        holder.usernameWall.setText(usrnm);

        //Status Update
        String updt = statusUpdate.getString("Status");
        holder.updateWall.setText(updt);

        //Object ID
        objectId = statusUpdate.getObjectId();
    }

    @Override
    public int getItemCount() {
        return sStatus.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView usernameWall;
        public TextView updateWall;

        public ViewHolder(View v) {
            super(v);
            usernameWall = (TextView) v.findViewById(R.id.usrView);
            updateWall = (TextView) v.findViewById(R.id.updtView);
        }
    }
}