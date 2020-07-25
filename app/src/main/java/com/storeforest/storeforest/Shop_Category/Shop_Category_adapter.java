package com.storeforest.storeforest.Shop_Category;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.shop_info.Kirana_stores;

import java.util.ArrayList;
import java.util.List;

public class Shop_Category_adapter  extends RecyclerView.Adapter<Shop_Category_adapter.ProductViewHolder> {

    private ArrayList<Shop_category_item> shop_category_items;
    private List<Shop_category_item> exampleListFull;
    //private Domain d;
    private Context context;
    private Boolean x;
    TextView certificate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences1;
    public static final String MyPrefid = "MyPrefid";
    public static final String userId = "userId";
    public static final String shop_name = "shop_name";
    public static final String shop_image = "shop_image";
    public static final String min_order = "min_order";


    @NonNull
    @Override
    public Shop_Category_adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_category_item, parent, false);
        Shop_Category_adapter.ProductViewHolder lvh = new Shop_Category_adapter.ProductViewHolder(view);
        return lvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Shop_Category_adapter.ProductViewHolder holder, int position) {
        final Shop_category_item currentItem = shop_category_items.get(position);
        Domain d=new Domain();

      /*  Picasso.get().load(d.getProfile_img() + currentItem.getImage())
                .fit()
                .centerCrop()
                .into(holder.imageView);*/
      holder.value.setText(currentItem.getCategory_name());
        holder.stext.setText(currentItem.getCategory());
        holder.value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Kirana_stores.class);
                intent.putExtra("title",currentItem.getCategory_name());
                intent.putExtra("value",currentItem.getValue());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shop_category_items.size();
    }

    /* @Override
     public Filter getFilter() {
         return exampleFilter;
     }
 */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

      ImageView imageView;
      TextView stext;
      TextView value;
      LinearLayout kirana;
      GridView gridView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
           // gridView=itemView.findViewById(R.id.gridView);
          // imageView=itemView.findViewById(R.id.image);
          // stext=itemView.findViewById(R.id.stext);
          ///  kirana=itemView.findViewById(R.id.kirana);
           value=itemView.findViewById(R.id.value);

        }
    }

    public Shop_Category_adapter(Context context, ArrayList<Shop_category_item> shop_category_items, Boolean x) {
        this.context = context;
        // d = new Domain();
        this.shop_category_items = shop_category_items;
        this.x = x;
    }


}

