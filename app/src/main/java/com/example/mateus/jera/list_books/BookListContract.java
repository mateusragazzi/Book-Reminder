package com.example.mateus.jera.list_books;

import android.content.Context;

import com.example.mateus.jera.helpers.BaseContract;
import com.example.mateus.jera.helpers.Book;

/**
 * Created by mateus on 06/08/17.
 */

public interface BookListContract {

    interface View extends BaseContract.View {
        void showAlert(Book bookClicked);
    }

    interface Presenter extends BaseContract.Presenter {
        void deleteBook(Book book);
        void confirmDelete(Book bookClicked);
        void callBookRegister(Context context);
        void setNewReminder(Book bookClicked);
    }
}
