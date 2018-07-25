package com.tao.isthara.Rest;

import com.tao.isthara.Model.Categories;
import com.tao.isthara.Model.CloseTicketRequest;
import com.tao.isthara.Model.CloseTicketResponse;
import com.tao.isthara.Model.DeclinedTicketRequest;
import com.tao.isthara.Model.DeclinedTicketResponse;
import com.tao.isthara.Model.IssueDetailsResponse;
import com.tao.isthara.Model.IssueListResponse;
import com.tao.isthara.Model.LoginResponse;
import com.tao.isthara.Model.NewTokenRequest;
import com.tao.isthara.Model.NewTokenResponse;
import com.tao.isthara.Model.ProfileResponse;
import com.tao.isthara.Model.ResolveTicketRequest;
import com.tao.isthara.Model.ResolveTicketResponse;
import com.tao.isthara.Model.SubCategories;
import com.tao.isthara.Model.SubmitUserRatingResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiInterface {
    @GET
    Call<LoginResponse> getLoginResponse(@Url String apiKey);

    @GET
    Call<Categories> getCategories(@Url String apiKey);

    @GET
    Call<SubCategories> getSubCategories(@Url String apiKey);

    @GET
    Call<IssueListResponse> getIssueListResponse(@Url String apiKey);

    @GET
    Call<IssueDetailsResponse> getIssueDetailsResponse(@Url String apiKey);

    @POST
    Call<NewTokenResponse> getNewTokenRegister(@Url String apiKey, @Body NewTokenRequest newTokenRequest);

    @POST
    Call<CloseTicketResponse> getCloseTicketResponse(@Url String apiKey, @Body CloseTicketRequest closeTicketRequest);

    @POST
    Call<ResolveTicketResponse> getResolveTicketResponse(@Url String apiKey, @Body ResolveTicketRequest resolveTicketRequest);


    @POST
    Call<DeclinedTicketResponse> getDeclinedTicketRequest(@Url String apiKey, @Body DeclinedTicketRequest declinedTicketRequest);


    @GET
    Call<ProfileResponse> getProfile(@Url String apiKey);

    @GET
    Call<SubmitUserRatingResponse> SubmitRatingResponse(@Url String apiKey);


    /*@GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}
