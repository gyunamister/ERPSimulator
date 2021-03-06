package org.processmining.erpsimulator.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class TestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class.getName());

    public static void main(String... args) {
        IntStream.rangeClosed(1, 10).forEach(counter -> {
            logger.info("Counter:" + counter);
        });
    }
}
