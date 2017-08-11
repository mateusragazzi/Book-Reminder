package com.example.mateus.jera.list_books;

import android.graphics.Bitmap;

import com.example.mateus.jera.helpers.BaseContract;
import com.example.mateus.jera.helpers.Book;

import java.util.ArrayList;

/**
 * Created by mateus on 06/08/17.
 */

public interface BookListContract {

    interface View extends BaseContract.View {
        void showAlert(final Book bookClicked, int position);
        void removeItemFromList(final Book book, int position);
        void startBookRegisterActivity();
        void startReminderActivity(final Book book);
    }

    interface Presenter extends BaseContract.Presenter {
        void deleteBook(final Book book, int position);
        void confirmDelete(final Book bookClicked, int position);
        void startRegisterBookScreen();
        void startReminderScreen(final Book bookClicked);
        Bitmap parsePathToBitmap(String params);
        ArrayList<Book> findAllBooks();
    }
}
