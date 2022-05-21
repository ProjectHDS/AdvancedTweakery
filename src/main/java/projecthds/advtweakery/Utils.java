package projecthds.advtweakery;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.liquid.WeightedLiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.RecipesMachine.*;

import java.util.List;

public class Utils {

    public static ChanceItemStack toChanceStack(WeightedItemStack wStack) {
        return new ChanceItemStack(InputHelper.toStack(wStack.getStack()), wStack.getChance());
    }

    public static ChanceFluidStack toChanceStack(WeightedLiquidStack wStack) {
        return new ChanceFluidStack(InputHelper.toFluid(wStack.getStack()), wStack.getChance());
    }

    public static boolean matchRecipe(IRecipe recipeOne, IRecipe recipeTwo) {

        if (recipeOne == recipeTwo) return true;

        return recipeOne.getOutput() == recipeTwo.getOutput()
                && recipeOne.getFluidOutputs() == recipeTwo.getFluidOutputs();
    }
}
