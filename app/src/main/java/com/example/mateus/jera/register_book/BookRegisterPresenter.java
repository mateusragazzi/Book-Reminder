package com.example.mateus.jera.register_book;

import com.example.mateus.jera.R;
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
    public void insertBook(final String title, final int pages) {
        if (isBookValid(title, pages)) {
            try {
                Book book = new Book(title, pages);
                book.save();
                mView.showSuccess(R.string.message_register_book);
            } catch (Exception e) {
                mView.showError(R.string.message_error);
            }
        } else {
            mView.showError(R.string.message_invalid_book);
        }
    }

    private boolean isBookValid(final String title, final int pages) {
        return title.length() > 2 && pages > 5;
    }
}
