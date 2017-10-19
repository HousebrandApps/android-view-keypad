package com.housebrandapps.keypad;

class KeyPadViewModel {

    static final int KEY_CLEAR = 9;
    static final int KEY_ZERO = 10;
    static final int KEY_OKAY = 11;

    private final KeyPadView view;
    private String[] entries;
    private int currentScreen = 0;

    private String value = "";

    KeyPadViewModel(KeyPadView view, int displayTotal) {
        this.view = view;
        this.entries = new String[displayTotal];
    }

    String getValue() {
        return value;
    }

    void buttonPressOn(int position) {
        switch (position) {
            case KEY_CLEAR:
                currentScreen = 0;
                value = "";
                for (int i = 0; i < entries.length; i++) {
                    entries[i] = null;
                    view.updateDisplays(i, entries[i]);
                }
                break;
            case KEY_OKAY:
                view.okayClicked();
                break;
            case KEY_ZERO:
            default:
                if (currentScreen <= entries.length - 1) {
                    int enteredValue = position + (position == KEY_ZERO ? -position : 1);
                    value += "" + enteredValue;
                    view.updateDisplays(currentScreen, "" + enteredValue);
                    currentScreen++;
                }
                break;
        }
    }

    interface KeyPadView {
        void updateDisplays(int pos, String text);
        void okayClicked();
    }
}
