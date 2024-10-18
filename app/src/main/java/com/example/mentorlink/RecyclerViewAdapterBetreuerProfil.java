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

public class RecyclerViewAdapterBetreuerProfil extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Abschlussarbeit> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    // data is passed into the constructor
    RecyclerViewAdapterBetreuerProfil(Context context, ArrayList<Abschlussarbeit> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;  // Header an der Position 0
        }
        return VIEW_TYPE_ITEM;  // Alle anderen Positionen sind Listeneinträge
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = mInflater.inflate(R.layout.recyclerviewbetreuerprofil_userdaten, parent, false);  // Lade das Header-Layout
            return new HeaderViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.recyclerviewbetreuerprofil_row, parent, false);  // Lade das Listenelement-Layout
            return new ItemViewHolder(view);
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            int betreuerID = mData.get(position).getBetreuer();
            DBHandler db = new DBHandler(holder.itemView.getContext());
            User betreuer = db.getUserNachID(betreuerID);


            String kompletterName = betreuer.getVorname() + " " + betreuer.getNachname();
            headerHolder.tvProfilName.setText(kompletterName);
            headerHolder.tvProfilAuslastung.setText(betreuer.getAuslastungsString(betreuer.getAuslastung()));
            if (betreuer.getAuslastung() == 0) {
                ((HeaderViewHolder) holder).tvProfilAuslastung.setTextColor(((HeaderViewHolder) holder).tvProfilAuslastung.getResources().getColor(R.color.darkGreen));
            } else if (betreuer.getAuslastung() == 1) {
                ((HeaderViewHolder) holder).tvProfilAuslastung.setTextColor(((HeaderViewHolder) holder).tvProfilAuslastung.getResources().getColor(R.color.orange));
            } else if (betreuer.getAuslastung() == 2) {
                ((HeaderViewHolder) holder).tvProfilAuslastung.setTextColor(((HeaderViewHolder) holder).tvProfilAuslastung.getResources().getColor(R.color.darkRot));
            }
            ((HeaderViewHolder) holder).tvProfilFachbereiche.setText(betreuer.getFachbereiche());

            headerHolder.tvProfilFachbereicheHeader.setText("Fachbereiche:");
            headerHolder.tvProfilArbeitenHeader.setText("Ausgeschriebene Arbeiten:");
        } else {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            // Für Listenelemente Position -1, da der Header Position 0 belegt
            Abschlussarbeit arbeit = mData.get(position - 1);
            SpannableString str = new SpannableString(arbeit.getUeberschrift());
            str.setSpan(new BackgroundColorSpan(Color.rgb(255, 167, 58)), 0, str.length(), 0);
            itemHolder.tvUeberschriftBP.setText(str);
            itemHolder.tvKurzbeschreibungBP.setText(arbeit.getKurzbeschreibung());

            itemHolder.btnAuswaehlen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        int currentposition = itemHolder.getAdapterPosition();
                        if (currentposition != RecyclerView.NO_POSITION) {
                            mClickListener.onItemClick(view, currentposition);
                        }
                    }
                }
            });
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    // ViewHolder für den Header
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvProfilName, tvProfilAuslastung, tvProfilFachbereiche, tvProfilFachbereicheHeader, tvProfilArbeitenHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            tvProfilName = itemView.findViewById(R.id.tvProfilName);
            tvProfilAuslastung = itemView.findViewById(R.id.tvProfilAuslastung);
            tvProfilFachbereiche = itemView.findViewById(R.id.tvProfilFachbereiche);
            tvProfilFachbereicheHeader = itemView.findViewById(R.id.tvProfilFachbereicheHeader);
            tvProfilArbeitenHeader = itemView.findViewById(R.id.tvProfilArbeitenHeader);
        }
    }

    // ViewHolder für Listeneinträge
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUeberschriftBP;
        TextView tvKurzbeschreibungBP;
        Button btnAuswaehlen;

        ItemViewHolder(View itemView) {
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
