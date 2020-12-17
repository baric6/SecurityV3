package com.baric.securityv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageHolder> {

    private final RecyclerView recyclerView;
    private final Context context;
    private final ArrayList<UrlDataModel> items;
    //interface for on card click
    private OnItemClickListener mClickListener;


    //recyclerView
    public StorageAdapter(RecyclerView recyclerView, Context context, ArrayList<UrlDataModel> items) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;

    }

    //set on card click
    public void setOnItemClickLitener(OnItemClickListener clickLitener) {
        mClickListener = clickLitener;
    }

    //inflate layout
    @Override
    public StorageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_card_view, parent, false);
        return new StorageHolder(view, mClickListener);
    }

    //set text to cardview from data base
    @Override
    public void onBindViewHolder(@NonNull StorageHolder holder, int position) {
        holder.cardTopic.setText(items.get(position).getRealFileName());
        //holder.cardUrl.setText(items.get(position).getUrl());

    }

    //size of array
    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public class StorageHolder extends RecyclerView.ViewHolder {

        public RecyclerView recyclerView;
        public TextView cardTopic;
        public TextView cardUrl;

        public StorageHolder(final View itemView, final StorageAdapter.OnItemClickListener clickListener) {
            super(itemView);

            cardTopic = itemView.findViewById(R.id.cardTopic);
            //cardUrl = itemView.findViewById(R.id.cardUrl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clickListener != null) {
                                int pos = getAdapterPosition();

                                if (pos != RecyclerView.NO_POSITION) {
                                    clickListener.onItemClick(pos);
                                }
                            }
                        }
                    });
                }
            });
        }
    }


}
