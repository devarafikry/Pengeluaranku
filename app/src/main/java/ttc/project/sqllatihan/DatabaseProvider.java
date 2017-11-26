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

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDatabaseHelper = new DatabaseHelper(context);
        return true;
    }

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

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
