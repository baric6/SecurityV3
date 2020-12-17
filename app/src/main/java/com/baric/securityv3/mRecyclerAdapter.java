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

public class mRecyclerAdapter extends RecyclerView.Adapter<mCalcRecyclerHolder> implements Filterable{

    private final Context context;
    private final ArrayList<mCalcdbModel> sub;

    //firebase
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Programming");
    private final mCalcFirebaseHelper helper = new mCalcFirebaseHelper(ref);

    //interface for on card click
    private mRecyclerAdapter.OnItemClickListener mClickListener;
    private final Filter subFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<mCalcdbModel> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(helper.retrieve());
            } else {
                String filterPattern = constraint.toString().trim().toLowerCase();

                for (mCalcdbModel item : helper.retrieve()) {
                    if (item.getStartmiles().toLowerCase().contains(filterPattern)) {
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
    public mRecyclerAdapter(Context context, ArrayList<mCalcdbModel> sub) {
        this.context = context;
        this.sub = sub;
    }

    //set on card click
    public void setOnItemClickLitener(mRecyclerAdapter.OnItemClickListener clickLitener) {
        mClickListener = clickLitener;
    }

    //inflate layout
    @Override
    public mCalcRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mcalc_card, parent, false);
        return new mCalcRecyclerHolder(view, mClickListener);
    }

    //set text to cardview from data base
    @Override
    public void onBindViewHolder(@NonNull mCalcRecyclerHolder holder, int position) {
        holder.timeStart.setText(sub.get(position).getStartTime());
        holder.milesStart.setText(sub.get(position).getStartmiles());
        holder.startPlace.setText(sub.get(position).getStartPlace());
        holder.timeEnd.setText(sub.get(position).getEndTime());
        holder.milesEnd.setText(sub.get(position).getEndMiles());
        holder.endPlace.setText(sub.get(position).getEndPlace());
        holder.totalMiles.setText(sub.get(position).getTotalMiles());

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
