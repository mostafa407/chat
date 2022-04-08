package com.mostafamohamed.Chat.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mostafamohamed.Chat.R;
import com.mostafamohamed.Chat.User.Users;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

 ArrayList<Users>userList;

    public UserListAdapter(ArrayList<Users> userList){
        this.userList=userList;

    }


    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutview= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.contact_design,null,false );
        RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        layoutview.setLayoutParams ( lp );
        UserListViewHolder rcv=new UserListViewHolder ( layoutview );
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        holder.mName.setText ( userList.get ( position ).getName ( ) );
        holder.mPhone.setText ( userList.get ( position ).getPhone ( ) );

    }

    @Override
    public int getItemCount() {
        return userList.size ();
    }

     class UserListViewHolder extends RecyclerView.ViewHolder{
         TextView mName ,mPhone;
         ImageView imageView;
         LinearLayout mLayout;
         UserListViewHolder(View view){
            super(view);
            mName=view.findViewById ( R.id.name );
            mPhone=view.findViewById ( R.id.phone );
            mLayout=view.findViewById ( R.id.linear_layout );
            imageView=view.findViewById ( R.id.image_contact );

         }


    }
}
