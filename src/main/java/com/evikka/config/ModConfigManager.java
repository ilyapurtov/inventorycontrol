package com.evikka.config;

import com.azure.json.implementation.jackson.core.type.TypeReference;
import com.evikka.ModConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigManager {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Path.of(ModConstants.MOD_ID, "config.json");

    public static <T> T load(T defaultConfig, Class<T> configClass) throws IOException, JsonSyntaxException {
        Files.createDirectories(CONFIG_PATH.getParent());
        if (Files.exists(CONFIG_PATH)) {
            return GSON.fromJson(
                    Files.readString(CONFIG_PATH),
                    configClass
            );
        }
        save(defaultConfig);
        return defaultConfig;
    }

    private static <T> void save(T obj) throws IOException {
        Files.createDirectories(CONFIG_PATH.getParent());
        Files.writeString(CONFIG_PATH, GSON.toJson(obj));
    }
}
