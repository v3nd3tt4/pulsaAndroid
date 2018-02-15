package com.example.vendetta.belajarlagi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vendetta on 15/02/18.
 */

public class BelumBayar extends AppCompatActivity {

    String[] daftarBelumBayar;
    ListView listPembelian01;
    protected Cursor cursor;
    DatabaseAdapter dbHelper;
    public static BelumBayar ma;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pulsa);

        /*listPembelian01 = (ListView)findViewById(R.id.listPembelianBelumBayar);

        ma = this;
        dbHelper = new DatabaseAdapter(this);
        RefreshList();

        listPembelian01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
                String id_pem = map.get("id").toString();
            }
        });*/
    }

    public void RefreshList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pembelian where status = 'belum lunas' " +
                "order by id_pembelian DESC", null);
        daftarBelumBayar = new String[cursor.getCount()];

        cursor.moveToFirst();
        final String sfaf[] = new String[cursor.getCount()];

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            try {
                HashMap<String, String> showData = new HashMap<String, String>();
                showData.put("id", cursor.getString(0).toString());
                showData.put("nama", cursor.getString(2).toString());
                showData.put("nomor", cursor.getString(1).toString());
                showData.put("status", cursor.getString(7).toString());
                list.add(showData);
            } catch (Exception e) {

            }
        }

        ListAdapter adapter = new SimpleAdapter(
                BelumBayar.this, list, R.layout.list,
                new String[]{"nama", "nomor", "status"},
                new int[]{R.id.tnama, R.id.tnomor, R.id.tstatus});

        listPembelian01.setAdapter(adapter);
    }
}
