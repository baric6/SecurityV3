package com.baric.securityv3;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ProgrammingRecyclerHolder extends RecyclerView.ViewHolder {
    public TextView programmingCardTopic;
    public TextView programmingCardTitle;
    public TextView programmingCardComment;
    public WebView ProgrammingCardWebview;

    public ProgrammingRecyclerHolder(final View itemView, final RecyclerAdapter.OnItemClickListener clickListener) {
        super(itemView);

        programmingCardTopic = itemView.findViewById(R.id.programmingCardTopic);
        programmingCardTitle = itemView.findViewById(R.id.programmingCardTitle);
        programmingCardComment = itemView.findViewById(R.id.programmingCardComment);
        ProgrammingCardWebview = itemView.findViewById(R.id.ProgrammingCardWebview);

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

