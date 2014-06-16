package org.bsc.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.dynjs.runtime.ExecutionContext;
import org.dynjs.runtime.JSFunction;
import org.dynjs.runtime.ThreadContextManager;


/**
 * Created by bsorrentino on 16/06/14.
 */
public class DynjsBroadcastReceiver extends BroadcastReceiver {

    JSFunction _onReceive;

    public DynjsBroadcastReceiver( JSFunction onReceive ) {
        this._onReceive = onReceive;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if( _onReceive != null ) {
            ThreadContextManager.currentContext().call(_onReceive, this, context, intent);
        }
    }
}
