package com.project1.eventlogs;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.*;
import org.json.*;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;

import static java.lang.System.err;

public class ListItem extends AppCompatActivity {

    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText edittextsearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        listView=(ListView)findViewById(R.id.lv_items);
        edittextsearch = findViewById(R.id.searchid);
        getItems();
    }



    private void getItems() {
        loading = ProgressDialog.show(this, "Loading", "Please Wait!...", false, true);
        StringRequest stringRequesr = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzBT-rVkxqThYFFuLz_Amp27rWIdkv0quryPbBsHw1ykhOenDjR-m5-EQ/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequesr.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequesr);

    }

private void parseItems(String jsonResponse){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");

            for(int i=0;i<jarray.length();i++){

            JSONObject jo=jarray.getJSONObject(i);
            String name =jo.getString("username");
            String title =jo.getString("title");
            String menu =jo.getString("menu");

            HashMap<String, String> item = new HashMap<>();
            item.put("username",name);
            item.put("title",title);
            item.put("menu",menu);
            list.add(item);

            
            }

        }catch(JSONException e) {
            e.printStackTrace();
        }
        adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
        new String[]{"username","title","menu"},new int[]{R.id.textView1,R.id.textView2,R.id.textView3});

        listView.setAdapter(adapter);
        loading.dismiss();

        edittextsearch .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ListItem.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        }
}