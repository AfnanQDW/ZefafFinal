package com.zefaf.zefaffinal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zefaf.zefaffinal.Model.Bookmark;
import com.zefaf.zefaffinal.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarkViewHolder> {
    ArrayList<Bookmark> bookmarks = new ArrayList<>();
    OnItemClickListener listener;


    public interface OnItemClickListener {
        void OnItemClick(int position);

        void onBookmarkClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BookmarksAdapter(ArrayList<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bookmark, parent, false);
        BookmarkViewHolder holder = new BookmarkViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Bookmark bm = bookmarks.get(position);

        holder.mImgVenueImage.setImageResource(bm.getVenuePic());
        holder.rating.setRating(4);
        holder.mTxtVenueAddress.setText(bm.getVenueAddress());
        holder.mTxtVenueName.setText(bm.getVenueName());

    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgVenueImage;
        private ImageView mBookmarkImage;
        private TextView mTxtVenueName;
        private RatingBar rating;
        private TextView mTxtVenueAddress;

        public BookmarkViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImgVenueImage = itemView.findViewById(R.id.imgVenueImage);
            mTxtVenueName = itemView.findViewById(R.id.txtVenueName);
            rating = itemView.findViewById(R.id.rating);
            mTxtVenueAddress = itemView.findViewById(R.id.txtVenueAddress);
            mBookmarkImage = itemView.findViewById(R.id.imageView13);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
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
    }
}
