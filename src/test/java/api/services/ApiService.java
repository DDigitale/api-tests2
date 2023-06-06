package api.services;

import api.dto.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("/api/users?page=2")
    Call<UsersDataList> getUsersList();

    @GET("/api/users/23")
    Call<UsersDataList> getNotFoundUser();

    @POST("/api/register")
    Call<LoginResponse> registerUser(@Body LoginData data);

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body LoginData data);

    @GET("/api/unknown")
    Call<UnknownDataList> getUnknownList();

    @DELETE("api/users/2")
    Call<UserData> deleteUser();

    @PUT("/api/users/2")
    Call<CreatedUserResponse> updateUser(@Body CreatedUser data);

    @POST("/api/users")
    Call<CreatedUserResponse> createUser(@Body CreatedUser data);
}
