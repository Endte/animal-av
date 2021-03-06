package generators.generatorframe.loading;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

import animal.gui.AnimalStartUpProgress;
import animal.main.Animal;
import generators.framework.Generator;
import generators.framework.GeneratorType;
import generators.generatorframe.store.SaveInfos;
import generators.generatorframe.store.SearchLoader;

/**
 * 
 * @author Nora Wester
 *
 */

public class GeneratorLoader {
	
	private static boolean checkedOnce = false;

	static int[] categorySizes;
	static String[] categoryNames;
	static SaveInfos sI;
	static int totalGeneratorsNumber = 0;
	
	public GeneratorLoader(){
		if(!checkedOnce){
		    if(Animal.ProgressPanel!=null) Animal.ProgressPanel.addTextWithPercentStart(AnimalStartUpProgress.PROGRESS_LABEL_LoadGenerators);
			int[] category = GeneratorType.getTypes();
			categoryNames = new String[category.length];
			for(int i=0; i<categoryNames.length; i++){
				categoryNames[i] = GeneratorType.getStringForType(category[i]);
			}
			
			categorySizes = new int[category.length];
			
			sI = SaveInfos.getInstance();
			
			fillSearchInto();
			
			checkedOnce = true;
		    if(Animal.ProgressPanel!=null) Animal.ProgressPanel.addTextWithPercentEnd(AnimalStartUpProgress.PROGRESS_LABEL_LoadGenerators);
		}
	}
	
//GR
 private String generateChainPathName(Generator g) {
   StringBuilder sb = new StringBuilder(256);
//   sb.append('/').append(g.getContentLocale().getLanguage()).append('/');
//   sb.append(g.getOutputLanguage()).append('/');
//   sb.append(GeneratorType.getStringForType(g.getGeneratorType().getType())).append('/');
//   sb.append(g.getAlgorithmName()).append('/');
//   sb.append(g.getName().replaceAll(" ", "_"));
//   System.out.println(sb.toString());
   sb.append('/');
   sb.append(GeneratorType.getStringForType(g.getGeneratorType().getType())).append('/');
   sb.append(g.getAlgorithmName()).append('/');
   sb.append(g.getOutputLanguage()).append('/');
   sb.append(g.getContentLocale().getLanguage()).append('/');
   sb.append(g.getName().replaceAll(" ", "_"));
//   System.out.println(sb.toString());
   return sb.toString();
 }
	
	private void fillSearchInto() {
	    int ctr = 1;
	 // java.util.HashMap<String,Integer> hm = GeneratorsMap.generatorMap;
		for(int i=0; i<categoryNames.length; i++){
			Loader load = new Loader(categoryNames[i].toLowerCase());
			Vector<Generator> generators = load.getGeneratorList();
			for(int j=0; j<generators.size(); j++){
				Generator temp = generators.get(j);
				String s = generateChainPathName(temp);
				sI.addValue(temp, totalGeneratorsNumber, s);
				totalGeneratorsNumber++;
				/* ****GR
				//GR
				System.err.println(ctr +";\"" +temp.getAlgorithmName() +"\";\"" + temp.getAnimationAuthor() +"\";\"" + 
						   GeneratorType.getStringForType(temp.getGeneratorType().getType()) +"\"");
				ctr++;
				*/

}
//			this.generators.addAll(generators);
			
			//speichern der Generatoranzahl dieser Kategorie
			categorySizes[i] = generators.size();
			
		//	totalGeneratorsNumber = totalGeneratorsNumber + categorySizes[i];

			if (Animal.ProgressPanel != null) {
	        	Animal.ProgressPanel.addTextWithPercent("..." + categoryNames[i].toLowerCase() + " ["+categorySizes[i]+"]", Animal.ProgressPanel.getTextPercentRange(AnimalStartUpProgress.PROGRESS_LABEL_LoadGenerators).getPERCENT_FromStates(categoryNames.length, i));
	        }
		}
		
		SearchLoader sL = SearchLoader.getInstance();
		sL.init(categorySizes, categoryNames);
		sL.setSelectedGeneratorIndexes(new LinkedList<Integer>());
		System.err.println("#" + totalGeneratorsNumber);
	}
	
	public HashMap<String, Vector<String>> getContentAuthors() {
		return sI.getContentAuthors();
	}
	
	public int getTotalNumber(){
		return totalGeneratorsNumber;
	}
}
