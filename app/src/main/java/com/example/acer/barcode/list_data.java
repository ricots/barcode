package com.example.acer.barcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class list_data extends AppCompatActivity implements ListView.OnItemClickListener  {
    public static final String TAG_JSON_ARRAY = "daftar_siswa";
    public static final String KEY_NIM = "nim";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_SISWA = "nama_siswa";
    String url_ALL = "http://192.168.20.30/phpqrcode/list_siswa.php";
    private ListView listView;
    private String JSON_STRING;
    ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        listView      = (ListView) findViewById(R.id.lv_book);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(KEY_NIM);
                String name = jo.getString(KEY_NAMA);
                String siswa = jo.getString(KEY_SISWA);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(KEY_NIM,id);
                employees.put(KEY_NAMA,name);
                employees.put(KEY_SISWA,siswa);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                list_data.this, list, R.layout.data,
                new String[]{KEY_NIM,KEY_NAMA, KEY_SISWA},
                new int[]{R.id.tv_id, R.id.tv_title, R.id.tv_author});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(list_data.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(url_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent intent = new Intent(this, list_data.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(KEY_NIM).toString();
        intent.putExtra(KEY_NAMA,empId);
        startActivity(intent);*/
        String nim_siswa = ((TextView) view.findViewById(R.id.tv_id)).getText().toString();
        String nama_siswa = ((TextView) view.findViewById(R.id.tv_title)).getText().toString();
        String _siswa = ((TextView) view.findViewById(R.id.tv_author)).getText().toString();
        Intent in = new Intent(getApplicationContext(),Update.class);
        in.putExtra(KEY_NIM, nim_siswa);
        in.putExtra(KEY_NAMA, nama_siswa);
        in.putExtra(KEY_SISWA, _siswa);
        startActivity(in);
    }
}
