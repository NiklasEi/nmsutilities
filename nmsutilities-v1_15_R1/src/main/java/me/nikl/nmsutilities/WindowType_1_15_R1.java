package me.nikl.nmsutilities;

import net.minecraft.server.v1_15_R1.Containers;

/**
 * @author Niklas Eicker
 */
public enum WindowType_1_15_R1 {
    GENERIC_9_1(Containers.GENERIC_9X1),
    GENERIC_9_2(Containers.GENERIC_9X2),
    GENERIC_9_3(Containers.GENERIC_9X3),
    GENERIC_9_4(Containers.GENERIC_9X4),
    GENERIC_9_5(Containers.GENERIC_9X5),
    GENERIC_9_6(Containers.GENERIC_9X6);

    private Containers<?> type;
    WindowType_1_15_R1(Containers<?> type) {
        this.type = type;
    }

    public Containers<?> getType() {
        return type;
    }

    public static WindowType_1_15_R1 guessBySlots(int slots) {
        if (slots%9 == 0) {
            switch (slots/9) {
                case 1:
                    return GENERIC_9_1;
                case 2:
                    return GENERIC_9_2;
                case 3:
                    return GENERIC_9_3;
                case 4:
                    return GENERIC_9_4;
                case 5:
                    return GENERIC_9_5;
                case 6:
                    return GENERIC_9_6;
            }
        }
        // just default to normal chest inventory...
        return GENERIC_9_3;
    }
}
