package animalscript.extensions;

import algoanim.primitives.generators.VariablesGenerator;
import animal.animator.VariableDeclaration;
import animal.animator.VariableDiscard;
import animal.animator.VariableUpdate;
import animal.main.Animal;
import animal.misc.ParseSupport;
import animal.misc.XProperties;
import animal.variables.Variable;
import animalscript.core.AnimalParseSupport;
import animalscript.core.AnimalScriptInterface;
import animalscript.core.BasicParser;
import java.io.IOException;
import java.util.Hashtable;

import algoanim.primitives.generators.VariablesGenerator;
import animal.animator.VariableDeclaration;
import animal.animator.VariableDiscard;
import animal.animator.VariableUpdate;
import animal.misc.ParseSupport;
import animal.variables.Variable;
import animalscript.core.AnimalParseSupport;
import animalscript.core.AnimalScriptInterface;
import animalscript.core.BasicParser;

import java.io.IOException;
import java.util.Hashtable;

public class ImageSupport extends BasicParser implements AnimalScriptInterface {
    public ImageSupport() {
        handledKeywords = new Hashtable<String, Object>();
        handledKeywords.put("image", "parseImageInput");
    }

    public boolean generateNewStep(String currentCommand) {
        return !sameStep;
    }

    public void parseImageInput() throws IOException {
        String name;
        String path;

        int initStep = AnimalParseSupport.getCurrentStep();
        String value = "", role = null;
        name = AnimalParseSupport.parseText(stok, "image key");
        path = AnimalParseSupport.parseText(stok, "image path");

System.out.println("Ende");
       // BasicParser.addAnimatorToAnimation(varDec, anim);
    }

}
