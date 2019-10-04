package com.example.adminporto;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText judul, url,des;
    private Button submit,delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Update berita");
        judul = findViewById(R.id.judul);
        url = findViewById(R.id.url);
        des = findViewById(R.id.desc);
        submit = findViewById(R.id.submit);
        delete = findViewById(R.id.delete);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post("http://192.168.6.233/Latihan/register1.php")
                        .addBodyParameter("title", judul.getText().toString().trim())
                        .addBodyParameter("urlgambar", url.getText().toString().trim())
                        .addBodyParameter("deskripsi", des.getText().toString().trim())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                try {
                                    JSONObject respon = response.getJSONObject("hasil");
                                    boolean sukses = respon.getBoolean("respon");
//                                    System.out.println("res " + respon);
//                                    System.out.println("Sukses " + suksess);
                                    if (sukses == true) {
                                        Toast.makeText(getApplicationContext(), "Upload Sukses", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Upload Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                                System.out.println("ErrorReg " + error);
                            }
                        });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), delete.class);
                startActivity(i);

            }
        });



    }
}
