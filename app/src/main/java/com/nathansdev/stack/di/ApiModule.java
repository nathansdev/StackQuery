package com.nathansdev.stack.di;

import com.nathansdev.stack.data.api.StackExchangeApi;
import com.nathansdev.stack.data.model.MyAdapterFactory;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApiModule {
    private static final String BASE_URL = "https://api.stackexchange.com";

    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder().add(MyAdapterFactory.create()).build();
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();
                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public StackExchangeApi providesApi(
            Retrofit retrofit) {
        return retrofit.create(StackExchangeApi.class);
    }
}
