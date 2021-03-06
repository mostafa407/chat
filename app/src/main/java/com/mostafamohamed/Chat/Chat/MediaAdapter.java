package com.mostafamohamed.Chat.Chat;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mostafamohamed.Chat.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder>{
  private   ArrayList<String>mediaList;
     private  Context context;
    public MediaAdapter(Context context, ArrayList<String>mediaList){
        this.context=context;
        this.mediaList=mediaList;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View layoutView= LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_media ,parent, false);
     MediaViewHolder mediaViewHolder=new  MediaViewHolder(layoutView);

        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {

        Glide.with ( context ).load ( Uri.parse ( mediaList.get ( position ) ) ).into ( holder.mMedia );
    }

    @Override
    public int getItemCount() {
        return mediaList.size ();
    }
       class MediaViewHolder extends RecyclerView.ViewHolder{
        ImageView mMedia;

        MediaViewHolder(@NonNull View itemView) {
            super ( itemView );
            mMedia=itemView.findViewById ( R.id.media );
        }
    }
}
