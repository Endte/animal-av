package animal.graphics;

import java.awt.*;

import animal.graphics.meta.ImmediateTextContainer;
import animal.main.Animal;
import animal.main.AnimalConfiguration;
import animal.misc.XProperties;

import javax.swing.*;

/**
 * Text-Object for Animal.
 *
 * @author <a href="http://www.ahrgr.de/guido/">Guido
 *         R&ouml;&szlig;ling</a>
 * @version 1.0 24.08.1998
 */
public class PTImage extends PTGraphicObject{
    // =====================================================================
    // Public Constants
    // =====================================================================

    public static final String TEXT_TYPE = "Text";
    protected Point position = new Point(0, 0);
    protected int width = 0;
    protected int height = 0;
    
    protected double rotationAngle = 0.0;

    /**
     * The current x-scaling factor (default is 1.0)
     */
    protected double scalingFactorX = 1.0;

    /**
     * The current y-scaling factor (default is 1.0)
     */
    protected double scalingFactorY = 1.0;

    /**
     * The actual text used by this component, represented by a String object
     */
    protected String text = "";

    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Create an empty PTImage instance
     */
    public PTImage() {
        // initialize attributes to their default values
        initializeWithDefaults(getType());
    }


    public PTImage(String textValue, Point targetPosition, int width, int height) {
        setText(textValue);
        setPosition(targetPosition);
        setWidth(width);
        setHeight(height);
    }

    // ======================================================================
    // Attribute accessing
    // ======================================================================

    /**
     * This method initializes the primitive with the primitive
     * type's default values (looked up at the default properties)
     *
     * @param primitiveName the name of the primitive to support
     * inheritance, e.g. "Square".
     */
    public void initializeWithDefaults(String primitiveName) {
        super.initializeWithDefaults(primitiveName);
        AnimalConfiguration config =
                AnimalConfiguration.getDefaultConfiguration();
    }

    /**
     * file version history:
     * <ol>
     * <li>basic version</li>
     * <li>depth</li>
     * </ol>
     *
     * @return the file version of this class
     */
    public int getFileVersion() {
        return 2;
    }


    /**
     * returns this text's position as a Point
     * @return the position used for this text
     */
    public Point getPosition() {
        return position;
    }

    /**
     * returns the current rotation angle (if any). Defaults to 0.0
     * @return the current rotation angle
     */
    public double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * returns the current scaling factor for the text in x direction.
     * Defaults to 1.0
     * @return the current scaling factor in x direction
     */
    public double getScalingFactorX() {
        return scalingFactorX;
    }

    /**
     * returns the current scaling factor for the text in y direction.
     * Defaults to 1.0
     * @return the current scaling factor in y direction
     */
    public double getScalingFactorY() {
        return scalingFactorX;
    }

    /**
     * returns this text's text value
     * @return the text value used for this text
     */
    public String getText() {
        return text;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * returns the type of this object as a String
     *
     * @return this object's type as a String (i.e., "Text")
     */
    public String getType() {
        return PTImage.TEXT_TYPE;
    }

    /**
     * Returns the names of the structures this object can parse.
     *
     * @return an array of Strings containing all handled keywords in the stream
     */
    public String[] handledKeywords() {
        return new String[] { "Text" };
    }

    /**
     * changes this text's position, if the parameter position is not null
     *
     * @param p if not null, this will be the new position of the text
     */
    public void setPosition(Point p) {
        if (p != null)
            setPosition(p.x, p.y);
    }

    /**
     * changes this text's position, if the parameter position is not null
     *
     * @param x the new x position of the text
     * @param y the new y position of the text
     */
    public void setPosition(int x, int y) {
        if (position != null)
            position.setLocation(x, y);
        else
            position = new Point(x, y);
    }

    public void setWidth(int w){
        this.width = w;
    }

    public void setHeight(int h){
        this.height = h;
    }

    /**
     * sets the new rotation angle (if any). Defaults to 0.0
     *
     * @param newAngle the new rotation angle
     */
    public void setRotationAngle(double newAngle) {
        rotationAngle = newAngle;
    }

    /**
     * sets the current scaling factor for the text in x direction.
     *
     * @param scaleX the new scaling factor in x direction
     */
    public void setScalingFactorX(double scaleX) {
        scalingFactorX = scaleX;
    }

    /**
     * returns the current scaling factor for the text in y direction.
     *
     * @param scaleY the new scaling factor in y direction
     */
    public void setScalingFactorY(double scaleY) {
        scalingFactorY = scaleY;
    }

    /**
     * assigns a new text for this object
     *
     * @param newText the new text to be stored. If null, an empty String
     * ("") will be assigned instead to ensure well-defined attributes.
     */
    public void setText(String newText) {
        if (newText == null)
            text = "";
        else
            text = newText;
    }

    /*****************************************************************************
     * the paint method
     ****************************************************************************/

    /**
     * This routine draws the PTImage on the Graphics object
     * passed as a parameter. Drawing is based on the <em>drawString</em>
     * method of the Graphics API.
     *
     * @param g The platform-specific Graphics object used for
     * all graphic operations.
     */
    public void paint(Graphics g) {
            Image img = new ImageIcon("test.jpg").getImage();

            // ---------------------------------------------------------
            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // to get the former version change g2 into g and remove the code above
            // under the line
        g2.drawImage(img, getPosition().x,getPosition().y, getWidth(), getHeight(),null);
           // g2.drawString(getText(), position.x, position.y);

    }

    /*****************************************************************************
     * standard graphics routines
     ****************************************************************************/

    public void rotate(double angle) {
        setRotationAngle(angle);
    }

    public void rotate(double angle, PTPoint center) {
        // Translate center of rotation to origin
        translate(-center.getX(), -center.getY());

        // Rotate(around point of origin)
        rotate(angle);

        // Translate back
        translate(center.getX(), center.getY());
    }

    public void scale(double scaleX, double scaleY) {
        setScalingFactorX(scaleX);
        setScalingFactorY(scaleY);
    }

    public void translate(int x, int y) {
        position.translate(x, y);
    }

    /*****************************************************************************
     * other GraphicObject's methods that have to be implemented
     ****************************************************************************/

    /**
     * Clones the current graphical object. Note that the method will per
     * definition return Object, so the result has to be cast to the appropriate
     * type.
     *
     * @return a clone of the current object, statically typed as Object.
     */
    public Object clone() {
        // create new object
        PTImage targetShape = new PTImage();

        // clone shared attributes
        // from PTGO: color, depth, num, objectName
        cloneCommonFeaturesInto(targetShape);

        // clone anything else that is specific to this type
        // and its potential subtypes
        return targetShape;
    }

    /**
     * Offers cloning support by cloning or duplicating the shared attributes
     *
     * @param targetShape the shape into which the values are to be copied. Note
     * the direction -- it is "currentObject.cloneCommonFeaturesInto(newObject)",
     * not vice versa!
     */
    protected void cloneCommonFeaturesInto(PTImage targetShape) {
        // clone features from PTGraphicsObject: color, depth, num, objectName
        super.cloneCommonFeaturesInto(targetShape);

        // clone anything else that is specific to this type
        // and its potential subtypes
        targetShape.setPosition(getPosition().x, getPosition().y);
        targetShape.setRotationAngle(rotationAngle);
        targetShape.setScalingFactorX(scalingFactorX);
        targetShape.setScalingFactorY(scalingFactorY);
        targetShape.setText(new String(text));
        targetShape.setHeight((getHeight()));
        targetShape.setWidth(getWidth());
    }

    /**
     * returns the Text's bounding box, i.e. the smallest rectangle that contains
     * the box and the pointer.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(position.x, position.y,
                width, height);
    }

    /**
     * Converts the Text to a string representation.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(30 + getText().length());
        sb.append("Text ");
        if (getObjectName() != null)
            sb.append("\"").append(getObjectName()).append("\" ");
        sb.append("{ '").append(text).append("' at (").append(position.x);
        sb.append(", ").append(position.y).append(") }");
        return sb.toString();
    }

    /*****************************************************************************
     * File I/O
     ****************************************************************************/

    public static String escapeText(String input) {
        if (input.indexOf("\"") == -1)
            return new String(input);

        StringBuilder sb = new StringBuilder(input.length() + 30);
        int start = 0;
        int end = input.indexOf("\"", start);
        while (end >= 0) {
            sb.append(input.substring(start, end));
            sb.append("\\\"");
            start = end + 1;
            end = input.indexOf("\"", start);
        }
        if (start < input.length())
            sb.append(input.substring(start));
        return sb.toString();
    }

    /**
     * Update the default properties for this object
     * @param defaultProperties the properties to be updated
     */
    public void updateDefaults(XProperties defaultProperties) {
        super.updateDefaults(defaultProperties);
        defaultProperties.put(getType() +".text", getText());
    }

    public void discard() {
        super.discard();
        position = null;
        text = null;
    }
} // PTImage
