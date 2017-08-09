package com.example.mateus.jera.register_book;

import com.example.mateus.jera.helpers.Book;

import static com.example.mateus.jera.register_book.BookRegisterContract.Presenter;
import static com.example.mateus.jera.register_book.BookRegisterContract.View;

/**
 * Created by mateus on 05/08/17.
 */

class BookRegisterPresenter implements Presenter {

    private View mView;

    public BookRegisterPresenter(BookRegisterContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void insertBook(String title, int pages) {
        if (isBookValid(title, pages)) {
            try {
                Book book = new Book(title, pages);
                book.save();
                mView.logSuccess();
                mView.showSuccess("Yeah, the book has been inserted!");
            } catch (Exception e) {
                mView.logError(e);
                mView.showError("Oh no, we failed!");
            }
        } else {
            mView.showError("Oh no, your book isn't valid!");
        }
    }

    private boolean isBookValid(String title, int pages) {
        return title.length() > 2 && pages > 5;
    }
}
