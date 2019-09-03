/*
 * Created on 05.01.2005 by T. Ackermann
 */

package generators.framework;

/**
 * This Class is used to save the Informations (Type, Name, Description, Code
 * Example) from a Generator.
 * 
 * @author T. Ackermann
 */
public class GeneratorInfos {

	/** stores the ClassName of the Generator */
	private String theClassName;

	/** stores the Type of the Generator */
	private GeneratorType theType;

	/** stores the Name of the Generator */
	private String theName;

	/** stores the Description of the Generator */
	private String theDescription;

	/** stores the Code Example of the Generator */
	private String theCodeExample;

	/** stores the File Extension of the files generated by the Generator */
	private String theFileExtension;


	/**
	 * Constructor creates a new GeneratorInfos-Object.
	 * 
	 * @param newClassName The ClassName of the Generator.
	 * @param newType The Type of the Generator.
	 * @param newName The Name of the Generator.
	 * @param newDescription The Description of the Generator.
	 * @param newCodeExample The Code Example of the Generator.
	 * @param newFileExtension The File Extension of the files generated by the
	 * Generator.
	 */
	public GeneratorInfos(String newClassName, GeneratorType newType,
			String newName, String newDescription, String newCodeExample,
			String newFileExtension) {
	  theClassName = (newClassName != null) ? newClassName : "";
	  theName = (newName != null) ? newName : "";
	  theDescription = (newDescription != null) ? newDescription : "";
	  theCodeExample = (newCodeExample != null) ? newCodeExample : "";
		theFileExtension = (newFileExtension != null) ? newFileExtension : "asu";
		// check for null values
//		if (newClassName == null) newClassName = "";
//		if (newName == null) newName = "";
//		if (newDescription == null) newDescription = "";
//		if (newCodeExample == null) newCodeExample = "";
//		if (newFileExtension == null) newFileExtension = "asu";
		
		// check the values...
		while (theFileExtension.length() > 0
				&& theFileExtension.charAt(0) == '.')
		  theFileExtension = theFileExtension.substring(1);
		if (theFileExtension.length() == 0)
		  theFileExtension = "asu";

//		this.theClassName = newClassName;
//		this.theType = newType;
//		this.theName = newName;
//		this.theDescription = newDescription;
//		this.theCodeExample = newCodeExample;
//		this.theFileExtension = newFileExtension;
	}


	/**
	 * getClassName returns the ClassName of the Generator.
	 * 
	 * @return The ClassName of the Generator.
	 */
	public String getClassName() {
		return this.theClassName;
	}


	/**
	 * getType returns the Type of the Generator.
	 * 
	 * @return The Type of the Generator.
	 */
	public GeneratorType getType() {
		return this.theType;
	}


	/**
	 * getName returns the Name of the Generator.
	 * 
	 * @return The Name of the Generator.
	 */
	public String getName() {
		return this.theName;
	}


	/**
	 * getDescription returns the Description of the Generator.
	 * 
	 * @return The Description of the Generator.
	 */
	public String getDescription() {
		return this.theDescription;
	}


	/**
	 * getCode returns the Code Example of the Generator.
	 * 
	 * @return The Code Example of the Generator.
	 */
	public String getCodeExample() {
		return this.theCodeExample;
	}


	/**
	 * getCode returns the File Extension of the files generated by the
	 * Generator.
	 * 
	 * @return The File Extension of the files generated by the Generator.
	 */
	public String getFileExtension() {
		return this.theFileExtension;
	}
}
