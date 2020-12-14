package il.co.expertize.emailauthfirebase.UI.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import il.co.expertize.emailauthfirebase.R;
import il.co.expertize.emailauthfirebase.UI.NavigationDrawerActivity;

public class CreateAccount extends AppCompatActivity {

    private EditText emailTV, passwordTV;
    private Button regBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);
        mAuth = FirebaseAuth.getInstance();
        initializeUI();
    }

    private void registerNewUser() {


        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }





        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
//                            ContentLoadingProgressBar loadingProgressBar;
//                            loadingProgressBar.set
                            //Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    CreateAccount.this);
                            builder.setTitle("Hello "+ emailTV.getText().toString());
                            builder.setMessage("Verify your email for continue");

                            builder.setPositiveButton("VERIFY",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            Toast.makeText(getApplicationContext(), "VERIFY is clicked", Toast.LENGTH_LONG).show();
                                            sendEmailVerification();
                                            Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            builder.setNeutralButton("CANCEL",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            Toast.makeText(getApplicationContext(),"Cancel is clicked",Toast.LENGTH_LONG).show();
                                        }
                                    });
                            builder.show();
                            progressBar.setVisibility(View.GONE);

                        }
                        else {
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }



    private void sendEmailVerification() {
        // Disable Verify Email button
        //findViewById(R.id.btn_verify_email).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            if (user.isEmailVerified())
                            {
                                // user is verified, so you can finish this activity or send user to activity which you want.
                                // finish();

                                //Toast.makeText(CreateAccount.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                // update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                                Intent intent = new Intent(CreateAccount.this, NavigationDrawerActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            //Log.e(TAG, "sendEmailVerification failed!", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }
}
