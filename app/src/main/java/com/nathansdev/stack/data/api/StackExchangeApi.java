package com.nathansdev.stack.data.api;


import com.nathansdev.stack.data.model.QuestionsResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackExchangeApi {
    String API_V1_QUESTIONS_JSON = "/2.2/questions?";
    String SORT = "sort";
    String SITE = "site";
    String ORDER = "order";
    String PAGE = "page";
    String PAGE_SIZE = "pagesize";

    @GET(API_V1_QUESTIONS_JSON)
    Observable<QuestionsResponse> getQuestionsRx(@Query(SORT) String sort, @Query(SITE) String site,
                                                 @Query(ORDER) String order, @Query(PAGE) String page,
                                                 @Query(PAGE_SIZE) String size);

    @GET(API_V1_QUESTIONS_JSON)
    Flowable<QuestionsResponse> getQuestionsFlowable(@Query(SORT) String sort, @Query(SITE) String site,
                                                     @Query(ORDER) String order, @Query(PAGE) long page,
                                                     @Query(PAGE_SIZE) long size);

    @GET(API_V1_QUESTIONS_JSON)
    Call<QuestionsResponse> getQuestions(@Query(SORT) String sort, @Query(SITE) String site,
                                         @Query(ORDER) String order, @Query(PAGE) String page,
                                         @Query(PAGE_SIZE) String size);
}
