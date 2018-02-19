package com.example.vendetta.belajarlagi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class MenuActivity extends AppCompatActivity {

    public static MainActivity ma;
    private Button bkategori;
    private Button bbeli;
    private Button bbelumbayar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
//        bkategori =(Button)findViewById(R.id.btnkategori);
//        bkategori.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//        });

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
        final GridAdapter gridAdapter = new GridAdapter(this, sData);
        gridView.setAdapter(gridAdapter);

    }

    private GridFunction[] sData = {
            new GridFunction("Rp. 10.000", "Belum Bayar"),
            new GridFunction("Rp. 20.000", "Modal"),
            new GridFunction("Rp. 30.000", "Transaksi"),
            new GridFunction("Rp. 30.000", "Transaksi"),
            new GridFunction("Rp. 30.000", "Transaksi"),
            new GridFunction("Rp. 30.000", "Transaksi"),
    };
}
