package uninova.cts.arrowhead.owlmatcher.translations;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import uninova.cts.arrowhead.owlmatcher.utils.file.UninovaFileFactory;

public class XSLTFileFactory extends UninovaFileFactory {


	public XSLTFileFactory() {
	}
	
	public XSLTFileFactory(String location, String filename) {
		super(location, filename, ".xslt");
	}
	
	
	public void saveToFile(Document xmlDoc, String fileName, boolean append){
		System.out.println("Saving file <"+fileName+_fileExtension+"> to <"+_fileLocation+">");
		super.openFile(_fileLocation+fileName+_fileExtension, append);
		this.writeToFile(xmlDoc);
		this.closeFile();
	}

	public void saveToFile(Document xmlDoc, boolean append){
		System.out.println("Saving file <"+this._fileName+_fileExtension+"> to <"+_fileLocation+">");
		super.openFile(_fileLocation+this._fileName+_fileExtension, append);
		this.writeToFile(xmlDoc);
		this.closeFile();
	}

	private void writeToFile(Document xmlDoc){
		//JDOM document is ready now, lets write it to file now
	    XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
	    try {
			xmlOutputter.output(xmlDoc, this.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		XSLTFileFactory ls = new XSLTFileFactory("","xpto");
		
		// ls._logFolder = "C:\\Users\\Luis\\Documents\\Uninova\\ProaSense\\workspace\\logs\\";
		// ls._logFileName = "logTest.log";
		
		ls.saveToFile("Hello");
		
		ls.saveToFile("Hello", "xpto2");
		
		ls.saveToFile("Hello");
	}
}
