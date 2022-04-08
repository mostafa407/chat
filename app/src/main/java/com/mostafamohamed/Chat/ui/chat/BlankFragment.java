package com.mostafamohamed.Chat.ui.chat;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mostafamohamed.Chat.R;


public class BlankFragment extends Fragment {

   private BlankViewModel blankViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        blankViewModel= ViewModelProviders.of ( this ).get ( BlankViewModel.class );
       View root= inflater.inflate ( R.layout.fragment_blank, container, false );
        final TextView textView = root.findViewById ( R.id.text_gallery );
        blankViewModel.getText ( ).observe ((LifecycleOwner) getViewLifecycleOwnerLiveData(), new Observer<String> ( ) {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText ( s );
            }
        } );
        return root;
    }
}

