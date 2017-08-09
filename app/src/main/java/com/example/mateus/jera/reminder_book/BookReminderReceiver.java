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
import com.example.mateus.jera.list_books.BookListActivity;

/**
 * Created by mateus on 07/08/17.
 */

public class BookReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = (int) intent.getLongExtra("requestCode", 0);
        showNotification(context, id);
        updateBookStatus(id);
    }

    private void showNotification(Context context, int id) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, createNotification(context, getBook(id)).build());
    }

    private NotificationCompat.Builder createNotification(Context context, Book book) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(book.getTitle())
                        .setContentText("Hey, did you read this book today?")
                        .setVibrate(new long[]{150, 300, 150, 600});
        Intent resultIntent = new Intent(context, BookListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(BookListActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder;
    }

    private Book getBook(int id) {
        return Book.findById(Book.class, (long) id);
    }

    private void updateBookStatus(long bookId) {
        Book book = Book.findById(Book.class, bookId);
        book.setReminderEnabled(false);
        book.save();
    }
}
