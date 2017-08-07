package com.example.mateus.jera.list_books;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.list_books.BookListContract.Presenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookListActivity extends AppCompatActivity implements BookListContract.View {

    @BindView(R.id.book_list_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_list)
    RecyclerView mMainList;

    private static final String TAG = "BookActivityError";
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        setupToolbar();
        mPresenter = new BookListPresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.callBookRegister(this);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }

    @Override
    public void showSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        setupList();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlert(Book bookClicked) {
        AlertDialog.Builder alertDialog = buildAlertDialog(bookClicked);
        alertDialog.show();
    }

    @Override
    public void logSuccess() {
        Log.i(TAG, "OK, it worked!");
    }

    @Override
    public void logError(Exception e) {
        Log.i(TAG, "No, it failed! " + e);
    }

    private void setupList() {
        ArrayList<Book> books = getBooks();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMainList.setLayoutManager(layoutManager);
        mMainList.setItemAnimator(new DefaultItemAnimator());
        mMainList.setAdapter(new BookListAdapter(this, books, mPresenter));
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.title_activity_book_list);
        setSupportActionBar(mToolbar);
    }

    private AlertDialog.Builder buildAlertDialog(final Book bookClicked) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning_title);
        builder.setMessage(R.string.warning_delete_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPresenter.deleteBook(bookClicked);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder;
    }

    public ArrayList<Book> getBooks() {
        return (ArrayList<Book>) Book.listAll(Book.class);
    }
}
