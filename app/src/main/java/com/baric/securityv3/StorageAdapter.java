package com.baric.securityv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageHolder> implements Filterable {

    private final RecyclerView recyclerView;
    private final Context context;
    private final ArrayList<UrlDataModel> items;
    //interface for on card click
    private OnItemClickListener mClickListener;


    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("storageLinks");
    private final StorageHelper helper = new StorageHelper(ref);
    
    private final Filter subFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<UrlDataModel> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(helper.retrieve());
            } else {
                String filterPattern = constraint.toString().trim().toLowerCase();

                for (UrlDataModel item : helper.retrieve()) {
                    if (item.getFileName().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);

                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        //this refreshes the recyclerView
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            items.clear();
            items.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };


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

    @Override
    public Filter getFilter() {
        return subFilter;
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
