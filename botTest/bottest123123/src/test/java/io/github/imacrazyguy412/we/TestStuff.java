package io.github.imacrazyguy412.we;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStuff {

    public static final Logger log = LoggerFactory.getLogger(TestStuff.class);

    public static long timeActionMillis(Runnable action){
        long start = System.currentTimeMillis();
        action.run();
        long end = System.currentTimeMillis();

        return end - start;
    }

    public static long timeActionNanos(Runnable action){
        long start = System.nanoTime();
        action.run();
        long end = System.nanoTime();

        return end - start;
    }
}
