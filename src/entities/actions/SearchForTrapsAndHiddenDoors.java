package entities.actions;

public class SearchForTrapsAndHiddenDoors extends Action {

	private static final long serialVersionUID = -8442030273252018701L;
	
	protected byte sourceRow;
	protected byte sourceColumn;
	
	public byte getSourceRow() {
		return sourceRow;
	}

	public void setSourceRow(byte sourceRow) {
		this.sourceRow = sourceRow;
	}

	public byte getSourceColumn() {
		return sourceColumn;
	}

	public void setSourceColumn(byte sourceColumn) {
		this.sourceColumn = sourceColumn;
	}
}
