package com.storeforest.storeforest.second_hand_product.sec_upload_img;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.second_hand_product.Second_hand_thank_you;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.mobile;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.Login.user_name;

public class Add_second_hand_img2 extends AppCompatActivity {
    LinearLayout adhar_front;
    LinearLayout adhar_back;
    ImageView adhar;
    ImageView back;
    private ChoosePhotoHelper choosePhotoHelper;
    private ChoosePhotoHelper choosePhotoHelper1;
    Bitmap bitmap;
    Bitmap bitmap1;
    String image = "";
    String image1 = "";
    Button upload;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_second_hand_img2);
        adhar=findViewById(R.id.select_image);
        upload=findViewById(R.id.upload);
        setTitle("Add Item Image");
        skip=findViewById(R.id.skip);
       // Toast.makeText(this, getIntent().getStringExtra("p_id"), Toast.LENGTH_SHORT).show();
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        // String url = d.getAdhar_upload()+"?user_id="+sharedPreferences.getString(user_id, null);
        adhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoHelper.showChooser();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Add_second_hand_img2.this,Second_hand_thank_you.class);
                startActivity(intent);
                finish();
            }
        });
        // sharedPreferences1 = getSharedPreferences(MYProfile, Context.MODE_PRIVATE);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adhar.getDrawable() != null) {
                    bitmap = ((BitmapDrawable) adhar.getDrawable()).getBitmap();
                    image = getStringImage(bitmap);

                    RequestQueue q =  Volley.newRequestQueue(Add_second_hand_img2.this);
                    Domain d = new Domain();
                    final ProgressDialog progressDialog=new ProgressDialog(Add_second_hand_img2.this);
                    progressDialog.setMessage("Please Wait !");
                    progressDialog.show();
                    String url ="https://trendingstories4u.com/android_app/add_second_hand_multi_img.php?user_id="+sharedPreferences.getString(user_id, null)+"&name="+sharedPreferences.getString(user_name,null)+"&contact_no="+sharedPreferences.getString(mobile,null);
                    Log.e("urls",url);
                    //  Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
                    final StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                                Log.e("responses",response);
                                progressDialog.dismiss();
                                Toast.makeText(Add_second_hand_img2.this, "Thank you", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Add_second_hand_img2.this, Add_second_hand_img3.class);
                                intent.putExtra("p_id",getIntent().getStringExtra("p_id"));
                                startActivity(intent);
                                finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            // makeToast("Please Check Your Internet Connection");
                        }
                    }

                    ){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("signature_imae",image);
                            params.put("p_id",getIntent().getStringExtra("p_id"));

                            //Log.e("payment",params.toString());
                            return params;
                        }
                    };

                    q.add(stringRequest);


                } else {
                    image = "";
                }
            }
        });
        choosePhotoHelper = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        Glide.with(adhar)
                                .load(photo)
                                .apply(RequestOptions.placeholderOf(R.drawable.ic_add))
                                .into(adhar);
                    }
                });

    }
    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
