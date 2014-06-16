"use strict";

var BroadcastReceiver       = Packages.android.content.BroadcastReceiver;
var LocalBroadcastManager   = Packages.android.support.v4.content.LocalBroadcastManager;
var Intent                  = Packages.android.content.Intent;

var msg = 'hello dynjs';

try {

print(msg);

var receiver = new BroadcastReceiver();

receive.onReceive = function(ctx, ii) { print( "handle message " );  }


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

msg;
