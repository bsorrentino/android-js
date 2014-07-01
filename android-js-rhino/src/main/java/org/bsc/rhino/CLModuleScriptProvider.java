package org.bsc.rhino;

import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.commonjs.module.ModuleScript;
import org.mozilla.javascript.commonjs.module.ModuleScriptProvider;

import java.io.IOException;
import java.net.URI;

/**
 * Created by softphone on 01/07/14.
 */
public class CLModuleScriptProvider implements ModuleScriptProvider {

    private final ClassLoader cl;

    public CLModuleScriptProvider(ClassLoader cl) {
        if( cl == null ) throw new IllegalArgumentException("cl is null!");
        this.cl = cl;
    }

    @Override
    public ModuleScript getModuleScript(Context cx, final String moduleId, URI moduleUri, URI baseUri, Scriptable paths) throws Exception {

        final java.net.URL sourceURL = cl.getResource(moduleId);
        if( sourceURL == null ) throw new IllegalStateException( String.format("no resource found [%s] in classpath !", moduleId));

        final Script moduleScript = new Script() {

            @Override
            public Object exec(Context cx, Scriptable scope) {
                Object result = null;
                try {
                    final java.io.Reader source = new java.io.InputStreamReader(cl.getResourceAsStream(moduleId));

                    result = cx.evaluateReader(scope, source,moduleId, 1, null);

                } catch (IOException e) {
                    Log.e( "org.bsc", String.format("error evaluating module [%s]", moduleId), e);
                }
                return result;
            }
        };

        return new ModuleScript( moduleScript, (moduleUri!=null) ? moduleUri : sourceURL.toURI(), baseUri);
    }
}
