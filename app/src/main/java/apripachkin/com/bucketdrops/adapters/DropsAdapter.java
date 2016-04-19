package apripachkin.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apripachkin.com.bucketdrops.Filter;
import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.utils.SharedPreferencesUtil;
import apripachkin.com.bucketdrops.viewholders.DropsViewHolder;
import apripachkin.com.bucketdrops.viewholders.FooterHolder;
import apripachkin.com.bucketdrops.viewholders.NoItemsViewHolder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by root on 12.04.16.
 */
public class DropsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    public static final int FOOTER = 2;
    public static final int NO_ITEM = 1;
    private static final int ITEM = 0;
    private static final int COUNT_FOOTER = 1;
    private static final int COUNT_NO_ITEMS = 1;
    private int sortOption;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private LayoutInflater layoutInflater;
    private RealmResults<Drop> content;
    private DialogAddListener addListener;
    private Realm realmDb;
    private RealmResults<Drop> realmResults;
    private MarkListener markListener;

    public DropsAdapter(Context context, RealmResults<Drop> content, DialogAddListener addListener, Realm realmDb, RealmResults<Drop> realmResults, MarkListener markListener) {
        this.addListener = addListener;
        this.realmDb = realmDb;
        this.realmResults = realmResults;
        this.markListener = markListener;
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
        layoutInflater = LayoutInflater.from(context);
        updateData(content);
    }

    public void updateData(RealmResults<Drop> data) {
        content = data;
        sortOption = sharedPreferencesUtil.load();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!realmResults.isEmpty()) {
            if (isDropItem(position)) {
                return ITEM;
            } else {
                return FOOTER;
            }
        } else {
            if (sortOption == Filter.COMPLETED ||
                    sortOption == Filter.INCOMPLETE) {
                if (position == 0) {
                    return NO_ITEM;
                } else {
                    return FOOTER;
                }
            } else {
                return ITEM;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate;
        if (viewType == FOOTER) {
            inflate = layoutInflater.inflate(R.layout.button_footer, parent, false);
            return new FooterHolder(inflate);
        } else if (viewType == ITEM) {
            inflate = layoutInflater.inflate(R.layout.drop, parent, false);
            return new DropsViewHolder(inflate, markListener);
        } else {
            inflate = layoutInflater.inflate(R.layout.no_item_to_display, parent, false);
            return new NoItemsViewHolder(inflate);
        }
    }

    @Override
    public long getItemId(int position) {
        if (isDropItem(position)) {
            return realmResults.get(position).getAdded();
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropsViewHolder) {
            DropsViewHolder dropsViewHolder = (DropsViewHolder) holder;
            Drop drop = content.get(position);
            dropsViewHolder.tv_drop.setText(drop.getWhat());
            dropsViewHolder.tv_when.setText(
                    DateUtils.getRelativeTimeSpanString(
                            drop.getWhen(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE
                    )
            );
            dropsViewHolder.setBackground(drop.isCompleted());
        } else if (holder instanceof FooterHolder) {
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
        if (!realmResults.isEmpty()) {
            return realmResults.size() + COUNT_FOOTER;
        }
        if (sortOption == Filter.LEAST_TIME_LEFT ||
                sortOption == Filter.MOST_TIME_LEFT ||
                sortOption == Filter.NONE) {
            return 0;
        }
        return COUNT_NO_ITEMS + COUNT_FOOTER;
    }

    @Override
    public void onSwipe(int position) {
        if (isDropItem(position)) {
            realmDb.beginTransaction();
            realmResults.get(position).removeFromRealm();
            realmDb.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    private boolean isDropItem(int position) {
        return position < realmResults.size();
    }

    public void markComplete(int position) {
        if (isDropItem(position)) {
            realmDb.beginTransaction();
            realmResults.get(position).setCompleted(true);
            realmDb.commitTransaction();
            notifyItemChanged(position);
        }
    }
}
