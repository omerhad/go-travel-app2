package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AppActivity extends AppCompatActivity {
    TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        mTextview = (TextView)findViewById(R.id.textView);

        mTextview.setText("Hello:   " + getIntent().getStringExtra("myEmail"));
    }
}