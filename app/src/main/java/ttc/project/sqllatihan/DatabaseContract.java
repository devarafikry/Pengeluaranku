package ttc.project.sqllatihan;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fikry-PC on 11/25/2017.
 */

public class DatabaseContract {
    // TODO (2) Pertama, definisikan DatabaseContract, seperti kita memulai project SQLite
    // TODO (3) tapi di dalam contract ini, tambahkan authority, base_content_uri, dan path
    // TODO (4) jadi di dalam content provider, kita nantinya membutuhkan variabel2 ini untuk
    // membangun URI. URI diperlukan untuk mengakses resources yang kita butuhkan. Bisa dianalogikan
    // sebagai http get/post, namun bedanya pada http get/post kita mencari resources di internet
    // menggunakan URL, sedangkan pada URI mencari resources di dalam Device kita sendiri.
    // TODO (5) kemudian definisikan CONTENT_URI di dalam pengeluaran Entry sebagai lokasi URI
    // yang akan diakses oleh aplikasi lain(/aplikasi kita sendiri) untuk mencari lokasi ContentProvider

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
