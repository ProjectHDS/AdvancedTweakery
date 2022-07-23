package projecthds.advtweakery;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.utils.BaseAction;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zmaster587.libVulpes.interfaces.IRecipe;
import zmaster587.libVulpes.recipe.RecipesMachine;
import zmaster587.libVulpes.tile.TileEntityMachine;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class ImplBaseAction {
  public static final RecipesMachine recipeMachine = RecipesMachine.getInstance();

  public static class ImplBaseActionAdd extends BaseAction {
    private final Class<?> recipeClass;
    private final Object[] inputs;
    private final Object[] outputs;
    private final int time;
    private final int power;

    public ImplBaseActionAdd(
        Class<?> recipeClass, Object[] outputs, int time, int power, Object[] inputs) {
            super(recipeClass.getName());
            this.recipeClass = recipeClass;
            this.outputs = outputs;
            this.time = time;
            this.power = power;
            this.inputs = inputs;
        }

        @Override
        public void apply() {
          Object[] ins = Utils.convertFromCT(inputs);
          Object[] outs = Utils.convertFromCT(outputs);
          recipeMachine.addRecipe(recipeClass, outs, time, power, ins);
    }
  }

  public static class ImplBaseActionRemove extends BaseAction {
    private final Class<?> recipeClass;
    private final Object[] outputs;

    public ImplBaseActionRemove(Class<?> recipeClass, Object[] outputs) {
      super(recipeClass.getName());
      this.recipeClass = recipeClass;
      this.outputs = outputs;
    }

    @Override
    public void apply() {
        if (recipeClass != null) {
            List<?> recipeList = Utils.getRecipesForMachine(recipeClass);
            ListIterator<?> recipeIterator = recipeList.listIterator();

            while(recipeIterator.hasNext()) {
                IRecipe currentRecipe = (IRecipe)recipeIterator.next();
                IRecipe workingRecipe = new IRecipe() {
                    @Override
                    public List<ItemStack> getOutput() {
                        return null;
                    }

                    @Override
                    public List<FluidStack> getFluidOutputs() {
                        return null;
                    }

                    @Override
                    public List<List<ItemStack>> getIngredients() {
                        return null;
                    }

                    @Override
                    public List<FluidStack> getFluidIngredients() {
                        return null;
                    }

                    @Override
                    public int getTime() {
                        return 0;
                    }

                    @Override
                    public int getPower() {
                        return 0;
                    }

                    @Override
                    public String getOreDictString(int i) {
                        return null;
                    }
                };
                if (Utils.matchRecipe(currentRecipe, workingRecipe)) {
                    recipeIterator.remove();
                    break;
                }
            }

            }
        /*
      List<?> recipeList = Utils.getRecipesForMachine(recipeClass);
      HashMap<String, List<?>> outMap = Utils.outputConvert(outputs);
      ListIterator<?> recipeIterator = recipeList.listIterator();

      IRecipe nowRecipe =
          new IRecipe() {
            @Override
            public List<ItemStack> getOutput() {
              return (List<ItemStack>) outMap.get("items");
            }

            @Override
            public List<FluidStack> getFluidOutputs() {
              return (List<FluidStack>) outMap.get("fluids");
            }

            @Override
            public List<List<ItemStack>> getIngredients() {
              return null;
            }

            @Override
            public List<FluidStack> getFluidIngredients() {
              return null;
            }

            @Override
            public int getTime() {
              return 0;
            }

            @Override
            public int getPower() {
              return 0;
            }

            @Override
            public String getOreDictString(int i) {
              return (String) outMap.get("items").get(i);
            }
          };

      while (recipeIterator.hasNext()) {
        IRecipe recipe = (IRecipe) recipeIterator.next();
        if (Utils.matchRecipe(nowRecipe, recipe)) {
          recipeIterator.remove();
        }
      }*/
    }
  }

  public static class ImplBaseActionAll extends BaseAction {
    private final Class<?> recipeClass;

    public ImplBaseActionAll(Class<?> recipeClass) {
      super(recipeClass.getName());
      this.recipeClass = recipeClass;
    }

    @Override
    public void apply() {
      RecipesMachine.getInstance().clearRecipes((Class<? extends TileEntityMachine>) recipeClass);
    }
  }
}
