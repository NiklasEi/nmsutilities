package me.nikl.nmsutilities;

import java.util.Collection;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by niklas
 */
public class NmsUtility_1_17_R1 implements NmsUtility {
    @Override
    public void updateInventoryTitle(Player player, String newTitle) {
        final ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final ClientboundOpenScreenPacket packet = new ClientboundOpenScreenPacket(entityPlayer.containerMenu.containerId,
                WindowType_1_17_R1.guessBySlots(entityPlayer.containerMenu.getBukkitView().getTopInventory().getSize()).getType()
                , Component.Serializer.fromJson("{\"text\": \""
                        + ChatColor.translateAlternateColorCodes('&', newTitle + "\"}")));
        entityPlayer.connection.getConnection().send(packet);
        // left out
    }

    @Override
    public void sendJSON(Player player, String json) {
        final Component comp = Component.Serializer.fromJson(json);
        final ClientboundChatPacket packet = new ClientboundChatPacket(comp, ChatType.CHAT, player.getUniqueId());
        ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
    }

    @Override
    public void sendJSON(Player player, Collection<String> json) {
        for (final String message : json) {
            final Component comp = Component.Serializer.fromJson(message);
            final ClientboundChatPacket packet = new ClientboundChatPacket(comp, ChatType.CHAT, player.getUniqueId());
            ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
        }
    }

    @Override
    public void sendJSON(Collection<Player> players, String json) {
        final Component comp = Component.Serializer.fromJson(json);
        for (final Player player : players) {
            ((CraftPlayer) player).getHandle().connection.getConnection().send(new ClientboundChatPacket(comp, ChatType.CHAT, player.getUniqueId()));
        }
    }

    @Override
    public void sendJSON(Collection<Player> players, Collection<String> json) {
        for (final String message : json) {
            sendJSON(players, message);
        }
    }

    @Override
    public void sendTitle(Player player, String title, String subTitle, int durationTicks) {
//        if (title != null) {
//            final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \""
//                    + ChatColor.translateAlternateColorCodes('&', title + "\"}"));
//            final PacketPlayOutTitle pTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
//            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pTitle);
//        }
//        if (subTitle != null) {
//            final IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', subTitle + "\"}"));
//            final PacketPlayOutTitle pSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
//            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pSubTitle);
//        }
//        final PacketPlayOutTitle length = new PacketPlayOutTitle(10, durationTicks, 10);
//        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }

    @Override
    public void sendActionbar(Player player, String message) {
//        final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message + "\"}"));
//        final PacketPlayOutChat bar = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO, null);
//        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }

    @Override
    public void sendListFooter(Player player, String footer) {
//        final IChatBaseComponent bottom = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
//        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
//        try {
//            final Field footerField = packet.getClass().getDeclaredField("b");
//            footerField.setAccessible(true);
//            footerField.set(packet, bottom);
//            footerField.setAccessible(false);
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendListHeader(Player player, String header) {
//        final JsonReader reader = new JsonReader(new StringReader("{\"text\": \"" + header + "\"}"));
//        final IChatBaseComponent bottom = IChatBaseComponent.ChatSerializer.a((reader.toString()));
//        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
//        try {
//            final Field footerField = packet.getClass().getDeclaredField("a");
//            footerField.setAccessible(true);
//            footerField.set(packet, bottom);
//            footerField.setAccessible(false);
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public org.bukkit.inventory.ItemStack removeGlow(org.bukkit.inventory.ItemStack item) {
        if (item == null)
        {
            return null;
        }
        final ItemMeta meta = item.getItemMeta();
        for (final Enchantment enchantment : Enchantment.values()) {
            meta.removeEnchant(enchantment);
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public org.bukkit.inventory.ItemStack addGlow(org.bukkit.inventory.ItemStack item) {
        if (item == null)
        {
            return null;
        }
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
