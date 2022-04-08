package com.mostafamohamed.Chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mostafamohamed.Chat.Chat.MediaAdapter;
import com.mostafamohamed.Chat.Chat.MessageAdapter;
import com.mostafamohamed.Chat.Chat.Messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mChat ,mMedia;
    private RecyclerView.Adapter mChatAdapter, mMediaAdapter;
    private RecyclerView.LayoutManager mChatLayoutManger,mMediaLayoutManger;
    ArrayList<Messages>messageList;
    String chatID;
    DatabaseReference mChatDb;
    CircleImageView profile_imagee;
    TextView username;
    FirebaseUser fuser;
    Intent intent;
    ImageView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_chat );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        profile_imagee=findViewById ( R.id.profile_image );
        Button mSend=findViewById ( R.id.send );
        Button Camera=findViewById ( R.id.camera );
        view=findViewById ( R.id.capture );
        username=findViewById ( R.id.username );
        mChatDb=FirebaseDatabase.getInstance ().getReference ("Message");
        intent=getIntent ();
        String userid=intent.getStringExtra ( "userid" );
        fuser=FirebaseAuth.getInstance ().getCurrentUser ();

        mSend.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        } );

        Camera.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult ( intent1,0 );
            }
        } );
        intializeMessage();
        intializeMedia();

    }




    private void sendMessage() {
        EditText mMessage=findViewById ( R.id.messageInput );
        mChatDb.child ( Long.toString ( System.currentTimeMillis () ) ).setValue ( mMessage.getText ().toString () );
        mMessage.setText ( "" );
        if (!mMessage.getText ().toString ().isEmpty ()){

            DatabaseReference newMessage= FirebaseDatabase.getInstance ().getReference ().child ( "chat" ).child ( chatID ).push ();
            Map<String, Object> newMessageMap=new HashMap<String, Object> (  );
            newMessageMap.put ( "text", mMessage.getText ().toString ());
            newMessageMap.put ( "creator", FirebaseAuth.getInstance ().getUid () );
            newMessage.updateChildren ( newMessageMap );


        }
        mMessage.setText ( null );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ( ).inflate ( R.menu.chat, menu );
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.call:
                Intent intent=new Intent ( Intent.ACTION_CALL );
                intent.setData ( Uri.parse ( "tel:"+username) );
                startActivity ( intent );

                    case R.id.media:
                        Intent intent1=new Intent (  );
                        intent1.setType ( "image/*" );
                        intent1.putExtra ( intent1.EXTRA_ALLOW_MULTIPLE, true );
                        intent1.setAction ( intent1.ACTION_GET_CONTENT );
                        startActivityForResult ( intent1.createChooser ( intent1,"Select Picture(s)" ), PICK_IMAGE_INTENT);


        }
        return super.onOptionsItemSelected ( item );


    }

    @SuppressLint("WrongConstant")
    private void intializeMessage() {
        messageList=new ArrayList<> (  );
        mChat=findViewById ( R.id.messageList );
        mChat.setNestedScrollingEnabled ( false );
        mChat.setHasFixedSize ( false );
        mChatLayoutManger=new LinearLayoutManager ( getApplicationContext (), LinearLayout.VERTICAL,false );
        mChat.setLayoutManager ( mChatLayoutManger );
        mChatAdapter=new MessageAdapter (messageList);
        mChat.setAdapter ( mChatAdapter );
    }
    int PICK_IMAGE_INTENT=1;
    ArrayList<String>mediaUriList=new ArrayList<> (  );

    @SuppressLint("WrongConstant")
    private void intializeMedia() {
        messageList=new ArrayList<> (  );
        mMedia=findViewById ( R.id.mediaList );
        mMedia.setNestedScrollingEnabled ( false );
        mMedia.setHasFixedSize ( false );
        mMediaLayoutManger=new LinearLayoutManager ( getApplicationContext (), LinearLayout.HORIZONTAL,false );
        mMedia.setLayoutManager ( mMediaLayoutManger );
        mMediaAdapter=new MediaAdapter (getApplicationContext (),mediaUriList);
        mMedia.setAdapter ( mMediaAdapter );
    }

    private void openaGallery() {
        Intent intent=new Intent (  );
        intent.setType ( "image/*" );
        intent.putExtra ( Intent.EXTRA_ALLOW_MULTIPLE, true );
        intent.setAction ( intent.ACTION_GET_CONTENT );
        startActivityForResult ( Intent.createChooser ( intent,"Select Picture(s)" ), PICK_IMAGE_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        assert data != null;
        Bitmap bitmap=(Bitmap)data.getExtras ().get ( "data" );
        view.setImageBitmap ( bitmap );
        if (requestCode==RESULT_OK){
            if (requestCode==PICK_IMAGE_INTENT){
                if (data.getClipData ()==null){
                    mediaUriList.add ( data.getData ().toString () );

                }else {
                    for (int i=0; i<data.getClipData ().getItemCount ();i++){
                        mediaUriList.add ( data.getClipData ().getItemAt ( i ).getUri ().toString () );

                    }

                }
                mMediaAdapter.notifyDataSetChanged ();

            }
        }
    }

}
