package torch.sdslabs.co.in.torch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener{

    //private TextView mTextView;

    private ImageView imageView;

    private static final long CONNECTION_TIME_OUT_MS = 100;
    private static final String MESSAGE = "Hello Wear!";

    private GoogleApiClient client;
    private String nodeId;

    private static String[] MESSAGES = {"on","off"};
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);
        /*final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });*/
        imageView = (ImageView)findViewById(R.id.iv_torch);
        imageView.setOnClickListener(this);

        initApi();
    }
        /**
         * Initializes the GoogleApiClient and gets the Node ID of the connected device.
         */
        private void initApi() {
            client = getGoogleApiClient(this);
            retrieveDeviceNode();
        }

        /**
         * Returns a GoogleApiClient that can access the Wear API.
         * @param context
         * @return A GoogleApiClient that can make calls to the Wear API
         */
        private GoogleApiClient getGoogleApiClient(Context context) {
            return new GoogleApiClient.Builder(context)
                    .addApi(Wearable.API)
                    .build();
        }

        /**
         * Connects to the GoogleApiClient and retrieves the connected device's Node ID. If there are
         * multiple connected devices, the first Node ID is returned.
         */
        private void retrieveDeviceNode() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                    NodeApi.GetConnectedNodesResult result =
                            Wearable.NodeApi.getConnectedNodes(client).await();
                    List<Node> nodes = result.getNodes();
                    if (nodes.size() > 0) {
                        nodeId = nodes.get(0).getId();
                    }
                    client.disconnect();
                }
            }).start();
        }

        /**
         * Sends a message to the connected mobile device, telling it to show a Toast.
         */
        private void sendMessage(final String message) {
            if (nodeId != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                        Wearable.MessageApi.sendMessage(client, nodeId, message, null);
                        client.disconnect();
                    }
                }).start();
            }
        }

    @Override
    public void onClick(View view) {
        if(count==0){
            sendMessage("off");
            count=1;
        }else{
            count=1;
            sendMessage("on");
        }
    }
}