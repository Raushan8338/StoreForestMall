package com.storeforest.storeforest.order_details;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;

import java.util.ArrayList;

public class Order_adapter extends RecyclerView.Adapter<Order_adapter.BookingViewHolder> {
private ArrayList<Order_item> order_items;
private Context context;
private String months[] = {"Jan" , "Feb" , "Mar" , "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct",
        "Nov", "Dec"};
@NonNull
@Override
public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_irem,parent,false);

    BookingViewHolder bvh=new BookingViewHolder(view);
    return bvh;
      }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final Order_adapter.BookingViewHolder holder, int position) {
        final Order_item currentItem = order_items.get(position);

   // holder.start.setText(currentItem.getStart());
    //holder.end.setText(currentItem.getEnd());
        Domain d=new Domain();

        holder.shop_name.setText(currentItem.getItem_name());
        holder.item_amount.setText(currentItem.getBooking_amount());
        holder.payment_mode.setText(currentItem.getPayment_mode());
        if (currentItem.getBooking_status().equals("3")){
            holder.cancel.setText("Canceled");
            holder.b_status.setText("Running");
        }
        else if (currentItem.getBooking_status().equals("4")){
            holder.cancel.setText("Complete");
        }
        else if (currentItem.getBooking_status().equals("5")){
            holder.cancel.setText("Canceled");
            holder.b_status.setText("Canceled");
        }
        else if (currentItem.getBooking_status().equals("6")){
            holder.cancel.setText("Canceled");
            holder.b_status.setText("Cancled");
        }
        else if (currentItem.getBooking_status().equals("2")){
            holder.cancel.setText("Canceled");
            holder.b_status.setText("Priparing");
        }

        else {
            holder.cancel.setText("Cancel");
            holder.cancel.setTextColor(context.getColor(R.color.red));
            holder.cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final Dialog da = new Dialog(context);
                    da.setContentView(R.layout.cancel_resion);
                    final Button cancels = da.findViewById(R.id.cancels);
                    final EditText message = da.findViewById(R.id.messages);
                    final ImageView close_dailog = da.findViewById(R.id.close_dailog);
                    close_dailog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            da.dismiss();
                        }
                    });
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait");
                    progressDialog.setCancelable(false);
                    cancels.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressDialog.show();
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            String url = "https://trendingstories4u.com/android_app/cancel_booking.php?b_id="+currentItem.getB_id()+"&b_item_id="+currentItem.getB_item_id()+"&b_amount="+currentItem.getBooking_amount()+"&message="+message.getText().toString();
                            Log.e("car_ad",url);
                            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    Log.e("response",response);
                                    //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(context, Cancel_order_thanku.class);
                                    intent.putExtra("booking_amount",currentItem.getBooking_amount());
                                    intent.putExtra("b_id",currentItem.getB_id());
                                    context.startActivity(intent);
                                    ((Activity)context).finish();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            requestQueue.add(stringRequest1);

                        }
                    });
                    da.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    da.show();
                }
            });
        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem.getBooking_status().equals("1")){
                    Intent intent=new Intent(context,Pending_order.class);
                    intent.putExtra("booking_amount",currentItem.getBooking_amount());
                    context.startActivity(intent);
                }
                else if (currentItem.getBooking_status().equals("2")){
                    Intent intent=new Intent(context,Thank_you_order.class);
                    intent.putExtra("booking_amount",currentItem.getBooking_amount());
                    context.startActivity(intent);
                }
                else {
                    Intent intent=new Intent(context,Cancel_order_thanku.class);
                    intent.putExtra("booking_amount",currentItem.getBooking_amount());
                    intent.putExtra("b_id",currentItem.getB_id());
                    context.startActivity(intent);
                }


            }
        });
        holder.how_much.setText(currentItem.getQuantity()+" " + currentItem.getAttribute());
        Picasso.get().load(d.getProfile_img()+currentItem.getItem_image())
                .fit()
                .centerCrop()
                .into(holder.imageView);
}

    @Override
    public int getItemCount() {
        return order_items.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name;
        ImageView imageView;
        TextView city;
        TextView item_amount;
        TextView how_much;
        TextView payment_mode;
        TextView b_status;
        CardView details;
        TextView cancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_name=itemView.findViewById(R.id.shop_name);
            imageView=itemView.findViewById(R.id.van_image);
            city=itemView.findViewById(R.id.city);
            item_amount=itemView.findViewById(R.id.item_amount);
            how_much=itemView.findViewById(R.id.how_much);
            payment_mode=itemView.findViewById(R.id.payment_mode);
            b_status=itemView.findViewById(R.id.b_status);
            details=itemView.findViewById(R.id.details);
            cancel=itemView.findViewById(R.id.cancel);

        }
    }
    public Order_adapter(Context context,ArrayList<Order_item> order_items){
        this.context=context;
        this.order_items=order_items;

    }

}

