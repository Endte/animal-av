package animal.exchange.animalscript;

import animal.graphics.PTGraphicObject;
import animal.graphics.PTImage;
import animal.misc.ColorChoice;

import java.awt.*;

public class PTImageExporter extends PTGraphicObjectExporter{
    public String getExportString(PTGraphicObject ptgo) {
        // write out the information of the super object
        StringBuilder sb = new StringBuilder(200);
        PTImage image = (PTImage) ptgo;
        if (getExportStatus(image))
            return "# previously exported: '" + image.getNum(false) + "/"
                    + image.getObjectName();

        sb.append("image \"");
        sb.append(image.getObjectName()).append("\" ");

        sb.append("\"").append(image.getPathName()).append("\" (");

        Point node = image.getPosition();
        sb.append((int)node.getX()).append(",").append((int)node.getY()).append(") ");
        sb.append("(").append(image.getWidth()).append(",").append(image.getHeight()).append(")");
        if(image.getRotation()!=0.0){
            sb.append(" ").append("rotation ").append(image.getRotation());
        }



        hasBeenExported.put(image, image.getObjectName());
        return sb.toString();
    }
}
