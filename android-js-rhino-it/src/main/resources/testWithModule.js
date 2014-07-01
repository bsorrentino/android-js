
var BroadcastReceiver       = Packages.org.bsc.rhino.RhinoBroadcastReceiver;
var LocalBroadcastManager   = android.support.v4.content.LocalBroadcastManager;
var Intent                  = Packages.android.content.Intent;
var IntentFilter            = Packages.android.content.IntentFilter;

var bm       = require("event.js");

function print( msg ) {
    java.lang.System.out.println(msg);
}

function error( msg ) {
    java.lang.System.err.println( "ERROR: " +msg);
}

try {

print( "ACTIVITY: " + activity );

var receiver = new BroadcastReceiver(
    function(ctx, ii) { print( "handle message " + ctx + " " + ii  ) }
);

print( "LOCAL_BROADCAST_MANAGER: " + bm );

print( "LocalBroadcastManager: " + bm );

bm.registerReceiver(receiver, new IntentFilter("custom-event-name"));

bm.sendBroadcastSync( new Intent("custom-event-name") );

bm.unregisterReceiver(receiver);


} catch(e) {

    error( e );
};


