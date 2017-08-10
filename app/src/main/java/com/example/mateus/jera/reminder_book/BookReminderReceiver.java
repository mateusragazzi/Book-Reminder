package com.example.mateus.jera.reminder_book;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.helpers.Constants;
import com.example.mateus.jera.list_books.BookListActivity;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mateus on 07/08/17.
 */

public class BookReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = (int) intent.getLongExtra(Constants.REQUEST_CODE_BOOK_REMINDER, 0);
        String mode = intent.getStringExtra(Constants.REQUEST_REMINDER_MODE);
        showNotification(context, id);
        if (mode.equals(Constants.BOOK_INTERVAL_ONCE)) {
            updateBookStatus(id);
        }
    }

    private void showNotification(Context context, int id) {
        Object notificationService = context.getSystemService(NOTIFICATION_SERVICE);
        NotificationManager mNotificationManager = (NotificationManager) notificationService;
        mNotificationManager.notify(id, createNotification(context, getBook(id)).build());
    }

    private NotificationCompat.Builder createNotification(Context context, Book book) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(book.getTitle())
                .setContentText(getMessage(R.string.message_remember_notification, context))
                .setVibrate(new long[]{150, 300, 150, 600});

        Intent resultIntent = new Intent(context, BookListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(BookListActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder;
    }

    private Book getBook(int id) {
        return Book.findById(Book.class, (long) id);
    }

    private String getMessage(int msg, Context context) {
        return context.getResources().getString(msg);
    }

    private void updateBookStatus(long bookId) {
        Book book = Book.findById(Book.class, bookId);
        book.setReminderEnabled(false);
        book.save();
    }
}
