package org.bsc.rhino;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.Scriptable;

/**
 * Created by softphone on 17/06/14.
 */
public class RhinoBroadcastReceiver extends BroadcastReceiver {

    final NativeFunction onReceiveFunction;

    /**
     *
     * @param onReceiveFunction
     */
    public RhinoBroadcastReceiver(NativeFunction onReceiveFunction) {
        this.onReceiveFunction = onReceiveFunction;
    }

    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive( final Context context, final Intent intent) {

        RhinoContext.current().evalInCurrentScope( new F2<org.mozilla.javascript.Context, Scriptable, Void>() {
            @Override
            public Void f(org.mozilla.javascript.Context cx, Scriptable scope) {

                onReceiveFunction.call(cx,
                        scope,
                        null,
                        new Object[]{context, intent});
                return null;
            }
        });
    }
}
