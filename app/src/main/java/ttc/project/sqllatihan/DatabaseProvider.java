package ttc.project.sqllatihan;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Fikry-PC on 11/26/2017.
 */

public class DatabaseProvider extends ContentProvider{
    // TODO (6) inilah kelas yang harus dibuat ketika kita membuat ContentProvider, yaitu kelas yang
    // extend ContentProvider
    // TODO (7) disini ada 2 variable konstan yaitu pengeluaran dan pengeluaran_with_id, ini berperan
    // untuk menyeleksi request dari aplikasi nantinya apakah request tersebut di peruntukkan untuk
    // data yang jamak (semua data) atau salah satu data saja (with id)
    // TODO (8) definisikan juga mDatabaseHelper untuk melakukan operasi database. Disinilah perbedaannya
    // dalam menggunakan Content Provider dengan langsung melakukan operasi db di activity. Jadi dengan
    // menggunakan Content Provider, akses ke DatabaseHelper di sendirikan dalam kelas ini sehingga
    // tidak sembarangan aplikasi bisa lansung melakukan operasi database
    // TODO (9) definisikan Uri matcher. Bayangkan, nantinya kelas ini akan menerima URI. tentunya
    // kelas ini perlu mengidentifikasi request seperti apa yang diinginkan. Oleh karena itu, Uri
    // matcher ini akan membantu Content Provider untuk mengidentifikasi Uri yang masuk berdasarkan
    // konstan yang kita definisian di atas
    // TODO (10) method build Uri matcher untuk membuat filter terhadap uri yang masuk
    public static final int PENGELUARAN = 100;
    public static final int PENGELUARAN_WITH_ID = 101;

    private DatabaseHelper mDatabaseHelper;
    private static final UriMatcher sUriMathcer = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_PENGELUARAN, PENGELUARAN);
        uriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.PATH_PENGELUARAN + "/#", PENGELUARAN_WITH_ID);
        return uriMatcher;
    }

    // TODO (11) didalam oncreate definisikan database helper yang akan kita gunakan untuk
    // melakukan operasi database
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDatabaseHelper = new DatabaseHelper(context);
        return true;
    }

    // TODO (12) method query mendefinisikan apa yang akan dikembalikan oleh Content Provider untuk
    // Uri request query
    // Disini pertama mendefinisikan SQLiteDatabase apa yang akan kita gunakan, kemudian filter Uri
    // dengan match.
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        int match = sUriMathcer.match(uri);
        Cursor retCursor;

        switch (match){
            case PENGELUARAN:
                retCursor = db.query(
                        DatabaseContract.PengeluaranEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    // TODO (13) method insert mendefinisikan apa yang akan dikembalikan oleh Content Provider untuk
    // Uri request insert
    // Disini pertama mendefinisikan SQLiteDatabase apa yang akan kita gunakan, kemudian filter Uri
    // dengan match.
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        int match = sUriMathcer.match(uri);
        Uri returnUri;
        switch (match){
            case PENGELUARAN:
                long id = db.insert(DatabaseContract.PengeluaranEntry.TABLE_NAME,
                        null,
                        contentValues);
                if(id>0){
                    returnUri = ContentUris.withAppendedId(DatabaseContract.PengeluaranEntry.CONTENT_URI,
                            id);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    // TODO (14) method delete mendefinisikan apa yang akan dikembalikan oleh Content Provider untuk
    // Uri request delete
    // Disini pertama mendefinisikan SQLiteDatabase apa yang akan kita gunakan, kemudian filter Uri
    // dengan match.
    // Di method delete ini Content Provider memerlukan Id yang didapat dari URI, oleha karena itu
    // diambil variable id dari uri.getPathSegements().get(1) yaitu variable # pertama yang didapat
    // setelah path
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int pengeluaranDeleted;
        int match = sUriMathcer.match(uri);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        switch (match){
            case PENGELUARAN_WITH_ID:
                String id = uri.getPathSegments().get(1);
                pengeluaranDeleted = db.delete(
                        DatabaseContract.PengeluaranEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{id}
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (pengeluaranDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return pengeluaranDeleted;
    }

    // TODO (15) method update mendefinisikan apa yang akan dikembalikan oleh Content Provider untuk
    // Uri request update
    // Disini pertama mendefinisikan SQLiteDatabase apa yang akan kita gunakan, kemudian filter Uri
    // dengan match.
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
