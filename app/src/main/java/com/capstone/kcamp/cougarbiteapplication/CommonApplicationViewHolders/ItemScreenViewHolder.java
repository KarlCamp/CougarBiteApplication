package com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.capstone.kcamp.cougarbiteapplication.R;

/**
 * The ItemScreenViewHolder class is the holder utilized to fill the recycler view. In
 * other words, the class allows for the population of the recycler view with the
 * appropriate information regarding a food item.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class ItemScreenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName; //text view to be filled by id as presented in the xml
    private ItemClickListener itemClickListener; //create item on click functionality

    //generated appropriate constructor utilizing inheritance

    public ItemScreenViewHolder(View itemView) {
        super(itemView);
        txtFoodName = itemView.findViewById(R.id.food_name);
        itemView.setOnClickListener(this); //set on click listener
    }

    //set function for itemCLickListener

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    //overrides inherited functionality from ItemClickListener

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

