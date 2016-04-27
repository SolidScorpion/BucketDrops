package apripachkin.com.bucketdrops.viewholders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import apripachkin.com.bucketdrops.AppBucketDrops;
import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.MarkListener;
import apripachkin.com.bucketdrops.extras.Util;

/**
 * Created by root on 27.04.16.
 */
public class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mTextWhat;
    TextView mTextWhen;
    MarkListener mMarkListener;
    Context mContext;
    View mItemView;

    public DropHolder(View itemView, MarkListener listener) {
        super(itemView);
        mItemView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
        mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);
        mMarkListener = listener;
        AppBucketDrops.setSetRalewayThinFont(mContext, mTextWhat, mTextWhen);
    }

    public void setWhat(String what) {
        mTextWhat.setText(what);
    }

    @Override
    public void onClick(View v) {
        mMarkListener.onMark(getAdapterPosition());
    }

    public void setBackground(boolean completed) {
        Drawable drawable;
        if (completed) {
            drawable = ContextCompat.getDrawable(mContext, R.color.bg_drop_complete);
        } else {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.bg_row_drop);
        }
        Util.setBackground(mItemView, drawable);
    }

    public void setWhen(long when) {
        mTextWhen.setText(DateUtils.getRelativeTimeSpanString(when, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
    }
}
