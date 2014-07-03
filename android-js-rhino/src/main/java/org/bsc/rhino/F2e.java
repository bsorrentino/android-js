package org.bsc.rhino;

import org.mozilla.javascript.Scriptable;

/**
 * Created by softphone on 03/07/14.
 */
public interface F2e<P1,P2,R,E extends Throwable> {

        R f( P1 param1, P2 param2) throws E;
}