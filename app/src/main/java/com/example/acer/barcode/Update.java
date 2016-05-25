package com.example.acer.barcode;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {
    public static final String TAG_JSON_ARRAY = "daftar_siswa";
    public static final String KEY_NIM = "nim";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_SISWA = "nama_siswa";
    String url = "http://192.168.20.29/phpqrcode/update_siswa.php";
    String url_hapus = "http://192.168.20.29/phpqrcode/delete_siswa.php";
    String url_cari = "http://192.168.20.29/phpqrcode/cari_siswa.php?nim=";

    EditText nim,nama,siswa;
    ProgressDialog PD;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nim = (EditText) findViewById(R.id.edid);
        nama = (EditText) findViewById(R.id.ednama);
        siswa = (EditText) findViewById(R.id.edsiswa);

        Intent in = getIntent();

        // Get JSON values from previous intent
        String name = in.getStringExtra(KEY_NIM);
        String email = in.getStringExtra(KEY_NAMA);
        String mobile = in.getStringExtra(KEY_SISWA);

        // Displaying all values on the screen
        /*EditText lblName = (EditText) findViewById(R.id.edid);
        EditText lblEmail = (EditText) findViewById(R.id.ednama);
        EditText lblMobile = (EditText) findViewById(R.id.edsiswa);*/

        nim.setText(name);
        nama.setText(email);
        siswa.setText(mobile);

        PD = new ProgressDialog(this);
        PD.setMessage("please wait.....");
        PD.setCancelable(false);
    }

    public void update_siswa(View view) {
        PD.show();
        final String name = nim.getText().toString().trim();
        final String username = nama.getText().toString();
        final String nm_siswa = siswa.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        //item_et.setText("");
                        Toast.makeText(getApplicationContext(),
                                "Data update Successfully",
                                Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "failed to update", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NIM,name);
                params.put(KEY_NAMA,username);
                params.put(KEY_SISWA,nm_siswa);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
//MyApplication.getInstance().addToReqQueue(postRequest);
// Adding request to request queue
//MyApplication.getInstance().addToReqQueue(postRequest);

    }

    private void delete_siswa() {
        PD.show();
        final String name = nim.getText().toString().trim();
        final String username = nama.getText().toString();
        final String nm_siswa = siswa.getText().toString();

        StringRequest hapusRequest = new StringRequest(Request.Method.POST, url_hapus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        //item_et.setText("");
                        Toast.makeText(getApplicationContext(),
                                "Data delete Successfully",
                                Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "failed to delete", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NIM,name);
                //params.put(KEY_NAMA,username);
                //params.put(KEY_SISWA,nm_siswa);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(hapusRequest);

    }

    public void delete(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this student?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        delete_siswa();
                        // startActivity(new Intent(ViewEmployee.this,ViewAllEmployee.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void getData() {
        String id_nim = nim.getText().toString().trim();
        if (id_nim.equals("")) {
            Toast.makeText(this, "silahkan masukkan nim", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url_cari_siswa = url_cari+nim.getText().toString();

        StringRequest stringRequest = new StringRequest(url_cari_siswa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String kode_name="";
        String siswa_name="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(TAG_JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            kode_name = collegeData.getString(KEY_NAMA);
            siswa_name = collegeData.getString(KEY_SISWA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nama.setText(kode_name);
        siswa.setText(siswa_name);

    }

    public void cari(View v){
        getData();
    }
}
