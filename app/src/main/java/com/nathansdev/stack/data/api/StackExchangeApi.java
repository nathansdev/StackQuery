package com.nathansdev.stack.data.api;


import com.nathansdev.stack.data.model.CommonResponseWrapper;
import com.nathansdev.stack.data.model.QuestionsResponse;
import com.nathansdev.stack.data.model.UsersResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackExchangeApi {
    String API_V1_QUESTIONS_JSON = "/2.2/questions?";
    String API_V1_USERS_QUESTIONS_JSON = "/2.2/users/{ids}/questions?";
    String API_V1_USER_ME_JSON = "/2.2/me?";
    String API_V1_ACCESS_TOKEN_INVALIDATE_JSON = "/2.2/access-tokens/{accessTokens}/invalidate";
    String SORT = "sort";
    String SITE = "site";
    String ORDER = "order";
    String PAGE = "page";
    String PAGE_SIZE = "pagesize";
    String IDS = "ids";
    String ACCESS_TOKENS = "accessTokens";

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

    @GET(API_V1_USERS_QUESTIONS_JSON)
    Call<QuestionsResponse> getUsersQuestions(@Path(IDS) String ids, @Query(SORT) String sort, @Query(SITE) String site,
                                              @Query(ORDER) String order, @Query(PAGE) String page,
                                              @Query(PAGE_SIZE) String size);

    @GET(API_V1_USERS_QUESTIONS_JSON)
    Observable<QuestionsResponse> getUsersQuestionsRx(@Path(IDS) String ids, @Query(SORT) String sort, @Query(SITE) String site,
                                                      @Query(ORDER) String order, @Query(PAGE) String page,
                                                      @Query(PAGE_SIZE) String size);

    @GET(API_V1_USERS_QUESTIONS_JSON)
    Flowable<QuestionsResponse> getUsersQuestionsFlowable(@Path(IDS) Long ids, @Query(SORT) String sort, @Query(SITE) String site,
                                                          @Query(ORDER) String order, @Query(PAGE) long page,
                                                          @Query(PAGE_SIZE) long size);

    @GET(API_V1_USER_ME_JSON)
    Call<UsersResponse> getUser(@Query(SORT) String sort, @Query(SITE) String site,
                                @Query(ORDER) String order);

    @GET(API_V1_USER_ME_JSON)
    Observable<UsersResponse> getUserRx(@Query(SORT) String sort, @Query(SITE) String site,
                                        @Query(ORDER) String order);

    @GET(API_V1_USER_ME_JSON)
    Flowable<UsersResponse> getUserFlowable(@Query(SORT) String sort, @Query(SITE) String site,
                                            @Query(ORDER) String order);

    @GET(API_V1_ACCESS_TOKEN_INVALIDATE_JSON)
    Observable<CommonResponseWrapper> invalidateRx(@Path(ACCESS_TOKENS) String sort);
}
