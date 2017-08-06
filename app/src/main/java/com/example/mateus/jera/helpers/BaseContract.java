package com.example.mateus.jera.helpers;

/**
 * Created by mateus on 06/08/17.
 */

public interface BaseContract {

    interface View {
        void logSuccess();
        void logError(Exception e);
        void showSuccess(String msg);
        void showError(String msg);
    }

    interface Presenter {

    }
}
