package org.bsc.test;

import android.test.ActivityInstrumentationTestCase2;
import org.bsc.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivityDynjs() {
    /*
        final HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);

        final Config config = new Config();

        config.setInvokeDynamicEnabled(false);

        //config.setGlobalObjectFactory(factory);
        //config.setOutputStream(System.out);
        //config.setErrorStream(System.err);
        final GlobalObjectFactory factory = new GlobalObjectFactory() {

            @Override
            public GlobalObject newGlobalObject(DynJS runtime) {
                return new GlobalObject(runtime) {
                    {

                        defineReadOnlyGlobalProperty("activity", activity);
                    }
                };
            }
        };

        config.setGlobalObjectFactory(factory);

        final DynJS dynjs = new DynJS(config);

        final Runner runner = dynjs.newRunner();

        System.out.println( "START TEST OF DYNJS");

        Object result = runner.withSource(
                new java.io.InputStreamReader(getClass().getClassLoader().getResourceAsStream("test.js")) )
                .execute();

        System.out.println( "END TEST OF DYNJS " + result);
*/
    }

    public void testActivity() throws Exception {
        final HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);


        Context cx = Context.enter();

        try {
            cx.setOptimizationLevel(-1);
            cx.setLanguageVersion(Context.VERSION_1_7);
            cx.setApplicationClassLoader(getClass().getClassLoader());

            final Scriptable scope = cx.initStandardObjects();

            System.out.println("START TEST OF RHINO");

            Object result = cx.evaluateReader(scope,
                    new java.io.InputStreamReader(getClass().getClassLoader().getResourceAsStream("test.js")),
                    "test", 1, null);
            System.out.println( "END TEST OF RHINO " + result);

        }
        finally {
            Context.exit();
        }
    }
}
