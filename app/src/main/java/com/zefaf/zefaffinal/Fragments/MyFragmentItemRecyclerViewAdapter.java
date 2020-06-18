package com.zefaf.zefaffinal.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.zefaf.zefaffinal.Fragments.dummy.DummyContent.DummyItem;
import com.zefaf.zefaffinal.Model.Reservation;
import com.zefaf.zefaffinal.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyFragmentItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFragmentItemRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mValues;

    public static final int RESERVATION_CHECKING = 0;
    public static final int RESERVATION_CONFIRMED = 1;
    public static final int RESERVATION_DENIED = 2;

//    private final OnListFragmentInteractionListener mListener;

    public MyFragmentItemRecyclerViewAdapter(List<Reservation> items
//            , OnListFragmentInteractionListener listener
    ) {
        mValues = items;
//        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
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
        } else if (mValues.get(position).getReservationStatus() == RESERVATION_CHECKING) {
            holder.txtReservationStatus.setText(R.string.reservation_checking);
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
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
