package com.storeforest.storeforest.navigation;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.Jawellery.Jawellry_shop_details;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Select_Location;
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.hotel.Hotel_list_display;
import com.storeforest.storeforest.image_slider.Image_slider_adapter;
import com.storeforest.storeforest.image_slider.Image_slider_item;
import com.storeforest.storeforest.mobile.Mobile_display;
import com.storeforest.storeforest.notification.Live_chat;
import com.storeforest.storeforest.notification.Notifications;
import com.storeforest.storeforest.second_hand_product.Second_select_category;
import com.storeforest.storeforest.shop_info.Display_shop;
import com.storeforest.storeforest.shop_info.Favroite_shop;
import com.storeforest.storeforest.shop_info.Kirana_stores;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;
import static com.storeforest.storeforest.MainActivity.user_lat;
import static com.storeforest.storeforest.MainActivity.user_lng;
import static com.storeforest.storeforest.Verify_otp.MyPref;
import static com.storeforest.storeforest.Verify_otp.user_id;


public class DashboardFragment extends Fragment {
    private ArrayList<Integer> imageArray = new ArrayList<Integer>();
    String city, latitudeName, longitudeName;
    String [] cityNameList, latitudeList, longitudeList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    private Image_slider_adapter adapters;
    ArrayList<Image_slider_item> image_slider_items;
   ProgressDialog progressDialog;
   ImageView kirana;
   Context context;
   ImageView second_hand_product;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        kirana = v.findViewById(R.id.kirana);
        LinearLayout share=v.findViewById(R.id.share);
        LinearLayout cart=v.findViewById(R.id.cart);
        LinearLayout  livechat=v.findViewById(R.id.livechat);
        final TextView cart_badge=v.findViewById(R.id.cart_badge);
        LinearLayout  notification=v.findViewById(R.id.notification);
        ImageView grocery = v.findViewById(R.id.grocery);
        ImageView fruits_vagitable = v.findViewById(R.id.fruits_vagitable);
        ImageView hotel = v.findViewById(R.id.hotel);
        ImageView mobiles = v.findViewById(R.id.mobiles);
        ImageView jewellery = v.findViewById(R.id.jewellery);
        ImageView clothing = v.findViewById(R.id.clothing);
        CardView eggs = v.findViewById(R.id.eggs);
        CardView babycare = v.findViewById(R.id.babycare);
        second_hand_product=v.findViewById(R.id.second_hand_product);
        second_hand_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Second_select_category.class);
                startActivity(intent);
            }
        });
        fruits_vagitable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Kirana_stores.class);
                intent.putExtra("title", "Fruits & Vegitables");
                intent.putExtra("value", "fruits");
                startActivity(intent);
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Hotel_list_display.class);
                intent.putExtra("title", "Hotels & Restaurent");
                intent.putExtra("value", "hotel");
                startActivity(intent);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Mobile_display.class);
                intent.putExtra("title", "Mobile & Accesories");
                intent.putExtra("value", "mobile");
                startActivity(intent);
            }
        });
        jewellery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Jawellry_shop_details.class);
                intent.putExtra("title", "Jewelry");
                intent.putExtra("value", "jewelry");
                startActivity(intent);
            }
        });
        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Item Is Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        //profile_statuss=headerView.findViewById(R.id.profile_status);
        sharedPreferences = getContext().getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(address,null).isEmpty()) {
            final Dialog da = new Dialog(getActivity());
            da.setContentView(R.layout.selct_location);
            final Button submit_review=da.findViewById(R.id.select_loc);
            submit_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), Select_Location.class);
                    startActivity(intent);
                }
            });
            da.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            da.setCancelable(true);
            da.show();
            Toast.makeText(getContext(), "Please select Address", Toast.LENGTH_SHORT).show();
        }
        else {

            livechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Live_chat.class);
                    startActivity(intent);
                }
            });
          /*  share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Favroite_shop.class);
                    startActivity(intent);
                }
            });*/
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Display_cart_details.class);
                    startActivity(intent);
                }
            });
            notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Notifications.class);
                    startActivity(intent);
                }
            });
        }
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Display_shop.class);
                startActivity(intent);
            }
        });
        sharedPreferences1 = getContext().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        RequestQueue qq =  Volley.newRequestQueue(getContext());
        Domain d=new Domain();
        String urls = "https://trendingstories4u.com/android_app/cart_length.php?user_id="+sharedPreferences1.getString(user_id, null);
        Log.e("url : ", urls);
        StringRequest stringRequests = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    cart_badge.setText(js.getString("count"));
                } catch (Throwable throwable){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error",error.toString()+" " +url);
             //   makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        qq.add(stringRequests);

        /*Slider end*/

        return v;
    }
    }
