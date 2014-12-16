package com.v4ivstudio.cocan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Vaibhav Sharma on 12/15/2014.
 */
public class PostAdapter extends ArrayAdapter<ParseObject> {
    protected Context sContext;
    protected List<ParseObject> sPost;

    public  PostAdapter(Context context, List<ParseObject> posts) {
        super(context, R.layout.updatelayout, posts);
        sContext = context;
        sPost = posts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(sContext).inflate(R.layout.updatelayout, null);
            holder = new ViewHolder();
            holder.usernameWall = (TextView) convertView.findViewById(R.id.usrView);
            holder.updateWall = (TextView) convertView.findViewById(R.id.updtView);
            holder.updateTimeWall = (TextView) convertView.findViewById(R.id.timeView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject statusUpdate = sPost.get(position);

        //Username
        String usrnm = statusUpdate.getString("Username");
        holder.usernameWall.setText(usrnm);

        //Status Update
        String updt = statusUpdate.getString("Status");
        holder.updateWall.setText(updt);

        //Created At
        Date created = statusUpdate.getCreatedAt();
        String cAT = created.toString();
        holder.updateTimeWall.setText(cAT);


        return convertView;
    }

    public static class ViewHolder {
        TextView usernameWall;
        TextView updateWall;
        TextView updateTimeWall;
    }
}
