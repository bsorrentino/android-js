package org.bsc.test;

import android.test.ActivityInstrumentationTestCase2;
import org.bsc.*;
import org.dynjs.Config;
import org.dynjs.runtime.DynJS;
import org.dynjs.runtime.GlobalObject;
import org.dynjs.runtime.GlobalObjectFactory;
import org.dynjs.runtime.Runner;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
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
/*
        final String header = new StringBuilder()
                .append( "var msg = 'hello dynjs';")
                .append( "try { print(msg); } catch(e) {};")
                .append( "try { print(msg); } catch(e) {};")
                .append( "msg;")
                .toString();

        Object result = dynjs.execute(header);
*/

        Object result = runner.withSource(
                new java.io.InputStreamReader(getClass().getClassLoader().getResourceAsStream("test.js")) )
                .execute();

        System.out.println( "END TEST OF DYNJS " + result);
    }
}

