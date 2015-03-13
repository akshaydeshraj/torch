package torch.sdslabs.co.in.torch;

import android.app.Application;
import android.content.Context;

/**
 * Created by marauder on 3/14/15.
 */
public class TorchApplication extends Application {

    private static TorchApplication sInstance;

    public static TorchApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
}
