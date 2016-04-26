package apripachkin.com.bucketdrops.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import apripachkin.com.bucketdrops.R;

/**
 * Created by root on 26.04.16.
 */
public class BucketPickerView extends LinearLayout {
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
}
