package com.zefaf.zefaffinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zefaf.zefaffinal.Model.Hajz;
import com.zefaf.zefaffinal.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HajzAdapter extends RecyclerView.Adapter<HajzAdapter.Hajzviewholder> {
    ArrayList<Hajz> hajzs;
    OnHajzClickListener listener;
    private Context mContext;


    public interface OnHajzClickListener {
        void onHajzItemClick(int position);

        void onBookmarkClick(int position);

    }

    public void filterList(ArrayList<Hajz> filteredList) {
        hajzs = filteredList;
        notifyDataSetChanged();

    }

    public HajzAdapter(ArrayList<Hajz> emps, Context mContext) {
        this.hajzs = emps;
        this.mContext = mContext;

    }

    public void OnHajzClickListener(OnHajzClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public Hajzviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bookmark, parent, false);
        Hajzviewholder holder = new Hajzviewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Hajzviewholder holder, int position) {
        Hajz hajz = hajzs.get(position);

        holder.txtVenueName.setText(hajz.getName());
        holder.txtVenueAddress.setText(hajz.getAdress());
        Picasso.get().load(hajz.getLink()).into(holder.imgVenueImage);
        holder.rating.setRating(4);
    }

    @Override
    public int getItemCount() {
        return hajzs.size();
    }

    class Hajzviewholder extends RecyclerView.ViewHolder {

        private ImageView imgVenueImage;
        private TextView txtVenueName;
        private RatingBar rating;
        private TextView txtVenueAddress;
        private ImageView mBookmarkImage;

        public Hajzviewholder(@NonNull View itemView) {
            super(itemView);

            imgVenueImage = itemView.findViewById(R.id.imgVenueImage);
            txtVenueName = itemView.findViewById(R.id.txtVenueName);
            rating = itemView.findViewById(R.id.rating);
            txtVenueAddress = itemView.findViewById(R.id.txtVenueAddress);
            mBookmarkImage = itemView.findViewById(R.id.imageView13);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHajzItemClick(position);
                        }
                    }

                    txtVenueAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("geo:19.067,72.877"));
                            mContext.startActivity(intent);
                        }
                    });
                    mBookmarkImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (listener != null) {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onBookmarkClick(position);
                                }
                            }
                        }
                    });
                }
            });
        }
    }
}
