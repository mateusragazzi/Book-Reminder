package com.example.mateus.jera.register_book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.register_book.BookRegisterContract.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookRegisterActivity extends AppCompatActivity implements View {

    @BindView(R.id.book_title)
    EditText mBookTitle;
    @BindView(R.id.book_pages)
    EditText mBookPages;

    private static final String TAG = "BookActivityError";
    private BookRegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);
        ButterKnife.bind(this);
        mPresenter = new BookRegisterPresenter(this);
    }

    @OnClick(R.id.book_save)
    public void setBookSave() {
        String title = mBookTitle.getText().toString();
        String numberOfPages = mBookPages.getText().toString();
        mPresenter.insertBook(title, Integer.parseInt(numberOfPages));
    }

    @Override
    public void showSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logSuccess() {
        Log.e(TAG, "Yeah, worked!");
        finish();
    }

    @Override
    public void logError(Exception e) {
        Log.e(TAG, e.toString());
        finish();
    }
}
