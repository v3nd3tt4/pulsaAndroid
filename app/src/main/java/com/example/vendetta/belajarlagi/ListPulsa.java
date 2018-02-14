package com.example.vendetta.belajarlagi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ListPulsa extends AppCompatActivity {


    private Button bTambah;
    String[] daftar;
    ListView listPembelian01;
    protected Cursor cursor;
    DatabaseAdapter dbHelper;
    public static ListPulsa ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pulsa);

        bTambah = (Button)findViewById(R.id.bTambahPembelian);
        bTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BeliPulsa1.class);
                startActivity(i);
            }
        });

        ma = this;
        dbHelper = new DatabaseAdapter(this);
        RefreshList();
    }

    public void RefreshList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pembelian order by id_pembelian DESC",null);
        daftar = new String[cursor.getCount()];

        cursor.moveToFirst();
        final String sfaf[] = new String[cursor.getCount()];
        int nomor=1;
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            sfaf[cc] = cursor.getString(0).toString();
            daftar[cc] = nomor+". "+cursor.getString(2).toString()+"\n    "+cursor.getString(1).toString();
            nomor++;
        }
        listPembelian01 = (ListView)findViewById(R.id.listPembelian);
        listPembelian01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listPembelian01.setSelected(true);
        listPembelian01.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                //final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                //final String id_pembelian = ;
                final String selection = sfaf[arg2];
                //Toast.makeText(getApplicationContext(),sfaf[arg2], Toast.LENGTH_SHORT).show();
                final CharSequence[] dialogitem = {"Lihat", "Hapus", "Lunas"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ListPulsa.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), ListPembelian.class);
                                i.putExtra("id_pembelian", selection);
                                startActivity(i);
                                break;
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), UbahMahasiswa.class);
                                in.putExtra("id_pembelian", selection);
                                startActivity(in);
                                break;
                            case 2 :
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
                                String strDate = mdformat.format(calendar.getTime());

                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("update tb_pembelian set status = 'lunas', tgl_bayar = '"+strDate+"' where id_pembelian = '"+selection+"'");
                                Toast.makeText(getApplicationContext(), "Berhasil Dibayar", Toast.LENGTH_LONG).show();
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)listPembelian01.getAdapter()).notifyDataSetInvalidated();
    }

}
