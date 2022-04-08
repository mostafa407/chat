package com.mostafamohamed.Chat.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlankViewModel extends ViewModel {
    private MutableLiveData<String> mText;


    public BlankViewModel() {
        mText = new MutableLiveData<> ( );
        mText.setValue ( "This is slideshow fragment" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}

