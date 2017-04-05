package uninova.cts.arrowhead.owlmatcher.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UninovaFileFactory {

	public UninovaFileFactory() {
	}

	protected String _fileName = "";
	protected String _fileLocation = "";
	protected String _fileExtension = ".txt";
	
	protected FileOutputStream fis = null;
	protected PrintStream out = null;
	protected File file = null;
    
	public UninovaFileFactory(String location, String filename) {
		if ( location.endsWith("/") || location.endsWith("\\") ) {
			this._fileLocation = location;
		} else {
			if (location.contains("\\")){
				this._fileLocation = location+"\\";
			}
			else if (location.contains("/")){
				this._fileLocation = location+"/";
			} else {
				this._fileLocation = location;
			}
			
		}
//		this._fileLocation = location;
		this._fileName = filename;
	}
	
	public UninovaFileFactory(String location, String filename, String fileExtension) {
		this(location, filename);
		this.setFileExtension(fileExtension);
	}
	
	public void saveToFile(String msg){
		this.openFile(_fileLocation+_fileName+getFileExtension(), true);

		this.writeToFile(msg);
		this.closeFile();
	}
	
	public void saveToFile(String msg, String fileName){
		this.openFile(_fileLocation+fileName+getFileExtension(), true);
		this.writeToFile(msg);
		this.closeFile();
	}	
	
	private void writeToFile(String msg){
		this.out.println(msg);
	}


    protected void openFile(String fileName, boolean append){
        
    	if ( (file == null) || (!file.getPath().equals(fileName)) )
    		file = new File(fileName);
    
        this.fis = null;
        try {
        	if (file.exists())
        		this.fis = new FileOutputStream(file, append);
        	else
        		this.fis = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UninovaFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.out = new PrintStream(this.fis);
    };
    
    protected void closeFile() {
        try {
             this.out.close();
             this.fis.close();
         } catch (IOException ex) {
             Logger.getLogger(UninovaFileFactory.class.getName()).log(Level.SEVERE, null, ex);
         }
     };
    

 	public String getFileExtension() {
		return _fileExtension;
	}

	public void setFileExtension(String _fileExtension) {
		this._fileExtension = _fileExtension;
	}

	public static void main(String[] args) {
		
		UninovaFileFactory ls = new UninovaFileFactory("","xpto");
		
		// ls._logFolder = "C:\\Users\\Luis\\Documents\\Uninova\\ProaSense\\workspace\\logs\\";
		// ls._logFileName = "logTest.log";
		
		ls.saveToFile("Hello");
		
		ls.saveToFile("Hello", "xpto2");
		
		ls.saveToFile("Hello");
	}

}
