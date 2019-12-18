package com.mortuza.report.Network;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface APIServices {

//    @POST("User")
//    @Headers({"app-type: consumer-app", "Content-Type: application/json"})
//    Call<ResponseWrapperArray<ModelUser>> login(@Header(KEY_APP_AUTHENTICATION_TOKEN) String app_authorization_key, @Header(KEY_USER_AUTHENTICATION_TOKEN) String authorization_key, @Body HashMap<String, Object> body);
//

    //http://192.168.0.102/report/postuser.php   user_id  password
    //http://192.168.0.102/report/getuserreport.php   user_id
    //http://192.168.0.102/report/createreport.php   user_id  report_title  report_description


}
