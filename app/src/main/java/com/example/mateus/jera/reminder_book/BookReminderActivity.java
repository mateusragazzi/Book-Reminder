package com.example.mateus.jera.reminder_book;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Constants;
import com.example.mateus.jera.reminder_book.BookReminderContract.Presenter;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookReminderActivity extends AppCompatActivity implements BookReminderContract.View {

    @BindView(R.id.book_reminder_once)
    RadioButton mBookReminderOnce;
    @BindView(R.id.book_reminder_daily)
    RadioButton mBookReminderDaily;
    @BindView(R.id.book_reminder_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_reminder_option_time)
    LinearLayout mTimeLinearLayout;
    @BindView(R.id.book_reminder_option_date)
    LinearLayout mDateLinearLayout;
    @BindView(R.id.book_reminder_date)
    TextView mBookReminderTextDate;
    @BindView(R.id.book_reminder_time)
    TextView mBookReminderTextTime;
    @BindView(R.id.book_reminder_weekly)
    RadioButton mBookReminderWeekly;

    private Presenter mPresenter;
    private long mBookId;
    private GregorianCalendar mCalendarToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reminder);
        ButterKnife.bind(this);
        setupToolbar();
        setupBookItems();
        mPresenter = new BookReminderPresenter(mBookId, getBaseContext(), this);
        mCalendarToday = new GregorianCalendar();
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.title_activity_book_reminder);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupBookItems() {
        Intent intent = getIntent();
        mBookId = intent.getLongExtra(Constants.BOOK_ID, 100);
        mDateLinearLayout.setOnClickListener(openDialog(0));
        mTimeLinearLayout.setOnClickListener(openDialog(1));
        mBookReminderTextDate.setText(getMessage(R.string.message_select_date));
        mBookReminderTextTime.setText(getMessage(R.string.message_select_time));
    }

    private View.OnClickListener openDialog(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.alertDialogs(position);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.book_reminder_menu_item) {
            mPresenter.insertReminder(currentDate(), mCalendarToday, getSelectedMode());
        }
        return false;
    }

    private GregorianCalendar currentDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        return new GregorianCalendar(year, month, day, hour, min);
    }

    private String getSelectedMode() {
        if (mBookReminderOnce.isChecked()) {
            return Constants.BOOK_INTERVAL_ONCE;
        } else if (mBookReminderDaily.isChecked()) {
            return Constants.BOOK_INTERVAL_DAILY;
        } else if (mBookReminderWeekly.isChecked()) {
            return Constants.BOOK_INTERVAL_WEEKLY;
        } else {
            return "";
        }
    }

    @Override
    public void showDateAlert() {
        GregorianCalendar today = currentDate();
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(this, dateSet(), today.get(Calendar.YEAR),
                today.get(Calendar.MONTH) - 1, today.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSet() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarToday.set(Calendar.YEAR, year);
                mCalendarToday.set(Calendar.MONTH, month + 1);
                mCalendarToday.set(Calendar.DAY_OF_MONTH, day);
                mBookReminderTextDate.setText(getDateAsString(year, month + 1, day));
            }
        };
    }

    private String getDateAsString(int year, int i, int day) {
        return String.valueOf(day) + '/' + String.valueOf(i) + '/' + String.valueOf(year);
    }

    @Override
    public void showTimeAlert() {
        GregorianCalendar today = currentDate();
        TimePickerDialog timePicker = new TimePickerDialog(this, timeSet(),
                today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), true);
        timePicker.show();
    }

    private TimePickerDialog.OnTimeSetListener timeSet() {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mCalendarToday.set(Calendar.HOUR_OF_DAY, selectedHour);
                mCalendarToday.set(Calendar.MINUTE, selectedMinute);
                mBookReminderTextTime.setText(getTimeAsString(selectedHour, selectedMinute));
            }
        };
    }

    private String getTimeAsString(int selectedHour, int selectedMinute) {
        return String.valueOf(selectedHour) + ':' + String.valueOf(selectedMinute);
    }

    @Override
    public void showSuccess(int msg) {
        Toast.makeText(this, getMessage(msg), Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getMessage(int msg) {
        return getResources().getString(msg);
    }

    @Override
    public void showError(int msg) {
        Toast.makeText(this, getMessage(msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_register_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}