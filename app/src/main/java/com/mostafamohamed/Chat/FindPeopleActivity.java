package com.mostafamohamed.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FindPeopleActivity extends AppCompatActivity {

    RecyclerView findFriend;
    EditText searchE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_find_people );
    searchE=findViewById ( R.id. search_user);
    findFriend=findViewById ( R.id.find_list );
    findFriend.setLayoutManager ( new LinearLayoutManager ( getApplicationContext () ) );
    }
    public static class FindFriendViewHolder extends RecyclerView.ViewHolder {

        TextView userNameText;
        Button videocall;
        ImageView profileimageView;
        RelativeLayout cardView;

        public FindFriendViewHolder(@NonNull View itemView) {
            super ( itemView);
            userNameText=itemView.findViewById ( R.id.name_notification );
           videocall=itemView.findViewById ( R.id.call_btn );
            profileimageView=itemView.findViewById ( R.id.image_notification );
            cardView=itemView.findViewById ( R.id.card_view1 );


        }
    }
}
