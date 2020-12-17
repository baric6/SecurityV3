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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> implements Filterable {

    private final Context context;
    private final ArrayList<ListSecDBModel> sub;

    //firebase
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes");
    private final FireBaseHelper helper = new FireBaseHelper(ref);

    //interface for on card click
    private OnItemClickListener mClickListener;
    private final Filter subFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ListSecDBModel> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(helper.retrieve());
            } else {
                String filterPattern = constraint.toString().trim().toLowerCase();

                for (ListSecDBModel item : helper.retrieve()) {
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
    public RecyclerAdapter(Context context, ArrayList<ListSecDBModel> sub) {
        this.context = context;
        this.sub = sub;
    }

    //set on card click
    public void setOnItemClickLitener(OnItemClickListener clickLitener) {
        mClickListener = clickLitener;
    }

    //inflate layout
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_card, parent, false);
        return new RecyclerHolder(view, mClickListener);
    }

    //set text to cardview from data base
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.cardTopic.setText(sub.get(position).getTopic());
        holder.noteTitle.setText(sub.get(position).getTitle());
        holder.noteComment.setText(sub.get(position).getComment());
        holder.noteWebview.loadUrl(sub.get(position).getkeyRefrence());

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
