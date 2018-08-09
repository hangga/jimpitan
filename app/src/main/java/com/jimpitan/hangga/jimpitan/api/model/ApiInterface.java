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
    @GET("AKfycbzrUOP5IYWEsNfMP6YkkdowGGetZatt36--txlsHtDnbtudnXB1/exec?sheet=warga")
    Call<Getwarga> getWarga();


    /*@GET("AKfycbzrUOP5IYWEsNfMP6YkkdowGGetZatt36--txlsHtDnbtudnXB1/exec?sheet=config")
    Call<Getconfig> getConfig();

    @FormUrlEncoded
    @POST("AKfycbwZfFSifiACnWfWO2g60I9Y5ItmQD2I-zzBoprs64R8Xa8eCto/exec")
    Call<PostJimpitan> postJimpitan(@Field("idjimp") String idjimp,
                                  @Field("hari") String hari,
                                  @Field("tanggal") String tanggal,
                                  @Field("bulan") String bulan,
                                  @Field("tahun") String tahun,
                                  @Field("jam") String jam,
                                  @Field("nama") String nama,
                                  @Field("nominal") int nominal,
                                  @Field("submitby") String submitby
    );*/

    @FormUrlEncoded
    @POST("AKfycbyPZ7AneiiqCLAo0wxxY5u8zj_FYxJOkkqX5gqiJ5gNXXoanuw/exec")
    Call<PostJimpitan> postJimpitanNew(@Field("idjimp") String idjimp,
                                    @Field("hari") String hari,
                                    @Field("tanggal") String tanggal,
                                    @Field("bulan") String bulan,
                                    @Field("tahun") String tahun,
                                    @Field("jam") String jam,
                                    @Field("jambulat") int jambulat,
                                    @Field("nama") String nama,
                                    @Field("nominal") int nominal,
                                    @Field("submitby") String submitby
    );
}
