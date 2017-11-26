package ttc.project.sqllatihan;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fikry-PC on 11/25/2017.
 */

public class DatabaseContract {
    public static final String AUTHORITY = "ttc.project.sqllatihan";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_PENGELUARAN = "pengeluaran";

    public static final class PengeluaranEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PENGELUARAN).build();

        public static final String TABLE_NAME = "pengeluaran";
        public static final String COLUMN_NAMA = "nama";
        public static final String COLUMN_DESKRIPSI = "deskripsi";
        public static final String COLUMN_HARGA = "harga";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
