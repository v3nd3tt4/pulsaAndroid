package com.example.vendetta.belajarlagi;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BelumLunas extends AppCompatActivity {

    private Button bTambah;
    String[] daftar;
    ListView listPembelian01;
    protected Cursor cursor;
    DatabaseAdapter dbHelper;
    public static BelumLunas ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belum_lunas);


        listPembelian01 = (ListView)findViewById(R.id.listBelumLunas);

        ma = this;
        dbHelper = new DatabaseAdapter(this);
        RefreshList();

        listPembelian01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
                String id_pem = map.get("id").toString();
                TampilDialog(id_pem);
            }
        });

    }

    public void RefreshList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pembelian " +
                "where status = 'belum lunas'  order by nama ASC", null);
        daftar = new String[cursor.getCount()];

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
                showData.put("status", "Rp. "+cursor.getString(4).toString());
                list.add(showData);
            } catch (Exception e) {

            }
        }

        ListAdapter adapter = new SimpleAdapter(
                BelumLunas.this, list, R.layout.list,
                new String[]{"nama", "nomor", "status"},
                new int[]{R.id.tnama, R.id.tnomor, R.id.tstatus});

        listPembelian01.setAdapter(adapter);
    }

    public  void TampilDialog(final String s){
        final CharSequence[] dialogitem = {"Detail", "Lunas", "Belum Lunas"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BelumLunas.this);
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                    case 0 :
                        Intent i = new Intent(getApplicationContext(), ListPembelian.class);
                        i.putExtra("id_pembelian", s);
                        startActivity(i);
                        break;
                    case 1 :
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
                        String strDate = mdformat.format(calendar.getTime());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("update tb_pembelian set status = 'lunas', tgl_bayar = '"+strDate+"' where id_pembelian = '"+s+"'");
                        Toast.makeText(getApplicationContext(), "Berhasil Dibayar", Toast.LENGTH_LONG).show();
                        RefreshList();
                        break;
                    case 2 :
                        calendar = Calendar.getInstance();
                        mdformat = new SimpleDateFormat("yyyy-MM-dd ");
                        strDate = mdformat.format(calendar.getTime());
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("update tb_pembelian set status = 'belum lunas', tgl_bayar = '"+strDate+"' where id_pembelian = '"+s+"'");
                        Toast.makeText(getApplicationContext(), "Berhasil Diset Belum Lunas", Toast.LENGTH_LONG).show();
                        RefreshList();
                        break;
                }
            }
        });
        builder.create().show();
    }
}
