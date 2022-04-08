package com.mostafamohamed.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainpageActivity extends AppCompatActivity {
BottomNavigationView navView;
RecyclerView mContactList;
ImageView findpeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_contact );
       navView = findViewById ( R.id.nav_view );
      navView.setOnNavigationItemSelectedListener ( navigationItemSelectedListener );
      findpeople=findViewById ( R.id.find_people );
      mContactList=findViewById ( R.id.contact_list );
      mContactList.setLayoutManager (new  LinearLayoutManager(getApplicationContext ()) );
findpeople.setOnClickListener ( new View.OnClickListener ( ) {
    @Override
    public void onClick(View v) {
        Toast.makeText ( MainpageActivity.this, "contacts is selected", Toast.LENGTH_SHORT ).show ( );
        Intent intent1=new Intent ( getApplicationContext (), ContactsActivity.class );
        startActivity ( intent1 );
    }
} );


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener ( ) {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId ()){
                case R.id.navigation_home:
                    Intent mainIntent=new Intent ( MainpageActivity.this, MainpageActivity.class );
                    startActivity ( mainIntent );
                    break;
                case R.id.navigation_settings:
                    Intent settingIntent=new Intent ( MainpageActivity.this,SettingsActivity.class );
                    startActivity ( settingIntent );
                    break;
                case R.id.navigation_notifications:
                    Intent notificationsIntent=new Intent ( MainpageActivity.this,NotificationActivity.class );
                    startActivity ( notificationsIntent );
                    break;
                case R.id.navigation_logout:
                    FirebaseAuth.getInstance ().signOut ();
                    Intent logoutIntent=new Intent ( MainpageActivity.this,LoginActivity.class );
                    startActivity ( logoutIntent );
                    finish ();
                    break;
            }
            return true;
        }
    };
}
