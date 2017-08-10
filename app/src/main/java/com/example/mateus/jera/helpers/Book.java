package com.example.mateus.jera.helpers;

import com.orm.SugarRecord;

/**
 * Created by mateus on 04/08/17.
 */

public class Book extends SugarRecord<Book> {

    private String title;
    private int pages;
    private boolean reminderEnabled;
    private String imagePath;

    public Book() {}

    public Book(String title, int pages, String image) {
        this.title = title;
        this.pages = pages;
        reminderEnabled = false;
        imagePath = image;

    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }
}
