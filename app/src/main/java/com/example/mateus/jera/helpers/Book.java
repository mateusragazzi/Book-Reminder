package com.example.mateus.jera.helpers;

import com.orm.SugarRecord;

/**
 * Created by mateus on 04/08/17.
 */

public class Book extends SugarRecord<Book> {

    private long mId;
    private String title;
    private int pages;
    private boolean reminderEnabled;

    public Book() {
    }

    public Book(long ID, String title, int pages) {
        mId = ID;
        this.title = title;
        this.pages = pages;
        reminderEnabled = false;
    }

    public long getmId() {
        return mId;
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

    public String getPagesAsString() {
        return String.valueOf(pages);
    }
}
