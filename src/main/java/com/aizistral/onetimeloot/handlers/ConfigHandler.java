package com.aizistral.onetimeloot.handlers;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
	private static ForgeConfigSpec config;
	private static ConfigValue<List<? extends String>> lootListOption;
	private static List<? extends String> lastStringList;
	private static List<ResourceLocation> locationList;

	public static ForgeConfigSpec getConfig() {
		if (config == null) {
			ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

			builder.comment("Main options for the mod").push("Generic Config");

			lootListOption = builder
					.comment("List of items that should only generate one time for any given player.")
					.defineList("lootList", List.of("minecraft:bedrock", "minecraft:end_portal_frame"),
							obj -> obj instanceof String);

			builder.pop();

			config = builder.build();
		}

		return config;
	}

	public static List<ResourceLocation> getLootList() {
		if (!lootListOption.get().equals(lastStringList)) {
			lastStringList = lootListOption.get();
			locationList = new ArrayList<>();

			lastStringList.stream().map(str -> {
				String[] splat = str.split(":");

				if (splat.length != 2)
					throw new IllegalArgumentException(str + " is not a valid resource location!");

				return new ResourceLocation(splat[0], splat[1]);
			}).forEach(locationList::add);
		}

		return locationList;
	}

}
