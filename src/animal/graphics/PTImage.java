package animal.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;

import animal.main.AnimalConfiguration;
import animal.misc.XProperties;

import javax.swing.*;

/**
 * Image-Object for Animal.
 *
 * @author <a href="http://www.ahrgr.de/guido/">Guido
 *         R&ouml;&szlig;ling</a>
 * @version 1.0 24.08.1998
 */
public class PTImage extends PTGraphicObject{
    // =====================================================================
    // Public Constants
    // =====================================================================

    public static final String IMAGE_TYPE = "Image";
    protected String pathName = "";
    protected Point position = new Point(0, 0);
    protected int width;
    protected int height;
    protected double rotation = 0;

    AffineTransform currentTransform;
    

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


    public PTImage(String pathName,  Point targetPosition, int width, int height) {
        setPathName(pathName);
        setPosition(targetPosition);
        setWidth(width);
        setHeight(height);
    }

    public PTImage(String pathName,  Point targetPosition, int width, int height, double rotation) {
        setPathName(pathName);
        setPosition(targetPosition);
        setWidth(width);
        setHeight(height);
        setRotation(rotation);
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
        AnimalConfiguration config = AnimalConfiguration.getDefaultConfiguration();
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
     * returns this images's position as a Point
     * @return the position used for this image
     */
    public Point getPosition() {
        return position;
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public String getPathName() { return pathName; }

    /**
     * returns the type of this object as a String
     *
     * @return this object's type as a String (i.e., "Text")
     */
    public String getType() {
        return PTImage.IMAGE_TYPE;
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

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public void setRotation(double degrees) {
        this.rotation = degrees;
    }

    public double getRotation(){
        return this.rotation;
    }

    public void scaleFromNewPoint(Point p) {
        int originalX = this.position.x;
        int originalY = this.position.y;

        if(originalX <= p.x) {
            this.setWidth(p.x - originalX);
        }
        if(originalY <= p.y) {
            this.setHeight(p.y - originalY);
        }
    }

    public void setPathName(String path){
        pathName = path;
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
        Image img = new ImageIcon(getPathName()).getImage();
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        AffineTransform old = g2.getTransform();

        g2.translate(getPosition().x + getWidth() / 2, getPosition().y + getHeight() / 2);
        g2.rotate(Math.toRadians(this.rotation));
        g2.translate(-getWidth() / 2, -getHeight() / 2);
        currentTransform = g2.getTransform();

        g2.drawImage(img, 0,0, getWidth(), getHeight(),null);


        //revert to old transformation. Otherwise every other objects rotates
        g2.setTransform(old);
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
        PTImage targetImage = new PTImage();

        // clone shared attributes
        // from PTGO: color, depth, num, objectName
        cloneCommonFeaturesInto(targetImage);

        // clone anything else that is specific to this type
        // and its potential subtypes
        return targetImage;
    }

    /**
     * Offers cloning support by cloning or duplicating the shared attributes
     *
     * @param targetImage the image into which the values are to be copied. Note
     * the direction -- it is "currentObject.cloneCommonFeaturesInto(newObject)",
     * not vice versa!
     */
    protected void cloneCommonFeaturesInto(PTImage targetImage) {
        // clone features from PTGraphicsObject: color, depth, num, objectName
        super.cloneCommonFeaturesInto(targetImage);

        // clone anything else that is specific to this type
        // and its potential subtypes
        targetImage.setPosition(getPosition().x, getPosition().y);
        targetImage.setHeight((getHeight()));
        targetImage.setWidth(getWidth());
        targetImage.setPathName(getPathName());
        targetImage.setRotation(getRotation());
    }

    /**
     * returns the Image's bounding box, i.e. the smallest rectangle that contains
     * the box and the pointer.
     */
    public Rectangle getBoundingBox() {
//        return (Rectangle) currentTransform.createTransformedShape(new Rectangle(position.x, position.y, width, height));
        return new Rectangle(position.x, position.y, width, height);
    }

    /**
     * Update the default properties for this object
     * @param defaultProperties the properties to be updated
     */
    public void updateDefaults(XProperties defaultProperties) {
        super.updateDefaults(defaultProperties);
    }

    public void discard() {
        super.discard();
        position = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("image");
        sb.append(" ");
        if (getObjectName() != null)
            sb.append("\"").append(getObjectName()).append("\" ");
        sb.append("\"").append(getPathName()).append("\" ");
        sb.append("(").append(getPosition().x).append(",").append(getPosition().y).append(")").append(" ");
        sb.append("(").append(getWidth()).append(",").append(getHeight()).append(")").append("\" ");
        sb.append(" rotation ").append(getRotation());
        return sb.toString();
    }
}
