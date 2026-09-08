package com.oibsip.reservation.util;

import java.security.SecureRandom;

public final class PnrGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private PnrGenerator() {}

    public static String next() {
        long value = 1_000_000_000L + (Math.floorMod(RANDOM.nextLong(), 9_000_000_000L));
        return Long.toString(value);
    }
}
