package me.nikl.nmsutilities;

import org.bukkit.Bukkit;

/**
 * @author Niklas Eicker
 */
public class NmsFactory {
    private final static String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static NmsUtility nmsUtility;

    public static NmsUtility getNmsUtility() {
        if (nmsUtility != null) return nmsUtility;
        switch (VERSION) {
            case "v1_8_R1":
                return nmsUtility = new NmsUtility_1_8_R1();
            case "v1_8_R2":
                return nmsUtility = new NmsUtility_1_8_R2();
            case "v1_8_R3":
                return nmsUtility = new NmsUtility_1_8_R3();
            case "v1_9_R1":
                return nmsUtility = new NmsUtility_1_9_R1();
            case "v1_9_R2":
                return nmsUtility = new NmsUtility_1_9_R2();
            case "v1_10_R1":
                return nmsUtility = new NmsUtility_1_10_R1();
            case "v1_11_R1":
                return nmsUtility = new NmsUtility_1_11_R1();
            case "v1_12_R1":
                return nmsUtility = new NmsUtility_1_12_R1();
            case "v1_13_R1":
                return nmsUtility = new NmsUtility_1_13_R1();
            case "v1_13_R2":
                return nmsUtility = new NmsUtility_1_13_R2();
            case "v1_14_R1":
                return nmsUtility = new NmsUtility_1_14_R1();
            case "v1_15_R1":
                return nmsUtility = new NmsUtility_1_15_R1();
            case "v1_16_R1":
                return nmsUtility = new NmsUtility_1_16_R1();
            case "v1_16_R2":
                return nmsUtility = new NmsUtility_1_16_R2();
            default:
                return null;
        }
    }
}
