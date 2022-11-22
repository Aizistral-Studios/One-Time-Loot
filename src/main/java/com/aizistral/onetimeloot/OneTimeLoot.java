package com.aizistral.onetimeloot;

import com.aizistral.onetimeloot.handlers.ConfigHandler;
import com.aizistral.onetimeloot.handlers.OneTimeLootModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(OneTimeLoot.MODID)
public class OneTimeLoot {
	public static final String MODID = "onetimeloot";

	public static final DeferredRegister<Codec< ? extends IGlobalLootModifier>> LOOT_MODIFIER_REFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ONE_TIME_LOOT_MOD = LOOT_MODIFIER_REFERRED_REGISTER.register("one_time_loot_modifier", () -> OneTimeLootModifier.CODEC);

	public OneTimeLoot() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.getConfig(), "OneTimeLoot.toml");

		LOOT_MODIFIER_REFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	private void onLoadComplete(FMLLoadCompleteEvent event) {
		ConfigHandler.getLootList();
	}



}
