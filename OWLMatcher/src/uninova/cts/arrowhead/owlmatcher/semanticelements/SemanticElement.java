package uninova.cts.arrowhead.owlmatcher.semanticelements;

public abstract class SemanticElement {
	protected SemanticElementType _type;
	
	private String _name;

	public SemanticElement(){
		
	}
	
	public SemanticElement (SemanticElementType type){
		this._type = type;
	}
	
	public SemanticElementType getType(){
		return _type;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
}

