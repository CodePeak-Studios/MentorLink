package com.example.mentorlink;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterBetreuerProfil extends RecyclerView.Adapter<RecyclerViewAdapterBetreuerProfil.ViewHolder>
{
    private ArrayList<Abschlussarbeit> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapterBetreuerProfil(Context context, ArrayList<Abschlussarbeit> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerviewbetreuerprofil_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SpannableString str = new SpannableString(mData.get(position).getUeberschrift());

        str.setSpan(new BackgroundColorSpan(Color.rgb(255, 167, 58)), 0, str.length(), 0);
        holder.tvUeberschriftBP.setText(str);
        holder.tvKurzbeschreibungBP.setText(mData.get(position).getKurzbeschreibung());

        holder.btnAuswaehlen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    int currentposition = holder.getAdapterPosition();
                    if (currentposition != RecyclerView.NO_POSITION) {
                        mClickListener.onItemClick(view, currentposition);
                    }

                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUeberschriftBP;
        TextView tvKurzbeschreibungBP;
        Button btnAuswaehlen;

        ViewHolder(View itemView) {
            super(itemView);
            tvUeberschriftBP = itemView.findViewById(R.id.tvAbschlussarbeitUeberschriftBP);
            tvKurzbeschreibungBP = itemView.findViewById(R.id.tvAbschlussarbeitenKurzbeschreibungBP);
            btnAuswaehlen = itemView.findViewById(R.id.btnBetreuerProfilAuswaehlen);
            btnAuswaehlen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
        });
    }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int position) {
        return mData.get(position).getUeberschrift();
    }

    int getBetreuerID(int position) {
        return mData.get(position).getBetreuer();
    }

    int getAbschlussarbeitID(int position) {
        return mData.get(position).getId();
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