package apripachkin.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.viewholders.DropsViewHolder;
import io.realm.RealmResults;

/**
 * Created by root on 12.04.16.
 */
public class DropsAdapter extends RecyclerView.Adapter<DropsViewHolder> {
    LayoutInflater layoutInflater;
    private RealmResults<Drop> content;

    public DropsAdapter(Context context, RealmResults<Drop> content) {
        layoutInflater = LayoutInflater.from(context);
        updateData(content);
    }

    public void updateData(RealmResults<Drop> data) {
        content = data;
        notifyDataSetChanged();
    }

    @Override
    public DropsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.drop, parent, false);
        return new DropsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(DropsViewHolder holder, int position) {
        Drop drop = content.get(position);
        holder.tv_drop.setText(drop.getWhat());
    }

    @Override
    public int getItemCount() {
        return content.size();
    }
}
