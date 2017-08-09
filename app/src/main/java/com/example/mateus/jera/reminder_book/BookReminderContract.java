package com.example.mateus.jera.reminder_book;

import com.example.mateus.jera.helpers.BaseContract;

import java.util.GregorianCalendar;

/**
 * Created by mateus on 07/08/17.
 */

public interface BookReminderContract {

    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {
        void insertReminder(GregorianCalendar now, GregorianCalendar selected);
    }
}
