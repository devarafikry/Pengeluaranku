package ttc.project.sqllatihan;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Fikry-PC on 11/26/2017.
 */

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranViewHolder> {
    Context mContext;
    Cursor mCursor;
    ItemAction itemAction;

    public PengeluaranAdapter(Context context, ItemAction itemAction){
        this.mContext = context;
        this.itemAction = itemAction;
    }

    public void swapCursor(Cursor cursor){
        this.mCursor = cursor;
    }

    @Override
    public PengeluaranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new PengeluaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PengeluaranViewHolder holder, int position) {
        if(mCursor != null){
            mCursor.moveToPosition(position);
            final String id = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.PengeluaranEntry._ID));
            String nama = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.PengeluaranEntry.COLUMN_NAMA));
            String deskripsi = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.PengeluaranEntry.COLUMN_DESKRIPSI));
            String harga = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.PengeluaranEntry.COLUMN_HARGA));

            holder.tv_name.setText(nama);
            holder.tv_deskripsi.setText(deskripsi);
            holder.tv_harga.setText(harga);

            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemAction.delete(id);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
