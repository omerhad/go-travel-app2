/**
 * Code based on:
 * URL 1: https://blog.usejournal.com/firebase-email-and-password-authentication-for-android-e335c81a1dad
 * URL 2: https://github.com/buildbro/FirebaseEmailPasswordExample
 */

package il.co.expertize.emailauthfirebase.UI.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import il.co.expertize.emailauthfirebase.R;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button registerBtn, loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);
    }
}
