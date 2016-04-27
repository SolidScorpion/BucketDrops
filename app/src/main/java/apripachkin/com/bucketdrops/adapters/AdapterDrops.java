package apripachkin.com.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apripachkin.com.bucketdrops.AppBucketDrops;
import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.viewholders.DropHolder;
import apripachkin.com.bucketdrops.viewholders.FooterHolder;
import apripachkin.com.bucketdrops.viewholders.NoItemsHolder;
import io.realm.Realm;
import io.realm.RealmResults;

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    public static final int COUNT_FOOTER = 1;
    public static final int COUNT_NO_ITEMS = 1;
    public static final int ITEM = 0;
    public static final int NO_ITEM = 1;
    public static final int FOOTER = 2;
    private final ResetListener mResetListener;
    private MarkListener mMarkListener;

    private LayoutInflater mInflater;
    private Realm mRealm;
    private RealmResults<Drop> mResults;
    private AddListener mAddListener;
    private int mFilterOption;
    private Context mContext;


    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> results, AddListener listener, MarkListener markListener, ResetListener resetListener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        update(results);
        mRealm = realm;
        mAddListener = listener;
        mMarkListener = markListener;
        mResetListener = resetListener;
    }

    public void update(RealmResults<Drop> results) {
        mResults = results;
        mFilterOption = AppBucketDrops.load(mContext);
        notifyDataSetChanged();

    }

    @Override
    public long getItemId(int position) {
        if (position < mResults.size()) {
            return mResults.get(position).getAdded();
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemCount() {
        if (!mResults.isEmpty()) {
            return mResults.size() + COUNT_FOOTER;
        } else {
            if (mFilterOption == Filter.LEAST_TIME_LEFT
                    || mFilterOption == Filter.MOST_TIME_LEFT
                    || mFilterOption == Filter.NONE) {
                return 0;
            } else {
                return COUNT_NO_ITEMS + COUNT_FOOTER;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mResults.isEmpty()) {
            if (position < mResults.size()) {
                return ITEM;
            } else {
                return FOOTER;
            }
        } else {
            if (mFilterOption == Filter.COMPLETE ||
                    mFilterOption == Filter.INCOMPLETE) {
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
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.button_footer, parent, false);
            return new FooterHolder(view, mAddListener);
        } else if (viewType == NO_ITEM) {
            View view = mInflater.inflate(R.layout.no_item_to_display, parent, false);
            return new NoItemsHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.drop, parent, false);
            return new DropHolder(view, mMarkListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);
            dropHolder.setWhat(drop.getWhat());
            dropHolder.setWhen(drop.getWhen());
            dropHolder.setBackground(drop.isCompleted());
        }
    }

    @Override
    public void onSwipe(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).removeFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
        resetFilterIfEmpty();
    }

    private void resetFilterIfEmpty() {
        if (mResults.isEmpty() && (mFilterOption == Filter.COMPLETE ||
                mFilterOption == Filter.INCOMPLETE)) {
            mResetListener.onReset();
        }
    }

    public void markComplete(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }
    }

}
