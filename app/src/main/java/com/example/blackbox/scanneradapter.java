package com.example.blackbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class scanneradapter extends RecyclerView.Adapter<scanneradapter.viewholder> {

    ArrayList<scannerlistitem> scannerlistitems;
    Context mcontext;

    public scanneradapter(ArrayList<scannerlistitem> scannerlistitems, Context mcontext) {
        this.scannerlistitems = scannerlistitems;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.scanner_listitem,parent,false);
        return new viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        scannerlistitem scannerlistitem = scannerlistitems.get(position);
        holder.date.setText(scannerlistitem.getDate());
        holder.docname.setText(scannerlistitem.getDocname());

    }

    @Override
    public int getItemCount() {
        return scannerlistitems.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
        TextView date,docname;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvdb_docdate);
            docname = itemView.findViewById(R.id.tvdb_docname);

        }

    }
}
