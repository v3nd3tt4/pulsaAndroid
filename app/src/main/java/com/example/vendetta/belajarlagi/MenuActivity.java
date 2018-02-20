package com.example.vendetta.belajarlagi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    public static MainActivity ma;
    private Button bkategori;
    private Button bbeli;
    private Button bbelumbayar;
    protected Cursor cursor;
    private String sKeuntungan;
    DatabaseAdapter dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dbHelper = new DatabaseAdapter(this);
//        bkategori =(Button)findViewById(R.id.btnkategori);
//        bkategori.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//        });


        getKeuntungan();
        bbeli = (Button)findViewById(R.id.btnpembelian);
        bbeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListPulsa.class);
                startActivity(i);
            }
        });

        bbelumbayar =(Button)findViewById(R.id.btnbelumbayar);
        bbelumbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BelumLunas.class);
                startActivity(i);
            }
        });


        GridView gridView = findViewById(R.id.gridview);
        final GridAdapter gridAdapter = new GridAdapter(this, new GridFunction[] {
                new GridFunction("Rp. "+sKeuntungan, "Keuntungan"),
                new GridFunction("Rp. 30000", "Piutang"),
                new GridFunction("Rp. 25000", "Total Transaksi")

        }  );
        gridView.setAdapter(gridAdapter);


    }

    public void getKeuntungan(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT sum(harga_jual) - sum(modal) as keuntungan FROM tb_pembelian where status = 'lunas'", null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            sKeuntungan = cursor.getString(0).toString();
        }
    }

    public  Integer getPiutang(){
        return 1;
    }

}
