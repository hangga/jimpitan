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
    @POST("https://script.google.com/macros/s/AKfycbwZfFSifiACnWfWO2g60I9Y5ItmQD2I-zzBoprs64R8Xa8eCto/exec")
    Call<PostJimpitan> postJimpitan(@Field("id") String id,
                                  @Field("sheet") String sheet,
                                  @Field("hari") String hari,
                                  @Field("tanggal") String tanggal,
                                  @Field("bulan") String bulan,
                                  @Field("tahun") String tahun,
                                  @Field("jam") String jam,
                                  @Field("nama") String nama,
                                  @Field("nominal") int nominal
    );
}
