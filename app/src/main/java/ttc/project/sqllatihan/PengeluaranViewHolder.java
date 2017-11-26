package ttc.project.sqllatihan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Fikry-PC on 11/26/2017.
 */

public class PengeluaranViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_name, tv_deskripsi, tv_harga;
    public Button btn_delete;
    public PengeluaranViewHolder(View itemView) {
        super(itemView);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_deskripsi = (TextView) itemView.findViewById(R.id.tv_deskripsi);
        tv_harga = (TextView) itemView.findViewById(R.id.tv_harga);

        btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
    }
}
