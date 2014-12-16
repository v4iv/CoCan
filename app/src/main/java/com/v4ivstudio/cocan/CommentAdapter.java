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
 * Created by Vaibhav Sharma on 12/14/2014.
 */
public class CommentAdapter extends ArrayAdapter<ParseObject> {
    protected Context sContext;
    protected List<ParseObject> sComment;


    public CommentAdapter(Context context, List<ParseObject> status) {
        super(context, R.layout.commentlayout, status);
        sContext = context;
        sComment = status;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(sContext).inflate(R.layout.commentlayout, null);
            holder = new ViewHolder();
            holder.usernameCmmt = (TextView) convertView.findViewById(R.id.usrNmV);
            holder.contntCmmt = (TextView) convertView.findViewById(R.id.cmmtView);
            holder.postTimeCmmt = (TextView) convertView.findViewById(R.id.timView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject statusUpdate = sComment.get(position);

        //Username
        String usrnm = statusUpdate.getString("Username");
        holder.usernameCmmt.setText(usrnm);

        //Status Update
        String updt = statusUpdate.getString("Content");
        holder.contntCmmt.setText(updt);

        //Created At
        Date created = statusUpdate.getCreatedAt();
        String cAT = created.toString();
        holder.postTimeCmmt.setText(cAT);


        return convertView;
    }

    public static class ViewHolder {
        TextView usernameCmmt;
        TextView contntCmmt;
        TextView postTimeCmmt;
    }
}
