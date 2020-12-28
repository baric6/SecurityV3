package com.baric.securityv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProgrammingRecyclerAdapter extends RecyclerView.Adapter<ProgrammingRecyclerHolder> implements Filterable {

    private final Context context;
    private final ArrayList<ProgrammingdbModel> sub;

    //firebase
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Programming");
    private final ProgrammingHelper helper = new ProgrammingHelper(ref);

    //interface for on card click
    private ProgrammingRecyclerAdapter.OnItemClickListener mClickListener;
    private final Filter subFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ProgrammingdbModel> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(helper.retrieve());
            } else {
                String filterPattern = constraint.toString().trim().toLowerCase();

                for (ProgrammingdbModel item : helper.retrieve()) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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

            sub.clear();
            sub.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    //recyclerView
    public ProgrammingRecyclerAdapter(Context context, ArrayList<ProgrammingdbModel> sub) {
        this.context = context;
        this.sub = sub;
    }

    //set on card click
    public void setOnItemClickLitener(ProgrammingRecyclerAdapter.OnItemClickListener clickLitener) {
        mClickListener = clickLitener;
    }

    //inflate layout
    @Override
    public ProgrammingRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.programming_card, parent, false);
        return new ProgrammingRecyclerHolder(view, mClickListener);
    }

    //set text to cardview from data base
    @Override
    public void onBindViewHolder(@NonNull ProgrammingRecyclerHolder holder, int position) {
        holder.programmingCardTopic.setText(sub.get(position).getTopic());
        holder.programmingCardTitle.setText(sub.get(position).getTitle());
        holder.programmingCardComment.setText(sub.get(position).getComment());
        holder.ProgrammingCardWebview.loadUrl(sub.get(position).getKeyRefrence());

    }

    //size of array
    @Override
    public int getItemCount() {
        return sub.size();
    }

    //search methods
    @Override
    public Filter getFilter() {
        return subFilter;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}

