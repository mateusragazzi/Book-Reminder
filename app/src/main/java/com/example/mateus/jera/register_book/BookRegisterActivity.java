package com.example.mateus.jera.register_book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.register_book.BookRegisterContract.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mateus.jera.register_book.BookRegisterContract.Presenter;

public class BookRegisterActivity extends AppCompatActivity implements View {

    @BindView(R.id.book_register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_title)
    EditText mBookTitle;
    @BindView(R.id.book_pages)
    EditText mBookPages;

    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);
        ButterKnife.bind(this);
        setupToolbar();
        mPresenter = new BookRegisterPresenter(this);
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.title_activity_book);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.book_save)
    public void setBookSave() {
        String title = mBookTitle.getText().toString();
        String numberOfPages = mBookPages.getText().toString();
        mPresenter.insertBook(title, Integer.parseInt(numberOfPages));
    }

    @Override
    public void showSuccess(int msg) {
        Toast.makeText(this, getMessage(msg), Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getMessage(int msg) {
        return getResources().getString(msg);
    }

    @Override
    public void showError(int msg) {
        Toast.makeText(this, getMessage(msg), Toast.LENGTH_SHORT).show();
    }
}
