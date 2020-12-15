package il.co.expertize.emailauthfirebase.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import il.co.expertize.emailauthfirebase.R;
import il.co.expertize.emailauthfirebase.UI.Main.CompanyTravelsFragment;
import il.co.expertize.emailauthfirebase.UI.Main.HistoryTravelsFragment;
import il.co.expertize.emailauthfirebase.UI.Main.RegisteredTravelsFragment;

public class NavigationDrawerActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

   // @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_nevigation_drawer);
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
                        loadFragment(new CompanyTravelsFragment());
                        return true;
                    case R.id.Registered_Travel:
                        loadFragment(new HistoryTravelsFragment());
                        return true;
                    case R.id.Company_Travel:
                        loadFragment(new RegisteredTravelsFragment());
                        return true;
                    case R.id.exit:
                        finish();
                        System.exit(0);
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
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, tmp);
        fragmentTransaction.commit(); // save the changes
    }
}
