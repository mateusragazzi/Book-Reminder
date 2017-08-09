package com.example.mateus.jera.register_book;

import com.example.mateus.jera.helpers.BaseContract;
import com.example.mateus.jera.helpers.Book;

/**
 * Created by mateus on 04/08/17.
 */

public interface BookRegisterContract {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {
        void insertBook(String title, int pages);
    }
}
