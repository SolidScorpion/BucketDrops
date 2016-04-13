package apripachkin.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.viewholders.DropsViewHolder;

/**
 * Created by root on 12.04.16.
 */
public class DropsAdapter extends RecyclerView.Adapter<DropsViewHolder> {
    LayoutInflater layoutInflater;
    private ArrayList<String> content = new ArrayList<>();

    public DropsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        content.addAll(generateDummyContent());
    }

    @Override
    public DropsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.drop, parent, false);
        return new DropsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(DropsViewHolder holder, int position) {
        holder.tv_drop.setText(content.get(position));
    }

    private ArrayList<String> generateDummyContent() {
        ArrayList<String> resultList = new ArrayList<>(100);
        for (int i = 1; i < 101; i++) {
            resultList.add("Item #" + i);
        }
        return resultList;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }
}
