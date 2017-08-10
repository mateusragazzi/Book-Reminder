package com.example.mateus.jera.reminder_book;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.mateus.jera.helpers.Constants;

/**
 * Created by mateus on 07/08/17.
 */

public class BookReminderService extends IntentService {

    public BookReminderService() {
        super("Book Reminder Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(Constants.APPLICATION_TAG, "Service running");
    }
}
