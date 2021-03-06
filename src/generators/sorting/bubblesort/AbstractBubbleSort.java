package generators.sorting.bubblesort;

import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.helpers.AnimatedIntArrayAlgorithm;

import interactionsupport.parser.InteractionFactory;

import java.util.Hashtable;
import java.util.Locale;

import translator.Translator;
import algoanim.animalscript.AnimalScript;
import algoanim.primitives.ArrayMarker;
import algoanim.primitives.Text;
import algoanim.properties.AnimationProperties;
import algoanim.properties.TextProperties;
import algoanim.util.Offset;

public class AbstractBubbleSort extends AnimatedIntArrayAlgorithm {
  protected Text               swapLabel, swapPerf;
  protected Locale             contentLocale = null;
  protected InteractionFactory factory;

  public AbstractBubbleSort(String aResourceName, Locale aLocale) {
    resourceName = aResourceName;
    locale = aLocale;
    init();
  }

  public void init() {
    translator = new Translator(resourceName, locale);
    primitiveProps = new Hashtable<String, AnimationProperties>(59);
    localType = new GeneratorType(GeneratorType.GENERATOR_TYPE_SORT);
    contentLocale = locale;
  }

  /**
   * hides the array, code, and number of steps taken from the display
   */
  protected void hideNrStepsArrayCode() {
    super.hideNrStepsArrayCode();
    if (array != null)
      array.hide();
  }

  /**
   * Bubble Sort swaps neighbours if they are not sorted. It iterates up to n
   * times over the array, regarding only the elements at indices [0, n-i] in
   * iteration i. Run-time complexity in worst case: O(n*n)
   */
  public void sort() {
    int nrElems = array.getLength();
    ArrayMarker iMarker = null, jMarker = null;
    // highlight method header
    code.highlight("header");
    lang.nextStep();

    // switch to variable declaration
    code.toggleHighlight("header", "variables");
    int i, j; // Schleifenzaehler

    lang.nextStep();
    incrementNrAssignments(2);
    // switch to init for swapPerformed
    code.toggleHighlight("variables", "initSwap");
    boolean swapPerformed = true; // wurde getauscht?
    incrementNrAssignments(); // swapPerformed = true

    swapLabel = lang.newText(
        new Offset(30, 0, array, AnimalScript.DIRECTION_SE), "swapPerformed=",
        "swapLabel", null, (TextProperties) primitiveProps.get("title"));
    swapPerf = lang.newText(new Offset(10, 0, swapLabel,
        AnimalScript.DIRECTION_BASELINE_END), "true", "swapVal", null,
        (TextProperties) primitiveProps.get("title"));

    // create i marker
    lang.nextStep();
    code.unhighlight("initSwap");
    incrementNrAssignments(); // i = nrElems

    for (i = nrElems; swapPerformed && i > 0; i--) {
      code.highlight("outerLoop");
      if (i == nrElems) {
        iMarker = installArrayMarker("iMarker", array, nrElems - 1);
        iMarker.moveOutside(null, DEFAULT_TIMING);
      } else {
        iMarker.move(i, null, DEFAULT_TIMING);
        array.highlightCell(i, null, DEFAULT_TIMING);
      }
      incrementNrComparisons(2); // swapPer, i>0
      incrementNrAssignments(); // i-- [i = nrElems in first iteration]

      // reset swapPerformed
      lang.nextStep();
      code.toggleHighlight("outerLoop", "resetSwap");
      swapPerf.setText("false", null, DEFAULT_TIMING);
      swapPerformed = false;
      incrementNrAssignments(); // swapPerformed = false

      // create j marker on entering the loop
      lang.nextStep();
      for (j = 1; j < i; j++) {
        code.toggleHighlight("resetSwap", "innerLoop");
        if (jMarker == null) {
          jMarker = installArrayMarker("jMarker", array, j);
        } else
          jMarker.move(j, null, DEFAULT_TIMING);
        incrementNrAssignments(); // j = 1 // j++
        incrementNrComparisons(); // j < i

        // compare a[j-1], a[j]
        lang.nextStep();
        code.toggleHighlight("innerLoop", "if");
        array.highlightElem(j - 1, null, null);
        array.highlightElem(j, null, null);

        incrementNrComparisons(); // if (a[j-1] > a[j])
        lang.nextStep();
        if (array.getData(j - 1) > array.getData(j)) {
          // swap elements
          code.toggleHighlight("if", "swap");
          array.swap(j - 1, j, null, DEFAULT_TIMING);
          incrementNrAssignments(3); // swap(j-1, j)

          // set swapPerformed to true
          lang.nextStep();
          code.toggleHighlight("swap", "swap=true");
          swapPerf.setText("true", null, DEFAULT_TIMING);
          swapPerformed = true;
          incrementNrAssignments(); // swapPerformed = true

          // clean up...
          lang.nextStep();
          code.unhighlight("swap=true");
        } else {
          code.unhighlight("if");
        }
        array.unhighlightElem(j - 1, null, null);
        array.unhighlightElem(j, null, null);
      } // for j...
      incrementNrComparisons(); // last iteration of inner loop
      incrementNrAssignments(); // last iteration of inner loop
    }
    incrementNrComparisons(); // last iteration of outer loop
    incrementNrAssignments(); // last increment of outer loop

    // some interaction...
    factory = new InteractionFactory(lang, "InteractionPatterns.xml");
    factory.generateTFQuestion("tfQ", "tfQ");
    factory.generateFIBQuestion("fibQ", "fibQ");
    factory.generateDocumentation("link", "link");
    factory.generateMCQuestion("mcq", "mcq");
    factory.generateTFQuestion("msq", "msq");

  }

  /**
   * getContentLocale returns the target Locale of the generated output Use e.g.
   * Locale.US for English content, Locale.GERMANY for German, etc.
   * 
   * @return a Locale instance that describes the content type of the output
   */
  public Locale getContentLocale() {
    return contentLocale;
  }

  public String getAlgorithmName() {
    return "Bubble Sort";
  }

  public String getAnimationAuthor() {
    return "Guido Rößling";
  }

  public String generate(AnimationPropertiesContainer props,
      Hashtable<String, Object> prims) {
    setUpDefaultElements(props, prims, "array", "code", "code", 0, 20);
    // new Offset(0, 20, array, AnimalScript.DIRECTION_SW));
    sort();
    if (swapPerf != null)
      swapPerf.hide();
    if (swapLabel != null)
      swapLabel.hide();
    wrapUpAnimation();

    // This is needed for the Interactions to be saved and displayed
    lang.finalizeGeneration();
    return lang.getAnimationCode();
  }
}
