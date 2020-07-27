package com.alfred0ga.uberclone.providers;

import com.alfred0ga.uberclone.models.FCMBody;
import com.alfred0ga.uberclone.models.FCMResponse;
import com.alfred0ga.uberclone.retrofit.IFCMApi;
import com.alfred0ga.uberclone.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class NotificationProvider {
    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {
    }

    public Call<FCMResponse> sendNotification(FCMBody body) {
        return RetrofitClient.getClientObject(url).create(IFCMApi.class).send(body);
    }
}
