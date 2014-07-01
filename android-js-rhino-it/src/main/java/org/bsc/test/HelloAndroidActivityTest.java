package org.bsc.test;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.bsc.*;
import org.bsc.rhino.CLModuleScriptProvider;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.commonjs.module.RequireBuilder;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.net.URI;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testFunction() throws Exception {
        Context cx = Context.enter();

        try {
            cx.setOptimizationLevel(-1);
            cx.setLanguageVersion(Context.VERSION_1_7);
            cx.setApplicationClassLoader(getClass().getClassLoader());

            final Scriptable scope = cx.initStandardObjects();

            System.out.println("START TEST OF RHINO");

            Object result = cx.evaluateString(scope,
                    new StringBuffer()
                            .append("function( a, b ) { return a + b ; }")
                            .toString(),
                    "testFunction", 1, null
            );

            Assert.assertNotNull(result);
            Assert.assertTrue( result instanceof NativeFunction );

            result = ((NativeFunction)result).call( cx, scope, null /*this*/, new Object[] { 10, 5 });
            Assert.assertNotNull(result);
            Assert.assertTrue(result instanceof Number);
            Assert.assertEquals(((java.lang.Number) result).intValue(), 15);


            System.out.printf("END TEST OF RHINO [%s]\n", result.getClass());

        }
        finally {
            Context.exit();
        }

    }

    private <T> T initStandardObjects( Context cx, Fe<Scriptable,T, Exception> code ) throws Exception {

        final Scriptable scope = cx.initStandardObjects();

        cx.putThreadLocal( Scriptable.class.getName(), scope);

        T result = null;
        try {

            result = (T)code.f( scope );

        }
        finally {
            cx.removeThreadLocal(Scriptable.class.getName());

        }

        return result;

    }

    public void testActivity() throws Exception {
        final HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);


        final Context cx = Context.enter();

        final ClassLoader cl = getClass().getClassLoader();
        try {
            cx.setOptimizationLevel(-1);
            cx.setLanguageVersion(Context.VERSION_1_7);
            cx.setApplicationClassLoader(getClass().getClassLoader());


            initStandardObjects(cx, new Fe<Scriptable, Object, Exception>() {
                @Override
                public Object f(Scriptable scope) throws Exception {
                    System.out.println("START TEST OF RHINO");

                    final RequireBuilder rb = new RequireBuilder();
                    rb.setModuleScriptProvider( new CLModuleScriptProvider(cl) );
                    rb.setSandboxed(false);
                    final Require req = rb.createRequire( cx, scope);
                    req.install( scope );

                    Object scriptWrap = Context.javaToJS(activity, scope);
                    ScriptableObject.putProperty(scope, "activity", scriptWrap);

                    Object result = cx.evaluateReader(scope,
                            new java.io.InputStreamReader(cl.getResourceAsStream("testWithModule.js")),
                            "test", 1, null);

                    System.out.println("END TEST OF RHINO " + result);

                    return result;
                }
            });



        }
        finally {
            Context.exit();
        }
    }
}
