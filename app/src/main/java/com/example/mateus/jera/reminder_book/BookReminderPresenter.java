package com.example.mateus.jera.reminder_book;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.mateus.jera.helpers.Book;

import java.util.GregorianCalendar;

import static com.example.mateus.jera.reminder_book.BookReminderContract.Presenter;
import static com.example.mateus.jera.reminder_book.BookReminderContract.View;

/**
 * Created by mateus on 07/08/17.
 */

public class BookReminderPresenter implements Presenter {

    private View mView;
    private Context mContext;
    private Book mBook;

    public BookReminderPresenter(long bookId, Context context, View view) {
        mView = view;
        mContext = context;
        mBook = getBook(bookId);
    }

    @Override
    public void insertReminder(GregorianCalendar now, GregorianCalendar selected, String selectedMode) {
        long difference = (selected.getTimeInMillis() - now.getTimeInMillis());
        if (isValidInfo(difference, selectedMode)) {
            try {
                scheduleAlarm(mBook.getId(), difference);
                updateBookStatus(mBook.getId(), true);
                mView.showSuccess("Yeah, your reminder is active");
                mView.logSuccess();
            } catch (Exception e) {
                mView.showError("Oh no, we failed!");
                mView.logError(e);
            }
        } else {
            mView.showError("Oh no, something is wrong in your reminder!");
        }
    }

    private boolean isValidInfo(long difference, String selectedMode) {
        return difference > 0 && selectedMode != null;
    }

    @Override
    public void alertDialogs(int position) {
        switch (position) {
            case 0:
                mView.showDateAlert();
                break;
            case 1:
                mView.showTimeAlert();
                break;
            default:
                mView.showError("Oh no, we failed");
        }
    }

    @Override
    public void updateTimeElement(int selectedHour, int selectedMinute) {

    }

    private void updateBookStatus(long bookId, boolean command) {
        Book book = Book.findById(Book.class, bookId);
        book.setReminderEnabled(command);
        book.save();
    }

    public void scheduleAlarm(long bookId, long time) {
        Intent intent = new Intent(mContext, BookReminderReceiver.class);
        intent.putExtra("requestCode", bookId);
        final PendingIntent pIntent = PendingIntent.getBroadcast(mContext, (int) bookId,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, firstMillis + time, pIntent);
    }

    private Book getBook(Long id) {
        return Book.findById(Book.class, id);
    }
}
