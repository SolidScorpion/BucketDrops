package apripachkin.com.bucketdrops.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
    private static final int LEFT = 0;
    private static final int RIGHT = 2;
    private static final int TOP = 1;
    private static final int BOTTOM = 3;
    private TextView mDay;
    private TextView mMonth;
    private TextView mYear;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

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
            if (topDrawableHit(textView, topBounds.height(), x, y)) {
                if (isActionDown(event)) {
                    increment(textView.getId());
                }

            } else if (bottomDrawableHit(textView, bottomBounds.height(), x, y)) {
                if (isActionDown(event)) {
                    decrement(textView.getId());
                }
            } else {

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
}
