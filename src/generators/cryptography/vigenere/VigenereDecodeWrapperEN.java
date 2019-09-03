/*
 * Created on 28.09.2005 by Guido Roessling (roessling@acm.org>
 */
package generators.cryptography.vigenere;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;

import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Locale;

import de.ahrgr.animal.kohnert.generators.VigenereEncode2;

public class VigenereDecodeWrapperEN implements Generator {

  private static final String DESCRIPTION =
    "Vigenere-Encryption encrypts a given text using a key phrase. "
    +"First, the key phrase is copied below the text until each input "
    +"character of the text is associated with a character of the key phrase. "
    +"For a reasonable encryption, the key phrase should have a "
    +"sufficient length.\nThe algorithm then creates a n*m table, where "
    +"n is the size of the underlying alphabet, and m is the length "
    +"of the key phrase.\nThe key phrase is inserted into the first row "
    +"of this table. All columns are then filled with the continuous "
    +"characters from the alphabet, starting with the character in the "
    +"given column's first row."
    +"\nA given input character is then encoded by the table in row "
    +"(code of the key phrase associated with the current input character) "
    +"and column (code of the current input character).";

  
  private GeneratorType myType = new GeneratorType(
      GeneratorType.GENERATOR_TYPE_CRYPT);

  public GeneratorType getGeneratorType() {
    return myType;
  }

  public String getName() {
    return "Vigenere Decryption";
  }

  public String getDescription() {
    return DESCRIPTION;
  }

  public String getCodeExample() {
    return "TBD";
  }

  /**
   * getFileExtension returns the Extension for the file that is generated by
   * this Generator. This should be "asu" (animal-script-uncompressed), "asc"
   * (animal-script-compressed), "ama" (animal-ascii-uncompressed), "aml"
   * (animal-ascii-compressed), "tex", "txt", "pdf", ...
   * 
   * @return The Extension for the file that is generated by this Generator.
   */
  public String getFileExtension() {
    return Generator.ANIMALSCRIPT_FORMAT_EXTENSION;
  }

  private Hashtable<String, Object> provideMappedProperties(
  		AnimationPropertiesContainer props,
      Hashtable<String, Object> primitives) {
    Hashtable<String, Object> mapper = new Hashtable<String, Object>(59);
    mapper.put("stringToDecode", primitives.get("stringToDecode"));
    mapper.put("key", primitives.get("key"));
    mapper.put("encodeLabel", primitives.get("encodeLabel"));
    mapper.put("keyLabel", primitives.get("keyLabel"));
    mapper.put("repeatedCopyLabel", primitives.get("repeatedCopyLabel"));
    mapper.put("charSet", primitives.get("charSet"));
    mapper.put("title", primitives.get("title"));
    mapper.put("tableCreateLabel", primitives.get("tableCreateLabel"));
    mapper.put("codePosLabel", primitives.get("codePosLabel"));
    mapper.put("numberSteps", primitives.get("numberSteps (0=alle)"));
    mapper.put("codeColor", props.get("code", "color"));
    mapper.put("codeFont", props.get("code", "font"));
    mapper.put("titleColor", props.get("title", "color"));
    mapper.put("titleFont", props.get("title", "font"));
    mapper.put("tableRowColor", props.get("tableRow", "color"));
    mapper.put("tableColumnColor", props.get("tableColumn", "color"));
    mapper.put("tableFillColor", props.get("tableFill", "color"));
    mapper.put("resultColor", props.get("result", "color"));
    mapper.put("backgroundColor", props.get("background", "color"));
    mapper.put("backgroundColFirstRow", 
        props.get("backgroundColFirstRow", "color"));
    return mapper;
  }

  public String generate(AnimationPropertiesContainer props,
      Hashtable<String, Object> primitives) {
    Hashtable<String, Object> mapper = provideMappedProperties(props, primitives);
    VigenereEncode2 encoder = new VigenereEncode2(mapper);
    StringWriter myWriter = new StringWriter();
    encoder.generate(myWriter);
    return myWriter.toString();
  }
  
  
	/**
	 * getContentLocale returns the target Locale of the generated output
	 * Use e.g. Locale.US for English content, Locale.GERMANY for German, etc.
	 * 
	 * @return a Locale instance that describes the content type of the output
	 */
	public Locale getContentLocale() {
		return Locale.US;
	}

    public String getAnimationAuthor() {
      return "Eike Kohnert";
    }

    public String getOutputLanguage() {
      return Generator.JAVA_OUTPUT;
    }
    
    public String getAlgorithmName() {
      return "Vigenere Decoding";
    }
    public void init() {
      // nothing to be done here
    }

}