package torch.sdslabs.co.in.torch;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by marauder on 3/14/15.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton () {
        mRequestQueue = Volley.newRequestQueue(TorchApplication.getAppContext());
    }

    public static VolleySingleton getInstance(){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}