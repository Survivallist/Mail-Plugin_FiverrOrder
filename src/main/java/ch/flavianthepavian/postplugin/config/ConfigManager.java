package ch.flavianthepavian.postplugin.config;

public class ConfigManager
{
    public static void reload()
    {
        NPCConfig.reload();
        SkinConfig.reload();
        Config.reload();
        GrusskartenConfig.reload();
        PaketeConfig.reload();
    }

    public static void createConfigs()
    {
        SkinConfig.createCustomConfig();
        NPCConfig.createCustomConfig();
        Config.createConfig();
        GrusskartenConfig.createCustomConfig();
        PaketeConfig.createCustomConfig();
    }
}
