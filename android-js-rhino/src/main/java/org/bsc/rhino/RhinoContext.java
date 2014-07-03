package org.bsc.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Created by softphone on 03/07/14.
 */
public final class RhinoContext {

    private boolean exit;
    private Context cx;

    protected RhinoContext() {
        this(null,false);
    }
    protected RhinoContext(Context cx, boolean exit) {
        this.cx = cx;
        this.exit = exit;
    }

    public static RhinoContext enter() {

        return new RhinoContext(Context.enter(), true );

    }
    public static RhinoContext current() {

        return new RhinoContext( Context.getCurrentContext(), false );
    }


    /**
     *
     * @param code
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T initStandardObjects( F2e<Context,Scriptable,T, Exception> code ) throws Exception {

        final Scriptable scope = cx.initStandardObjects();

        cx.putThreadLocal( Scriptable.class.getName(), scope);

        T result = null;
        try {

            result = (T)code.f( cx, scope );

        }
        finally {
            cx.removeThreadLocal(Scriptable.class.getName());

            if( exit ) Context.exit();

        }

        return result;

    }

    /**
     *
     * @param code
     * @param <T>
     * @return
     */
    public <T> T evalInCurrentScope( F2<Context, Scriptable,T> code ) {
        if( cx == null ) throw new IllegalArgumentException( "cx is null!");

        final Scriptable scope = (Scriptable) cx.getThreadLocal(Scriptable.class.getName());

        try {

            T result = (T)code.f( cx, scope );

            return result;
        }
        finally {
            if( exit ) Context.exit();
        }
    }


}
