package com.example.vendetta.belajarlagi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ListPembelian extends AppCompatActivity {
    protected Cursor cursor;
    DatabaseAdapter dbHelper;
    TextView nomor, nama, modal, harga_jual, tgl_beli, tgl_bayar, status, kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pembelian);
        dbHelper = new DatabaseAdapter(this);

        nomor = (TextView) findViewById(R.id.textView1);
        nama = (TextView) findViewById(R.id.textView2);
        modal = (TextView) findViewById(R.id.textView4);
        harga_jual = (TextView) findViewById(R.id.textView3);
        tgl_beli = (TextView) findViewById(R.id.textView6);
        tgl_bayar = (TextView) findViewById(R.id.textView5);
        status = (TextView) findViewById(R.id.textView9);
        kategori = (TextView) findViewById(R.id.textView10);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pembelian WHERE id_pembelian = '" +
                getIntent().getStringExtra("id_pembelian") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            nomor.setText(cursor.getString(1).toString());
            nama.setText(cursor.getString(2).toString());
            modal.setText(cursor.getString(3).toString());
            harga_jual.setText(cursor.getString(4).toString());
            tgl_beli.setText(cursor.getString(5).toString());
            tgl_bayar.setText(cursor.getString(6).toString());
            status.setText(cursor.getString(7).toString());
            kategori.setText(cursor.getString(8).toString());
        }
    }
}
