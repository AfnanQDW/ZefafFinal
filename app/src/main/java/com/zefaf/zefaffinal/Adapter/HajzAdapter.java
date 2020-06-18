package com.afq.zefaf.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zefaf.zefaffinal.BookmarksActivity;
import com.zefaf.zefaffinal.HajsALan;
import com.zefaf.zefaffinal.Model.Hajz;
import com.zefaf.zefaffinal.Onhijzclicklistener;
import com.zefaf.zefaffinal.R;

import java.util.ArrayList;

public class HajzAdapter extends RecyclerView.Adapter<HajzAdapter.Hajzviewholder> {
    ArrayList <Hajz> hajzs=new ArrayList<>();
    Onhijzclicklistener listener;

    public HajzAdapter(ArrayList<Hajz> emps, Onhijzclicklistener onemployeeclicklistener) {
        this.hajzs = emps;
        this.listener = onemployeeclicklistener;
    }

    @NonNull
    @Override
    public Hajzviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);
        Hajzviewholder holder=new Hajzviewholder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Hajzviewholder holder, int position) {
        Hajz employee=hajzs.get(position);
        holder.succss.setText(employee.getSuccss());
        holder.time.setText(employee.getTime());
        holder.img.setImageResource(employee.getImg());

    }

    @Override
    public int getItemCount() {
        return hajzs.size();
    }

    class Hajzviewholder extends RecyclerView.ViewHolder{
        TextView succss,time;
        ImageView img;

        public Hajzviewholder(@NonNull View itemView) {
            super(itemView);

            succss=itemView.findViewById(R.id.textsuccss);
            time=itemView.findViewById(R.id.textime);
            img=itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   listener.Onhijzclicklistener(succss.getText().toString());
                }
            });

        }
    }



}
