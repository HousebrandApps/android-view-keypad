package com.housebrandapps.keypad;

/**
 * Created by natieklopper on 2017/10/19.
 */

public class KeyPadDemoViewModel {

    private KeyPadDemoView view;

    public KeyPadDemoViewModel(KeyPadDemoView view) {
        this.view = view;
    }

    public void onOkay(String value) {
        view.showValue(value);
    }

    public interface KeyPadDemoView {
        void showValue(String value);
    }
}
