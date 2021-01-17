package il.co.expertize.emailauthfirebase.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import il.co.expertize.emailauthfirebase.Entities.User;
import il.co.expertize.emailauthfirebase.R;

public class CreateAccount extends AppCompatActivity {

    private EditText emailTV, passwordTV,name, phone;
    private Button regBtn;
    private ProgressBar progressBar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    private User user2;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);
        mAuth = FirebaseAuth.getInstance();
        initializeUI();

    }


    private void registerNewUser() {


        String email, password, fullName, phoneNumber;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();
        fullName= name.getText().toString();
        phoneNumber=phone.getText().toString();

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(getApplicationContext(), "Please enter name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "Please enter phone number...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        user2=new User(email,password,fullName,phoneNumber);



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
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
                                            FirebaseUser user2 = mAuth.getCurrentUser();
                                            updateUI(user2);
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

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            if (user.isEmailVerified())
                            {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent intent = new Intent(CreateAccount.this, NavigationDrawerActivity.class);
                                startActivity(intent);
                            }
                        } else {
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
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user2); //adding user info to database
        Intent intent = new Intent(CreateAccount.this, MainActivity.class);
        startActivity(intent);
    }
}
