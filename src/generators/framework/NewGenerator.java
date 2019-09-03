/*
 * Created on 29.12.2004 by T. Ackermann
 */

package generators.framework;

import generators.framework.properties.AnimationPropertiesContainer;

import java.util.Hashtable;
import java.util.Locale;

import algoanim.primitives.generators.Language;


/**
 * This Interface describes methods that every Generator has to have to work
 * together with the Generator GUI.
 * 
 * @author T. Ackermann
 */
public interface NewGenerator {
  public static final String JAVA_OUTPUT = "Java";
  public static final String PSEUDO_CODE_OUTPUT = "Pseudo-Code";

  /**
   * generate returns the generated String. This method is called by the
   * Generator GUI which passes the AnimationPropertiesContainer and the
   * Hashtable with Primitives with the values that the user changed.
   * 
   * @param props
   *          The AnimationPropertiesContainer with all the values set.
   * @param primitives
   *          The Hashtable with all the Primitives set.
   */
  public void generate(AnimationPropertiesContainer props,
      Hashtable<String, Object> primitives, String filename, Language targetLanguage);

  /**
   * returns the base name for the algorithm.
   * 
   * @return the base name for the underlying algorithm. This should be the same
   *         notation throughout the system, e.g. "Bubble Sort". Variants are
   *         specified in the getName() method
   */
  public String getAlgorithmName();

  /**
   * returns the name of the animation author
   * 
   * @return the name of the animation author, to be displayed in a list inside Animal
   */
  public String getAnimationAuthor();

  /**
   * getCodeExample returns a Code Example for this Generator.
   * 
   * @return A Code Example for this Generator.
   */
  public String getCodeExample();

  /**
   * getContentLocale returns the target Locale of the generated output Use e.g.
   * Locale.US for English content, Locale.GERMANY for German, etc.
   * 
   * @return a Locale instance that describes the content type of the output
   */
  public Locale getContentLocale();

  /**
   * getDescription returns the Description of this Generator.
   * 
   * @return The Description of this Generator.
   */
  public String getDescription();

  /* *
   * getFileExtension returns the Extension for the file that is generated by
   * this Generator. This should be "asu" (animal-script-uncompressed), "asc"
   * (animal-script-compressed), "ama" (animal-ascii-uncompressed), "aml"
   * (animal-ascii-compressed), "tex", "txt", "pdf", ...
   * 
   * @return The Extension for the file that is generated by this Generator.
   * /
  public String getFileExtension();
*/
  
  /**
   * getGeneratorType returns the Generator Type for the Generator (Search,
   * Sort, Graph, ...)
   * 
   * @return The Generator Type for the Generator.
   */
  public GeneratorType getGeneratorType();

  /**
   * getName returns the Name of this Generator (like "Quicksort with right
   * Pivot").
   * 
   * @return The Name of this Generator.
   */
  public String getName();

  /**
   * returns the output language of texts in the file
   * 
   * @return the name of the (programming) language used for texts in the
   *         animation
   */
  public String getOutputLanguage();

  /**
   * initializes the (optional) attributes the concrete generator has
   * Must ensure that each invocation of generate(x, y) with the same
   * parameter values will return exactly the same result, so that there
   * will be no net effect of attributes set in a previous generation process
   */
  public void init();
}
