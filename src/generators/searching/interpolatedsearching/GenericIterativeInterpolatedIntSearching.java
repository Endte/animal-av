/*
 * Created on 18.06.2007 by Guido Roessling (roessling@acm.org>
 */
package generators.searching.interpolatedsearching;

import extras.lifecycle.common.Variable;
import extras.lifecycle.monitor.CheckpointUtils;
import generators.framework.Generator;
import generators.framework.properties.AnimationPropertiesContainer;

import java.util.Hashtable;
import java.util.Locale;

import algoanim.animalscript.AnimalScript;
import algoanim.primitives.ArrayMarker;
import algoanim.util.Offset;

public class GenericIterativeInterpolatedIntSearching extends
    AbstractInterpolatedIntSearching implements Generator {

  public GenericIterativeInterpolatedIntSearching(String aResourceName,
      Locale aLocale) {
    super(aResourceName, aLocale);
  }

  /**
   * generates the animation
   * 
   * @param props
   *          the properties given by the animation viewer
   * @param prims
   *          the primitive objects as given by the animation viewer
   * @return the String output for the animation
   */
  public String generate(AnimationPropertiesContainer props,
      Hashtable<String, Object> prims) {
    setUpDefaultElements(props, prims, "array", "code", "code", 0, 20);
    int valueToSearchFor = ((Integer) prims.get("value")).intValue();
    // int resultIndex =
    search(valueToSearchFor);
    wrapUpAnimation();
    lang.finalizeGeneration();
    return lang.getAnimationCode();
  }

  protected int search(int valueToSearchFor) {
    ArrayMarker lMarker = null, rMarker = null, midMarker = null;

    valueText = installText("value", "value: " + valueToSearchFor, new Offset(
        30, 0, array, AnimalScript.DIRECTION_SE));

    // highlight method header
    code.highlight("header");
    lang.nextStep();
    code.toggleHighlight("header", "ifNull");
    incrementNrComparisons(2); // null, length == 0
    lang.nextStep();

    // switch to variable declaration
    code.toggleHighlight("ifNull", "getArrayLength");
    int nrElems = array.getLength();
    incrementNrAssignments();
    lang.nextStep();

    // initialize l counter to 0
    code.toggleHighlight("getArrayLength", "installLMarker");
    lMarker = installArrayMarker("lMarker", array, 0);
    incrementNrAssignments(); // l = 0
    lang.nextStep();

    // initialize r counter to arrayLength - 1
    code.toggleHighlight("installLMarker", "installRMarker");
    rMarker = installArrayMarker("rMarker", array, nrElems - 1);
    incrementNrAssignments(); // r = nrElems - 1
    array.highlightCell(0, nrElems - 1, DEFAULT_TIMING, null);
    lang.nextStep();

    // initialize r counter to arrayLength - 1
    code.toggleHighlight("installRMarker", "installMidMarker");
    int targetPos = lMarker.getPosition()
        + ((valueToSearchFor - array.getData(lMarker.getPosition())) * (rMarker
            .getPosition() - lMarker.getPosition()))
        / (array.getData(rMarker.getPosition()
            - array.getData(lMarker.getPosition())));
    midMarker = installArrayMarker("midMarker", array, targetPos);
    array.highlightElem(midMarker.getPosition(), null, null);
    incrementNrAssignments(); // r = nrElems - 1
    lang.nextStep();

    CheckpointUtils.checkpointEvent(this, "Iteration", 
        new Variable("SearchVal", valueToSearchFor),
        new Variable("midIndex", targetPos),
        new Variable("lowIndex", lMarker.getPosition()),
        new Variable("highIndex", rMarker.getPosition()));
    
    // switch to start of while loop
    code.toggleHighlight("installMidMarker", "whileLoop");
    // while (r > l && array[mid] != value) {
    while (rMarker.getPosition() > lMarker.getPosition()
        && array.getData(midMarker.getPosition()) != valueToSearchFor) {
      
      CheckpointUtils.checkpointEvent(this, "Iteration", 
          new Variable("SearchVal","valueToSearchFor"),
          new Variable("midIndex", targetPos),
          new Variable("midVal", array.getData(targetPos)),
          new Variable("lowIndex", lMarker.getPosition()),
          new Variable("lowVal", array.getData(lMarker.getPosition())),
          new Variable("highIndex", rMarker.getPosition()), 
          new Variable("highVal", array.getData(rMarker.getPosition()))
          );
      
      incrementNrComparisons(3); // >, !=, &&
      lang.nextStep();

      // check if less than
      code.toggleHighlight("whileLoop", "ifLess");
      incrementNrComparisons(); // if (<)
      lang.nextStep();

      if (valueToSearchFor < array.getData(midMarker.getPosition())) {

        code.toggleHighlight("ifLess", "continueLeft");
        array.unhighlightCell(midMarker.getPosition(), rMarker.getPosition(),
            DEFAULT_TIMING, DEFAULT_TIMING);
        rMarker.move(midMarker.getPosition() - 1, null, DEFAULT_TIMING); // r =
                                                                          // mid
                                                                          // -1
        incrementNrAssignments();
        lang.nextStep();

        code.unhighlight("continueLeft");
      } else {
        code.toggleHighlight("ifLess", "continueRight");
        array.unhighlightCell(lMarker.getPosition(), midMarker.getPosition(),
            DEFAULT_TIMING, DEFAULT_TIMING);
        lMarker.move(midMarker.getPosition() + 1, null, DEFAULT_TIMING); // l =
                                                                          // mid
                                                                          // + 1
        incrementNrAssignments();
        lang.nextStep();

        code.unhighlight("continueRight");
      }
      code.highlight("updateMidElem");
      array.unhighlightElem(midMarker.getPosition(), null, null);
      targetPos = lMarker.getPosition()
          + ((valueToSearchFor - array.getData(lMarker.getPosition())) * (rMarker
              .getPosition() - lMarker.getPosition()))
          / (array.getData(rMarker.getPosition()
              - array.getData(lMarker.getPosition())));
      midMarker.move(targetPos, null, DEFAULT_TIMING); // mid = (l + r) / 2
      array.highlightElem(midMarker.getPosition(), null, null);
      incrementNrAssignments();
      lang.nextStep();

      code.toggleHighlight("updateMidElem", "whileLoop");
    }
    incrementNrComparisons(3); // last iteration
    lang.nextStep();

    code.toggleHighlight("whileLoop", "checkFound");
    incrementNrComparisons();
    lang.nextStep();
    if (array.getData(midMarker.getPosition()) == valueToSearchFor) {
      code.toggleHighlight("checkFound", "found");
      result = installText("value", translator.translateMessage("result",
          new Integer[] { new Integer(midMarker.getPosition()) }), new Offset(
          20, 0, valueText, AnimalScript.DIRECTION_BASELINE_END));
      lang.nextStep();
      code.unhighlight("found");
      
      CheckpointUtils.checkpointEvent(this, "Found",
          new Variable("index", midMarker.getPosition()));
      
      return midMarker.getPosition();
    }
    code.toggleHighlight("checkFound", "notFound");
    result = installText("value", translator.translateMessage("result",
        new Integer[] { new Integer(-1) }), new Offset(20, 0, valueText,
        AnimalScript.DIRECTION_BASELINE_END));
    lang.nextStep();
    code.unhighlight("notFound");
    
    CheckpointUtils.checkpointEvent(this, "NotFound",
        new Variable("index", -1));
    
    return -1;
  }
}