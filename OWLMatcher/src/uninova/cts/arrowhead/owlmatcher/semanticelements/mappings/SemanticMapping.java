package uninova.cts.arrowhead.owlmatcher.semanticelements.mappings;

import javax.xml.transform.TransformerFactory;

import uninova.cts.arrowhead.owlmatcher.SemanticAnnotatedElement;

public class SemanticMapping {
	
	private SemanticAnnotatedElement _sourceSemAnnotElem;
	private SemanticAnnotatedElement _targetSemAnnotElem;
	private String _sourceSemXmlPath;
	private double SemanticSimilarityWeight;
	
	public SemanticMapping() {
		// TODO Auto-generated constructor stub
		
	}
	public SemanticAnnotatedElement getSourceSemAnnotElem() {
		return _sourceSemAnnotElem;
	}
	public void setSourceSemAnnotElem(SemanticAnnotatedElement sourceSemAnnotElem) {
		this._sourceSemAnnotElem = sourceSemAnnotElem;
	}
	public SemanticAnnotatedElement getTargetSemAnnotElemt() {
		return _targetSemAnnotElem;
	}
	public void setTargetSemAnnotElem(SemanticAnnotatedElement targetSemAnnotElem) {
		this._targetSemAnnotElem = targetSemAnnotElem;
	}

	public void setMapping(SemanticAnnotatedElement sourceSemAnnotElem, SemanticAnnotatedElement targetSemAnnotElem){
		this._sourceSemAnnotElem = sourceSemAnnotElem;
		this._targetSemAnnotElem = targetSemAnnotElem;
		
	}
	public String getSourceSemXmlPath() {
		return _sourceSemXmlPath;
	}
	public void setSourceSemXmlPath(String sourceSemXmlPath) {
		if (sourceSemXmlPath.endsWith("/"))
			this._sourceSemXmlPath = sourceSemXmlPath.substring(0, sourceSemXmlPath.length()-1);
		else
			this._sourceSemXmlPath = sourceSemXmlPath;
	}
}
