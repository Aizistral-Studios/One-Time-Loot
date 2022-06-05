package com.aizistral.onetimeloot.handlers;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class OneTimeLootModifier extends LootModifier {

	protected OneTimeLootModifier(LootItemCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		ServerLevel level = context.getLevel();
		Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
		Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);

		if (entity instanceof ServerPlayer player) {
			generatedLoot.removeIf(stack -> {
				if (ConfigHandler.getLootList().contains(stack.getItem().getRegistryName())) {
					String tag = "OneTimeLooted:" + stack.getItem().getRegistryName();

					if (!SuperpositionHandler.getPersistentBoolean(player, tag, false)) {
						SuperpositionHandler.setPersistentBoolean(player, tag, true);
						return false;
					} else
						return true;
				} else
					return false;
			});
		} else {
			this.remove(generatedLoot, ConfigHandler.getLootList());
		}

		return generatedLoot;
	}

	private void remove(List<ItemStack> loot, List<ResourceLocation> items) {
		loot.removeIf(stack -> items.contains(stack.getItem().getRegistryName()));
	}

	private void remove(List<ItemStack> loot, ResourceLocation item) {
		loot.removeIf(stack -> stack.getItem().getRegistryName().equals(item));
	}

	private boolean isVanillaChest(LootContext context) {
		return String.valueOf(context.getQueriedLootTableId()).startsWith("minecraft:chests/");
	}

	public static class Serializer extends GlobalLootModifierSerializer<OneTimeLootModifier> {
		@Override
		public OneTimeLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			return new OneTimeLootModifier(conditions);
		}

		@Override
		public JsonObject write(OneTimeLootModifier instance) {
			return this.makeConditions(instance.conditions);
		}
	}

}