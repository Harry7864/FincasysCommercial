package com.example.fincasyscommercial.network;



import com.example.fincasyscommercial.selectsociety.LocationResponce;
import com.example.fincasyscommercial.selectsociety.SocietyResponce;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Single;

public interface RestCall {

    @FormUrlEncoded
    @POST("admin_login_controller.php")
    Single<AdminLoginResponce> getLogin(@Field("send_otp") String admin_login,
                                        @Field("admin_mobile") String admin_mobile,
                                        @Field("admin_password") String admin_password,
                                        @Field("token") String token);

    @FormUrlEncoded
    @POST("admin_login_controller.php")
    Single<AdminLoginResponce> sendOtp(@Field("verify_otp") String verify_otp,
                                       @Field("admin_mobile") String admin_mobile,
                                       @Field("otp") String otp,
                                       @Field("token") String token,
                                       @Field("device") String device);



    @FormUrlEncoded
    @POST("location_controller_new.php")
    Single<LocationResponce> getCountry(@Field("getCountries") String getCountries);

    @FormUrlEncoded
    @POST("location_controller_new.php")
    Single<LocationResponce> getState(@Field("getState") String getState, @Field("country_id") String country_id);

    @FormUrlEncoded
    @POST("location_controller_new.php")
    Single<LocationResponce> getCity(@Field("getCity") String getCity, @Field("state_id") String state_id);


    @Multipart
    @POST("society_list_controller.php")
    Single<SocietyResponce> getSocietyList(
            @Part("getSociety") RequestBody tag,
            @Part("country_id") RequestBody country,
            @Part("state_id") RequestBody state,
            @Part("city_id") RequestBody city);

    @FormUrlEncoded
    @POST("event_passes_controller.php")
    Single<CommonResponce> scanerData(@Field("passAllow") String passAllow,
                                      @Field("pass_id") String pass_id,
                                      @Field("society_id") String society_id);

    @FormUrlEncoded
    @POST("admin_login_controller.php")
    Single<CommonResponce> logout(@Field("user_logout") String user_logout,
                                  @Field("admin_id") String admin_id,
                                  @Field("society_id") String society_id);

}
