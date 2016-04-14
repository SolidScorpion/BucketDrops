package apripachkin.com.bucketdrops.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import apripachkin.com.bucketdrops.R;

/**
 * Created by root on 14.04.16.
 */
public class Divider extends RecyclerView.ItemDecoration {
    public static final String TAG = Divider.class.getSimpleName();
    private Drawable divider;
    private int orientation;

    public Divider(Context context, int orientation) {
        divider = ContextCompat.getDrawable(context, R.drawable.divider);
        if (orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("Only to be used with RecyclerView with Horizontal orientation");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent, state);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, top, right, bottom;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            if (DropsAdapter.FOOTER != parent.getAdapter().getItemViewType(i)) {
                View current = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) current.getLayoutParams();
                top = current.getTop() - params.topMargin;
                bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
                Log.d(TAG, "drawHorizontalDivider: l:" + left + " t: " + top + " r: " + right + " b: " + bottom);
            }
        }
    }
}
