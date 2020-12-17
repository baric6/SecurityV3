package com.baric.securityv3;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class mCalcRecyclerHolder extends RecyclerView.ViewHolder {
    public TextView timeStart;
    public TextView milesStart;
    public TextView startPlace;
    public TextView timeEnd;
    public TextView milesEnd;
    public TextView endPlace;
    public TextView totalMiles;
    

    public mCalcRecyclerHolder(final View itemView, final mRecyclerAdapter.OnItemClickListener clickListener) {
        super(itemView);

        timeStart = itemView.findViewById(R.id.timeStart);
        milesStart = itemView.findViewById(R.id.milesStart);
        startPlace = itemView.findViewById(R.id.startPlace);
        timeEnd = itemView.findViewById(R.id.timeEnd);
        milesEnd = itemView.findViewById(R.id.milesEnd);
        endPlace = itemView.findViewById(R.id.endPlace);
        totalMiles = itemView.findViewById(R.id.totalMiles);
        
        
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
}

