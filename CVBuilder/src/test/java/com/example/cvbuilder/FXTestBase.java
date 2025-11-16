package com.example.cvbuilder;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class FXTestBase {
    private static volatile boolean started = false;

    @BeforeAll
    static void initFx() throws Exception {
        if (!started) {
            CountDownLatch latch = new CountDownLatch(1);
            try {
                Platform.startup(latch::countDown);
            } catch (IllegalStateException alreadyStarted) {
                // Toolkit already started
                latch.countDown();
            }
            latch.await(5, TimeUnit.SECONDS);
            started = true;
        }
    }
}

