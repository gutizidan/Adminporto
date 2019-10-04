package com.example.adminporto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.JadwalViewHolder> {
    private Context mContext;
    private List<Model> dataList;

    public Adapter(Context mContext, List<Model> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.recyclerlist, parent, false);
        return new JadwalViewHolder(view);
    }
    @Override
    public void onBindViewHolder(JadwalViewHolder holder, int position) {
        final Model surah = dataList.get(position);


        holder.title.setText(surah.getTitle());


    }

    @Override
    public int getItemCount() {
        return dataList.size();

    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public class JadwalViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public JadwalViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.Titleberita);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    // set title dialog
                    alertDialogBuilder.setTitle("Log out dari aplikasi?");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Klik Ya untuk Hapus "+ dataList.get(getAdapterPosition()).getTitle()+"!")
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // jika tombol diklik, maka akan menutup activity ini
                                    AndroidNetworking.post("http://192.168.6.233/Latihan/delete1.php")
                                            .addBodyParameter("title", title.getText().toString().trim())
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
                                                            Toast.makeText(mContext, "Delete Sukses", Toast.LENGTH_SHORT).show();

                                                        } else {
                                                            Toast.makeText(mContext, "Delete Gagal", Toast.LENGTH_SHORT).show();
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
                            })
                            .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // jika tombol ini diklik, akan menutup dialog
                                    // dan tidak terjadi apa2
                                    dialog.cancel();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();


                }
            });
        }
    }
}
