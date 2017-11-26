package ttc.project.sqllatihan;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ItemAction{

    // TODO (1) Definisikan database contract
    // TODO (2) Definisikan database table name dan column nya
    // TODO (3) Definisikan helper extends SQLiteOpenHelper untuk memudahkan operasi database
    // TODO (4) Override semua method yg diperlukan pada databasehelper
    // TODO (5) Di databasehelper definisikan nama db dan versi db
    // TODO (6) buat juga constructor pada databasehelper dengan parameter context
    // TODO (7) pada constructor databasehelper, masukkan context, database_name, null, dan database_version
    // TODO (8) kemudian pada oncreate buat string untuk membuat table pengeluaran (syntaks sqlite)
    // TODO (9) execute string tersebut pada oncreate menggunakan sqlitedatabase
    // TODO (10) pada onupgrade execute sql untuk drop table if exist
    // TODO (11) bikin layout activity main
    // TODO (11) bikin kelas addPengeluaran dan layoutnya


    FloatingActionButton btn_add;
    RecyclerView rv_pengeluaran;
    PengeluaranAdapter adapter;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null){
            updateRecyclerView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        mDb = dbHelper.getReadableDatabase();
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        rv_pengeluaran = (RecyclerView) findViewById(R.id.rv_pengeluaran);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPengeluaranActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new PengeluaranAdapter(this, this);
        rv_pengeluaran.setLayoutManager(linearLayoutManager);
        rv_pengeluaran.setAdapter(adapter);
    }

    private void updateRecyclerView(){
        adapter.swapCursor(getAllPengeluaran());
        adapter.notifyDataSetChanged();
    }

    private Cursor getAllPengeluaran(){
//        return mDb.query(
//                DatabaseContract.PengeluaranEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
        return getContentResolver().query(
                DatabaseContract.PengeluaranEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }


    @Override
    public void delete(final String deletedId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus pengeluaran ini?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
//                mDb.delete(
//                        DatabaseContract.PengeluaranEntry.TABLE_NAME,
//                        "_id=?",
//                        new String[]{deletedId}
//                );
                Uri uri = DatabaseContract.PengeluaranEntry.CONTENT_URI;
                getContentResolver().delete(
                        uri.buildUpon().appendPath(deletedId).build(),
                        null,
                        null
                );
                updateRecyclerView();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
