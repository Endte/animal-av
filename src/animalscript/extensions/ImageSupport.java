package animalscript.extensions;

import animal.graphics.*;
import animal.graphics.meta.PolygonalShape;
import animal.main.Animal;
import animal.misc.ParseSupport;
import animal.vhdl.graphics.PTT;
import animalscript.core.AnimalParseSupport;
import animalscript.core.AnimalScriptInterface;
import animalscript.core.BasicParser;

import java.awt.*;
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
        int initStep = AnimalParseSupport.getCurrentStep();
        String value = "", role = null;
        Double rotation = 0.0;

        String localType = ParseSupport.parseWord(stok, "image type").toLowerCase();
        String ObjName = ParseSupport.parseText(stok, "Image Obj name");
        String ImagePath = ParseSupport.parseText(stok, "Image pathName");
        Point position = ParseSupport.parseNode(stok, "Image Position");
        Point size = ParseSupport.parseNode(stok, "Image (Width,Height)");
        if (ParseSupport.parseOptionalWord(stok, "keyword 'rotation'", "rotation")) {
            rotation = ParseSupport.parseDouble(stok, "Image rotation");
        }

        PTImage image = new PTImage(ImagePath, position, size.x, size.y, rotation);
        image.setObjectName(ObjName);

        BasicParser.addGraphicObject(image, anim);

        StringBuilder oids = new StringBuilder();
        oids.append(image.getNum(false));

        // insert into object list -- necessary for lookups later on!
        getObjectIDs().put(image.getObjectName(), image.getNum(false));
        getObjectTypes().put(image.getObjectName(), getTypeIdentifier("image"));
        // display the component, unless marked as 'hidden'
        AnimalParseSupport.showComponents(stok, "" + oids.toString(), localType,
                true);



    }

    private void finishImageParsing(PolygonalShape shape, String localType)
            throws IOException {
        // parse and set the color
        shape.setColor(
                AnimalParseSupport.parseAndSetColor(stok, localType, "color"));

        // check for depth information and set it, if available
        AnimalParseSupport.parseAndSetDepth(stok, shape, localType);

        shape.setFilled(
                ParseSupport.parseOptionalWord(stok, localType + " filled", "filled"));

        if (shape.isFilled())
            shape.setFillColor(
                    AnimalParseSupport.parseAndSetColor(stok, localType, "fillColor"));

        // add the object to the list of graphic objects
        BasicParser.addGraphicObject(shape, anim);

        // append the object id to the list
        StringBuilder oids = new StringBuilder();
        oids.append(shape.getNum(false));

        // insert into object list -- necessary for lookups later on!
        getObjectIDs().put(shape.getObjectName(), shape.getNum(false));
        getObjectTypes().put(shape.getObjectName(), getTypeIdentifier("triangle"));
        // display the component, unless marked as 'hidden'
        AnimalParseSupport.showComponents(stok, "" + oids.toString(), localType,
                true);
    }

}
