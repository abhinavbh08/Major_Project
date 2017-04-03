package com.example.abhinav.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by abhinav on 25/3/17.
 */
public class WiFiDirectBroadCastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private Activity activity;
    WifiP2pManager.PeerListListener peerListListener;


    public WiFiDirectBroadCastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity mainActivity, WifiP2pManager.PeerListListener peerListListener) {
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.activity = mainActivity;
        this.peerListListener = peerListListener;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Log.d("TAG","P2P state enabled");
            } else {
                Log.d("TAG","P2P state not enabled");
            }
        }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d("TAG","Connection changed");
            // Connection state changed!  We should probably do something about
            // that.

        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                Log.d("TAG","Device Changed");
                Toast.makeText(activity, WifiP2pManager.EXTRA_WIFI_P2P_DEVICE,Toast.LENGTH_SHORT).show();
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Toast.makeText(activity, "P2P Peers changed",Toast.LENGTH_SHORT).show();

            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }
            Log.d("TAG", "P2P peers changed");
        }
    }
}