package com.example.softwaretest;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.googlecode.openwnn.legacy.listener.IKeyboardSelectListener;

import java.util.Locale;

/**
 * @author: YJZ
 * @date: 2020/5/7 10:57
 * @Des: 副屏上键盘文字传递
 **/
public class Empty extends Presentation implements IKeyboardSelectListener, View.OnClickListener {

    private MySoftWare mySoftWare;
    private TextView textView;
    private TextView textView2;

    public Empty(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_empty);

        textView = findViewById(R.id.tv);
        textView.setOnClickListener(this);

        textView2 = findViewById(R.id.tv2);
        textView2.setOnClickListener(this);

        mySoftWare = findViewById(R.id.keyboard);
        mySoftWare.setKeyboardSelectListener(this);
    }

    @Override
    public void keyboardSelect(View view, String content) {
        switch (view.getId()) {
            case R.id.tv:
                insertTextByCustomKeyboard(textView, content);
                break;

            case R.id.tv2:
                insertTextByCustomKeyboard(textView2, content);
                break;
        }
    }

    /**
     * 通过键盘选择的文字添加到TextView上
     *
     * @param textView 需要添加的文字
     *
     * @param content 键盘选择的文字
     */
    private void insertTextByCustomKeyboard(TextView textView, String content) {
        textView.setText(String.format(Locale.CHINA, "%s%s", textView.getText().toString(), content));
    }

    @Override
    public void deleteText(View view) {
        switch (view.getId()) {
            case R.id.tv:
                delTextByKeyboardDel(textView);
                break;

            case R.id.tv2:
                delTextByKeyboardDel(textView2);
                break;
        }
    }

    /**
     * 键盘删除回调textView上的文字
     *
     * @param textView 需要刪除的textView
     */
    private void delTextByKeyboardDel(TextView textView) {
        String localTextStr = textView.getText().toString();
        Log.e("TAG", "localTextStr = " + localTextStr);
        if (TextUtils.isEmpty(localTextStr)) {
            return;
        }
        String relStr = localTextStr.substring(0, localTextStr.length() - 1);
        textView.setText(relStr);
    }


    @Override
    public void onClick(View view) {
        mySoftWare.setVisibility(View.VISIBLE);
        switch (view.getId()) {
            case R.id.tv:
                mySoftWare.setView(textView);
                break;

            case R.id.tv2:
                mySoftWare.setView(textView2);
                break;
        }
    }
}
