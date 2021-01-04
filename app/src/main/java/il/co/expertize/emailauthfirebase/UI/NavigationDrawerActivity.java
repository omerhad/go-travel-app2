package il.co.expertize.emailauthfirebase.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import il.co.expertize.emailauthfirebase.Entities.User;
import il.co.expertize.emailauthfirebase.R;
import il.co.expertize.emailauthfirebase.UI.Main.CompanyTravelsFragment;
import il.co.expertize.emailauthfirebase.UI.Main.HistoryTravelsFragment;
import il.co.expertize.emailauthfirebase.UI.Main.LoginActivity;
import il.co.expertize.emailauthfirebase.UI.Main.RegisteredTravelsFragment;

public class NavigationDrawerActivity extends AppCompatActivity {
    private TextView name,email;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DatabaseReference userRef;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private User user;
    private static final String USERS = "users";
    private final String TAG = this.getClass().getName().toUpperCase();

   // @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_nevigation_drawer);
        Intent intent=getIntent();
        String recieveEmail=intent.getStringExtra(LoginActivity.Email);
        name=findViewById(R.id.textName);
       // email= findViewById(R.id.textEmail);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USERS);




        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("email").getValue(String.class).equals(recieveEmail)){
                        user=new User(ds.child("email").getValue(String.class)
                                ,ds.child("password").getValue(String.class),
                                ds.child("fullName").getValue(String.class),
                                ds.child("phone").getValue(String.class));
                        Toast.makeText(getApplicationContext(),user.getEmail(),Toast.LENGTH_LONG).show();
                                break;
                    }
                }
                String a=user.getEmail().toString();
                String b=user.getFullName().toString();
                //email.setText(a+"\n"+b);
                name.setText("\n"+"\n"+"\n"+"\n"+b+"\n"+a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        userRef.addValueEventListener(postListener);





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dl = (DrawerLayout)findViewById(R.id.navigation_drawer);
        t = new ActionBarDrawerToggle(this, dl,toolbar,R.string.Open, R.string.Close)
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("Close");
                // calling onPrepareOptionsMenu() to show action bar icons
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Open");
                // calling onPrepareOptionsMenu() to hide action bar icons
                supportInvalidateOptionsMenu();
            }
        };

        dl.addDrawerListener(t);
        t.syncState();
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.History_Travel:
                        loadFragment(new  HistoryTravelsFragment());
                        return true;
                    case R.id.Registered_Travel:
                        loadFragment(new RegisteredTravelsFragment());
                        return true;
                    case R.id.Company_Travel:
                        loadFragment(new CompanyTravelsFragment());
                        return true;
//                    case R.id.exit:
//                        finish();
//                        System.exit(0);
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    private void loadFragment(Fragment tmp) {
        dl.closeDrawer(nv);
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, tmp);
        fragmentTransaction.commit(); // save the changes
    }
}
