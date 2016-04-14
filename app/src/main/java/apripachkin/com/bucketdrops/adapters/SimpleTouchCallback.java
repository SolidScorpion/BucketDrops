package apripachkin.com.bucketdrops.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by root on 14.04.16.
 */
public class SimpleTouchCallback extends ItemTouchHelper.Callback {
    private SwipeListener swipeListener;

    public SimpleTouchCallback(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        swipeListener.onSwipe(viewHolder.getLayoutPosition());
    }
}
