package com.example.mateus.jera.register_book;

import com.example.mateus.jera.helpers.Book;

import static com.example.mateus.jera.register_book.BookRegisterContract.*;

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
        try {
            Book book = new Book(0, title, pages);
            book.save();
            mView.logSuccess();
        } catch (Exception e) {
            mView.logError(e);
        }
    }

    @Override
    public void deleteBook(Book book) {

    }
}
