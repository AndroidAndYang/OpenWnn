package com.googlecode.openwnn.legacy.listener;


import android.view.View;

/**
 * @author: YJZ
 * @date: 2020/5/8 19:46
 * @Des:
 **/
public interface IKeyboardSelectListener {

    void keyboardSelect(View view, String content);

    void deleteText(View view);
}
