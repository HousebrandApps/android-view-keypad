package com.housebrandapps.keypad;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class KeyPadViewModelTest {

    private KeyPadViewModel viewModel;
    private KeyPadViewModel.KeyPadView view;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(KeyPadViewModel.KeyPadView.class);
    }

    @Test
    public void testWithFour() throws Exception {
        viewModel = new KeyPadViewModel(view, 4);
        viewModel.buttonPressOn(2);
        Mockito.verify(view).updateDisplays(0, "3");
        viewModel.buttonPressOn(4);
        Mockito.verify(view).updateDisplays(1, "5");
        Assert.assertEquals("35", viewModel.getValue());
        viewModel.buttonPressOn(0);
        Mockito.verify(view).updateDisplays(2, "1");
        Assert.assertEquals("351", viewModel.getValue());
        viewModel.buttonPressOn(1);
        Mockito.verify(view).updateDisplays(3, "2");
        Assert.assertEquals("3512", viewModel.getValue());
        viewModel.buttonPressOn(3);
        Mockito.verify(view, Mockito.never()).updateDisplays(4, "2");
        Assert.assertEquals("3512", viewModel.getValue());

        viewModel.buttonPressOn(KeyPadViewModel.KEY_OKAY);
        Mockito.verify(view).okayClicked();

        viewModel.buttonPressOn(KeyPadViewModel.KEY_CLEAR);
        Mockito.verify(view).updateDisplays(0, null);
        Mockito.verify(view).updateDisplays(1, null);
        Mockito.verify(view).updateDisplays(2, null);
        Mockito.verify(view).updateDisplays(3, null);
        Mockito.verify(view, Mockito.never()).updateDisplays(4, null);
    }

    @Test
    public void testZeroClicked() throws Exception {
        viewModel = new KeyPadViewModel(view, 4);
        viewModel.buttonPressOn(10);
        Mockito.verify(view).updateDisplays(0, "0");
    }

    @Test
    public void testWithVar() throws Exception {
        viewModel = new KeyPadViewModel(view, 6);
        String valueChecker = "";
        for (int i = 0; i < 6; i++) {
            int checkedV = i + 1;
            valueChecker += "" + checkedV;
            viewModel.buttonPressOn(i);
            Mockito.verify(view).updateDisplays(i, "" + checkedV);
            Assert.assertEquals(valueChecker, viewModel.getValue());
        }
        Assert.assertEquals("123456", viewModel.getValue());
        Mockito.verify(view, Mockito.never()).updateDisplays(4, "2");
        viewModel.buttonPressOn(KeyPadViewModel.KEY_CLEAR);
        for (int i = 0; i < 6; i++) {
            Mockito.verify(view).updateDisplays(i, null);
        }
        Mockito.verify(view, Mockito.never()).updateDisplays(7, null);

        viewModel.buttonPressOn(KeyPadViewModel.KEY_OKAY);
        Mockito.verify(view).okayClicked();
    }
}