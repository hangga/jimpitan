package com.jimpitan.hangga.jimpitan.api.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sayekti on 7/6/18.
 */

public interface ApiInterface {
    @GET("AKfycbzrUOP5IYWEsNfMP6YkkdowGGetZatt36--txlsHtDnbtudnXB1/exec?id=1mgHj9W2WEpubMamySgVrDukhJ65ZHQN1fJS9uy7NJrw&sheet=warga")
    Call<Getwarga> getWarga();

    @FormUrlEncoded
    @POST("https://script.google.com/macros/s/AKfycbzpcp7YIGhJfGzb_nx9tbYBx_3E2OIsoEOEhrZNkNEq3bALf_c/exec?id=1mgHj9W2WEpubMamySgVrDukhJ65ZHQN1fJS9uy7NJrw&sheet=jimpit")
    Call<PostJimpitan> postKontak(@Field("nama") String nama, @Field("nomor") String nomor);
}
