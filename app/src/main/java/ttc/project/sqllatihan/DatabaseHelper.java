package ttc.project.sqllatihan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fikry-PC on 11/26/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "pengeluaran.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PENGELUARAN_TABLE =
                "CREATE TABLE " + DatabaseContract.PengeluaranEntry.TABLE_NAME + "(" +
                        DatabaseContract.PengeluaranEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatabaseContract.PengeluaranEntry.COLUMN_NAMA + " TEXT NOT NULL," +
                        DatabaseContract.PengeluaranEntry.COLUMN_DESKRIPSI + " TEXT NOT NULL," +
                        DatabaseContract.PengeluaranEntry.COLUMN_HARGA + " INTEGER NOT NULL," +
                        DatabaseContract.PengeluaranEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
        sqLiteDatabase.execSQL(SQL_CREATE_PENGELUARAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.PengeluaranEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
