package com.example.acer.barcode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ACER on 2015-12-23.
 */
public class Custom_List extends ArrayAdapter<String> {
    private String[] nim;
    private String[] nama;
    private String[] siswa;
    private Activity context;


    public Custom_List(Activity context, String[] nim, String[] nama, String[] siswa)
    {
        super(context, R.layout.activity_list_data, nim);
        this.context    = context;
        this.nim        = nim;
        this.nama     = nama;
        this.siswa     = siswa;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater     = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View listViewItem           = inflater.inflate(R.layout.data, null, true);

        TextView tvId               = (TextView) listViewItem.findViewById(R.id.tv_id);
        TextView tvTitle            = (TextView) listViewItem.findViewById(R.id.tv_title);
        TextView tvsiswa            = (TextView) listViewItem.findViewById(R.id.tv_author);

        tvId.setText(nim[position]);
        tvTitle.setText(nama[position]);
        tvsiswa.setText(siswa[position]);
        return  listViewItem;
    }
}
