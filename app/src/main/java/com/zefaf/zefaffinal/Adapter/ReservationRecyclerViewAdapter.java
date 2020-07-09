package com.zefaf.zefaffinal.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zefaf.zefaffinal.Model.Reservation;
import com.zefaf.zefaffinal.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ReservationRecyclerViewAdapter extends RecyclerView.Adapter<ReservationRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mValues;

    //الحجز قيد المراجعة
    public static final int RESERVATION_PENDING = 0;
    //الحجز تم التاكيد عليه
    public static final int RESERVATION_CONFIRMED = 1;
    //الحجز تم رفضه
    public static final int RESERVATION_DENIED = 2;

    private final OnReservationItemClickListener mListener;

    public interface OnReservationItemClickListener {
        void OnReservationItemClick(int position);

    }
    public ReservationRecyclerViewAdapter(List<Reservation> items
            , OnReservationItemClickListener listener
    ) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtVenueName.setText(mValues.get(position).getVenueName());
        holder.txtVenueAddress.setText(mValues.get(position).getVenueAddress());
        holder.txtResTime.setText(mValues.get(position).getReservationDate());
        holder.txtResPrice.setText(mValues.get(position).getVenuePrice());

        if (mValues.get(position).getReservationStatus() == RESERVATION_CONFIRMED) {
            holder.txtReservationStatus.setText(R.string.reservation_confirmed);
        } else if (mValues.get(position).getReservationStatus() == RESERVATION_DENIED) {
            holder.txtReservationStatus.setText(R.string.reservation_denied);
        } else if (mValues.get(position).getReservationStatus() == RESERVATION_PENDING) {
            holder.txtReservationStatus.setText(R.string.reservation_pending);
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtResTime;
        private TextView txtResPrice;
        private TextView txtVenueName;
        private TextView txtVenueAddress;
        private TextView txtReservationStatus;

        public ViewHolder(View view) {
            super(view);
            txtResTime = view.findViewById(R.id.txtResTime);
            txtResPrice = view.findViewById(R.id.txtResPrice);
            txtVenueName = view.findViewById(R.id.txtVenueName);
            txtVenueAddress = view.findViewById(R.id.txtVenueAddress);
            txtReservationStatus = view.findViewById(R.id.txtReservationStatus);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnReservationItemClick(position);
                        }
                    }
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
