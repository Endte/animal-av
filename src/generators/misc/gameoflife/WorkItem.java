package generators.misc.gameoflife;

import util.StringMatrixExtended;

public class WorkItem {
	
	public final int originalMatrixRows;
	
	public final StringMatrixExtended matrix;
	
	public final int rowNumber;
	
	public WorkItem(StringMatrixExtended matrix, int rowNumber, int originalMatrixRows){
		this.matrix = matrix;
		this.rowNumber = rowNumber;
		this.originalMatrixRows = originalMatrixRows;
	}
	
	@Override
	public String toString() {
		return matrix.toString();
	}
}
