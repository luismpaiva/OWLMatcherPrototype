package uninova.cts.arrowhead.owlmatcher.semanticelements;

public class SemanticConcept extends SemanticElement {

	private SemanticElement _individual = null;
	
	public SemanticConcept() {
		super(SemanticElementType.CONCEPT);
	}
	
	public SemanticElement getIndividual() {
		return _individual;
	}
	public void setIndividual(SemanticElement individual) {
		this._individual = individual;
	}
	
	public boolean hasIndividual(){
		return (this._individual==null?false:true);
	}

}
