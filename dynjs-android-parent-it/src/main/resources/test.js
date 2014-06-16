//"use strict";

var LocalBroadcastManager   = Packages.android.support.v4.content.LocalBroadcastManager;
var Intent                  = Packages.android.content.Intent;
var BroadcastReceiver       = org.bsc.test.DynjsBroadcastReceiver;

var msg = 'hello dynjs';
var handler = function(ctx, ii) { print( "handle message " );  }
try {

print(BroadcastReceiver);

var list = new java.util.ArrayList();

//var receiver =  new org.bsc.test.DynjsBroadcastReceiver(null);

/*
LocalBroadcastManager.getInstance(activity)
    .registerReceiver(receiver, new IntentFilter("custom-event-name"));


LocalBroadcastManager.getInstance(activity)
        .sendBroadcast( new Intent("custom-event-name") );

LocalBroadcastManager.getInstance(activity)
    .unregisterReceiver(receiver);
*/

} catch(e) {

    print( 'ERROR ' + e );
};


handler;