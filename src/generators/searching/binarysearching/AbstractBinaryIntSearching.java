/*
 * Created on 18.06.2007 by Guido Roessling (roessling@acm.org>
 */
package generators.searching.binarysearching;

import generators.framework.Generator;
import generators.searching.helpers.AbstractIntSearchingAlgorithm;

import java.util.Locale;

public class AbstractBinaryIntSearching extends AbstractIntSearchingAlgorithm
    implements Generator {

  public AbstractBinaryIntSearching(String aResourceName, Locale aLocale) {
    super(aResourceName, aLocale);
  }

  public String getAlgorithmName() {
    return translator.translateMessage("algoName");
  }
}