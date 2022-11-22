package com.aizistral.onetimeloot.handlers;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class OneTimeLootModifier extends LootModifier {

	public static final Codec<OneTimeLootModifier> CODEC = RecordCodecBuilder.create(instance ->
			codecStart(instance).apply(instance, OneTimeLootModifier::new));

	protected OneTimeLootModifier(LootItemCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);

		if (entity instanceof ServerPlayer player) {
			generatedLoot.removeIf(stack -> {
				final ResourceLocation stackResourceLocation = ForgeRegistries.ITEMS.getKey(stack.getItem());
				if (ConfigHandler.getLootList().contains(stackResourceLocation)) {
					String tag = "OneTimeLooted:" + stackResourceLocation;

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
		loot.removeIf(stack -> items.contains(ForgeRegistries.ITEMS.getKey(stack.getItem())));
	}

	//Methods unused?
	private void remove(List<ItemStack> loot, ResourceLocation item) {
		loot.removeIf(stack -> ForgeRegistries.ITEMS.getKey(stack.getItem()).equals(item));
	}

	//Same as above
	private boolean isVanillaChest(LootContext context) {
		return String.valueOf(context.getQueriedLootTableId()).startsWith("minecraft:chests/");
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}


}