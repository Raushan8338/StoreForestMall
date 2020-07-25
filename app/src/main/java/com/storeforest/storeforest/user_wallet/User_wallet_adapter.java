package com.storeforest.storeforest.user_wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Wallet_payment_status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User_wallet_adapter extends RecyclerView.Adapter<User_wallet_adapter.ProductViewHolder>  {

    private ArrayList<User_wallet_item> user_wallet_items;
    private Context context;
    private String months[] =
            {"Jan" , "Feb" , "Mar" , "Apr", "May",
                    "Jun", "Jul", "Aug", "Sep", "Oct",
                    "Nov", "Dec"};

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_items,parent, false);
        ProductViewHolder lvh = new ProductViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final User_wallet_item currentItem = user_wallet_items.get(position);


        try{
            Date d = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a", Locale.ENGLISH).parse(currentItem.getDate_time());
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int day = cal.get(Calendar.DATE);
            int mon = cal.get(Calendar.MONTH);
            int yy = cal.get(Calendar.YEAR);
            int hh = cal.get(Calendar.HOUR);
            int mm = cal.get(Calendar.MINUTE);
            holder.date_time.setText(day + " "+ months[mon]+" "+yy+" "+hh+":"+mm);
        } catch (Exception e){

        }

        if(currentItem.getDiducted_balance().equals("0")){
           // holder.vis.setVisibility(View.VISIBLE);
            holder.deducted_balance.setText("+"+currentItem.getDeposited()+"");
            holder.deducted_balance.setTextColor(Color.GREEN);
            holder.status.setText("Credited");
            holder.certain.setText("+"+currentItem.getAmou());
            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Wallet_payment_status.class);
                    intent.putExtra("status",false);
                    intent.putExtra("amount",currentItem.getAmou());
                    intent.putExtra("certain",currentItem.getDeposited());
                    intent.putExtra("t_detail",currentItem.getT_detail());
                    intent.putExtra("t_id","TXN_ID"+currentItem.getT_detail());
                    intent.putExtra("time",holder.date_time.getText());
                    context.startActivity(intent);
                }
            });
        }
        else {
          //  holder.vis.setVisibility(View.VISIBLE);
            holder.deducted_balance.setText("-"+currentItem.getDiducted_balance()+"");
            holder.deducted_balance.setTextColor(Color.RED);
            holder.status.setText("Debited");
            holder.certain.setText("-"+currentItem.getAmou());
            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,Wallet_payment_status.class);
                    intent.putExtra("status",true);
                    intent.putExtra("amount",currentItem.getAmou());
                    intent.putExtra("certain",currentItem.getDiducted_balance());
                    intent.putExtra("t_detail",currentItem.getT_detail());
                    intent.putExtra("t_id","STF"+currentItem.getT_detail());
                    intent.putExtra("time",holder.date_time.getText());

                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return user_wallet_items.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView status;
        TextView certain;
        TextView deducted_balance;
        TextView date_time;
        CardView main_layout;
        LinearLayout vis;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            //reffreal_code=itemView.findViewById(R.id.reffreal_code);
            status=itemView.findViewById(R.id.status);
            deducted_balance=itemView.findViewById(R.id.deducted_balance);
            date_time=itemView.findViewById(R.id.date_time);
            certain = itemView.findViewById(R.id.certain_balance);
            main_layout=itemView.findViewById(R.id.main_layout);
            vis = itemView.findViewById(R.id.layout_visibility);

        }
    }
    public User_wallet_adapter(Context context, ArrayList<User_wallet_item> user_wallet_items){
        this.context=context;
       this. user_wallet_items=user_wallet_items;

    }

}