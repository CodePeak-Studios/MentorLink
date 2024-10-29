package com.example.mentorlink;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterAktiveArbeiten extends RecyclerView.Adapter<RecyclerViewAdapterAktiveArbeiten.ViewHolder>
{
    private ArrayList<Abschlussarbeit> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private User user;

    // data is passed into the constructor
    RecyclerViewAdapterAktiveArbeiten(Context context, ArrayList<Abschlussarbeit> data, User angemeldeterUser) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.user = angemeldeterUser;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerviewaktivearbeiten_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String Ueberschrift = mData.get(position).getUeberschrift();
        holder.tvUeberschrift.setText(Ueberschrift);
        holder.tvKurzbeschreibung.setText(mData.get(position).getKurzbeschreibung());
        holder.tvStatus.setText(mData.get(position).getStatusName(mData.get(position).getStatus()));

        if(mData.get(position).getBetreuer() == user.getId())
        {
            holder.btnRolle.setText("Betreuer");
        } if (mData.get(position).getZweitgutachter() == user.getId())
        {
            holder.btnRolle.setText("Zweitgutachter");
            holder.btnRolle.setBackgroundColor(ContextCompat.getColor(holder.btnRolle.getContext(), R.color.pink));
        }

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
        TextView tvStatus;
        Button btnRolle;

        ViewHolder(View itemView) {
            super(itemView);
            tvUeberschrift = itemView.findViewById(R.id.tvAktiveArbeitenUeberschrift);
            itemView.setOnClickListener(this);
            tvKurzbeschreibung = itemView.findViewById(R.id.tvAktiveArbeitenKurzbeschreibung);
            itemView.setOnClickListener(this);
            tvStatus = itemView.findViewById(R.id.tvAktiveArbeitenStatus);
            itemView.setOnClickListener(this);
            btnRolle = itemView.findViewById(R.id.btnAktiveArbeitenRolle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    Abschlussarbeit getItem(int id) {
        return mData.get(id);
    }

}
