package org.bsc.test;

/**
 * Created by softphone on 17/06/14.
 */
public interface Fe<P,R,E extends Throwable> {

    R f( P param ) throws E;
}
