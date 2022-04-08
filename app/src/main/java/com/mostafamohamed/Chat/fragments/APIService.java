package com.mostafamohamed.Chat.fragments;

import com.mostafamohamed.Chat.Notifications.MyResponse;
import com.mostafamohamed.Chat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers (
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAPckaUNM:APA91bHOUdFRAXRWrbFAx4xE6MuwBDdkFPwRZ8dz16aqVFNlr8ksAu57iK2DEXYdCuQ08XgY4sD0wXs2nhjr6AWBPOwBWSOmDLl-MIdeDUyOdQYSQkbBuu7Lmo7M4xQ98bWA4gVq95_u"
            }
    )
    @POST("from/send")
    Call<MyResponse>sendNotification(@Body Sender body);
}
