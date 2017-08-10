package com.example.mateus.jera.register_book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.mateus.jera.helpers.BaseContract;

import java.io.File;

/**
 * Created by mateus on 04/08/17.
 */

public interface BookRegisterContract {

    interface View extends BaseContract.View {
        void showImageSelector();
        void returnImage(Bitmap bitmap, String imagePath);
    }

    interface Presenter extends BaseContract.Presenter {
        void insertBook(final String title, final int pages, String imagePath);
        void callImageSelector();
        void getFilePath(Intent uri);
    }
}
