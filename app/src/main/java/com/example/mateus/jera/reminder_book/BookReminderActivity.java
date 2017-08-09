package com.example.mateus.jera.reminder_book;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
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

    @BindView(R.id.book_reminder_once)
    RadioButton mBookReminderOnce;
    @BindView(R.id.book_reminder_daily)
    RadioButton mBookReminderDaily;
    @BindView(R.id.book_reminder_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_reminder_list_options)
    ListView mBookReminderListOptions;

    private static final String TAG = "BookReminderActivity";
    private Presenter mPresenter;
    private long mBookId;
    private GregorianCalendar mCalendarToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reminder);
        ButterKnife.bind(this);
        mPresenter = new BookReminderPresenter(mBookId, getBaseContext(), this);
        mCalendarToday = new GregorianCalendar();
        setupActivity();
        setupBookItens();
    }

    private void setupBookItens() {
        Intent intent = getIntent();
        mBookId = intent.getLongExtra("id", 100);
        mBookReminderListOptions.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                mPresenter.alertDialogs(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.book_reminder_menu_item) {
            mPresenter.insertReminder(currentDate(), mCalendarToday, getSelectedMode());
        }
        return false;
    }

    @Override
    public void showDateAlert() {
        GregorianCalendar today = currentDate();
        DatePickerDialog DatePickerDialog =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mCalendarToday.set(Calendar.YEAR, year);
                        mCalendarToday.set(Calendar.MONTH, month + 1);
                        mCalendarToday.set(Calendar.DAY_OF_MONTH, day);
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH) - 1, today.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog.show();
    }

    @Override
    public void showTimeAlert() {
        GregorianCalendar today = currentDate();
        TimePickerDialog timePicker =
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mCalendarToday.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mCalendarToday.set(Calendar.MINUTE, selectedMinute);
                    }
                }, today.get(Calendar.HOUR_OF_DAY), today.get(Calendar.MINUTE), true);
        timePicker.show();
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
    }

    private String getSelectedMode() {
        if (mBookReminderOnce.isChecked()) {
            return "Once";
        } else if (mBookReminderDaily.isChecked()) {
            return "Daily";
        } else {
            return null;
        }
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
}