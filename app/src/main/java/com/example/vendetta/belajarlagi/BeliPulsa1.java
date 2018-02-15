package com.example.vendetta.belajarlagi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BeliPulsa1 extends AppCompatActivity {
    private static MenuActivity ma;
    DatabaseAdapter dbHelper;
    Button simpan;
    EditText nomor, nama, modal, harga_jual, tgl_beli, tgl_bayar, status, kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_pulsa);

        dbHelper = new DatabaseAdapter(this);
        nomor = (EditText) findViewById(R.id.edNomor);
        nama = (EditText) findViewById(R.id.edNama);
        modal = (EditText) findViewById(R.id.edModal);
        harga_jual = (EditText) findViewById(R.id.edJual);
        tgl_beli = (EditText) findViewById(R.id.edTglBeli);
        kategori = (EditText) findViewById(R.id.edKategori);
        simpan = (Button) findViewById(R.id.bSimpan);

        simpan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                String   snomor, snama, smodal, shargajual, stglbeli, stglbayar, skategori, sstatus;
                snomor = nomor.getText().toString();
                snama = nama.getText().toString();
                smodal = modal.getText().toString();
                shargajual = harga_jual.getText().toString();
                stglbeli = tgl_beli.getText().toString();
                skategori = kategori.getText().toString();

                if ( snomor.equals("") || snama.equals("") || smodal.equals("") || shargajual.equals("")
                        || stglbeli.equals("")  || skategori.equals("") ){
                    Toast.makeText(BeliPulsa1.this,"Semua Data Harus Diisi!", Toast.LENGTH_SHORT ).show();
                }else{
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into tb_pembelian (nomor, nama, modal, harga_jual, tgl_beli, tgl_bayar, kategori, status) values('" +
                            snomor + "','" +
                            snama + "','" +
                            smodal + "','" +
                            shargajual + "','" +
                            stglbeli + "','0000-00-00','" +
                            skategori + "','belum lunas')");
                    Toast.makeText(getApplicationContext(), "Berhasil Disimpan", Toast.LENGTH_LONG).show();
                    ListPulsa.ma.RefreshList();
                    finish();
                }
            }
        });
    }
}
