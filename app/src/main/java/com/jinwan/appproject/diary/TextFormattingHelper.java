package com.jinwan.appproject.diary;

import android.text.Spannable;
import android.widget.EditText;

public class TextFormattingHelper {

    private EditText editText;

    public TextFormattingHelper(EditText editText) {
        this.editText = editText;
    }

    public void applyStyle(Object span) {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        if (start < end) {
            Spannable spannable = editText.getText();
            spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            editText.setText(spannable);
            editText.setSelection(end);
        }
    }
}
