package generators.sorting.quicksort;

import extras.lifecycle.common.Variable;
import extras.lifecycle.monitor.CheckpointUtils;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.framework.properties.AnimationPropertiesContainer;
import generators.helpers.AnimatedIntArrayAlgorithm;

import java.util.Hashtable;
import java.util.Locale;

import translator.Translator;
import algoanim.primitives.ArrayMarker;
import algoanim.primitives.Text;
import algoanim.properties.AnimationProperties;

public class GenericAnnotatedQuickSort extends AnimatedIntArrayAlgorithm
    implements Generator {
  protected Text   swapLabel, swapPerf;
  protected Locale contentLocale = null;
  protected ArrayMarker iMarker = null, jMarker = null;
  // private InteractionFactory factory = null;
  // private int questionCounter = 0, random1 = 0, random2 = 0;
  // private MultipleChoiceQuestionModel mcq = null;

  String                resourceName;
  Locale                locale;

  public GenericAnnotatedQuickSort(String aResourceName, Locale aLocale) {
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

  public void sort() {
    // lang.setInteractionType(Language.INTERACTION_TYPE_AVINTERACTION);
    // factory = new InteractionFactory(lang, "InteractionPatterns.xml");
    // // Questions in group "pivot" have to be answered correct for 2 times in
    // total
    // lang.addQuestionGroup(new QuestionGroupModel("pivot", 2));
    //
    // MultipleChoiceQuestionModel year = new
    // MultipleChoiceQuestionModel("AlgorithmYear");
    // year.setPrompt("In which year did C.A.R. Hoare develop the QuickSort Algorithm?");
    // year.setPointsPossible(1);
    // year.addAnswerOption("1960", true, "That is correct", 1);
    // year.addAnswerOption("2006", false,
    // "The QuickSort Algorithm is much older", -1);
    // year.addAnswerOption("1972", false, "Close, but not right", 0);
    // lang.addMCQuestion(year);
    // lang.nextStep();
    //
    // FillInBlanksQuestionModel fib = new
    // FillInBlanksQuestionModel("Comparisons");
    // fib.setPrompt("How many Comparisons will it take the Algorithm to sort the given Array?");
    // fib.setPointsPossible(5);
    // lang.addFIBQuestion(fib);
    // lang.nextStep();
    //
    // questionCounter = 0;
    quickSort(0, (array.getLength() - 1), 0);

    // The FillInBlanks QuestionView from start gets the correct answer option
    // fib.addAnswer("" + nrComparisons, 5, "The AnswerModel is " +
    // nrComparisons + " Comparisons");
    //
    // HtmlDocumentationModel link = new
    // HtmlDocumentationModel("QuickSort Docu");
    // link.setLinkAddress("http://www.linux-related.de/index.html?/coding/sort/sort_quick.htm");
    // lang.addDocumentationLink(link);
  }

  private String makeLabel(int l, int r, int depth) {
    StringBuilder sb = new StringBuilder(32);
    for (int i = 0; i < depth; i++)
      sb.append(' ');
    sb.append("quicksort(").append(l).append(", ").append(r);
    sb.append(")");
    return sb.toString();
  }

  public void quickSort(int l, int r, int depth) {
    int i, j, pivot;

    code.highlight("header");
    array.highlightCell(l, r, null, null);
    lang.nextStep(makeLabel(l, r, depth));

    code.toggleHighlight("header", "variables");
    lang.nextStep();

    code.toggleHighlight("variables", "check1");
    lang.nextStep();

    if (r > l) {
      // questionCounter++;

      incrementNrComparisons();
      code.toggleHighlight("check1", "setPivot");
      lang.nextStep();

      pivot = array.getData(r);
      // int index = r;

      CheckpointUtils.checkpointEvent(this, "pivotChange", new Variable(
          "pivotValue", pivot), new Variable("pivotIndex", r), new Variable(
          "leftIndex", l), new Variable("rightIndex", r), new Variable(
          "animstep", lang.getStep()));

      // Ask the Question. The Pivot Element is loaded dynamical threw a
      // placeholder {0} in the Pattern
      // mcq = factory.generateMCQuestion("PivotPlaceholder", "Pivot" +
      // questionCounter, "" + pivot);

      incrementNrAssignments();
      code.toggleHighlight("setPivot", "loop");
      lang.nextStep();

      incrementNrAssignments(2);
      for (i = l, j = r - 1; i < j;) {
        if (iMarker == null)
          iMarker = installArrayMarker("iMarker", array, i);
        else
          iMarker.move(i, null, null);
        if (jMarker == null)
          jMarker = installArrayMarker("jMarker", array, j);
        else
          jMarker.move(j, null, null);

        incrementNrComparisons();
        code.toggleHighlight("loop", "loopI");
        lang.nextStep();

        incrementNrComparisons(2);
        while (array.getData(i) <= pivot && j > i) {
          code.toggleHighlight("loopI", "incrementI");
          lang.nextStep();

          i++;
          incrementNrAssignments();
          iMarker.move(i, null, null);
          code.toggleHighlight("incrementI", "loopI");
          lang.nextStep();

          incrementNrComparisons(2);
        }
        code.toggleHighlight("loopI", "loopJ");
        lang.nextStep();

        incrementNrComparisons(2);
        // while the pivot is smaller than the values on its right
        while (pivot < array.getData(j) && j > i) {
          code.toggleHighlight("loopJ", "decrementJ");
          lang.nextStep();

          j--;
          incrementNrAssignments();
          jMarker.move(j, null, null);
          code.toggleHighlight("decrementJ", "loopJ");
          lang.nextStep();

          incrementNrComparisons(2);
        }
        code.toggleHighlight("loopJ", "check2");
        lang.nextStep();

        incrementNrComparisons();
        if (i < j) {
          code.toggleHighlight("check2", "swapIJ");
          lang.nextStep();

          CheckpointUtils.checkpointEvent(this, "swap1", new Variable(
              "thisone", array.getData(i)),
              new Variable("nextone", array.getData(j)), new Variable("i", i),
              new Variable("j", j), new Variable("animstep", lang.getStep()));
          array.swap(i, j, null, null);

          incrementNrAssignments(2);
          code.toggleHighlight("swapIJ", "loop");
          lang.nextStep();
        } else {
          code.toggleHighlight("check2", "loop");
          lang.nextStep();
        }
      }
      code.toggleHighlight("loop", "check3");
      lang.nextStep();

      incrementNrComparisons();

      if (pivot < array.getData(i)) {
        code.toggleHighlight("check3", "swapIR");
        lang.nextStep();
        incrementNrAssignments(2);

        CheckpointUtils.checkpointEvent(this, "swap2", new Variable("thisone",
            array.getData(i)), new Variable("nextone", array.getData(r)),
            new Variable("i", i), new Variable("r", r), new Variable(
                "animstep", lang.getStep()));

        array.swap(i, r, null, null);

        code.toggleHighlight("swapIR", "sortLeft");
        lang.nextStep();
      } else {
        code.toggleHighlight("check3", "setItoR");
        lang.nextStep();

        i = r;
        incrementNrAssignments();
        code.toggleHighlight("setItoR", "sortLeft");
        lang.nextStep();
      }

      // random1 = (int)(Math.random() * (array.getLength() -1));
      // random2 = (int)(Math.random() * (array.getLength() -1));

      // check(i);

      // mcq.addAnswerOption("" + i, true, "right", 5);
      // mcq.addAnswerOption("" + random1, false, "wrong, " + i +
      // " where the correct position", -3);
      // mcq.addAnswerOption("" + random2, false, "wrong, " + i +
      // " where the correct position", -3);

      code.unhighlight("sortLeft");
      array.unhighlightCell(l, r, null, null);

      // CheckpointUtils.checkpointEvent(this, "finished", new
      // Variable("iFinal", i));

      quickSort(l, i - 1, depth + 1);
      code.highlight("sortRight");
      lang.nextStep();

      code.unhighlight("sortRight");
      quickSort(i + 1, r, depth + 1);
      array.highlightCell(l, r, null, null);

    } else {
      code.unhighlight("check1");
    }
    array.unhighlightCell(l, r, null, null);

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

    lang.finalizeGeneration();
    // System.err.println(lang.toString());
    return lang.toString();
  }

  public String getAlgorithmName() {
    return "Quicksort";
  }

  public String getAnimationAuthor() {
    return "Krasimir Markov";
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

  // private void check(int i) {
  // if(random1 == i) {
  // random1++;
  // if(random1 > array.getLength() - 1) random1 = 0;
  // }
  // if(random1 == random2) {
  // random2++;
  // if(random2 > array.getLength() - 1) random2 = 0;
  // }
  // if(random2 == i) {
  // random2++;
  // if(random2 > array.getLength() - 1) random2 = 0;
  // }
  // if(random1 == random2) {
  // random2++;
  // if(random2 > array.getLength() - 1) random2 = 0;
  // }
  // }
}
