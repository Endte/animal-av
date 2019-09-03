package animal.exchange.animalscript;

import java.awt.Color;
import java.awt.Point;

import animal.graphics.PTEllipse;
import animal.graphics.PTGraphicObject;
import animal.misc.ColorChoice;

public class PTEllipseExporter extends PTGraphicObjectExporter {
	public String getExportString(PTGraphicObject ptgo) {
		// write out the information of the super object
		StringBuilder sb = new StringBuilder(200);
		PTEllipse shape = (PTEllipse) ptgo;
		if (getExportStatus(shape))
			return "# previously exported: '" + shape.getNum(false) + "/"
					+ shape.getObjectName();

		sb.append("ellipse \"").append(shape.getObjectName()).append("\" (");

		Point node = shape.getCenter();
		sb.append((int)node.getX()).append(", ").append((int)node.getY());
		sb.append(") radius (").append((int)shape.getXRadius());
		sb.append(", ").append((int)shape.getYRadius()).append(") ");
		
		// write this object's information
		Color color = shape.getColor();
		sb.append(" color ").append(ColorChoice.getColorName(color));

		sb.append(" depth ").append(shape.getDepth());

		if (shape.isFilled()) {
      sb.append(" filled");
      sb.append(" fillColor ").append(
          ColorChoice.getColorName(shape.getFillColor()));
    }

		hasBeenExported.put(shape, shape.getObjectName());
		return sb.toString();
	}
}
