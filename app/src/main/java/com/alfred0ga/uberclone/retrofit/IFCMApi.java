package com.alfred0ga.uberclone.retrofit;

import com.alfred0ga.uberclone.models.FCMBody;
import com.alfred0ga.uberclone.models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAyGRmstI:APA91bFiEtYYHB62WJ-1aFe-y1EgYWE1mbphUhzDLa2gZwA9lZYNoGMCYzYHUKlNjVfQysOWCYS9XrFS94qWmoNxgCChX_94kPfVnivgnf7gezlHA41ETK8Fc7MSIoScXp4TFJsUhP0I"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);
}
