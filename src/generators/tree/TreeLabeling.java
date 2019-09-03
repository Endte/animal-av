/*
 * pruferCode.java
 * Edgardo Palza & Julian Zheng, 2017 for the Animal project at TU Darmstadt.
 * Copying this file for educational purposes is permitted without further authorization.
 */
package generators.tree;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.ValidatingGenerator;

import java.util.Locale;

import algoanim.primitives.ArrayMarker;
import algoanim.primitives.ArrayPrimitive;
import algoanim.primitives.IntArray;
import algoanim.primitives.Polyline;
import algoanim.primitives.SourceCode;
import algoanim.primitives.Text;
import algoanim.primitives.generators.AnimationType;
import algoanim.primitives.generators.ArrayMarkerGenerator;
import algoanim.primitives.generators.Language;
import algoanim.primitives.generators.PolylineGenerator;
import algoanim.properties.AnimationPropertiesKeys;
import algoanim.properties.ArrayMarkerProperties;
import algoanim.properties.ArrayProperties;
import algoanim.properties.CounterProperties;
import algoanim.properties.SourceCodeProperties;
import algoanim.properties.TextProperties;
import algoanim.util.Coordinates;
import algoanim.util.Node;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.graphics.helpers.Coordinate;
import interactionsupport.models.MultipleChoiceQuestionModel;
import algoanim.animalscript.AnimalArrayMarkerGenerator;
import algoanim.animalscript.AnimalScript;
import algoanim.counter.model.TwoValueCounter;
import algoanim.counter.view.TwoValueView;
import algoanim.exceptions.LineNotExistsException;

public class TreeLabeling implements ValidatingGenerator {
	private Language lang;
	private int[] code;
	private IntArray intArray;
	private IntArray counterArray;
	private TextProperties prueferProperty;
	private TextProperties nodeLabelProperty;
	private HashMap<Integer, Text> prueferTable;
	private ArrayProperties arrayProperty;
	private ArrayMarker markerCodeArray;
	private ArrayMarker markerDegreeArray;
	private ArrayMarkerGenerator markerGenerator;
	private ArrayMarkerProperties arrayMarkerProperty;
	private SourceCodeProperties scProperty;
	private int pointerCounter = 0;

	public void init() {
		lang = new AnimalScript("Tree Labeling", "Edgardo Palza & Teh-Hai Julian Zheng", 800, 600);
		lang.setInteractionType(Language.INTERACTION_TYPE_AVINTERACTION);
		lang.setStepMode(true);

		prueferTable = new HashMap<>();
	}

	public String generate(AnimationPropertiesContainer props, Hashtable<String, Object> primitives) {
		code = (int[]) primitives.get("code");
		prueferProperty = (TextProperties) props.getPropertiesByName("prueferProperty");
		nodeLabelProperty = (TextProperties) props.getPropertiesByName("nodeLabelProperty");
		arrayMarkerProperty = (ArrayMarkerProperties) props.getPropertiesByName("arrayMarkerProperty");
		arrayProperty = (ArrayProperties) props.getPropertiesByName("arrayProperty");
		scProperty = (SourceCodeProperties) props.getPropertiesByName("scProperty");
		prueferProperty.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Monospaced", Font.PLAIN, 20));
		nodeLabelProperty.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Monospaced", Font.BOLD, 24));
		markerGenerator = new AnimalArrayMarkerGenerator(lang);

		draw(code);
		lang.finalizeGeneration();
		return lang.toString();
	}

	public String getName() {
		return "Prüfer Code";
	}

	public String getAlgorithmName() {
		return "Prüfer Code";
	}

	public String getAnimationAuthor() {
		return "Edgardo Palza & Teh-Hai Julian Zheng";
	}

	public String getDescription() {
		return "Prüfer sequence (also Prüfer code or Prüfer numbers) of a labeled tree is a unique sequence associated with a tree. "
				+ "The sequence for a tree on n vertices has a length n-2, and can be generated by a "
				+ "simple iterative algorithm. Prüfer sequence were first used by Heinz Prüfer to prove Cayley's formula in 1918.";
	}

	public String getCodeExample() {
		return " Convert-Prüfer-to-Tree(a)" + "\n  n <- length[a]" // Line 1
				+ "\n  T <- a graph with n + 2 isolated prueferNodes, numbered 1 to n + 2" // Line
				// 2
				+ "\n  degree <- an array of integers" // Line 3
				+ "\n  for each prueferNode i in T" // Line 4
				+ "\n    do degree[i] <- 1" // Line 5
				+ "\n  for each value i in a" // Line 6
				+ "\n    do degree[i] <- degree[i] + 1" // Line 7
				+ "\n  for each value i in a" // Line 8
				+ "\n    for each prueferNode j in T" // Line 9
				+ "\n      if degree[j] = 1" // Line 10
				+ "\n        then Insert edge[i,j] into T" // Line 11
				+ "\n             degree[i] <- degree[i] - 1" // Line 12
				+ "\n             degree[j] <- degree[j] - 1" // Line 13
				+ "\n             break" // Line 14
				+ "\n  u <- v <- 0" // Line 15
				+ "\n  for each prueferNode i in T" // Line 16
				+ "\n    if degree[i] = 1" // Line 17
				+ "\n      then if u = 0" // Line 18
				+ "\n          then u <- i" // Line 19
				+ "\n          else v <- i" // Line 20
				+ "\n               break" // Line 21
				+ "\n  Insert edge[u,v] into T" // Line 22
				+ "\n  degree[u] <- degree[u] - 1" // Line 23
				+ "\n  degree[v] <- degree[v] - 1" // Line 24
				+ "\n  return T" // Line 25
		;
	}

	public String getFileExtension() {
		return "asu";
	}

	public Locale getContentLocale() {
		return Locale.ENGLISH;
	}

	public GeneratorType getGeneratorType() {
		return new GeneratorType(GeneratorType.GENERATOR_TYPE_TREE);
	}

	public String getOutputLanguage() {
		return Generator.JAVA_OUTPUT;
	}

	@Override
	public boolean validateInput(AnimationPropertiesContainer arg0, Hashtable<String, Object> arg1)
			throws IllegalArgumentException {
		for (Integer i : (int[]) arg1.get("code")) {
			if (i == 0) {
				return false;
			}
		}
		return true;
	}

	public void draw(int[] array) {

		Text title = lang.newText(new Coordinates(40, 15), "Pruefer Sequence", "title", null, nodeLabelProperty);
		lang.nextStep();

		SourceCode text = lang.newSourceCode(new Coordinates(40, 140), "Description", null, scProperty);
		text.addCodeLine("Description: ", null, 0, null);
		text.addCodeLine(
				"Prüfer sequence (also Prüfer code or Prüfer numbers) of a labeled tree is a unique sequence associated with a tree.",
				null, 1, null);
		text.addCodeLine("The sequence for a tree on n vertices has a length n-2, and can be generated by a ", null, 1,
				null);
		text.addCodeLine(
				"simple iterative algorithm. Prüfer sequence were first used by Heinz Prüfer to prove Cayley's formula in 1918.",
				null, 1, null);
		text.addCodeLine(" ", null, 0, null);
		text.addCodeLine("The goal of the Prüfer code is to convert the input array (or input sequence S) into a tree.", null, 0, null);
		text.addCodeLine("Each node becomes a degree d which is increased each time the node appears", null, 0, null);
		text.addCodeLine("in the input array S.", null, 0, null);
		text.addCodeLine("The degree d represents how many connection a node has.", null, 0, null);
		text.addCodeLine("The resulting tree is an unique representation of the input array S.", null, 0, null);
		lang.nextStep();
		
		MultipleChoiceQuestionModel q1 = new MultipleChoiceQuestionModel("q1");
		
		q1.setPrompt("What does the Prüfer Code do?");
		q1.addAnswer("Sorts the elements in the array by the Prüfer algorithm", 0, "Wrong!");
		q1.addAnswer("Converts an input array into a labeled tree", 1, "Correct!");
		q1.addAnswer("Takes the input array and use it for Encryption", 0, "Wrong!");
		lang.addMCQuestion(q1);
		
		text.hide();
		lang.nextStep();

		intArray = lang.newIntArray(new Coordinates(40, 90), array, "Code", null, arrayProperty);
		intArray.showIndices(false, null, null);
		lang.nextStep();
		int size = array.length;
		int numberOfprueferNodes = size + 2;
		int pos = 0;

		int[] counter = new int[numberOfprueferNodes];

		counterArray = lang.newIntArray(new Coordinates(40, 160), counter, "Counter", null, arrayProperty);
		counterArray.showIndices(false, null, null);

		SourceCode sc = lang.newSourceCode(new Coordinates(40, 180), "sourceCode", null, scProperty);
		sc.addCodeLine("Convert-Prüfer-to-Tree(a)", null, 0, null);
		sc.addCodeLine("n<-length[a]", null, 1, null);
		sc.addCodeLine("T <- a graph with n + 2 isolated prueferNodes, numbered 1 to n + 2", null, 1, null);
		sc.addCodeLine("degree <- an array of integers", null, 1, null);
		sc.addCodeLine("for each prueferNode i in T", null, 1, null);
		sc.addCodeLine("do degree[i] <- 1", null, 2, null);
		sc.addCodeLine("for each value i in a", null, 1, null);
		sc.addCodeLine("do degree[i] <- degree[i] + 1", null, 2, null);
		sc.addCodeLine("for each value i in a", null, 1, null);
		sc.addCodeLine("for each prueferNode j in T", null, 2, null);
		sc.addCodeLine("if degree[j] = 1", null, 3, null);
		sc.addCodeLine("then Insert edge[i,j] into T", null, 4, null);
		sc.addCodeLine("degree[i] <- degree[i] - 1", null, 7, null);
		sc.addCodeLine("degree[j] <- degree[j] - 1", null, 7, null);
		sc.addCodeLine("break", null, 7, null);
		sc.addCodeLine("u <- v <- 0", null, 1, null);
		sc.addCodeLine("for each prueferNode i in T", null, 1, null);
		sc.addCodeLine("if degree[i] = 1", null, 2, null);
		sc.addCodeLine("then if u = 0", null, 3, null);
		sc.addCodeLine("then u <- i", null, 4, null);
		sc.addCodeLine("else v <- i", null, 4, null);
		sc.addCodeLine("break", null, 7, null);
		sc.addCodeLine("Insert edge[u,v] into T", null, 1, null);
		sc.addCodeLine("degree[u] <- degree[u] - 1", null, 1, null);
		sc.addCodeLine("degree[v] <- degree[v] - 1", null, 1, null);
		sc.addCodeLine("return T", null, 1, null);

		lang.nextStep();
		intArray.highlightCell(0, intArray.getLength() - 1, null, null);
		counterArray.highlightCell(0, counterArray.getLength() - 1, null, null);
		try {
			codeToTree(array, sc);
		} catch (Exception e) {
			// TODO: handle exception
		}
		title.show();

		lang.nextStep();
		prueferProperty.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Monospaced", Font.PLAIN, 20));

		SourceCode closing = lang.newSourceCode(new Coordinates(40, 140), "Closing Text", null, scProperty);
		text.addCodeLine("Closing Text: ", null, 0, null);
		text.addCodeLine("The immediate consequence is that Prüfer sequences", null, 1, null);
		text.addCodeLine("provide a bijection between the set of labeled trees", null, 1, null);
		text.addCodeLine("on n vertices and the set of sequences of length n–2", null, 1, null);
		text.addCodeLine("on the labels 1 to n.", null, 1, null);
		text.addCodeLine("The latter set has size n^n−2, so the existence", null, 1, null);
		text.addCodeLine("of this bijection proves Cayley's formula,", null, 1, null);
		text.addCodeLine("i.e. that there are n^n−2 labeled trees on n vertices.", null, 1, null);
		lang.nextStep();

	}

	/**
	 * counter for the number of pointers
	 * 
	 */
	private int pointerPos = 0;

	public ArrayList<PrueferNode> codeToTree(int[] array, SourceCode sc) throws Exception {
		sc.highlight(0, 0, false);
		lang.nextStep();
		sc.unhighlight(0, 0, false);
		sc.highlight(1, 0, false);
		sc.highlight(2, 0, false);
		sc.highlight(3, 0, false);
		// Line 1
		int size = array.length;
		// Line 2
		int numberOfprueferNodes = size + 2;
		int counterForprueferNodes = 0;
		// Line 3
		ArrayList<PrueferNode> prueferNodes = new ArrayList<>();
		// TwoValueCounter counter = lang.newCounter(intArray);
		// CounterProperties cp = new CounterProperties();
		// cp.set(AnimationPropertiesKeys.FILLED_PROPERTY, true);
		// cp.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.BLUE);
		// TwoValueView view = lang.newCounterView(counter, new Coordinates(80,
		// 60), cp, true, true);
		lang.nextStep();
		sc.unhighlight(1, 0, false);
		sc.unhighlight(2, 0, false);
		sc.unhighlight(3, 0, false);
		sc.highlight(4, 0, false);
		
		MultipleChoiceQuestionModel q2 = new MultipleChoiceQuestionModel("q2");
		q2.setPrompt("How many entries does the second array have?");
		q2.addAnswer("n-1", 0, "Wrong!");
		q2.addAnswer("n+2", 1, "Correct!");
		q2.addAnswer("n+1", 0, "Wrong!");
		lang.addMCQuestion(q2);
		
		lang.nextStep();
		// Line 4 to 5
		SourceCode textHintBox1 = lang.newSourceCode(new Coordinates(600, 40), "sourceCode", null, scProperty);
		textHintBox1.addCodeLine("In steps 4 and 5 the second array", null, 0, null);
		textHintBox1.addCodeLine("is initialized with 1's as default degree.", null, 0, null);
		while (counterForprueferNodes < numberOfprueferNodes) {
			PrueferNode n = new PrueferNode(counterForprueferNodes + 1);
			prueferNodes.add(n);
			sc.toggleHighlight(4, 0, false, 5, 0);
			counterArray.put(counterForprueferNodes, 1, null, null);
			counterForprueferNodes++;
			lang.nextStep();
			sc.toggleHighlight(5, 0, false, 4, 0);
			lang.nextStep();
		}
		textHintBox1.hide();
		// Line 6 -7
		SourceCode textHintBox2 = lang.newSourceCode(new Coordinates(600, 40), "sourceCode", null, scProperty);
		textHintBox2.addCodeLine("In steps 6 and 7 the second array", null, 0, null);
		textHintBox2.addCodeLine("is filled with it proper degree.", null, 0, null);
		textHintBox2.addCodeLine("The degree is increased by 1,", null, 0, null);
		textHintBox2.addCodeLine("every time the number appears", null, 0, null);
		textHintBox2.addCodeLine("in the input array.", null, 0, null);
		sc.toggleHighlight(4, 0, false, 6, 0);
		markerCodeArray = lang.newArrayMarker(intArray, 0, "i" + pointerCounter, null, arrayMarkerProperty);
		lang.nextStep();
		for (int i = 0; i < array.length; i++) {
			sc.toggleHighlight(6, 0, false, 7, 0);
			markerCodeArray.move(i, null, null);
			searchprueferNode(array[i], prueferNodes).increaseDegree();
			lang.nextStep();
			sc.toggleHighlight(7, 0, false, 6, 0);
			counterArray.put(array[i] - 1, prueferNodes.get(array[i] - 1).getDegree(), null, null);
			lang.nextStep();
		}
		textHintBox2.hide();
		markerCodeArray.move(0, null, null);
		markerDegreeArray = lang.newArrayMarker(counterArray, 0, "i" + pointerCounter, null, arrayMarkerProperty);
		sc.unhighlight(6);
		lang.nextStep();
		// Line 8-14
		sc.toggleHighlight(6, 0, false, 8, 0);
		lang.nextStep();
		for (int i = 0; i < array.length; i++) {
			markerCodeArray.move(i, null, null);
			sc.toggleHighlight(8, 0, false, 9, 0);
			lang.nextStep();
			for (PrueferNode n : prueferNodes) {
				markerDegreeArray.move(n.getData() - 1, null, null);
				sc.toggleHighlight(9, 0, false, 10, 0);
				lang.nextStep();
				if (n.getDegree() == 1) {

					sc.toggleHighlight(10, 0, false, 11, 0);

					PrueferNode temp = searchprueferNode(array[i], prueferNodes);
					Coordinates coordinatesA = calcCoordinates(temp.getData(), prueferNodes.size());
					Coordinates coordinatesB = calcCoordinates(n.getData(), prueferNodes.size());

					lang.nextStep();
					if (!prueferTable.containsKey(temp.getData())) {
						Text prueferNode = lang.newText(coordinatesA, "" + array[i], "prueferNode", null,
								prueferProperty);
						prueferTable.put(temp.getData(), prueferNode);
					}

					if (!prueferTable.containsKey(n.getData())) {

						Text prueferNode = lang.newText(coordinatesB, "" + n.getData(), "prueferNode", null,
								prueferProperty);
						prueferTable.put(n.getData(), prueferNode);
					}

					Node[] nodeArray = new Node[2];
					nodeArray[0] = coordinatesA;
					nodeArray[1] = coordinatesB;
					Polyline line = lang.newPolyline(nodeArray, "line", null);
					lang.nextStep();

					sc.toggleHighlight(11, 0, false, 12, 0);
					n.addNeighbor(temp);
					n.decreaseDegree();
					counterArray.put(n.getData() - 1, n.getDegree(), null, null);
					lang.nextStep();

					sc.toggleHighlight(12, 0, false, 13, 0);
					temp.addNeighbor(n);
					temp.decreaseDegree();
					n.decreaseDegree();
					markerDegreeArray.move(temp.getData() - 1, null, null);
					counterArray.put(temp.getData() - 1, temp.getDegree(), null, null);
					lang.nextStep();

					sc.toggleHighlight(13, 0, false, 14, 0);
					lang.nextStep();
					break;
				}
			}
			sc.unhighlight(14, 0, false);
			sc.highlight(8, 0, false);
			lang.nextStep();
		}

		// Line 15
		SourceCode textHintBox3 = lang.newSourceCode(new Coordinates(600, 40), "sourceCode", null, scProperty);
		textHintBox3.addCodeLine("The last action of the Prüfer code algorithm", null, 0, null);
		textHintBox3.addCodeLine("is to search the last 2 nodes with degree 1.", null, 0, null);
		textHintBox3.addCodeLine("This nodes are saved in the temporary nodes", null, 0, null);
		textHintBox3.addCodeLine("u and v.", null, 0, null);
		sc.toggleHighlight(8, 0, false, 15, 0);
		PrueferNode u = null;
		PrueferNode v = null;
		Text uText = lang.newText(new Coordinates(400, 95), "Node u: " + 0, "u", null, prueferProperty);
		Text vText = lang.newText(new Coordinates(400, 125), "Node v: " + 0, "v", null, prueferProperty);
		lang.nextStep();

		// Line 16-21
		sc.toggleHighlight(15, 0, false, 16, 0);
		lang.nextStep();
		for (PrueferNode n : prueferNodes) {

			sc.toggleHighlight(16, 0, false, 17, 0);
			markerDegreeArray.move(n.getData() - 1, null, null);
			lang.nextStep();
			if (n.getDegree() == 1) {

				sc.toggleHighlight(17, 0, false, 18, 0);
				lang.nextStep();
				if (u == null) {
					sc.toggleHighlight(18, 0, false, 19, 0);
					u = n;
					uText.setText("Node u: " + (u.getData()), null, null);
					lang.nextStep();
				} else {
					sc.unhighlight(18, 0, false);
					sc.toggleHighlight(19, 0, false, 20, 0);
					v = n;
					vText.setText("Node v: " + (v.getData()), null, null);
					lang.nextStep();

					sc.toggleHighlight(20, 0, false, 21, 0);
					lang.nextStep();
					break;
				}
				sc.unhighlight(19, 0, false);
				sc.highlight(16, 0, false);
				lang.nextStep();
			}
			sc.unhighlight(17, 0, false);
			sc.highlight(16, 0, false);
			lang.nextStep();
		}
		// Line 22-24
		sc.unhighlight(16, 0, false);
		sc.toggleHighlight(21, 0, false, 22, 0);
		searchprueferNode(u.getData(), prueferNodes).addNeighbor(v);
		searchprueferNode(v.getData(), prueferNodes).addNeighbor(u);
		Coordinates coordinatesA = calcCoordinates(u.getData(), prueferNodes.size());
		Coordinates coordinatesB = calcCoordinates(v.getData(), prueferNodes.size());
		if (!prueferTable.containsKey(u.getData())) {
			Text prueferNode = lang.newText(coordinatesA, "" + u.getData(), "prueferNode", null, prueferProperty);
			prueferTable.put(u.getData(), prueferNode);
		}

		if (!prueferTable.containsKey(v.getData())) {

			Text prueferNode = lang.newText(coordinatesB, "" + v.getData(), "prueferNode", null, prueferProperty);
			prueferTable.put(v.getData(), prueferNode);
		}

		Node[] nodeArray = new Node[2];
		nodeArray[0] = coordinatesA;
		nodeArray[1] = coordinatesB;
		Polyline line = lang.newPolyline(nodeArray, "line", null);
		lang.nextStep();

		sc.toggleHighlight(22, 0, false, 23, 0);
		searchprueferNode(u.getData(), prueferNodes).decreaseDegree();
		counterArray.put(u.getData() - 1, u.getDegree(), null, null);
		lang.nextStep();
		sc.toggleHighlight(23, 0, false, 24, 0);
		searchprueferNode(v.getData(), prueferNodes).decreaseDegree();
		counterArray.put(v.getData() - 1, v.getDegree(), null, null);
		lang.nextStep();

		// Line 25
		sc.toggleHighlight(24, 0, false, 25, 0);
		lang.nextStep();
		sc.hide();
		textHintBox3.hide();
		counterArray.hide();
		markerCodeArray.hide();
		markerDegreeArray.hide();
		uText.hide();
		vText.hide();
		
		MultipleChoiceQuestionModel q3 = new MultipleChoiceQuestionModel("q3");
		q3.setPrompt("Which values did the Nodes U and V have?");
		

		q3.addAnswer("" + (u.getData() + 1) + " and " + (v.getData() - 4), 0, "Falsch!");
		q3.addAnswer("" + u.getData() + " and " + v.getData(), 1, "Correct!");
		q3.addAnswer("" + (u.getData()-3) + " and " + (v.getData() + 2), 0, "Falsch!");
		lang.addMCQuestion(q3);
		
		return prueferNodes;
	}

	/**
	 * Search a prueferNode i in the tree prueferNodes
	 * 
	 * @param i
	 *            the name of the prueferNode to be found
	 * @param prueferNodes
	 *            List of existing prueferNodes in the tree
	 * @return the prueferNode itself as a prueferNode object
	 */
	private PrueferNode searchprueferNode(int i, ArrayList<PrueferNode> prueferNodes) {
		PrueferNode temp = null;
		for (PrueferNode n : prueferNodes) {
			if (n.getData() == i) {
				temp = n;
			}
		}
		return temp;
	}

	private Coordinates calcCoordinates(int idx, int size) {
		// Kreis
		int xMid = 900;
		int yMid = 300;

		int radius = 200;

		return new Coordinates((int) (radius * Math.cos(idx * 2 * Math.PI / size)) + xMid,
				(int) (radius * Math.sin(idx * 2 * Math.PI / size)) + yMid);
	}

}