package com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders;

import android.view.View;
/**
 * The ItemClickListener interface is utilized by view holder classes
 * to allow for item onClick functionality of appropriate recycler views.
 *
 * @author Karl Camp
 * @version 1.0
 * @since 2019-05-04
 */
public interface ItemClickListener {

    /**
     * The method onClick is called whenever a specific item in a recycler view
     * is clicked. As a result, it requires the current view holder used by the
     * recycler view adapter, the position of the item clicked within the adapter,
     * and whether it was a long click as parameters to deliver proper functionality.
     *
     * @param view current view holder utilized by the appropriate recycler view adapter.
     * @param position current position of clicked item in appropriate recycler view adapter.
     * @param isLongClick current state of item onClick.
     */
    void onClick(View view, int position, boolean isLongClick);
}
