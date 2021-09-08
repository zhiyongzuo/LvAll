package com.epro.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private TextView mTextView;
    public static final String WEB_VIEW_EXTRA = "WEB_VIEW_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.et);
        mTextView = findViewById(R.id.tv);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra(WEB_VIEW_EXTRA, mEditText.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
