package apripachkin.com.bucketdrops.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.MarkListener;

/**
 * Created by root on 12.04.16.
 */
public class DropsViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_drop;

    public DropsViewHolder(View itemView, final MarkListener listener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMark(getAdapterPosition());
            }
        });
        tv_drop = (TextView) itemView.findViewById(R.id.tv_what);
    }
}
