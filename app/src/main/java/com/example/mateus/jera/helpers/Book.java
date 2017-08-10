package com.example.mateus.jera.helpers;

import com.orm.SugarRecord;

/**
 * Created by mateus on 04/08/17.
 */

public class Book extends SugarRecord<Book> {

    private String title;
    private int pages;
    private boolean reminderEnabled;

    public Book() {
    }

    public Book(String title, int pages) {
        this.title = title;
        this.pages = pages;
        reminderEnabled = false;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }
}
