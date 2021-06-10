package com.project1.eventlogs;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.project1.eventlogs.receiver.NetworkStateChangeReceiver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.project1.eventlogs.receiver.AttenderImageConfiguration.ADD_USER_URL;


public class AttenderImageUpload extends AppCompatActivity implements View.OnClickListener{

    private TextView editTextUserName;
    private TextView editTextUserId;
    private ImageView imageViewUserImage;
    private Button buttonAddUser, buttonAddImage;
    String userImage;

    final String username = Attender_1.username;
    final String menu = Attender_1.menu;

    private int PICK_IMAGE_REQUEST = 1;

    Bitmap rbitmap;
    int disable = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attender_image_upload);


        imageViewUserImage = (ImageView) findViewById(R.id.iv_uphoto);


        buttonAddUser = (Button) findViewById(R.id.btn_add_user);
        buttonAddImage = (Button) findViewById(R.id.btn_image);

        buttonAddImage.setOnClickListener(this);
        buttonAddUser.setOnClickListener(this);



    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    private void addUser() {

        if (disable == 1) {
            buttonAddUser.setVisibility(View.VISIBLE);
        } else{
            final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
            final String userId = username;
            final String userName = menu;
            //Bitmap  rbitmap = getResizedBitmap(bitmap,500);

            //Log.e("null", "values" + userImage);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_USER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            Toast.makeText(AttenderImageUpload.this, response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AttenderSuccess.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AttenderImageUpload.this, "Error Occured", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("action", "insert");
                    params.put("uId", userId);
                    params.put("uName", userName);
                    params.put("uImage", userImage);

                    return params;
                }

            };

            int socketTimeout = 30000; // 30 seconds. You can change it
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            stringRequest.setRetryPolicy(policy);


            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);
        }

    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        disable=0;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                rbitmap = getResizedBitmap(bitmap, 2000);//Setting the Bitmap to ImageView
                userImage = getStringImage(rbitmap);
                imageViewUserImage.setImageBitmap(rbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAddUser) {
            addUser();
        }
        if (v == buttonAddImage) {
            showFileChooser();
        }
    }
}