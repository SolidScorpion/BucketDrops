package apripachkin.com.bucketdrops.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import apripachkin.com.bucketdrops.utils.Util;

/**
 * Created by root on 13.04.16.
 */
public class BucketRecyclerView extends RecyclerView {
    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };
    private List<View> emptyViews = Collections.emptyList();
    private List<View> nonEmptyViews = Collections.emptyList();
    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(dataObserver);
        }
        dataObserver.onChanged();
    }

    public void hideIfEmpty(View ...view) {
        nonEmptyViews = Arrays.asList(view);
    }

    public void showIfEmpty(View ...emptyView) {
        emptyViews = Arrays.asList(emptyView);
    }

    private void toggleViews() {
        if (adapterIsInitialized()) {
            if (adapterIsEmpty()) {
                Util.showViews(emptyViews);
                setVisibility(GONE);
                Util.hideViews(nonEmptyViews);
            } else {
                Util.showViews(nonEmptyViews);
                setVisibility(VISIBLE);
                Util.hideViews(emptyViews);
            }
        }
    }

    private boolean adapterIsEmpty() {
        return getAdapter().getItemCount() == 0;
    }

    private boolean adapterIsInitialized() {
        return getAdapter() != null && !emptyViews.isEmpty() && !nonEmptyViews.isEmpty();
    }
}
