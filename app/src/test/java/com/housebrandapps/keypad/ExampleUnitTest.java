package com.housebrandapps.keypad;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private KeyPadDemoViewModel.KeyPadDemoView view;
    private String testValue = "";

    @Test
    public void testOkayClickedWhoop() throws Exception {
        view = new KeyPadDemoViewModel.KeyPadDemoView() {
            @Override
            public void showValue(String value) {
                testValue = value;
            }
        };
        KeyPadDemoViewModel viewModel = new KeyPadDemoViewModel(view);
        viewModel.onOkay("1234");
        assertEquals("1234", testValue);
    }
}