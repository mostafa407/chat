package com.mostafamohamed.Chat;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main3 );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences ( getBaseContext () );
        boolean performSync=prefs.getBoolean ( "perform_sync",true );
        String SyncInterval=prefs.getString ( "sync_interval","30" );
        String fullName=prefs.getString ( "full_name","" );
        String phone=prefs.getString ( "phone","" );
        Toast.makeText ( this, performSync +"", Toast.LENGTH_SHORT ).show ( );
        Toast.makeText ( this, SyncInterval , Toast.LENGTH_SHORT ).show ( );
        Toast.makeText ( this, fullName , Toast.LENGTH_SHORT ).show ( );
        Toast.makeText ( this, phone , Toast.LENGTH_SHORT ).show ( );
        prefs.edit ().putBoolean ( "shouldWe",true ).apply ();
        boolean shouldWe=prefs.getBoolean ( "shouldWe",false );


        FloatingActionButton fab = findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Snackbar.make ( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction ( "Action", null ).show ( );
            }
        } );
        final DrawerLayout drawer = findViewById ( R.id.drawer_layout );
        NavigationView navigationView = findViewById ( R.id.nav_view );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder ( R.id.nav_home,
                R.id.nav_new_group, R.id.nav_chat, R.id.nav_Contacts,
                R.id.nav_setting, R.id.nav_share, R.id.nav_send , R.id.nav_logout)
                .setDrawerLayout ( drawer )
                .build ( );
        NavController navController = Navigation.findNavController ( this, R.id.nav_host_fragment );
        NavigationUI.setupActionBarWithNavController ( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController ( navigationView, navController );
        navigationView.bringToFront ();

        navigationView.setNavigationItemSelectedListener ( new NavigationView.OnNavigationItemSelectedListener ( ) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId ()){
                    case R.id.nav_home:
                        Toast.makeText ( MainActivity.this, "home is selected", Toast.LENGTH_SHORT ).show ( );
                        break;
                    case R.id.nav_chat:
                        Toast.makeText ( MainActivity.this, "chat is selected", Toast.LENGTH_SHORT ).show ( );
                        Intent intent=new Intent ( MainActivity.this,ChatActivity.class );
                        startActivity ( intent );
                        break;
                        case R.id.nav_Contacts:
                        Toast.makeText ( MainActivity.this, "contacts is selected", Toast.LENGTH_SHORT ).show ( );
                        Intent intent1=new Intent ( getApplicationContext (), ContactsActivity.class );
                        startActivity ( intent1 );
                        break;
                        case R.id.nav_new_group:
                        Toast.makeText ( MainActivity.this, "group is selected", Toast.LENGTH_SHORT ).show ( );

                        break;
                        case R.id.nav_send:
                        Toast.makeText ( MainActivity.this, "send is selected", Toast.LENGTH_SHORT ).show ( );
                        break;
                        case R.id.nav_setting:
                            Intent intent2= new Intent ( MainActivity.this, SettingActivity.class );
                            startActivity ( intent2 );
                            Toast.makeText ( MainActivity.this, "settings is selected", Toast.LENGTH_SHORT ).show ( );
                        break;
                        case R.id.nav_share:
                        Toast.makeText ( MainActivity.this, "share is selected", Toast.LENGTH_SHORT ).show ( );
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance ().signOut ();
                        Intent i=new Intent ( MainActivity.this, LoginActivity.class );
                        startActivity ( i );
                        finish ();
                        break;

                }
                drawer.closeDrawers ();

                return false;
            }
        } );
        getPermission();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate ( R.menu.main3, menu );
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController ( this, R.id.nav_host_fragment );
        return NavigationUI.navigateUp ( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp ( );
    }
    private void getPermission(){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
        requestPermissions ( new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS},1 );
    }
    }

}
