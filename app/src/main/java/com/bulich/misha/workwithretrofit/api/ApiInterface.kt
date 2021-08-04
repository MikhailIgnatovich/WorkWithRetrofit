package com.bulich.misha.workwithretrofit.api

import com.bulich.misha.workwithretrofit.api.models.FilmApiModel
import com.bulich.misha.workwithretrofit.api.models.GenreApiModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("insertGenre.php")
    fun insertGenre(
        @Field("name") name: String?
    ): Call<ResponseBody?>

    @FormUrlEncoded
    @POST("insertFilm.php")
    fun insertFilm(
        @Field("name") name: String?,
        @Field("genre") genre:String?,
        @Field("rating") rating: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("updateGenre.php")
    fun updateGenre(
        @Field("id") id: Int,
        @Field("name") name: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("updateFilm.php")
    fun updateFilm(
        @Field("id") id: Int,
        @Field("name") name: String?,
        @Field("genre") genre:String?,
        @Field("rating") rating: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("deleteGenre.php")
    fun deleteGenre(
        @Field("id") id: Int
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("deleteFilm.php")
    fun deleteFilm(
        @Field("id") id: Int
    ): Call<ResponseBody?>?

    @DELETE("clearGenre.php")
    fun clearGenre(): Call<ResponseBody?>?

    @DELETE("clearFilm.php")
    fun clearFilm(): Call<ResponseBody?>?

    @GET("getGenre.php")
    fun getGenre(): Call<ArrayList<GenreApiModel>>

    @GET("getFilm")
    fun getFilm(): Call<ArrayList<FilmApiModel>>
}