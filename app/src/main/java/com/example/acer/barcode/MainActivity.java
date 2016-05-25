package com.example.acer.barcode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private String codeFormat, codeContent;
    private TextView formatTxt, contentTxt;
    EditText contex;
    Button captureButton;


    TextView tvContents;
    TextView tvFormat;

    Activity activity;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        captureButton = (Button) findViewById(R.id.capture);
        captureButton.setOnClickListener(listener);
        contex = (EditText) findViewById(R.id.etContents);
        tvContents = (TextView) findViewById(R.id.tvContents);
        tvFormat = (TextView) findViewById(R.id.tvFormat);
        Button btn = (Button) findViewById(R.id.button);
        sp = this.getSharedPreferences("isi_data", 0);
        spe = sp.edit();

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(MainActivity.this, list_data.class);
                startActivity(list);
            }
        });*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    IntentResult intentResult =
                            IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

                    if (intentResult != null) {
                        //String contents = intent.getStringExtra("SCAN_RESULT");
                        String contents = intentResult.getContents();
                        String format = intentResult.getFormatName();

                        Intent simpan = new Intent(MainActivity.this, com.example.acer.barcode.simpan.class);
                        startActivity(simpan);
                        //contex.setText(contents.toString());

                        tvContents.setText(contents.toString());
                        tvFormat.setText(format.toString());
                      //  String inp_user = tvContents.getText().toString();
                        spe.putString("isi_data", contents.toString());
                        spe.commit();
                        //this.elemQuery.setText(contents);
                        //this.resume = false;
                        Log.d("SEARCH_EAN", "OK, EAN: " + contents + ", FORMAT: " + format);
                    } else {
                        Log.e("SEARCH_EAN", "IntentResult je NULL!");
                    }
                } else if (resultCode == activity.RESULT_CANCELED) {
                    Log.e("SEARCH_EAN", "CANCEL");
                }
        }
    }

  /*  public void tambah(View v){
        Intent tambah_data = new Intent(MainActivity.this,simpan.class);
        startActivity(tambah_data);
    }

    public void create(View v){
        Intent qr = new Intent(MainActivity.this,create_qrcode.class);
        startActivity(qr);
    }*/

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.initiateScan();
        }
    };

   /* public void update(View v){
        Intent up = new Intent(MainActivity.this,Update.class);
        startActivity(up);
    }*/
}
