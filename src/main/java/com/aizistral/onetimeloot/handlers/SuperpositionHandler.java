package com.aizistral.onetimeloot.handlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

public class SuperpositionHandler {

	/**
	 * Retrieves the given Tag type from the player's persistent NBT.
	 */

	public static Tag getPersistentTag(Player player, String tag, Tag expectedValue) {
		CompoundTag data = player.getPersistentData();
		CompoundTag persistent;

		if (!data.contains(Player.PERSISTED_NBT_TAG)) {
			data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
		} else {
			persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
		}

		if (persistent.contains(tag))
			return persistent.get(tag);
		else
			//persistent.put(tag, expectedValue);
			return expectedValue;
	}

	/**
	 * Remove tag from the player's persistent NBT.
	 */

	public static void removePersistentTag(Player player, String tag) {
		CompoundTag data = player.getPersistentData();
		CompoundTag persistent;

		if (!data.contains(Player.PERSISTED_NBT_TAG)) {
			data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
		} else {
			persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
		}

		if (persistent.contains(tag)) {
			persistent.remove(tag);
		}
	}

	/**
	 * Sets the given Tag type to the player's persistent NBT.
	 */

	public static void setPersistentTag(Player player, String tag, Tag value) {
		CompoundTag data = player.getPersistentData();
		CompoundTag persistent;

		if (!data.contains(Player.PERSISTED_NBT_TAG)) {
			data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
		} else {
			persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
		}

		persistent.put(tag, value);
	}

	/**
	 * Sets the given boolean tag to the player's persistent NBT.
	 */

	public static void setPersistentBoolean(Player player, String tag, boolean value) {
		SuperpositionHandler.setPersistentTag(player, tag, ByteTag.valueOf(value));
	}

	/**
	 * Retrieves the given boolean tag from the player's persistent NBT.
	 */

	public static boolean getPersistentBoolean(Player player, String tag, boolean expectedValue) {
		Tag theTag = SuperpositionHandler.getPersistentTag(player, tag, ByteTag.valueOf(expectedValue));
		return theTag instanceof ByteTag ? ((ByteTag) theTag).getAsByte() != 0 : expectedValue;
	}

}
