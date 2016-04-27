package apripachkin.com.bucketdrops.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import apripachkin.com.bucketdrops.R;

/**
 * Created by root on 26.04.16.
 */
public class BucketPickerView extends LinearLayout implements View.OnTouchListener {
    public static final int DELAY = 250;
    private static final int LEFT = 0;
    private static final int RIGHT = 2;
    private static final int TOP = 1;
    private static final int BOTTOM = 3;
    private static final int MESSAGE_WHAT = 100500;
    private TextView mDay;
    private TextView mMonth;
    private TextView mYear;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int activeId;
    private boolean mIncrement, mDecrement;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mIncrement) {
                increment(activeId);
            }
            if (mDecrement) {
                decrement(activeId);
            }
            if (mIncrement || mDecrement) {
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
            }
            return true;
        }
    });

    public BucketPickerView(Context context) {
        super(context);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.bucket_picker_view, this);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MMM");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDay = (TextView) findViewById(R.id.tv_date);
        mMonth = (TextView) findViewById(R.id.tv_month);
        mYear = (TextView) findViewById(R.id.tv_year);
        mDay.setOnTouchListener(this);
        mMonth.setOnTouchListener(this);
        mYear.setOnTouchListener(this);
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        update(date, month, year, 0, 0, 0);
    }

    private void update(int date, int month, int year, int hour, int minute, int second) {
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        mYear.setText("" + year);
        mDay.setText("" + date);
        mMonth.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.tv_date:
                processEventForView(mDay, event);
                break;
            case R.id.tv_month:
                processEventForView(mMonth, event);
                break;
            case R.id.tv_year:
                processEventForView(mYear, event);
                break;
        }
        return true;
    }

    private void processEventForView(TextView textView, MotionEvent event) {
        Drawable[] compoundDrawables = textView.getCompoundDrawables();
        if (hasTopAndBottomDrawable(compoundDrawables)) {
            Rect topBounds = compoundDrawables[TOP].getBounds();
            Rect bottomBounds = compoundDrawables[BOTTOM].getBounds();
            float x = event.getX();
            float y = event.getY();
            activeId = textView.getId();
            if (topDrawableHit(textView, topBounds.height(), x, y)) {
                if (isActionDown(event)) {
                    mIncrement = true;
                    increment(textView.getId());
                    mHandler.removeMessages(MESSAGE_WHAT);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
                    toggleDrawable(textView, true);
                }

                if (isActionUpOrCancel(event)) {
                    mIncrement = false;
                    toggleDrawable(textView, false);
                }

            } else if (bottomDrawableHit(textView, bottomBounds.height(), x, y)) {
                if (isActionDown(event)) {
                    mDecrement = true;
                    decrement(textView.getId());
                    mHandler.removeMessages(MESSAGE_WHAT);
                    mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT, DELAY);
                    toggleDrawable(textView, true);
                }
                if (isActionUpOrCancel(event)) {
                    mDecrement = false;
                    toggleDrawable(textView, false);
                }
            } else {
                mIncrement = false;
                mDecrement = false;
                toggleDrawable(textView, false);
            }

        }
    }

    private void increment(int id) {
        switch (id) {
            case R.id.tv_date:
                calendar.add(Calendar.DATE, 1);
                break;
            case R.id.tv_month:
                calendar.add(Calendar.MONTH, 1);
                break;
            case R.id.tv_year:
                calendar.add(Calendar.YEAR, 1);
                break;
        }
        set(calendar);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", super.onSaveInstanceState());
        bundle.putInt("date", calendar.get(Calendar.DATE));
        bundle.putInt("month", calendar.get(Calendar.MONTH));
        bundle.putInt("year", calendar.get(Calendar.YEAR));
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Parcelable) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("super");
            int date = bundle.getInt("date");
            int month = bundle.getInt("month");
            int year = bundle.getInt("year");
            update(date, month, year, 0, 0, 0);
        }
        super.onRestoreInstanceState(state);
    }

    private void decrement(int id) {
        switch (id) {
            case R.id.tv_date:
                calendar.add(Calendar.DATE, -1);
                break;
            case R.id.tv_month:
                calendar.add(Calendar.MONTH, -1);
                break;
            case R.id.tv_year:
                calendar.add(Calendar.YEAR, -1);
                break;
        }
        set(calendar);
    }

    private void set(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);
        mDay.setText(date + "");
        mMonth.setText(simpleDateFormat.format(calendar.getTime()));
        mYear.setText(year + "");
    }

    private boolean topDrawableHit(TextView textView, int drawableHeight, float x, float y) {
        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();
        int ymin = textView.getPaddingTop();
        int ymax = textView.getPaddingTop() + drawableHeight;
        return x > xmin && x < xmax && y > ymin && y < ymax;
    }

    private boolean bottomDrawableHit(TextView textView, int drawableHeight, float x, float y) {
        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();
        int ymax = textView.getHeight() - textView.getPaddingBottom();
        int ymin = ymax - drawableHeight;
        return x > xmin && x < xmax && y > ymin && y < ymax;
    }

    private boolean hasTopAndBottomDrawable(Drawable[] compoundDrawables) {
        return hasDrawableBottom(compoundDrawables) && hasDrawableTop(compoundDrawables);
    }

    private boolean hasDrawableTop(Drawable[] compoundDrawable) {
        return compoundDrawable[TOP] != null;
    }

    private boolean hasDrawableBottom(Drawable[] compoundDrawable) {
        return compoundDrawable[BOTTOM] != null;
    }

    private boolean isActionDown(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    private boolean isActionUpOrCancel(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL;
    }

    private void toggleDrawable(TextView textView, boolean pressed) {
        if (pressed) {
            if (mIncrement) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up_pressed, 0, R.drawable.down_normal);
            }
            if (mDecrement) {
                textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up_normal, 0, R.drawable.down_pressed);
            }
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.up_normal, 0, R.drawable.down_normal);
        }
    }

}
