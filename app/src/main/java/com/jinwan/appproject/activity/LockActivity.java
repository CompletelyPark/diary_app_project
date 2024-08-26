package com.jinwan.appproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jinwan.appproject.R;

public class LockActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "AppLockPreferences";
    private static final String PASSWORD_KEY = "PasswordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // 이미 비밀번호가 설정되어 있는지 확인
        if (isPasswordSet()) {
            showUnlockDialog();
        } else {
            showSetPasswordDialog();
        }
    }

    private boolean isPasswordSet() {
        return sharedPreferences.contains(PASSWORD_KEY);
    }

    private void savePassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    private String getPassword() {
        return sharedPreferences.getString(PASSWORD_KEY, null);
    }

    private void showSetPasswordDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("패스워드 설정");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("저장", (dialog, which) -> {
            String password = input.getText().toString();
            savePassword(password);
            startActivity(new Intent(LockActivity.this, MainActivity.class));
            finish();
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showUnlockDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Enter password");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("Unlock", (dialog, which) -> {
            String password = input.getText().toString();
            if (password.equals(getPassword())) {
                dialog.dismiss();
                // 앱 메인 화면으로 이동
                startActivity(new Intent(LockActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LockActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
