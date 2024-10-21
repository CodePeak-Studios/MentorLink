package com.example.mentorlink;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private ArrayList<Abschlussarbeit> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, ArrayList<Abschlussarbeit> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpannableString str = new SpannableString(mData.get(position).getUeberschrift());

        str.setSpan(new BackgroundColorSpan(Color.rgb(255, 167, 58)), 0, str.length(), 0);
        holder.tvUeberschrift.setText(str);
        holder.tvKurzbeschreibung.setText(mData.get(position).getKurzbeschreibung());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUeberschrift;
        TextView tvKurzbeschreibung;

        ViewHolder(View itemView) {
            super(itemView);
            tvUeberschrift = itemView.findViewById(R.id.tvAbschlussarbeitUeberschrift);
            itemView.setOnClickListener(this);
            tvKurzbeschreibung = itemView.findViewById(R.id.tvAbschlussarbeitenKurzbeschreibung);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Gibt Abschlussarbeit an Position aus
    Abschlussarbeit getItem(int id) {
        return mData.get(id);
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
