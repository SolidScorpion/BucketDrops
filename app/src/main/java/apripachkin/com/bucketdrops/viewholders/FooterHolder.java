package apripachkin.com.bucketdrops.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import apripachkin.com.bucketdrops.R;

/**
 * Created by root on 14.04.16.
 */
public class FooterHolder extends RecyclerView.ViewHolder {
    public Button btn;
    public FooterHolder(View itemView) {
        super(itemView);
        btn = (Button) itemView.findViewById(R.id.btn_footer);
    }
}
