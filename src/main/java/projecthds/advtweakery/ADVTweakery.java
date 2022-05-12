package projecthds.advtweakery;

import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = ADVTweakery.MOD_ID,
        name = ADVTweakery.MOD_NAME,
        version = ADVTweakery.MOD_VERSION,
        dependencies = ADVTweakery.MOD_DEPENDENCIES
)
public class ADVTweakery {
    public static final String MOD_ID = "advtweakery";
    public static final String MOD_NAME = "Advanced Tweakery";
    public static final String MOD_VERSION = "0.1";
    public static final String MOD_DEPENDENCIES = "required-after:advancedrocketry;required-after:crafttweaker;";

    @Mod.Instance(MOD_ID)
    public static ADVTweakery INSTANCE;
}
