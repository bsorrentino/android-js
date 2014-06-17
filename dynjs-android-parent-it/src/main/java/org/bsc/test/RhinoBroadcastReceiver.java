package org.bsc.test;

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
    public void onReceive(Context context, Intent intent) {

        final org.mozilla.javascript.Context cx =
                org.mozilla.javascript.Context.getCurrentContext();

        if( cx != null ) {

            final Scriptable scope = (Scriptable) cx.getThreadLocal(Scriptable.class.getName());

            onReceiveFunction.call(cx,
                    scope,
                    null,
                    new Object[]{context, intent});
        }
    }
}
