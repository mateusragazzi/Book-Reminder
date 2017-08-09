package com.example.mateus.jera.list_books;

import android.content.Context;
import android.content.Intent;

import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.register_book.BookRegisterActivity;
import com.example.mateus.jera.reminder_book.BookReminderActivity;

import java.util.List;

import static com.example.mateus.jera.list_books.BookListContract.Presenter;
import static com.example.mateus.jera.list_books.BookListContract.View;

/**
 * Created by mateus on 06/08/17.
 */

class BookListPresenter implements Presenter {

    private View mView;

    public BookListPresenter(View bookListActivity) {
        mView = bookListActivity;
    }

    @Override
    public void deleteBook(Book bookClicked) {
        try {
            long id = getBookID(bookClicked).get(0).getId();
            Book book = Book.findById(Book.class, id);
            book.delete();
            mView.showSuccess("Yeah, your book has been deleted!");
            mView.logSuccess();
        } catch (Exception e) {
            mView.logError(e);
            mView.showError("Oh no, something happened!");
        }
    }

    @Override
    public void confirmDelete(Book bookClicked) {
        mView.showAlert(bookClicked);
    }

    @Override
    public void callBookRegister(Context context) {
        Intent intent = new Intent(context, BookRegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void callNewReminder(Context context, Book bookClicked) {
        Intent intent = new Intent(context, BookReminderActivity.class);
        intent.putExtra("id", bookClicked.getId());
        context.startActivity(intent);
    }

    public List<Book> getBookID(Book book) {
        return Book.find(Book.class, "title = ?", book.getTitle());
    }
}
