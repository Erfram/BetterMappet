package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.api.scripts.code.ScriptMatrix;
import mchorse.mappet.api.scripts.code.ScriptFactory;
import org.spongepowered.asm.mixin.Mixin;
import llama.bettermappet.api.scripts.user.IScriptMatrix;

@Mixin(value = ScriptFactory.class, remap = false)
public abstract class MixinScriptFactory {
    /**
     * Creates and returns a new {@link IScriptMatrix} object with the specified number of rows and columns.
     *
     * @param rows the number of rows in the new matrix
     * @param cols the number of columns in the new matrix
     * @return a new {@link IScriptMatrix} object with the specified dimensions
     */
    public ScriptMatrix createMatrix(int rows, int cols) {
        return new ScriptMatrix(rows, cols);
    }
}
