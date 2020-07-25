package com.storeforest.storeforest.booking_details;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.order_details.Order_details;

import java.util.ArrayList;

public class Booking_details_adapter  extends RecyclerView.Adapter<Booking_details_adapter.BookingViewHolder> {
    private ArrayList<Booking_details_item> booking_details_items;
    private Domain d;
    private Context context;
    private String months[] = {"Jan" , "Feb" , "Mar" , "Apr", "May",
            "Jun", "Jul", "Aug", "Sep", "Oct",
            "Nov", "Dec"};
    @NonNull
    @Override
    public Booking_details_adapter.BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_item,parent,false);

        Booking_details_adapter.BookingViewHolder bvh=new Booking_details_adapter.BookingViewHolder(view);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final Booking_details_adapter.BookingViewHolder holder, int position) {
        final Booking_details_item currentItem = booking_details_items.get(position);

        holder.shop_name.setText(currentItem.getShop_name());
        holder.city.setText(currentItem.getAddress());
        holder.booking_amount.setText("Rs."+currentItem.getBooking_amount());
        holder.b_date.setText(currentItem.getBooking_date());
        holder.item_name.setText(currentItem.getTotal_item());
        if (currentItem.getBooking_status().equals("1")){
            holder.b_status.setText("Pending");
        }
        else  if (currentItem.getBooking_status().equals("2")){
            holder.b_status.setText("confirmed");
        }
        else  if (currentItem.getBooking_status().equals("3")){
            holder.b_status.setText("confirmed");
        }
      else {
            holder.b_status.setText("Cancel");
        }
        Domain d=new Domain();
       Picasso.get().load(d.getProfile_img()+currentItem.getShop_image())
               .fit()
              .centerCrop()
               .into(holder.imageView);
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Order_details.class);
                intent.putExtra("item_id",currentItem.getItem_id());
                intent.putExtra("booking_status",currentItem.getBooking_status());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return booking_details_items.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name;
        ImageView imageView;
        TextView city;
        TextView item_name;
        TextView b_date;
        TextView booking_amount;
        CardView details;
        TextView b_status;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name=itemView.findViewById(R.id.shop_name);
            imageView=itemView.findViewById(R.id.shop_img);
            city=itemView.findViewById(R.id.city);
            item_name=itemView.findViewById(R.id.item_name);
            b_date=itemView.findViewById(R.id.b_date);
            booking_amount=itemView.findViewById(R.id.booking_amount);
            details=itemView.findViewById(R.id.details);
            b_status=itemView.findViewById(R.id.b_status);


        }
    }
    public Booking_details_adapter(Context context,ArrayList<Booking_details_item> booking_details_items){
        this.context=context;
        d=new Domain();
        this.booking_details_items=booking_details_items;

    }

}

