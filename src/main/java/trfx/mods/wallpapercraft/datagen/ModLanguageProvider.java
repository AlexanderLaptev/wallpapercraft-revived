package trfx.mods.wallpapercraft.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String modId, String locale) {
        super(gen, modId, locale);
    }

    @Override
    protected void addTranslations() {
    }
}
