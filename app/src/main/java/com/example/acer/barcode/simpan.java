package com.example.acer.barcode;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class simpan extends AppCompatActivity {
EditText contex,ednama;
    Button simpan;
    SharedPreferences sp;
    SharedPreferences.Editor spe;

    String url = "http://192.168.20.29/phpqrcode/simpan_siswa.php";
    public static final String KEY_KODE = "nim";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_NAMA_SISWA = "nama_siswa";
    String item_name;

    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpan);
        contex = (EditText) findViewById(R.id.etContents);
        ednama = (EditText) findViewById(R.id.nama);
        sp = this.getSharedPreferences("isi_data", 0);
        spe = sp.edit();
        String b = sp.getString("isi_data","");
        contex.setText(b);
        simpan = (Button) findViewById(R.id.simpan);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
    }

    public void insert(View v) {
        PD.show();
        final String username = contex.getText().toString();
        final String nama_siswa = ednama.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Data Inserted Successfully",
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "failed to insert", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAMA,username);
                params.put(KEY_NAMA_SISWA,nama_siswa);
                return params;
            }
        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
        //MyApplication.getInstance().addToReqQueue(postRequest);
    }
}
