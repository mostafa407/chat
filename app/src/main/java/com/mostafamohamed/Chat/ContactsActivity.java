package com.mostafamohamed.Chat;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mostafamohamed.Chat.Chat.UserListAdapter;
import com.mostafamohamed.Chat.User.Users;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayout;
    EditText searchE;
    Button videocall;
    ImageView profileimageView;
    RelativeLayout cardView;

    ArrayList<Users>userList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_contacts );
        searchE=findViewById ( R.id. search_user);
        videocall=findViewById ( R.id.call_btn );
        profileimageView=findViewById ( R.id.image_notification );
        cardView=findViewById ( R.id.card_view1 );
        userList=new ArrayList<> (  );
        intializeRecyclerView();
        getcontactslist();
    }

    private void getcontactslist(){

        Cursor phones=getContentResolver ().query ( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null );
        while (phones.moveToNext ()){
            String name=phones.getString ( phones.getColumnIndex ( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ) );
            String phone=phones.getString ( phones.getColumnIndex ( ContactsContract.CommonDataKinds.Phone.NUMBER ) );
            Users mContacts=new Users ( name, phone);
            userList.add ( mContacts );
            mUserListAdapter.notifyDataSetChanged ();


        }
    }
    @SuppressLint("WrongConstant")
    private void intializeRecyclerView() {
        mUserList=findViewById ( R.id.user_list );
        mUserList.setNestedScrollingEnabled ( false );
        mUserList.setHasFixedSize ( false );
        mUserListLayout=new LinearLayoutManager ( getApplicationContext (), LinearLayout.VERTICAL,false );
        mUserList.setLayoutManager ( mUserListLayout );
        mUserListAdapter=new UserListAdapter (userList);
        mUserList.setAdapter ( mUserListAdapter );
    }
}
