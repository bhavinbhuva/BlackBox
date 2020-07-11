package com.example.blackbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapte extends RecyclerView.Adapter<myadapte.viewholder> {

    ArrayList<mettinglistitem> mettinglistitems;
    Context mcontext;

    public myadapte(ArrayList<mettinglistitem> mettinglistitems, Context mcontext) {
        this.mettinglistitems =  mettinglistitems;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.meeting_listitem,parent,false);
        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        mettinglistitem mettinglistitem = mettinglistitems.get(position);
        holder.tvdate.setText(mettinglistitem.getDate());
        holder.tvtime.setText(mettinglistitem.getTime());
        holder.tvquery.setText(mettinglistitem.getQuery());
        holder.tvreply.setText(mettinglistitem.getReply());
    }

    @Override
    public int getItemCount() {
        return mettinglistitems.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
        TextView tvdate,tvtime,tvquery,tvreply;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tvdate = itemView.findViewById(R.id.tvdb_date);
            tvtime = itemView.findViewById(R.id.tvdb_time);
            tvquery = itemView.findViewById(R.id.tvdb_query);
            tvreply = itemView.findViewById(R.id.tvdb_reply);

        }

    }
}
