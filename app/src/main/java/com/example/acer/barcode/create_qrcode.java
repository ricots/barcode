package com.example.acer.barcode;


import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.UUID;

public class create_qrcode extends AppCompatActivity implements OnClickListener {
    private ImageView gambar;
    Button create,save;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qrcode);
        Button button1 = (Button) findViewById(R.id.button1);
        gambar = (ImageView) findViewById(R.id.imageView1);
        input = (EditText) findViewById(R.id.qrInput);
        button1.setOnClickListener(this);
        save = (Button) findViewById(R.id.save_image);

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gambar.setDrawingCacheEnabled(true);
                String imgSaved= MediaStore.Images.Media.insertImage(
                        getContentResolver(),gambar.getDrawingCache(), UUID.randomUUID().toString()+".png","drawing");
                Toast savedToast= Toast.makeText(getApplicationContext(), "gambar tersimpan", Toast.LENGTH_SHORT);
                savedToast.show();

            }
        });
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                EditText qrInput = (EditText) findViewById(R.id.qrInput);
                String qrInputText = qrInput.getText().toString();
              //  Log.v(LOG_TAG, qrInputText);

                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3/4;

                //Encode with a QR Code image
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                    myImage.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }


                break;
        }
    }
}
