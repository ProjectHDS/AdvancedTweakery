package projecthds.advtweakery.recipes;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IWeightedIngredient;
import projecthds.advtweakery.ADVTweakery;
import projecthds.advtweakery.ImplBaseAction.ImplBaseActionAdd;
import projecthds.advtweakery.ImplBaseAction.ImplBaseActionAll;
import projecthds.advtweakery.ImplBaseAction.ImplBaseActionRemove;
import projecthds.advtweakery.Utils;
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
    public static void addRecipe(IIngredient[] outputs, int time, int power, IIngredient[] inputs) {
        Add(InputHelper.toObjects(outputs), time, power, InputHelper.toObjects(inputs));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient[] outputs) {
    Remove(InputHelper.toObjects(outputs));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient output) {
    Remove(new Object[] {InputHelper.toObject(output)});
    }

    @ZenMethod
    public static void removeAll() {
        All();
    }

    public static void All() {
        ADVTweakery.LATE_REMOVALS.add(new ImplBaseActionAll(ArcFurnace.getMachineClass()));
    }

  public static void Remove(Object[] outputs) {
        ADVTweakery.LATE_REMOVALS.add(new ImplBaseActionRemove(ArcFurnace.getMachineClass(), Utils.convertFromCT(outputs)));
    }

  public static void Add(Object[] outputs, int time, int power, Object[] inputs) {
        ImplBaseActionAdd actionAdd = new ImplBaseActionAdd(ArcFurnace.getMachineClass(), Utils.convertFromCT(outputs), time, power, Utils.convertFromCT(inputs));
        ADVTweakery.LATE_ADDITIONS.add(actionAdd);
    }
}