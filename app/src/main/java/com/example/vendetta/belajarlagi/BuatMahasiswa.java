package com.example.vendetta.belajarlagi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuatMahasiswa extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button button1, button2;
    EditText text1, text2, text3, text4, text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_mahasiswa);

        dbHelper = new DataHelper(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        text5 = (EditText) findViewById(R.id.editText5);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String   stext1, stext2, stext3, stext4, stext5;
                stext1 = text1.getText().toString();
                stext2 = text2.getText().toString();
                stext3 = text3.getText().toString();
                stext4 = text4.getText().toString();
                stext5 = text5.getText().toString();

                if ( stext1.equals("") || stext2.equals("") || stext3.equals("") || stext4.equals("") || stext5.equals("")){
                    Toast.makeText(BuatMahasiswa.this,"Harus Diisi!", Toast.LENGTH_SHORT ).show();
                }else{
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into mahasiswa(no, nama, tgl, jk, jurusan) values('" +
                            stext1 + "','" +
                            stext2 + "','" +
                            stext3 + "','" +
                            stext4 + "','" +
                            stext5 + "')");
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                    MainActivity.ma.RefreshList();
                    finish();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
}
