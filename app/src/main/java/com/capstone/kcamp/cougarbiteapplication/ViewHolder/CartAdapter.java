package com.capstone.kcamp.cougarbiteapplication.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.capstone.kcamp.cougarbiteapplication.Common.Common;
import com.capstone.kcamp.cougarbiteapplication.Interface.ItemClickListener;
import com.capstone.kcamp.cougarbiteapplication.Model.Order;
import com.capstone.kcamp.cougarbiteapplication.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    String toppings="";
    String extras="";

    public CartAdapter(List<Order> listData) {
        this.listData = listData;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        double price = (Double.parseDouble(listData.get(position).getPrice())) * (Double.parseDouble(listData.get(position).getQuantity()));
        if (listData.get(position).isLettuce() || listData.get(position).isTomato()
                ||listData.get(position).isOnion() ||listData.get(position).isPickle()
                ||listData.get(position).isBeef() ||listData.get(position).isBreaded_chicken()
                ||listData.get(position).isBlack_bean() ||listData.get(position).isTurkey()
                ||listData.get(position).isGrilled_chicken() ||listData.get(position).isAmerican()
                ||listData.get(position).isBleu() ||listData.get(position).isPepper_jack()
                ||listData.get(position).isSwiss() ||listData.get(position).isProvolone()
                ||listData.get(position).isWhite_kaiser() ||listData.get(position).isSpinach_wrap()
                ||listData.get(position).isFlour_wrap() ||listData.get(position).isWheat_kaiser()
                ||listData.get(position).isFlat_bread() ||listData.get(position).isGarlic_wrap()
                ||listData.get(position).isGluten_free()) {
            toppings="With:";
            if (listData.get(position).isBeef()) {
                toppings=toppings+" beef,";
            }
            if (listData.get(position).isBreaded_chicken()) {
                toppings=toppings+" breaded chicken,";
            }
            if (listData.get(position).isBlack_bean()) {
                toppings=toppings+" black bean,";
            }
            if (listData.get(position).isTurkey()) {
                toppings=toppings+" turkey,";
            }
            if (listData.get(position).isGrilled_chicken()) {
                toppings=toppings+" grilled chicken,";
            }
            if (listData.get(position).isAmerican()) {
                toppings=toppings+" american,";
            }
            if (listData.get(position).isBleu()) {
                toppings=toppings+" bleu,";
            }
            if (listData.get(position).isPepper_jack()) {
                toppings=toppings+" pepper jack,";
            }
            if (listData.get(position).isSwiss()) {
                toppings=toppings+" swiss,";
            }
            if (listData.get(position).isProvolone()) {
                toppings=toppings+" provolone,";
            }
            if (listData.get(position).isWhite_kaiser()) {
                toppings=toppings+" white kaiser,";
            }
            if (listData.get(position).isSpinach_wrap()) {
                toppings=toppings+" spinach wrap,";
            }
            if (listData.get(position).isFlour_wrap()) {
                toppings=toppings+" flour wrap,";
            }
            if (listData.get(position).isWheat_kaiser()) {
                toppings=toppings+" wheat kaiser,";
            }
            if (listData.get(position).isFlat_bread()) {
                toppings=toppings+" flat bread,";
            }
            if (listData.get(position).isGarlic_wrap()) {
                toppings=toppings+" garlic wrap,";
            }
            if (listData.get(position).isGluten_free()) {
                toppings=toppings+" gluten free,";
            }
            if (listData.get(position).isLettuce()) {
                toppings=toppings+" lettuce,";
            }
            if (listData.get(position).isTomato()) {
                toppings=toppings+" tomato,";
            }
            if (listData.get(position).isOnion()) {
                toppings=toppings+" onion,";
            }
            if (listData.get(position).isPickle()) {
                toppings=toppings+" pickle,";
            }
            toppings=toppings.substring(0, toppings.length()-1);
        }
        if (listData.get(position).isBacon() || listData.get(position).isAvocado()
                ||listData.get(position).isCheese() ||listData.get(position).isChicken()
                ||listData.get(position).isPatty() ||listData.get(position).isFried_egg()) {
            extras="Add:";
            int count=0;
            if (listData.get(position).isBacon()) {
                extras=extras+" bacon($1.59),";
                price+=1.59;
                count++;
            }
            if (listData.get(position).isAvocado()) {
                if (count%2==0 && count!=0)
                    extras=extras+"\n         ";
                extras=extras+" avocado($1.59),";
                price+=1.59;
                count++;
            }
            if (listData.get(position).isCheese()) {
                if (count%2==0 && count!=0)
                    extras=extras+"\n         ";
                extras=extras+" cheese($0.89),";
                price+=0.89;
                count++;
            }
            if (listData.get(position).isPatty()) {
                if (count%2==0 && count!=0)
                    extras=extras+"\n         ";
                extras=extras+" patty($2.19),";
                price+=2.19;
                count++;
            }
            if (listData.get(position).isChicken()) {
                if (count%2==0 && count!=0)
                    extras=extras+"\n         ";
                extras=extras+" chicken($2.49),";
                price+=2.49;
                count++;
            }
            if (listData.get(position).isFried_egg()) {
                extras=extras+" fried egg($1.29),";
                price+=1.29;
            }
            extras=extras.substring(0, extras.length()-1);
        }
        holder.txt_price.setText(fmt.format(price));
        holder.txt_crt_name.setText(listData.get(position).getProductname());
        holder.txt_toppings.setText(toppings);
        holder.txt_extras.setText(extras);
        toppings="Plain";
        extras="Add: None";
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
