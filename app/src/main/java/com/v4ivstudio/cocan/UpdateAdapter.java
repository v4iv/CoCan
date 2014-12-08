package com.v4ivstudio.cocan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Vaibhav Sharma on 12/7/2014.
 */
public class UpdateAdapter extends ArrayAdapter<ParseObject> {

    protected Context sContext;
    protected List<ParseObject> sStatus;



    public UpdateAdapter(Context context, List<ParseObject> status){
        super(context, R.layout.updatelayout,status);
        sContext = context;
        sStatus = status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(sContext).inflate(R.layout.updatelayout, null);
            holder = new ViewHolder();
            holder.usernameWall = (TextView) convertView.findViewById(R.id.usrView);
            holder.updateWall = (TextView) convertView.findViewById(R.id.updtView);
            holder.updateTimeWall = (TextView) convertView.findViewById(R.id.timeView);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject statusUpdate = sStatus.get(position);

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


        return super.getView(position, convertView, parent);
    }

    public static class ViewHolder{
        TextView usernameWall;
        TextView updateWall;
        TextView updateTimeWall;
    }

}
