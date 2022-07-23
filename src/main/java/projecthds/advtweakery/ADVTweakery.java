package projecthds.advtweakery;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.LinkedList;
import java.util.List;

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

    public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
    public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        try {
            LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        } catch (Exception exception) {
            exception.printStackTrace();
            CraftTweakerAPI.logError("Error while applying action", exception);
        }
        LATE_REMOVALS.clear();
        LATE_ADDITIONS.clear();
    }
}
