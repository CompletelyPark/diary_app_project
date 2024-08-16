package com.jinwan.appproject.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HolidayApiService {
    @GET("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService") // API의 실제 경로로 변경해야 합니다.
    Call<HolidayResponse> getHolidays(@Query("year") int year, @Query("serviceKey") String serviceKey);
}
