package com.example.mateus.jera.register_book;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mateus.jera.R;
import com.example.mateus.jera.register_book.BookRegisterContract.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mateus.jera.R.string.message_select_image_title;
import static com.example.mateus.jera.register_book.BookRegisterContract.Presenter;

public class BookRegisterActivity extends AppCompatActivity implements View {

    @BindView(R.id.book_register_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.book_title)
    EditText mBookTitle;
    @BindView(R.id.book_pages)
    EditText mBookPages;
    @BindView(R.id.book_register_image)
    ImageView mBookRegisterImage;

    private Presenter mPresenter;
    private static final int ARQUIVO = 0;
    private String mImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);
        ButterKnife.bind(this);
        setupToolbar();
        mPresenter = new BookRegisterPresenter(getBaseContext(), this);
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
        mPresenter.insertBook(title, Integer.parseInt(numberOfPages), mImagePath);
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

    @OnClick(R.id.book_register_image)
    public void selectImage() {
        mPresenter.callImageSelector();
    }

    @Override
    public void showImageSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, getMessage(message_select_image_title)), ARQUIVO);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, getMessage(R.string.message_error), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getFilePath(data);
    }

    @Override
    public void returnImage(Bitmap bitmap, String imagePath) {
        mBookRegisterImage.setImageBitmap(bitmap);
        mImagePath = imagePath;
    }
}
