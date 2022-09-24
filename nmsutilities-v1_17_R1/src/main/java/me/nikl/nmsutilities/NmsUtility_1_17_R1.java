package me.nikl.nmsutilities;

import java.util.Collection;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
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

        final ClientboundContainerSetContentPacket contentPacket = new ClientboundContainerSetContentPacket(entityPlayer.containerMenu.containerId, 0,
                entityPlayer.containerMenu.getItems(), entityPlayer.getMainHandItem() );
        entityPlayer.connection.getConnection().send(packet);
        entityPlayer.connection.getConnection().send(contentPacket);
    }

    @Override
    public void sendJSON(Player player, String json) {
        final Component comp = Component.Serializer.fromJson(ChatColor.translateAlternateColorCodes('&',json));
        final ClientboundChatPacket packet = new ClientboundChatPacket(comp, ChatType.CHAT, player.getUniqueId());
        ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
    }

    @Override
    public void sendJSON(Player player, Collection<String> json) {
        for (final String message : json) {
            final Component comp = Component.Serializer.fromJson(ChatColor.translateAlternateColorCodes('&',message));
            final ClientboundChatPacket packet = new ClientboundChatPacket(comp, ChatType.CHAT, player.getUniqueId());
            ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
        }
    }

    @Override
    public void sendJSON(Collection<Player> players, String json) {
        final Component comp = Component.Serializer.fromJson(ChatColor.translateAlternateColorCodes('&',json));
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
        if (title != null) {
            final Component comp = Component.Serializer.fromJson("{\"text\": \""
                   + ChatColor.translateAlternateColorCodes('&', title + "\"}"));
            final ClientboundSetTitleTextPacket packet = new ClientboundSetTitleTextPacket(comp);
            ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
        }
        if (subTitle != null) {
            final Component comp = Component.Serializer.fromJson("{\"text\": \""
                    + ChatColor.translateAlternateColorCodes('&', subTitle + "\"}"));
            final ClientboundSetSubtitleTextPacket packet = new ClientboundSetSubtitleTextPacket(comp);
            ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
        }
        final ClientboundSetTitlesAnimationPacket length = new ClientboundSetTitlesAnimationPacket(10, durationTicks, 10);
        ((CraftPlayer) player).getHandle().connection.getConnection().send(length);
    }

    @Override
    public void sendActionbar(Player player, String message) {
        final Component comp = Component.Serializer.fromJson("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message + "\"}"));
        final ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(comp);
        ((CraftPlayer) player).getHandle().connection.getConnection().send(packet);
    }

    @Override
    public void sendListFooter(Player player, String footer) {
        // Todo: combine methods and use ClientboundTabListPacket
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
