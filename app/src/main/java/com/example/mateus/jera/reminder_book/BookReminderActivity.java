package com.example.mateus.jera.reminder_book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.reminder_book.BookReminderContract.Presenter;
import com.example.mateus.jera.reminder_book.BookReminderContract.View;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookReminderActivity extends AppCompatActivity implements View {

    @BindView(R.id.book_reminder_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_reminder_date)
    DatePicker mBookReminderDate;
    @BindView(R.id.book_reminder_time)
    TimePicker mBookReminderTime;

    private static final String TAG = "BookReminderActivity";
    private Presenter mPresenter;
    private long mBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reminder);
        ButterKnife.bind(this);
        setupActivity();
        mPresenter = new BookReminderPresenter(mBookId, getBaseContext(), this);
    }

    @Override
    public void logSuccess() {
        Log.i(TAG, "OK! The reminder has been inserted");
    }

    @Override
    public void logError(Exception e) {
        Log.e(TAG, e.toString());
    }

    @Override
    public void showSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.book_reminder_menu_item) {
            GregorianCalendar today = currentDate();
            GregorianCalendar selected = pickerDate();
            mPresenter.insertReminder(today, selected);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_register_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupActivity() {
        mToolbar.setTitle(R.string.title_activity_book_reminder);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBookReminderTime.setIs24HourView(true);
        Intent intent = getIntent();
        mBookId = intent.getLongExtra("id", 100);
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

    private GregorianCalendar pickerDate() {
        int hour = mBookReminderTime.getCurrentHour();
        int minute = mBookReminderTime.getCurrentMinute();
        int day = mBookReminderDate.getDayOfMonth();
        int month = mBookReminderDate.getMonth() + 1;
        int year = mBookReminderDate.getYear();
        return new GregorianCalendar(year, month, day, hour, minute);
    }
}