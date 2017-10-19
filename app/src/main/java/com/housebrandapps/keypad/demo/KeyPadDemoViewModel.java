package com.housebrandapps.keypad.demo;

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
