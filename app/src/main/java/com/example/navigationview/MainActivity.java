package com.example.navigationview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        DrawerLayout mDrawer = findViewById(R.id.drawerLayout);
        NavigationView mNavigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this,R.id.fragment2);

        //setup bottom navigation
        NavigationUI.setupWithNavController(mBottomNavigationView,navController);

        //Setup Top Back post_style_button
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(mDrawer).build();
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawer);

        //Setup Navigation Drawer
        NavigationUI.setupWithNavController(mNavigationView, navController);


        MenuItem explore = mNavigationView.getMenu().findItem(R.id.explore);
        explore.setOnMenuItemClickListener(item -> {
            String url ="https://www.who.int";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData( Uri.parse(url));
            startActivity(intent);

            return true;
        });


        MenuItem share = mNavigationView.getMenu().findItem(R.id.share);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"My Doctor");
                startActivity(Intent.createChooser(intent,"Choose One!"));

                return true;
            }
        });

        MenuItem feedback = mNavigationView.getMenu().findItem(R.id.user_feedbacck);
        feedback.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/email");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pkibaki081@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            intent.putExtra(Intent.EXTRA_TEXT,"there is an issue");
            startActivity(Intent.createChooser(intent, "Send Feedback"));

            return true;
        });

        MenuItem emagency_call = mBottomNavigationView.getMenu().findItem(R.id.emagency);
        emagency_call.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String emagency_No ="+254796808883";
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",emagency_No,null));
                startActivity(intent);

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    //For Back button --top

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration);
    }
}