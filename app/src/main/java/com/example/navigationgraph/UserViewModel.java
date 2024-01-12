package com.example.navigationgraph;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<String>userName = new MutableLiveData<>() ;
    public LiveData<String>getUsername (){
        if (userName==null){
            userName = new MutableLiveData<>();
            userName.setValue(null);
        }
        return userName ;

    }

    public void setUserName(String value) {
       userName.setValue(value);
    }


}
