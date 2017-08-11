package com.example.mateus.jera.register_book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.example.mateus.jera.R;
import com.example.mateus.jera.helpers.Book;

import java.io.ByteArrayOutputStream;

import static com.example.mateus.jera.register_book.BookRegisterContract.Presenter;
import static com.example.mateus.jera.register_book.BookRegisterContract.View;

/**
 * Created by mateus on 05/08/17.
 */

class BookRegisterPresenter implements Presenter {

    private View mView;
    private Context mContext;

    public BookRegisterPresenter(Context context, View view) {
        mView = view;
        mContext = context;
    }

    @Override
    public void insertBook(final String title, final String pages, String imagePath) {
        if (isBookValid(title, pages, imagePath)) {
            try {
                Book book = new Book(title, Integer.valueOf(pages), imagePath);
                book.save();
                mView.showSuccess(R.string.message_register_book);
            } catch (Exception e) {
                mView.showError(R.string.message_error);
            }
        } else {
            mView.showError(R.string.message_invalid_book);
        }
    }

    private boolean isBookValid(final String title, final String pages, String imagePath) {
        return title.length() > 2 && pages.length() >= 1 && !imagePath.equals("");
    }

    @Override
    public void getFilePath(Intent intent) {
        try {
            Uri uri = intent.getData();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            mView.setBitmapToImageView(bitmap);
            mView.setImagePath(getImagePath(bitmap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImagePath(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void callImageSelector() {
        mView.showImageSelector();
    }
}