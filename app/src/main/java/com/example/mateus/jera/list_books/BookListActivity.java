package com.example.mateus.jera.list_books;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.helpers.Constants;
import com.example.mateus.jera.list_books.BookListContract.Presenter;
import com.example.mateus.jera.register_book.BookRegisterActivity;
import com.example.mateus.jera.reminder_book.BookReminderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.LayoutManager;

public class BookListActivity extends AppCompatActivity implements BookListContract.View {

    @BindView(R.id.book_list_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_list)
    RecyclerView mMainList;

    private Presenter mPresenter;
    private BookListAdapter mBookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        setupToolbar();
        mPresenter = new BookListPresenter(this);
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.title_activity_book_list);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupList();
    }

    private void setupList() {
        mBookListAdapter = new BookListAdapter(this, mPresenter.findAllBooks(), mPresenter);
        LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMainList.setLayoutManager(layoutManager);
        mMainList.setItemAnimator(new DefaultItemAnimator());
        mMainList.setAdapter(mBookListAdapter);
    }

    @Override
    public void showAlert(final Book book, int position) {
        showBookDeleteAlert(book, position);
    }

    private DialogInterface.OnClickListener getDeleteBookClickListener(final Book book, final int position) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPresenter.deleteBook(book, position);
            }
        };
    }

    private void showBookDeleteAlert(final Book book, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.message_title);
        alert.setMessage(R.string.message_confirm_delete);
        alert.setPositiveButton(R.string.action_yes, getDeleteBookClickListener(book, position));
        alert.setNegativeButton(R.string.action_no, null);
        alert.show();
    }

    @Override
    public void removeItemFromList(Book book, int position) {
        if (mBookListAdapter != null) mBookListAdapter.remove(book);
        setupList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.startRegisterBookScreen();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showError(final int msg) {
        Toast.makeText(this, getMessage(msg), Toast.LENGTH_SHORT).show();
    }

    private String getMessage(int msg) {
        return getResources().getString(msg);
    }

    @Override
    public void showSuccess(final int msg) {
        Toast.makeText(this, getMessage(msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startBookRegisterActivity() {
        Intent intent = new Intent(this, BookRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void startReminderActivity(final Book book) {
        Intent intent = new Intent(this, BookReminderActivity.class);
        intent.putExtra(Constants.BOOK_ID, book.getId());
        startActivity(intent);
    }
}
