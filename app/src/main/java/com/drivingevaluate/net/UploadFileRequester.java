package com.drivingevaluate.net;

import com.drivingevaluate.config.AppConf;
import com.drivingevaluate.config.ServerConf;
import com.drivingevaluate.model.Image;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Yat3s on 8/21/15.
 * Email:hawkoyates@gmail.com
 */
public class UploadFileRequester {
    private Callback<Image> callback;
    private TypedFile file;
    private UploadFileService uploadFileService;
    public UploadFileRequester(Callback<Image> callback, TypedFile file) {
        this.callback = callback;
        this.file = file;
        createREST();
    }

    public interface UploadFileService {
        @Multipart
        @POST("/api/upload.htm")
        void uploadFileForId(@Part("file") TypedFile file,@Query("tag") int id,Callback<Image> callback);

        @Multipart
        @POST("/api/upload.htm")
        void uploadFileForPath(@Part("file") TypedFile file,Callback<Image> callback);
    }
    public void createREST(){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("token", AppConf.TOKEN);
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerConf.SERVER_IP)
                .setRequestInterceptor(requestInterceptor)
                .build();
        uploadFileService = restAdapter.create(UploadFileService.class);

    }

    public void uploadFileForId(){
        uploadFileService.uploadFileForId(file,1,callback);
    }

    public void uploadFileForPath(){
        uploadFileService.uploadFileForPath(file,callback);
    }
}
