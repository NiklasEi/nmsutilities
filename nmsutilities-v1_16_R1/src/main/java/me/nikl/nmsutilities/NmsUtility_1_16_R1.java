package me.nikl.nmsutilities;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.stream.JsonReader;

import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import net.minecraft.server.v1_16_R1.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_16_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R1.PacketPlayOutTitle;

/**
 * Created by niklas
 */
public class NmsUtility_1_16_R1 implements NmsUtility {
    @Override
    public void updateInventoryTitle(Player player, String newTitle) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(entityPlayer.activeContainer.windowId,
                WindowType_1_16_R1.guessBySlots(entityPlayer.activeContainer.getBukkitView().getTopInventory().getSize()).getType()
                , IChatBaseComponent.ChatSerializer.a("{\"text\": \""
                        + ChatColor.translateAlternateColorCodes('&', newTitle + "\"}")));
        entityPlayer.playerConnection.sendPacket(packet);
        entityPlayer.updateInventory(entityPlayer.activeContainer);
    }

    @Override
    public void sendJSON(Player player, String json) {
        final IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(json);
        final PacketPlayOutChat packet = new PacketPlayOutChat(comp, ChatMessageType.CHAT, player.getUniqueId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendJSON(Player player, Collection<String> json) {
        for (final String message : json) {
            final IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(message);
            final PacketPlayOutChat packet = new PacketPlayOutChat(comp, ChatMessageType.CHAT, player.getUniqueId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void sendJSON(Collection<Player> players, String json) {
        final IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(json);
        for (final Player player : players) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(comp, ChatMessageType.CHAT, player.getUniqueId()));
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
        if (title != null) {
            final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \""
                    + ChatColor.translateAlternateColorCodes('&', title + "\"}"));
            final PacketPlayOutTitle pTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pTitle);
        }
        if (subTitle != null) {
            final IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', subTitle + "\"}"));
            final PacketPlayOutTitle pSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pSubTitle);
        }
        final PacketPlayOutTitle length = new PacketPlayOutTitle(10, durationTicks, 10);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
    }

    @Override
    public void sendActionbar(Player player, String message) {
        final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message + "\"}"));
        final PacketPlayOutChat bar = new PacketPlayOutChat(icbc, ChatMessageType.GAME_INFO, null);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }

    @Override
    public void sendList(Player player, String header, String footer) {
        if (header !=  null) sendListHeader(player, header);
        if (footer !=  null) sendListFooter(player, footer);
    }

    @Override
    public void sendListFooter(Player player, String footer) {
        final IChatBaseComponent bottom = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            final Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, bottom);
            footerField.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void sendListHeader(Player player, String header) {
        final JsonReader reader = new JsonReader(new StringReader("{\"text\": \"" + header + "\"}"));
        final IChatBaseComponent bottom = IChatBaseComponent.ChatSerializer.a((reader.toString()));
        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            final Field footerField = packet.getClass().getDeclaredField("a");
            footerField.setAccessible(true);
            footerField.set(packet, bottom);
            footerField.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
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
