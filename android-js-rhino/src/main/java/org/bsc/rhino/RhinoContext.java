package org.bsc.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Created by softphone on 03/07/14.
 */
public final class RhinoContext {

    public class Result<T> {

        private final T result;

        Result(T result) {
            this.result = result;
        }

        public RhinoContext context() {
            return  RhinoContext.this;
        }

        public Result<T> exit() {
            context().exit();
            return this;
        }

        public T result() {
            return result;
        }

    }

    private Context cx;

    protected RhinoContext(Context cx) {
        this.cx = cx;
    }

    public static RhinoContext enter() {

        return new RhinoContext(Context.enter());

    }
    public static RhinoContext current() {

        return new RhinoContext( Context.getCurrentContext());
    }

    public Context getUnderlyingContext() {
        return cx;
    }

    public void exit() {
        if( cx!= null ) {
            cx.removeThreadLocal(Scriptable.class.getName());
            Context.exit();
            cx = null;
        }
    }

    /**
     *
     * @param code
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> Result<T> initStandardObjects( F2<Context,Scriptable,T> code ) {

        final Scriptable scope = cx.initStandardObjects();

        cx.putThreadLocal( Scriptable.class.getName(), scope);

        T result = (T)code.f( cx, scope );

        return new Result(result);

    }

    /**
     *
     * @param scope
     * @param code
     * @return
     */
    public <T> Result<T> evalInScope( Scriptable scope, F2<Context, Scriptable,T> code ) {
        if( cx == null ) throw new IllegalArgumentException( "cx is null!");
        if( scope == null ) throw new IllegalArgumentException( "scope is null!");

        T result = (T)code.f( cx, scope );

        return new Result(result);
    }

    /**
     *
     * @param code
     * @param <T>
     * @return
     */
    public <T> Result<T> evalInCurrentScope( F2<Context, Scriptable,T> code ) {

        final Scriptable scope = (Scriptable) cx.getThreadLocal(Scriptable.class.getName());

        return evalInScope(scope, code);
    }


}
