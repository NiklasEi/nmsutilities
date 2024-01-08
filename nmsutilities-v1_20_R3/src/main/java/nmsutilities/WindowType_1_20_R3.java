package nmsutilities;

import net.minecraft.world.inventory.MenuType;

/**
 * @author Niklas Eicker
 */
public enum WindowType_1_20_R3 {
    GENERIC_9_1(MenuType.GENERIC_9x1),
    GENERIC_9_2(MenuType.GENERIC_9x2),
    GENERIC_9_3(MenuType.GENERIC_9x3),
    GENERIC_9_4(MenuType.GENERIC_9x4),
    GENERIC_9_5(MenuType.GENERIC_9x5),
    GENERIC_9_6(MenuType.GENERIC_9x6);

    private MenuType<?> type;
    WindowType_1_20_R3(MenuType<?> type) {
        this.type = type;
    }

    public MenuType<?> getType() {
        return type;
    }

    public static WindowType_1_20_R3 guessBySlots(int slots) {
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
