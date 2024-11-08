package com.example.mentorlink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterBetreuerUebersicht extends RecyclerView.Adapter<RecyclerViewAdapterBetreuerUebersicht.ViewHolder>
{
    private ArrayList<User> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapterBetreuerUebersicht(Context context, ArrayList<User> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerviewbetreueruebersicht_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String kompletterName = mData.get(position).getVorname() + " " + mData.get(position).getNachname();
        holder.tvName.setText(kompletterName);
        holder.tvAuslastung.setText(mData.get(position).getAuslastungsString(mData.get(position).getAuslastung()));
        if (mData.get(position).getAuslastung() == 0) {
            holder.tvAuslastung.setTextColor(holder.tvAuslastung.getResources().getColor(R.color.darkGreen));
        } else if (mData.get(position).getAuslastung() == 1) {
            holder.tvAuslastung.setTextColor(holder.tvAuslastung.getResources().getColor(R.color.orange));
        } else if (mData.get(position).getAuslastung() == 2) {
            holder.tvAuslastung.setTextColor(holder.tvAuslastung.getResources().getColor(R.color.darkRot));
        }
        holder.tvFachbereiche.setText(mData.get(position).getFachbereiche());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvAuslastung;
        TextView tvFachbereiche;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.betreuerName);
            itemView.setOnClickListener(this);
            tvAuslastung = itemView.findViewById(R.id.betreuerAuslastung);
            itemView.setOnClickListener(this);
            tvFachbereiche = itemView.findViewById(R.id.betreuerFachbereiche);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getNachname();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
