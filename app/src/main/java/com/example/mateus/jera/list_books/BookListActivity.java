package com.example.mateus.jera.list_books;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;
import com.example.mateus.jera.register_book.BookRegisterActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookListActivity extends AppCompatActivity {

    @BindView(R.id.book_list_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_list)
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }

    @OnClick(R.id.main_new_book)
    public void callBookActivity() {
        Intent intent = new Intent(this, BookRegisterActivity.class);
        startActivity(intent);
    }

    private void setupList() {
        ArrayList<Book> books = getBooks();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMainList.setLayoutManager(layoutManager);
        mMainList.setItemAnimator(new DefaultItemAnimator());
        mMainList.setAdapter(new BookListAdapter(books, this));
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.title_activity_book_list);
        setSupportActionBar(mToolbar);
    }

    public ArrayList<Book> getBooks() {
        return (ArrayList<Book>) Book.listAll(Book.class);
    }
}
