package com.mostafamohamed.Chat.ui.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mostafamohamed.Chat.MainActivity;
import com.mostafamohamed.Chat.R;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    private Button UpdateAccountSettings;
    private EditText userName, userStatus;
    private CircleImageView userProfileImage;
    private String currentUserID;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private static final int GALLERY_PICK = 1;
    StorageReference mImageStorage;
    ProgressDialog progressDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of ( this ).get ( ToolsViewModel.class );
        View root = inflater.inflate ( R.layout.fragment_tools, container, false );
        auth=FirebaseAuth.getInstance ();
        currentUserID=auth.getCurrentUser ().getUid ();
        reference= FirebaseDatabase.getInstance ().getReference ();
        userName=root.findViewById ( R.id.user_name );
        userStatus=root.findViewById ( R.id.user_status );
        userProfileImage=root.findViewById ( R.id.set_profile_image );
        UpdateAccountSettings.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
              UpdateSettings();

            }
        } );
        RetriveUserInfo();


        return root;
    }

    private void UpdateSettings() {
        String setUserName=userName.getText ().toString ();
        String setStatus=userStatus.getText ().toString ();
        if (TextUtils.isEmpty ( setUserName )){
            Toast.makeText ( getActivity (), "please write your user name", Toast.LENGTH_SHORT ).show ( );
    }
        if (TextUtils.isEmpty ( setStatus )){
            Toast.makeText ( getActivity (), "please write your status", Toast.LENGTH_SHORT ).show ( );
    }else {
        HashMap<String ,String> profilemap=new HashMap<> (  );
        profilemap.put ( "uid", currentUserID);
        profilemap.put ( "name", setUserName );
        profilemap.put ( "status",setStatus );
        reference.child ( "Users" ).child ( currentUserID ).setValue ( profilemap )
                .addOnCompleteListener ( new OnCompleteListener<Void> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            SendUserToMainActivity ();
                            Toast.makeText ( getActivity (), "profile", Toast.LENGTH_SHORT ).show ( );

                        }   else {

                            String message=task.getException ().toString ();
                            Toast.makeText ( getActivity (), "ERROR"+message, Toast.LENGTH_SHORT ).show ( );

                        }
                    }
                } );


}
    }
    private void RetriveUserInfo() {
        reference.child ( "Users" ).child ( currentUserID ).addValueEventListener ( new ValueEventListener ( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists ())&&(dataSnapshot.hasChild ("name")&&(dataSnapshot.hasChild ( "image" )))){
                    String retriveusername=dataSnapshot.child ( "name" ).toString ();
                    String retrivestatus=dataSnapshot.child ( "status" ).toString ();
                    String retriveprofileimage=dataSnapshot.child ( "image" ).toString ();
                    userName.setText ( retriveusername );
                    userStatus.setText ( retrivestatus );

                }    else if ((dataSnapshot.exists ())&&(dataSnapshot.hasChild ( "name" ))){
                    String retriveusername=dataSnapshot.child ( "name" ).toString ();
                    String retrivestatus=dataSnapshot.child ( "status" ).toString ();
                    userName.setText ( retriveusername );
                    userStatus.setText ( retrivestatus );
                }else {
                    userName.setVisibility ( View.VISIBLE );
                    Toast.makeText ( getActivity (), "please set update your profile", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
    private void SendUserToMainActivity(){
        Intent intent= new Intent ( getActivity (), MainActivity.class );
        intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity ( intent );
        
    }


}