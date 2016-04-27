package apripachkin.com.bucketdrops.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import apripachkin.com.bucketdrops.AppBucketDrops;
import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.AddListener;

/**
 * Created by root on 27.04.16.
 */
public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    Button mBtnAdd;
    AddListener mListener;

    public FooterHolder(View itemView) {
        super(itemView);
        mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
        mBtnAdd.setOnClickListener(this);
    }

    public FooterHolder(View itemView, AddListener listener) {
        super(itemView);
        mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
        AppBucketDrops.setSetRalewayThinFont(itemView.getContext(), mBtnAdd);
        mBtnAdd.setOnClickListener(this);
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.add();
    }
}
