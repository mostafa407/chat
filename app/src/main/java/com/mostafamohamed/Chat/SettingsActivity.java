package com.mostafamohamed.Chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button saveBtn;
    EditText userName, userBio;
    ImageView profile;
    private static int GalleryPick=1;
    private Uri ImageUri;
    private StorageReference userprofileImage;
    private DatabaseReference userRef;
    private String downloadUrl;
   private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_settings );
        userprofileImage= FirebaseStorage.getInstance ().getReference ().child ( "Profile Image" );
         userRef= FirebaseDatabase.getInstance ().getReference ().child ( "Users" );
        saveBtn=findViewById ( R.id.save_setting );
    userName=findViewById ( R.id.username_setting );
    userBio=findViewById ( R.id.bio_setting );
    profile=findViewById ( R.id.settings_profile );
    progressDialog=new ProgressDialog ( this );

    profile.setOnClickListener ( new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            Intent gallerryIntent=new Intent (  );
            gallerryIntent.setAction (Intent.ACTION_GET_CONTENT);
            gallerryIntent.setType ( "image/*" );
            startActivityForResult ( gallerryIntent ,GalleryPick);
        }
    } );
    saveBtn.setOnClickListener ( new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            saveUserData();
        }
    } );
        retriveUserInfo();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
     if (requestCode==GalleryPick&& resultCode==RESULT_OK&&data!=null){
         ImageUri=data.getData ();
         profile.setImageURI ( ImageUri );
     }
    }
    private void saveUserData() {
        final String getUserName=userName.getText ().toString ();
        final String getUserStatus=userBio.getText ().toString ();
if (ImageUri==null){
    userRef.addValueEventListener ( new ValueEventListener ( ) {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () ).hasChild ( "image" )) {
            saveInfoWithoutImage();
        }  else {
            Toast.makeText ( SettingsActivity.this, "please select Image", Toast.LENGTH_SHORT ).show ( );
        }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    } );
}else if (getUserName.equals (  "")){
    Toast.makeText ( this, "userName is mandatory", Toast.LENGTH_SHORT ).show ( );
}
else if (getUserStatus.equals (  "")){
    Toast.makeText ( this, "bio is mandatory", Toast.LENGTH_SHORT ).show ( );
}
else {
    progressDialog.setTitle ( "Account Settings" );
    progressDialog.setMessage ( "please wait...." );
    progressDialog.show ();
    final StorageReference filebath=userprofileImage.child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () );
final UploadTask uploadTask=filebath.putFile ( ImageUri );
uploadTask.continueWithTask ( new Continuation<UploadTask.TaskSnapshot, Task<Uri>> ( ) {
    @Override
    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
     if (task.isSuccessful ()){
         throw task.getException ();
     }
     downloadUrl=filebath.getDownloadUrl ().toString ();
        return filebath.getDownloadUrl ();
    }
} ).addOnCompleteListener ( new OnCompleteListener<Uri> ( ) {
    @Override
    public void onComplete(@NonNull Task<Uri> task) {
    if (task.isSuccessful ()){
        downloadUrl=task.getResult ().toString ();
        HashMap<String, Object>profileMap=new HashMap<> (  );
        profileMap.put ( "uid",FirebaseAuth.getInstance ().getCurrentUser ().getUid () );
        profileMap.put ( "name", getUserName );
        profileMap.put ( "status", getUserStatus );
        profileMap.put ( "image", downloadUrl );
       userRef.child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () )
               .updateChildren ( profileMap ).addOnCompleteListener ( new OnCompleteListener<Void> ( ) {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
           if (task.isSuccessful ()){
               Intent intent=new Intent ( SettingsActivity.this,MainpageActivity.class );
               startActivity ( intent );
               finish ();
               Toast.makeText ( SettingsActivity.this, "Profile setting has been updated.", Toast.LENGTH_SHORT ).show ( );
           }

           }
       } );

    }
    }
} );
}
    }

    private void saveInfoWithoutImage() {
        final String getUserName=userName.getText ().toString ();
        final String getUserStatus=userBio.getText ().toString ();

         if (getUserName.equals (  "")){
            Toast.makeText ( this, "userName is mandatory", Toast.LENGTH_SHORT ).show ( );
        }
        else if (getUserStatus.equals (  "")){
            Toast.makeText ( this, "bio is mandatory", Toast.LENGTH_SHORT ).show ( );
        }
        else {
             progressDialog.setTitle ( "Account Settings" );
             progressDialog.setMessage ( "please wait...." );
             progressDialog.show ();
             HashMap<String, Object>profileMap=new HashMap<> (  );
             profileMap.put ( "uid",FirebaseAuth.getInstance ().getCurrentUser ().getUid () );
             profileMap.put ( "name", getUserName );
             profileMap.put ( "status", getUserStatus );

             userRef.child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () )
                     .updateChildren ( profileMap ).addOnCompleteListener ( new OnCompleteListener<Void> ( ) {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful ()){
                         Intent intent=new Intent ( SettingsActivity.this,MainpageActivity.class );
                         startActivity ( intent );
                         finish ();
                         progressDialog.dismiss ();
                         Toast.makeText ( SettingsActivity.this, "Profile setting has been updated.", Toast.LENGTH_SHORT ).show ( );
                     }

                 }
             } );
         }

    }
    private void retriveUserInfo(){
        userRef.child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () )
                .addValueEventListener ( new ValueEventListener ( ) {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists ()){
                        String imageDb=dataSnapshot.child ( "image" ).getValue ().toString ();
                        String nameDb=dataSnapshot.child ( "name" ).getValue ().toString ();
                        String bioDb=dataSnapshot.child ( "status" ).getValue ().toString ();

                        userName.setText ( nameDb );
                        userBio.setText ( bioDb );
                        Picasso.get ().load ( imageDb ).placeholder ( R.drawable.profile_image ).into ( profile );
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
    }
}
