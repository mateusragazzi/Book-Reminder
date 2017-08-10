package com.example.mateus.jera.helpers;

/**
 * Created by mateus on 06/08/17.
 */

public interface BaseContract {

    interface View {
        void showSuccess(final int msg);
        void showError(final int msg);
    }

    interface Presenter {}
}
