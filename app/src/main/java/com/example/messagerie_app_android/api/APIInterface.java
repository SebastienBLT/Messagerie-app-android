package com.example.messagerie_app_android.api;

import com.example.messagerie_app_android.api.structure.ConnectionStatus;
import com.example.messagerie_app_android.api.structure.ListConversations;
import com.example.messagerie_app_android.api.structure.ListMessages;
import com.example.messagerie_app_android.api.structure.Message;
import com.example.messagerie_app_android.api.structure.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("conversations")
    Call<ListConversations> doGetListConversation(@Header("hash") String hash);

    @GET("conversations/{id}/messages")
    Call<ListMessages> doGetListMessages(@Header("hash") String hash, @Path("id") int convId);

    @POST("conversations/{id}/messages")
    Call<Message> doPostMessages(@Header("hash") String hash, @Path("id") int convId, @Query("contenu") String contenu);

    @POST("authenticate")
    Call<ConnectionStatus> doLogin(@Query("user") String username, @Query("password") String password);

    @GET("users/{id}")
    Call<User> doGetUser(@Header("hash") String hash, @Path("id") int id);

}
