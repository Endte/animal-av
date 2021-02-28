package animal.editor.graphics.meta;

import animal.editor.Editor;
import animal.graphics.PTImage;
import animal.main.Animation;
import animal.misc.ColorChooserAction;
import animal.misc.XProperties;
import translator.ExtendedActionButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

public abstract class AbstractImageEditor extends GraphicEditor implements ActionListener {

    private static final long serialVersionUID = 1L;

    protected JTextField textField;

    protected ColorChooserAction ColorChooser;


    public void createImageRotationOperations(String borderKey) {
        //insert the properly labeled seperator
        insertSeparator(borderKey, cp, generator);

        // add a text input field
        textField = new JTextField(16);
        textField.addActionListener(this);
        cp.add(textField, Editor.LAYOUT_PARAGRAPH_GAP_GROWX_SPAN_WRAP);
    }

    public void createImageComponentChooser(Color initialColor, String colorName) {
        initializeLayoutComponents();

        createImageRotationOperations("AbstractImageEditor.rotate");

        ColorChooser = createColorChooser(colorName,
                "GenericEditor.chooseColor", "AbstractImageEditor.color",
                initialColor);
        ExtendedActionButton colorButton = new ExtendedActionButton(
                ColorChooser, KeyEvent.VK_T);
    }

    public void getProperties(XProperties props) {
        super.getProperties(props);
        String baseKey = getBasicType();

        props.put(baseKey + ".rotation", textField.getText());
    }

    public void setProperties(XProperties props) {
        super.setProperties(props);
        String baseKey = getBasicType();

        if(textField != null) {
            textField.setText(props.getProperty(baseKey + ".rotation", ""));
        }
    }

    public void propertyChange(PropertyChangeEvent event) {
        PTImage image = (PTImage) getCurrentObject();
        String eventName = event.getPropertyName();
        if("rotation".equals(eventName))
            image.setRotation((Double) event.getNewValue());
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        repaintNow();
        if (Animation.get() != null) {
            Animation.get().doChange();
        }
    }
}
