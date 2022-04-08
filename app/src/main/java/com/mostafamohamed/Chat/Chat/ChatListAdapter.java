package com.mostafamohamed.Chat.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mostafamohamed.Chat.ChatActivity;
import com.mostafamohamed.Chat.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>{
 private    ArrayList<Chat> chatList;
    public ChatListAdapter(ArrayList<Chat>chatList){ this.chatList=chatList; }


    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutview= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_message,parent,false );
        RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        layoutview.setLayoutParams ( lp );
        ChatListViewHolder rcv=new ChatListViewHolder ( layoutview );
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatListViewHolder holder, int position) {
        holder.mTitle.setText ( chatList.get ( position ).getChatid () );
        holder.mLayout.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent ( v.getContext (), ChatActivity.class );
                Bundle bundle=new Bundle (  );
                bundle.putString ( "chatID", chatList.get ( holder.getAdapterPosition () ).getChatid () );
                intent.putExtras ( bundle );
                v.getContext ().startActivity ( intent );
            }
        } );
    }



    @Override
    public int getItemCount() {
        return chatList.size ();
    }

     class ChatListViewHolder extends RecyclerView.ViewHolder{
        TextView mTitle;
         LinearLayout mLayout;
         ChatListViewHolder(@NonNull View view) {
            super ( view );
            mTitle=view.findViewById ( R.id.title );
            mLayout=view.findViewById ( R.id.linear_layout );
        }
    }}
