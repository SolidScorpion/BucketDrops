package apripachkin.com.bucketdrops.viewholders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
    public TextView tv_when;
    private Context context;

    public DropsViewHolder(View itemView, final MarkListener listener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMark(getAdapterPosition());
            }
        });
        context = itemView.getContext();
        tv_drop = (TextView) itemView.findViewById(R.id.tv_what);
        tv_when = (TextView) itemView.findViewById(R.id.tv_when);
    }

    public void setBackground(boolean completed) {
        Drawable drawable;
        if (completed) {
            drawable = ContextCompat.getDrawable(context, R.color.bg_drop_complete);
        } else {
            drawable = ContextCompat.getDrawable(context, R.drawable.bg_row_drop);
        }

    }

}
