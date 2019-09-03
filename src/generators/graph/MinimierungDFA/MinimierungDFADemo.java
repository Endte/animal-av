/**
 * MinimierungDFADemo.java
 * Nora Wester, 2014 for the Animal project at TU Darmstadt.
 * Copying this file for educational purposes is permitted without further authorization.
 */

package generators.graph.MinimierungDFA;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.ValidatingGenerator;

import java.util.Locale;

import javax.swing.JOptionPane;



import java.util.Hashtable;
import java.util.Vector;

import generators.framework.properties.AnimationPropertiesContainer;
import generators.graph.util.Automat;
import algoanim.properties.ArrayProperties;
import algoanim.properties.GraphProperties;
import algoanim.properties.MatrixProperties;
import algoanim.properties.RectProperties;
import algoanim.properties.SourceCodeProperties;
import algoanim.properties.TextProperties;


public class MinimierungDFADemo implements ValidatingGenerator {
   
    private String[] States;
    private String[] Alphabet;
    private String[][] stringMatrixAdja;
    private MatrixProperties AdjaProps;
    private String StartState;
    private String[] FinalStates;
    
    private MinimierungDFA algo;
	private MatrixProperties AequiClassProps;
	private TextProperties TextProps;
	private SourceCodeProperties SourceCodeProps;
	private ArrayProperties StatesArrayProps;
	private int[][] DFAvalid;
	private TextProperties HeaderTextProps;
	private RectProperties HeaderRectProps;
	private GraphProperties GraphProps;

  //---------
	private static final String DESCRIPTION =
            "Bei der Minimierung eines deterministischen endlichen Automaten verwendet man "
            + "den Satz von Myhill-Nerode \n und verfeinert die Aequivalenzklassen des DFA schrittweise,"
            + "bis keine weitere Verfeinerung mehr moeglich ist."
            + "\n Die uebrig gebliebenen nicht unterscheidbaren Zustandspaare, werden dann vereint."
            + "\n Zustandspaare sind unterscheidbar wenn gilt: \n"
            + "{Zielzustand, kein Zielzustand} \n"
            + "ausserdem, wenn sie mit demselben Ereignis in ein unterscheidbares Paar gelangen";
	
  	private static final String SOURCE_CODE = "public void Minimierung(){"
  	+"\n	boolean[][] aequivClassArray = new boolean[numberOfStates][numberOfStates];"
  	+"\n	for(int i=0; i &lt; numberOfStates-1; i++)"
  	+"\n		for(int j=1; j &lt; numberOfStates; j++)"
  	+"\n			if(isFinalState(states[i])^isFinalState(states[j])){"
  	+"\n				aequivClassArray[i][j] = true;"
  	+"\n				aequivClassArray[j][i] = true;"
  	+"\n			}"
  	+"\n"
  	+"\n	while(run){"
  	+"\n		run = false;"
  	+"\n		for(int i=0; i &lt; numberOfStates-1; i++)"
  	+"\n			for(int j=1; j &lt; numberOfStates; j++)"
  	+"\n				if(!aequivClassArray[i][j]){"
  	+"\n					int alphaN = 0;"
  	+"\n					while(alphaN &gt; -1 && alphaN &lt; alphabet.length){"
  	+"\n						int statePositionOne = getTargetStateToLetter(i, alphabet[alphaN]);"
  	+"\n						int statePositionTwo = getTargetStateToLetter(j, alphabet[alphaN]);"
  	+"\n						if(aequivClassArray[statePositionOne][statePositionTwo]){"
  	+"\n							aequivClassArray[i][j] = true;"
  	+"\n							aequivClassArray[j][i] = true;"
  	+"\n							run = true;"
  	+"\n							alphaN = -1;"
  	+"\n						}"
  	+"\n						else{"
  	+"\n							alphaN++;"
  	+"\n						}"
  	+"\n					}"
  	+"\n				}"
  	+"\n	}"
  	+"\n	combineNonMarkedStates();"
  	+"\n}";
    
    public void init(){
        
    }

    public String generate(AnimationPropertiesContainer props,Hashtable<String, Object> primitives) {
        States = (String[])primitives.get("States");
        Alphabet = (String[])primitives.get("Alphabet");
        stringMatrixAdja = (String[][])primitives.get("stringMatrixAdja");
        AdjaProps = (MatrixProperties)props.getPropertiesByName("AdjaProps");
        StartState = (String)primitives.get("StartState");
        FinalStates = (String[])primitives.get("FinalStates");
        AequiClassProps = (MatrixProperties)props.getPropertiesByName("AequiClassProps");
        TextProps = (TextProperties)props.getPropertiesByName("TextProps");
        SourceCodeProps = (SourceCodeProperties)props.getPropertiesByName("SourceCodeProps");
        StatesArrayProps = (ArrayProperties)props.getPropertiesByName("StatesArrayProps");
        HeaderTextProps = (TextProperties)props.getPropertiesByName("HeaderTextProps");
        HeaderRectProps = (RectProperties)props.getPropertiesByName("HeaderRectProps");
        GraphProps = (GraphProperties)props.getPropertiesByName("GraphProps");
        
        algo = new MinimierungDFA(new Automat(
        		States, FinalStates, Alphabet, stringMatrixAdja, StartState));
        
        algo.getDemo().setMProperties(AdjaProps);
        algo.getDemo().setACProperties(AequiClassProps);
        algo.getDemo().setTProperties(TextProps);
        algo.getDemo().setSCProperties(SourceCodeProps);
        algo.getDemo().setAProperties(StatesArrayProps);
        algo.getDemo().setGProperties(GraphProps);
        algo.getDemo().setHeader(HeaderRectProps, HeaderTextProps);
        algo.start();
        algo.getNewDFA();
        return algo.getDemo().getLang().toString();
    }

    public String getName() {
        return "DFA Minimierung";
    }

    public String getAlgorithmName() {
        return "MinimierungDFA";
    }

    public String getAnimationAuthor() {
        return "Nora Wester";
    }

    public String getDescription(){
        return DESCRIPTION;
    }

    public String getCodeExample(){
        return SOURCE_CODE;
    }

    public String getFileExtension(){
        return "asu";
    }

    public Locale getContentLocale() {
        return Locale.GERMAN;
    }

    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_GRAPH);
    }

    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }
    
    public boolean validateInput(AnimationPropertiesContainer arg0,
			Hashtable<String, Object> primitives) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		boolean valid = true;
		
		String[] states = (String[])primitives.get("States");
		String[][] adja = (String[][])primitives.get("stringMatrixAdja");
		String start = (String)primitives.get("StartState");
	    String[] target = (String[])primitives.get("FinalStates"); 
	    String[] alphabet = (String[])primitives.get("Alphabet");
		
		if((states.length != adja.length)){
			JOptionPane.showMessageDialog(null, 
					"Die Adjazenzmatrix stimmt nicht mit der Zustandsliste ueberein", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(adja.length < 1 || states.length < 1 || alphabet.length < 1 || target.length < 1 || start.isEmpty()){
			JOptionPane.showMessageDialog(null, 
					"kein vollstaendiger Automat angegeben", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(adja.length != adja[0].length){
			JOptionPane.showMessageDialog(null, 
			"Adjazenzmstrix hat nicht die Masse n x n", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(states.length < target.length){
			JOptionPane.showMessageDialog(null, 
			"mehr Zielzustaende als Zustaende", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		DFAvalid = new int[states.length][alphabet.length];
		for(int i=0; i<DFAvalid.length; i++)
			for(int j=0; j<DFAvalid[i].length; j++)
				DFAvalid[i][j] = 0;
		
		for(int i=0; i<alphabet.length; i++){
			if(alphabet[i].startsWith(" ")){
				JOptionPane.showMessageDialog(null, 
				"Buchstabe beginnt mit Leerzeichen", "Warning", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		boolean inStates = false;
		for(int i=0; i<states.length; i++){
			if(states[i].compareTo(start) == 0)
				inStates = true;
		}
		
		if(!inStates){
			JOptionPane.showMessageDialog(null, 
			"Startzustand nicht in Auflistung enthalten", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		inStates = false;
		for(int i=0; i<target.length; i++){
			for(int j=0; j<states.length; j++)
				if(target[i].compareTo(states[j]) == 0)
					inStates = true;
			
			if(!inStates){
				JOptionPane.showMessageDialog(null, 
				"Zielzustand "+i +" nicht in Auflistung enthalten", "Warning", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			inStates = false;
		}
		
		for(int i=0; i<adja.length; i++){
			for (int j=0; j<adja[0].length; j++){
				String field = adja[i][j];
				
				int[] l = getLines(field);
				if(l == null){
					JOptionPane.showMessageDialog(null, 
					"Feld "+i+","+j+" hat ein '+' zuviel", "Warning", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				
				if(l.length > 0){
					if(!inAlphabet(field.substring(0, l[0]), alphabet, i)){
						JOptionPane.showMessageDialog(null, 
								"Feld "+i+","+j+" ist nicht im Alphabet", "Warning", JOptionPane.WARNING_MESSAGE);
						return false;
					}
		
					for(int k=1; k<l.length; k++){
						if(!inAlphabet(field.substring(l[k-1]+1, l[k]), alphabet, i)){
							JOptionPane.showMessageDialog(null, 
									"Feld "+i+","+j+" ist nicht im Alphabet", "Warning", JOptionPane.WARNING_MESSAGE);
							return false;
						}
					}
				
					if(!inAlphabet(field.substring(l[l.length-1]+1, field.length()), alphabet, i)){
						JOptionPane.showMessageDialog(null, 
								"Feld "+i+","+j+" ist nicht im Alphabet", "Warning", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
				else{
					if(!inAlphabet(field, alphabet, i)){
						JOptionPane.showMessageDialog(null, 
								"Feld "+i+","+j+" ist nicht im Alphabet", "Warning", JOptionPane.WARNING_MESSAGE);
						return false;
					}
				}
			}
		}
		
		for(int i=0; i<DFAvalid.length; i++){
			for(int j=0; j<DFAvalid[i].length; j++){
				if(DFAvalid[i][j] != 1){
					JOptionPane.showMessageDialog(null, 
					"kein DFA, da nicht von jedem Zustand genau eine Translation per Buchstaben aus geht", "Warning", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		
		return valid;
	}

    private int[] getLines(String field) {
    	// TODO Auto-generated method stub
		Vector<Integer> lines = new Vector<Integer>();
		boolean run = true;
		String string = field;
		while(run){
			int index = string.indexOf("+");
			if(index == -1){
				run = false;
			}
			else{
				lines.add(index);
				if(index+1 > string.length()){
					JOptionPane.showMessageDialog(null, 
					"Buchstabe fehlt", "Warning", JOptionPane.WARNING_MESSAGE);
					return null;
				}
				string = string.substring(index+1);
			}
		}
		
		int[] re = new int[lines.size()];
		for(int i=0; i<re.length; i++)
			re[i] = lines.get(i);
		
		return re;
	}
	private boolean inAlphabet(String substring, String[] alphabet2, int state) {
		// TODO Auto-generated method stub
		for(int i=0; i<alphabet2.length; i++){
			if(alphabet2[i].compareTo(substring) == 0){
				DFAvalid[state][i] = DFAvalid[state][i]+1;
				return true;
			}	
		}
		if(substring == null || substring.length() == 0 || substring.compareTo("{}") == 0)
			return true;
		
		return false;
	}

}