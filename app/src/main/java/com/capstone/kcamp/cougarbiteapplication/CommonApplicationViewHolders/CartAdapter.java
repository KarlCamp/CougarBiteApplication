package com.capstone.kcamp.cougarbiteapplication.CommonApplicationViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstone.kcamp.cougarbiteapplication.CheckOutActivity;
import com.capstone.kcamp.cougarbiteapplication.Global.Global;
import com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels.Order;
import com.capstone.kcamp.cougarbiteapplication.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import static java.lang.Math.abs;

/**
 * The CartAdapter class is the adapter utilized to populate the CartViewHolder. In
 * other words, the class allows for the population of the recycler view with the
 * appropriate information regarding an order. As a result, it parses through all
 * the necessary information and presents them in a format palatable by the
 * customer. In short, its the means by which the checkout screen looks nice.
 *
 * @author Karl Camp
 * @version 1.0
 * @since 2019-05-04
 */
public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> orderData; //stores list of orders
    private CheckOutActivity checkOutActivity; //stores activity of adapter
    private String toppings = ""; //stores information about toppings to be displayed
    private String extras = ""; //stores information about extras to be displayed

    //generated appropriate constructor initializing all class variables

    public CartAdapter(List<Order> orderData, CheckOutActivity checkOutActivity) {
        this.orderData = orderData;
        this.checkOutActivity=checkOutActivity;
    }

    /**
     * The method onCreateViewHolder lays out the recycler view in the parent context
     * upon creation of the activity.
     *
     * @param parent stores parameter of the current parent view.
     * @param viewType stores the type of current view type as numerical code.
     * @return a new view based on parent layout and current view type.
     */
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(checkOutActivity);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    /**
     * The method onBindViewHolder binds the holder to the adapter upon binding
     * new information to the recycler view.
     *
     * @param holder the view holder utilized in populating the adapter.
     * @param position the current position of the view holder in the recycler view.
     */
    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position) {

        Locale locale = new Locale("en", "US"); //United States currency
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale); //generate in above mentioned format
        double price = generateDetails(holder.getAdapterPosition());
        holder.txt_price.setText(fmt.format(price)); //set price of card view
        String title = orderData.get(position).getProductname()+ " x "+orderData.get(position).getQuantity(); //create product name x quantity
        holder.txt_crt_name.setText(title); //set title of card view
        holder.txt_toppings.setText(toppings); //set "With:"  string of card view
        holder.txt_extras.setText(extras); //set "Add": string of card view
        holder.remove_Item.setOnClickListener(new View.OnClickListener() { //bind action when remove icon clicked
            Locale locale = new Locale("en", "US"); //United States currency
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale); //generate above mentioned format
            @Override
            public void onClick(View v) {
                Global.currentTotal=Global.currentTotal-Global.currentCartPrices.get(holder.getAdapterPosition()); //calculate new total
                checkOutActivity.txtTotalPrice.setText(fmt.format(abs(Global.currentTotal))); //set new total
                removeAt(holder.getAdapterPosition()); //remove the item
            }
        });
        extras=""; //restart string generation for new item in order
        toppings=""; //restart string generation for new item in order
    }

    /**
     * The method removeAt removes item from recycler view and updates appropriate values
     *
     * @param position position of item in recycler view to be deleted
     */
    private void removeAt(int position) {
        Global.currentCartPrices.remove(position); //delete price from list
        Global.currentCart.remove(position); //delete order from list
        notifyItemRemoved(position); //notify recycler view item is removed
        notifyItemRangeChanged(position, Global.currentCart.size()); //reconfigure the view
    }

    /**
     * The method getItemCount stores the size of the order list.
     *
     * @return return size of list representing number of items in an order.
     */
    @Override
    public int getItemCount() {
        return orderData.size();
    }

    /**
     * The method generateDetails generates three parts of the order details for
     * a specific item in a cardView. It generates the toppings string by first checking
     * if there is a topping and then proceeds as dictated by the code. It generates the
     * extras string by first checking there is an extra and then proceeds as dictated by
     * the code. It generates the price of an item by checking if there are any extras and
     * making the appropriate calculations as dictated by the code.
     *
     * @param position is the location of a specific card view in the recycler view
     * @return price of a particular order item
     */
    private double generateDetails(int position) {
        double itemPrice = (Double.parseDouble(orderData.get(position).getPrice())) * (Double.parseDouble(orderData.get(position).getQuantity()));
        if (orderData.get(position).isLettuce() || orderData.get(position).isTomato()
                || orderData.get(position).isOnion() || orderData.get(position).isPickle()
                || orderData.get(position).isBeef() || orderData.get(position).isBreaded_chicken()
                || orderData.get(position).isBlack_bean() || orderData.get(position).isTurkey()
                || orderData.get(position).isGrilled_chicken() || orderData.get(position).isAmerican()
                || orderData.get(position).isBleu() || orderData.get(position).isPepper_jack()
                || orderData.get(position).isSwiss() || orderData.get(position).isProvolone()
                || orderData.get(position).isWhite_kaiser() || orderData.get(position).isSpinach_wrap()
                || orderData.get(position).isFlour_wrap() || orderData.get(position).isWheat_kaiser()
                || orderData.get(position).isFlat_bread() || orderData.get(position).isGarlic_wrap()
                || orderData.get(position).isGluten_free()) {
            toppings = "With:";
            if (orderData.get(position).isBeef()) {
                toppings = toppings + " beef,";
            }
            if (orderData.get(position).isBreaded_chicken()) {
                toppings = toppings + " breaded chicken,";
            }
            if (orderData.get(position).isBlack_bean()) {
                toppings = toppings + " black bean,";
            }
            if (orderData.get(position).isTurkey()) {
                toppings = toppings + " turkey,";
            }
            if (orderData.get(position).isGrilled_chicken()) {
                toppings = toppings + " grilled chicken,";
            }
            if (orderData.get(position).isAmerican()) {
                toppings = toppings + " american,";
            }
            if (orderData.get(position).isBleu()) {
                toppings = toppings + " bleu,";
            }
            if (orderData.get(position).isPepper_jack()) {
                toppings = toppings + " pepper jack,";
            }
            if (orderData.get(position).isSwiss()) {
                toppings = toppings + " swiss,";
            }
            if (orderData.get(position).isProvolone()) {
                toppings = toppings + " provolone,";
            }
            if (orderData.get(position).isWhite_kaiser()) {
                toppings = toppings + " white kaiser,";
            }
            if (orderData.get(position).isSpinach_wrap()) {
                toppings = toppings + " spinach wrap,";
            }
            if (orderData.get(position).isFlour_wrap()) {
                toppings = toppings + " flour wrap,";
            }
            if (orderData.get(position).isWheat_kaiser()) {
                toppings = toppings + " wheat kaiser,";
            }
            if (orderData.get(position).isFlat_bread()) {
                toppings = toppings + " flat bread,";
            }
            if (orderData.get(position).isGarlic_wrap()) {
                toppings = toppings + " garlic wrap,";
            }
            if (orderData.get(position).isGluten_free()) {
                toppings = toppings + " gluten free,";
            }
            if (orderData.get(position).isLettuce()) {
                toppings = toppings + " lettuce,";
            }
            if (orderData.get(position).isTomato()) {
                toppings = toppings + " tomato,";
            }
            if (orderData.get(position).isOnion()) {
                toppings = toppings + " onion,";
            }
            if (orderData.get(position).isPickle()) {
                toppings = toppings + " pickle,";
            }
            toppings = toppings.substring(0, toppings.length() - 1); //remove last character, a comma
        }
        if (orderData.get(position).isBacon() || orderData.get(position).isAvocado()
                || orderData.get(position).isCheese() || orderData.get(position).isChicken()
                || orderData.get(position).isPatty() || orderData.get(position).isFried_egg()) {
            extras = "Add:";
            int count = 0;
            if (orderData.get(position).isBacon()) {
                extras = extras + " bacon($1.59),";
                itemPrice += 1.59 * (Double.parseDouble(orderData.get(position).getQuantity()));
                count++;
            }
            if (orderData.get(position).isAvocado()) {
                if (count % 2 == 0 && count != 0) //if two values, go to new line
                    extras = extras + "\n         ";
                extras = extras + " avocado($1.59),";
                itemPrice += 1.59* (Double.parseDouble(orderData.get(position).getQuantity()));
                count++;
            }
            if (orderData.get(position).isCheese()) {
                if (count % 2 == 0 && count != 0) //if two values, go to new line
                    extras = extras + "\n         ";
                extras = extras + " cheese($0.89),";
                itemPrice += 0.89* (Double.parseDouble(orderData.get(position).getQuantity()));
                count++;
            }
            if (orderData.get(position).isPatty()) {
                if (count % 2 == 0 && count != 0) //if two values, go to new line
                    extras = extras + "\n         ";
                extras = extras + " patty($2.19),";
                itemPrice += 2.19* (Double.parseDouble(orderData.get(position).getQuantity()));
                count++;
            }
            if (orderData.get(position).isChicken()) {
                if (count % 2 == 0 && count != 0) //if two values, go to new line
                    extras = extras + "\n         ";
                extras = extras + " chicken($2.49),";
                itemPrice += 2.49* (Double.parseDouble(orderData.get(position).getQuantity()));
            }
            if (orderData.get(position).isFried_egg()) {
                extras = extras + " fried egg($1.29),";
                itemPrice += 1.29* (Double.parseDouble(orderData.get(position).getQuantity()));
            }
            extras = extras.substring(0, extras.length() - 1); //remove last character, a comma
        }
        return itemPrice;
    }
}