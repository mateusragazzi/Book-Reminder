package com.example.mateus.jera.register_book;

import com.example.mateus.jera.helpers.BaseContract;

/**
 * Created by mateus on 04/08/17.
 */

public interface BookRegisterContract {

    interface View extends BaseContract.View {}

    interface Presenter extends BaseContract.Presenter {
        void insertBook(final String title, final int pages);
    }
}
