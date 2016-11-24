package com.mymovies.launchpad.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymovies.launchpad.moviesapp.R;
import com.mymovies.launchpad.moviesapp.models.Review;
import com.mymovies.launchpad.moviesapp.models.Reviews;
import com.mymovies.launchpad.moviesapp.utilities.Logging;

/**
 * Created by Dell on 11/11/2016.
 */

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.MyViewHolder> {


    private Reviews reviews;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.review_title);

        }
    }


    public ReviewsListAdapter(Reviews reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Review review = reviews.get(position);
        holder.title.setText(review.getAuthor());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reviewURL = review.getUrl();
                Logging.log(reviewURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
