package ttc.project.sqllatihan;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPengeluaranActivity extends AppCompatActivity {

    Button btn_simpan;
    EditText edt_nama, edt_deskripsi, edt_harga;
    View rootView;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pengeluaran);

        dbHelper = new DatabaseHelper(this);
        mDb = dbHelper.getWritableDatabase();

        btn_simpan = (Button) findViewById(R.id.btn_simpan);

        edt_nama = (EditText) findViewById(R.id.nama);
        edt_deskripsi = (EditText) findViewById(R.id.deskripsi);
        edt_harga = (EditText) findViewById(R.id.harga);

        rootView = (View) findViewById(R.id.rootView);

        getSupportActionBar().setTitle("Add Pengeluaran");

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanPengeluaran();
            }
        });
    }

    private void simpanPengeluaran(){
        String nama = edt_nama.getText().toString();
        String deskripsi = edt_deskripsi.getText().toString();
        String harga = edt_harga.getText().toString();

        if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(deskripsi) && !TextUtils.isEmpty(harga)){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PengeluaranEntry.COLUMN_NAMA, nama);
            contentValues.put(DatabaseContract.PengeluaranEntry.COLUMN_DESKRIPSI, deskripsi);
            contentValues.put(DatabaseContract.PengeluaranEntry.COLUMN_HARGA, harga);
            if(simpanKeDatabase(contentValues) != 0){
                finish();
            } else{
                Snackbar.make(rootView, "Gagal mengisi data ke database", Snackbar.LENGTH_LONG).show();
            }
        } else{
            Snackbar.make(rootView, "Mohon isi semua data terlebih dahulu", Snackbar.LENGTH_LONG).show();
        }
    }

    private long simpanKeDatabase(ContentValues contentValues){
//        return mDb.insert(
//                DatabaseContract.PengeluaranEntry.TABLE_NAME,
//                null,
//                contentValues
//        );
        Uri uri = getContentResolver().insert(
                DatabaseContract.PengeluaranEntry.CONTENT_URI,
                contentValues
        );
        if(uri != null){
            return 1;
        } else{
            return 0;
        }
    }
}
