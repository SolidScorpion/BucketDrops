package apripachkin.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.viewholders.DropsViewHolder;
import apripachkin.com.bucketdrops.viewholders.FooterHolder;
import io.realm.RealmResults;

/**
 * Created by root on 12.04.16.
 */
public class DropsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    public static final int FOOTER = 1;
    private LayoutInflater layoutInflater;
    private RealmResults<Drop> content;
    private DialogAddListener addListener;

    public DropsAdapter(Context context, RealmResults<Drop> content, DialogAddListener addListener) {
        this.addListener = addListener;
        layoutInflater = LayoutInflater.from(context);
        updateData(content);
    }

    public void updateData(RealmResults<Drop> data) {
        content = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (content == null || position < content.size()) {
            return ITEM;
        }
        return FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View inflate = layoutInflater.inflate(R.layout.button_footer, parent, false);
            return new FooterHolder(inflate);
        } else {
            View inflate = layoutInflater.inflate(R.layout.drop, parent, false);
            return new DropsViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropsViewHolder) {
            DropsViewHolder dropsViewHolder = (DropsViewHolder) holder;
            Drop drop = content.get(position);
            dropsViewHolder.tv_drop.setText(drop.getWhat());
        } else {
            FooterHolder footerHolder = (FooterHolder) holder;
            footerHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addListener.add();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return content.size() + 1;
    }
}
