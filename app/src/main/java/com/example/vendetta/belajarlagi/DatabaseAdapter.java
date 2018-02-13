package com.example.vendetta.belajarlagi;

/**
 * Created by vendetta on 13/02/18.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import android.os.Environment;
import android.content.ContentValues;


public class DatabaseAdapter extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;

    private static final String SQLITE_TABLE = "tb_kategori";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAMA = "nama_kategori";


    private static String DB_NAME = "db_pulsa.sqlite";

    //private static String DB_PATH = "/data/data/com.barcode.android/databases/";
    private File sdDir = Environment.getExternalStorageDirectory();
    private final Context mycontext;
    private static SQLiteDatabase myDataBase;
    private static String myPath = Environment.getExternalStorageDirectory() + File.separator + DB_NAME;

    public DatabaseAdapter (Context context)
    {
        super (context,myPath, null ,DATABASE_VERSION);
        this.mycontext=context;
        try{createdatabase();}catch(Exception e){}
    }

    @Override
    public void onCreate(SQLiteDatabase p1)
    {
        // TODO: Implement this method
    }

    @Override
    public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
    {
        // TODO: Implement this method
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        } super.close();
    }

    public void createdatabase() throws IOException
    {
        boolean dbexist = checkDataBase();
        if (dbexist) {
            opendatabase();
        } else {
            myDataBase = getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error createDataBase().");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = sdDir + File.separator + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }
        catch(SQLiteException e)
        {
            //database does't exist yet.
        }
        if(checkDB != null)
        {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        try{
            InputStream myinput = mycontext.getAssets().open(DB_NAME);
            String outfilename = sdDir + File.separator + DB_NAME;
            OutputStream myoutput = new FileOutputStream(outfilename);
            byte [] buffer = new byte [ 1024 ];
            int length;
            while ((length = myinput.read(buffer))> 0 ) {
                myoutput.write(buffer, 0 ,length);
            }
            myoutput.flush();
            myoutput.close();
            myinput.close();
        }catch(IOException e){
            throw new Error("Error copyDatabase(.)");
        }

    }

    public void opendatabase() throws SQLException, IOException
    {
        try{
            String mypath = sdDir + File.separator + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(mypath, null ,
                    SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }catch(SQLiteException e){
            throw new Error("Error openDataBase().");
        }

    }

    public boolean simpanData (String namax) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAMA, namax);
        db.insert("tb_kategori",null, values);
        return true;
    }

    public Cursor AllKategori() {
        Cursor mCursor = myDataBase.query(SQLITE_TABLE, new String[] {
                KEY_ID,
                KEY_NAMA}, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}