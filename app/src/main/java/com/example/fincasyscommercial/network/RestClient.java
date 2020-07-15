package com.example.fincasyscommercial.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {


    public static <S> S createService(Class<S> serviceClass,String BASE_URL,String key) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("key", key)
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
        );

        OkHttpClient okHttpClient = httpClient.connectTimeout(150, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

}
