package com.example.mateus.jera.register_book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
    public void insertBook(final String title, final int pages, String imagePath) {
        if (isBookValid(title, pages, imagePath)) {
            try {
                Book book = new Book(title, pages, imagePath);
                book.save();
                mView.showSuccess(R.string.message_register_book);
            } catch (Exception e) {
                mView.showError(R.string.message_error);
            }
        } else {
            mView.showError(R.string.message_invalid_book);
        }
    }

    @Override
    public void callImageSelector() {
        mView.showImageSelector();
    }

    @Override
    public void getFilePath(Intent intent) {
        try {
            Uri uri = intent.getData();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            mView.returnImage(bitmap, getImagePath(bitmap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImagePath(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    private boolean isBookValid(final String title, final int pages, String imagePath) {
        return title.length() > 2 && pages > 5 && !imagePath.equals("");
    }
}
