package com.zefaf.zefaffinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zefaf.zefaffinal.Model.Hajz;
import com.zefaf.zefaffinal.R;

import java.util.ArrayList;

public class HajzAdapter extends RecyclerView.Adapter<HajzAdapter.Hajzviewholder> {
    ArrayList<Hajz> hajzs;
    OnHajzClickListener listener;
    private Context mContext;

    public interface OnHajzClickListener {
        void onHajzItemClick(int position);
    }

    public void filterList(ArrayList<Hajz> filteredList) {
        hajzs = filteredList;
        notifyDataSetChanged();
    }

    public HajzAdapter(ArrayList<Hajz> emps, Context mContext) {
        this.hajzs = emps;
        this.mContext = mContext;

    }

    public void OnHajzClickListener(HajzAdapter.OnHajzClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public Hajzviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        Hajzviewholder holder = new Hajzviewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Hajzviewholder holder, int position) {
        Hajz employee = hajzs.get(position);
        holder.mTextname.setText(employee.getName());
        holder.mTextadress.setText(employee.getAdress());
        holder.mTextprice.setText(employee.getPrice());
        holder.mTextdese.setText(employee.getDese());
        holder.mImag.setImageResource(employee.getImg());
        holder.mImagfav.setImageResource(employee.getImgfav());
        holder.imgloction.setImageResource(employee.getImgloction());

    }

    @Override
    public int getItemCount() {
        return hajzs.size();
    }

    class Hajzviewholder extends RecyclerView.ViewHolder {

        private ImageView mImag;
        private ImageView mImagfav;
        private TextView mTextname;
        private TextView mTextadress;
        private ImageView imgloction;
        private TextView mTextprice;
        private TextView mTextdese;

        public Hajzviewholder(@NonNull View itemView) {
            super(itemView);

            mImag = itemView.findViewById(R.id.imag);
            imgloction = itemView.findViewById(R.id.imagloction);
            mImagfav = itemView.findViewById(R.id.imagfav);
            mTextname = itemView.findViewById(R.id.textname);
            mTextadress = itemView.findViewById(R.id.textadress);
            mTextprice = itemView.findViewById(R.id.textprice);
            mTextdese = itemView.findViewById(R.id.texdese);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHajzItemClick(position);
                        }
                    }

                    imgloction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("geo:19.067,72.877"));
                            mContext.startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}
