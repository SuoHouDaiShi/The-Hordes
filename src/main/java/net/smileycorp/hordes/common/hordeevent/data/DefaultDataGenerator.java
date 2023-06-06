package net.smileycorp.hordes.common.hordeevent.data;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.smileycorp.hordes.common.Hordes;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

public class DefaultDataGenerator {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Path CONFIG_FOLDER = FMLPaths.CONFIGDIR.get().resolve("hordes");

    public static boolean tryGenerateDataFiles() {
        if (CONFIG_FOLDER.toFile().exists()) return false;
        generateHordeTableFile();
        generateFunctionFile();
        generateMcmetaFile();
        return true;
    }

    private static void generateMcmetaFile() {
        try {
            File file = CONFIG_FOLDER.resolve("pack.mcmeta").toFile();
            file.createNewFile();
            JsonObject json = new JsonObject();
            JsonObject pack = new JsonObject();
            pack.addProperty("description", "The Hordes config datapack");
            pack.addProperty("pack_format", 8);
            json.add("pack", pack);
            FileWriter writer = new FileWriter(file);
            writer.write(GSON.toJson(json));
            writer.close();
        } catch (Exception e) {
            Hordes.logError("Failed to generate pack.mcmeta", e);
        }
    }

    private static void generateFunctionFile() {
        try {
            File file = CONFIG_FOLDER.resolve("data/hordes/horde_scripts/default.json").toFile();
            file.getParentFile().mkdirs();
            file.createNewFile();
            JsonObject json = new JsonObject();
            json.addProperty("function", "set_spawntable");
            json.addProperty("value", "hordes:default");
            json.add("conditions", new JsonArray());
            FileWriter writer = new FileWriter(file);
            writer.write(GSON.toJson(json));
            writer.close();
        } catch (Exception e) {
            Hordes.logError("Failed to generate pack.mcmeta", e);
        }
    }

    private static void generateHordeTableFile() {
        try {
            List<String> hordeEntries;
            List<String> oldHordeEntries = Lists.newArrayList("minecraft:zombie-35-0-20", "minecraft:zombie_villager-1-0-20",
                    "minecraft:husk-40-30-0", "minecraft:drowned{HandItems:[{id:trident,Count:1}]}-1-40-0", "minecraft:zombie_horse-3-30-0");
            List<String> defaultHordeEntries = Lists.newArrayList("minecraft:zombie-35-0-20", "minecraft:zombie_villager-1-0-20",
                    "minecraft:zombie_horse-3-20-0", "minecraft:husk-40-30-50", "minecraft:drowned{HandItems:[{id:trident,Count:1}]}-1-40-0",
                    "minecraft:zoglin-2-40-0", "minecraft:zombified_piglin-25-50-0");
            File file = CONFIG_FOLDER.resolve("data/hordes/tables/default.json").toFile();
            file.getParentFile().mkdirs();
            file.createNewFile();
            try {
                ModConfig config = ConfigTracker.INSTANCE.fileMap().get("hordes-common.toml");
                CommentedFileConfig configData = config.getHandler().reader(FMLPaths.CONFIGDIR.get()).apply(config);
                hordeEntries = configData.get(Lists.newArrayList("Horde Event", "spawnList"));
            } catch (Exception e) {
                hordeEntries = defaultHordeEntries;
                Hordes.logError("Error reading hordes-common.toml, generating fallback spawnlist", e);
            }
            if (hordeEntries == null || hordeEntries.isEmpty() || hordeEntries.equals(oldHordeEntries)) {
                hordeEntries = defaultHordeEntries;
            }
            JsonArray json = new JsonArray();
            for (String entry : hordeEntries) json.add(entry);
            FileWriter writer = new FileWriter(file);
            writer.write(GSON.toJson(json));
            writer.close();
        } catch (Exception e) {
            Hordes.logError("Failed to generate horde table file", e);
        }
    }

}
