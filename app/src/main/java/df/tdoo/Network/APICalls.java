package df.tdoo.Network;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class APICalls {
    private static final MediaType JSON=MediaType.get("application/json; charset=utf-8");

    OkHttpClient client=new OkHttpClient();

    public Call post(String jsonBody, String url, Callback callback){
        RequestBody body=RequestBody.create(JSON, jsonBody);
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call=client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call get(String url, Callback callback){
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call call=client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
