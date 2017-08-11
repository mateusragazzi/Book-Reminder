package com.example.mateus.jera.register_book;

import android.content.Intent;
import android.graphics.Bitmap;

import com.example.mateus.jera.helpers.BaseContract;

/**
 * Created by mateus on 04/08/17.
 */

public interface BookRegisterContract {

    interface View extends BaseContract.View {
        void showImageSelector();
        void setBitmapToImageView(Bitmap bitmap);
        void setImagePath(String imagePath);
    }

    interface Presenter extends BaseContract.Presenter {
        void insertBook(final String title, final String pages, String imagePath);
        void callImageSelector();
        void getFilePath(Intent uri);
    }
}
