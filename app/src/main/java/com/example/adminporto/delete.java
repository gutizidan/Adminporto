package com.example.adminporto;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class delete extends AppCompatActivity {
    private List<Model> getKudus = new ArrayList<>();
    private Adapter allLeaguesAdapter;
    private ProgressDialog mProgress;
    private RecyclerView rvSurah;
    SwipeRefreshLayout swipeLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        rvSurah = findViewById(R.id.recycler_view);
        swipeLayout = findViewById(R.id.swipe_container);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mProgress.show();
        fetchscheduleApi();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                getKudus.clear();
                fetchscheduleApi();
                // To keep animation for 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeLayout.setRefreshing(false);
                    }
                }, 1500);
                Toast.makeText(getApplicationContext(), "Data is Up to date!", Toast.LENGTH_SHORT).show();// Delay in millis
            }
        });



        setup();




    }

    private void setup(){ allLeaguesAdapter = new Adapter(this, getKudus);
        rvSurah.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSurah.setHasFixedSize(true);
        rvSurah.setAdapter(allLeaguesAdapter);}

    private void fetchscheduleApi(){
        AndroidNetworking.get("http://192.168.6.233/Latihan/getdata1.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasilList = response.getJSONArray("result");
                            for (int i = 0; i < hasilList.length(); i++) {
                                JSONObject hasil = hasilList.getJSONObject(i);
                                Model item = new Model();
                                item.setTitle(hasil.getString("title"));
                                item.setUrl(hasil.getString("urlgambar"));
                                item.setDesc(hasil.getString("deskripsi"));
                                System.out.println("sss" + hasil.getString("deskripsi"));

                                getKudus.add(item);}
                            mProgress.dismiss();
                            allLeaguesAdapter.notifyDataSetChanged();

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }}
                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError+"vfbnm");
                    }
                });
    }
}