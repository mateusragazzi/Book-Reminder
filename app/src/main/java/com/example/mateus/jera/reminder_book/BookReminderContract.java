package com.example.mateus.jera.reminder_book;

import com.example.mateus.jera.helpers.BaseContract;

import java.util.GregorianCalendar;

/**
 * Created by mateus on 07/08/17.
 */

public interface BookReminderContract {

    interface View extends BaseContract.View {
        void showDateAlert();
        void showTimeAlert();
    }

    interface Presenter extends BaseContract.Presenter {
        void insertReminder(GregorianCalendar now, GregorianCalendar selected, String selectedMode);
        void alertDialogs(int position);
        void updateTimeElement(int selectedHour, int selectedMinute);
    }
}
