package com.mostafamohamed.Chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity {
RecyclerView notification_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_notification );
    notification_list=findViewById ( R.id.notificatio_list );
    notification_list.setLayoutManager ( new LinearLayoutManager ( getApplicationContext () ) );
    }
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView userNameText;
        Button acceptBtn, cancelBtn;
        ImageView profileimageView;
        RelativeLayout cardView;

        public NotificationViewHolder(@NonNull View itemView) {
            super ( itemView);
            userNameText=itemView.findViewById ( R.id.name_notification );
            acceptBtn=itemView.findViewById ( R.id.request_accept );
            cancelBtn=itemView.findViewById ( R.id.request_decline );
            profileimageView=itemView.findViewById ( R.id.image_notification );
            cardView=itemView.findViewById ( R.id.card_view );


        }
    }
}
