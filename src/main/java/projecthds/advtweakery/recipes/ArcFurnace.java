package projecthds.advtweakery.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import projecthds.advtweakery.ADVTweakery;
import projecthds.advtweakery.ImplBaseAction.*;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import zmaster587.advancedRocketry.tile.multiblock.machine.TileElectricArcFurnace;

@ZenClass("mods.advancedrocketry.ArcFurnace")
@ZenRegister
public class ArcFurnace {

    public static Class<?> getMachineClass() {
        return TileElectricArcFurnace.class;
    }

    @ZenMethod
    public static void addRecipe(IItemStack[] outputs, int time, int power, IIngredient[] inputs) {
        Add(outputs, time, power, inputs);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack[] outputs) {
        Remove(outputs);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        Remove(new IItemStack[]{output});
    }

    @ZenMethod
    public static void removeAll() {
        All();
    }

    public static void All() {
        ImplBaseActionAll actionAll = new ImplBaseActionAll(ArcFurnace.getMachineClass());
        ADVTweakery.LATE_REMOVALS.add(actionAll);
    }

    public static void Remove(IItemStack[] outputs) {
        ImplBaseActionRemove actionRemove = new ImplBaseActionRemove(ArcFurnace.getMachineClass(), outputs);
        ADVTweakery.LATE_REMOVALS.add(actionRemove);
    }

    public static void Add(IItemStack[] outputs, int time, int power, IIngredient[] inputs) {
        ImplBaseActionAdd actionAdd = new ImplBaseActionAdd(ArcFurnace.getMachineClass(), outputs, time, power, inputs);
        ADVTweakery.LATE_ADDITIONS.add(actionAdd);
    }


}