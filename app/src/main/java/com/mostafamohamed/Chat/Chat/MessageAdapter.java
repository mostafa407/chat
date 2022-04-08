package com.mostafamohamed.Chat.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mostafamohamed.Chat.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    ArrayList<Messages>messageList;
    public MessageAdapter(ArrayList<Messages>messageList){
        this.messageList=messageList;

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutview= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_message,parent,false );
        RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        layoutview.setLayoutParams ( lp );
        MessageViewHolder rcv=new MessageViewHolder ( layoutview );
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
    holder.mMessage.setText ( messageList.get ( position ).getMessage () );
        holder.mSender.setText ( messageList.get ( position ).getSenderId () );

    }

    @Override
    public int getItemCount() {
        return messageList.size ();
    }

     class MessageViewHolder extends RecyclerView.ViewHolder{
         TextView mMessage,mSender;
         LinearLayout mLayout;
         MessageViewHolder(View view){
            super(view);
            mMessage=view.findViewById ( R.id. message);
            mSender=view.findViewById ( R.id.sender );
             mLayout=view.findViewById ( R.id.linear );

        }


    }
}
