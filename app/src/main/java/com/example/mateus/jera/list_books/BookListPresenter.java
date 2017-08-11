package com.example.mateus.jera.list_books;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;

import java.util.ArrayList;
import java.util.List;

import static com.example.mateus.jera.list_books.BookListContract.Presenter;
import static com.example.mateus.jera.list_books.BookListContract.View;

/**
 * Created by mateus on 06/08/17.
 */

class BookListPresenter implements Presenter {

    private final View mView;

    public BookListPresenter(final View bookListActivity) {
        mView = bookListActivity;
    }

    @Override
    public ArrayList<Book> findAllBooks() {
        return (ArrayList<Book>) Book.listAll(Book.class);
    }

    @Override
    public void deleteBook(final Book bookClicked, int position) {
        try {
            long id = getBookId(bookClicked).get(0).getId();
            Book book = Book.findById(Book.class, id);
            book.delete();
            mView.removeItemFromList(book, position);
            mView.showSuccess(R.string.message_delete_success);
        } catch (Exception e) {
            mView.showError(R.string.message_error);
        }
    }

    @Override
    public void confirmDelete(final Book bookClicked, int position) {
        mView.showAlert(bookClicked, position);
    }

    @Override
    public void startRegisterBookScreen() {
        mView.startBookRegisterActivity();
    }

    @Override
    public void startReminderScreen(final Book book) {
        mView.startReminderActivity(book);
    }

    public List<Book> getBookId(final Book book) {
        return Book.find(Book.class, "title = ?", book.getTitle());
    }

    @Override
    public Bitmap parsePathToBitmap(String param) {
        try {
            byte[] encodeByte = Base64.decode(param, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}