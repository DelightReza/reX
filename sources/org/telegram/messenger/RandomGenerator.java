package org.telegram.messenger;

/* loaded from: classes4.dex */
public class RandomGenerator {
    public static double random() {
        double dNanoTime = System.nanoTime();
        double dAbs = Math.abs(Math.cos(dNanoTime) * Math.sin(dNanoTime / 2.0d));
        return dAbs - Math.floor(dAbs);
    }
}
