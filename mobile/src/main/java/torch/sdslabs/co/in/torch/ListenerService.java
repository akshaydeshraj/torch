package torch.sdslabs.co.in.torch;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by marauder on 3/14/15.
 */
public class ListenerService extends WearableListenerService {

    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;

    private static final String BASE_URL= "http://192.168.208.177:3000/";
    private static final String ON = BASE_URL+"on";
    private static final String OFF = BASE_URL+"off";

    private static final String TAG="ListenerService";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        showToast(messageEvent.getPath());
        switch (messageEvent.getPath()){
            case "on" :
                sendRequest(ON);
                break;
            case "off":
                sendRequest(OFF);
                break;
        }
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void sendRequest(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG,response);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.getMessage());
            }
        });
        mRequestQueue.add(stringRequest);
    }
}