package uninova.cts.arrowhead.owlmatcher.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.xml.sax.SAXException;

import uninova.cts.arrowhead.owlmatcher.SemanticEngine;
import uninova.cts.arrowhead.owlmatcher.utils.TextAreaOutputStream;
import uninova.cts.arrowhead.owlmatcher.utils.TextPaneOutputStream;

import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class OwlMatcherGui {

	private JFrame frmIntelligentSemanticTranslator;
	private JLabel labelSourceXsd = new JLabel("<Load Source XSD to match>");
	private JLabel labelTargetXsd = new JLabel("<Load Target XSD to match>");
	private JTextPane txtPaneLog = new JTextPane();
	private JTextArea textAreaLog = new JTextArea();
	private JButton btnOntology = new JButton("Ontology");
	private JLabel labelOntology = new JLabel("<Load Ontology>");
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmExit = new JMenuItem("Exit");
	private JButton btnRun = new JButton("Run");

	private JButton btnSourceXsd = new JButton("Source XSD");
	private JButton btnTargetXsd = new JButton("Target XSD");
	
	private String helloTest = "Hello test";
	
	private String consumerXSDlocation = "";
	private String providerXSDlocation = "";
	private String ontologyLocation = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OwlMatcherGui window = new OwlMatcherGui();
					window.frmIntelligentSemanticTranslator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OwlMatcherGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIntelligentSemanticTranslator = new JFrame();
		frmIntelligentSemanticTranslator.setTitle("Intelligent Semantic Translator");
		frmIntelligentSemanticTranslator.setBounds(100, 100, 963, 651);
		frmIntelligentSemanticTranslator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIntelligentSemanticTranslator.getContentPane().setLayout(null);
		
		// JButton
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				TextAreaOutputStream txtAOutStream = new TextAreaOutputStream(textAreaLog, "");
				PrintStream pst = new PrintStream(txtAOutStream, true);
				
				pst.print("A Did i made it?\n");
//				pst.flush();

				pst.print("B Did i made it?\n");
//				pst.flush();
				pst.print("C Example: Did i made it 2?\n");
//				pst.flush();
				
				
				PrintStream pst2 = new PrintStream(new TextPaneOutputStream(txtPaneLog, ""), true);
				pst2.println("D Did i made it?");
				pst2.println("E Example: again?");
				
				
				PrintStream psTest = new PrintStream(System.out);
				psTest.print("hello world");
				psTest.print("hello world");
				psTest.flush();
				psTest.println();
				psTest.print("This is an example");
				psTest.flush();
				
//				SemanticEngine se = new SemanticEngine(txtAOutStream);
//				se.printSemanticAnnotatedElements(providerXSDlocation, consumerXSDlocation, ontologyLocation);
//				try {
//					se.run();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
		btnRun.setBounds(51, 152, 97, 25);
		frmIntelligentSemanticTranslator.getContentPane().add(btnRun);
		
		// JButton
		btnSourceXsd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				providerXSDlocation = chooseFile(labelSourceXsd);
			}
		});
		btnSourceXsd.setBounds(24, 45, 150, 25);
		frmIntelligentSemanticTranslator.getContentPane().add(btnSourceXsd);
		
		// JButton
		btnTargetXsd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consumerXSDlocation = chooseFile(labelTargetXsd);
			}
		});
		
		// JButton
		btnTargetXsd.setBounds(24, 78, 150, 25);
		frmIntelligentSemanticTranslator.getContentPane().add(btnTargetXsd);
		
		// JLabel
		labelTargetXsd.setBounds(197, 80, 736, 21);
		frmIntelligentSemanticTranslator.getContentPane().add(labelTargetXsd);
		btnOntology.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ontologyLocation = chooseFile(labelOntology);
			}
		});
		
		// JButton
		btnOntology.setBounds(24, 114, 150, 25);
		frmIntelligentSemanticTranslator.getContentPane().add(btnOntology);
		
		// JLabel
		labelSourceXsd.setBounds(197, 49, 736, 21);
		frmIntelligentSemanticTranslator.getContentPane().add(labelSourceXsd);
		
		// JLabel
		labelOntology.setBounds(197, 116, 736, 21);
		frmIntelligentSemanticTranslator.getContentPane().add(labelOntology);
		
		// JMenuBar
		menuBar.setBounds(0, 0, 945, 26);
		frmIntelligentSemanticTranslator.getContentPane().add(menuBar);
		
		// JMenu
		menuBar.add(mnFile);
		
		// JMenuItem
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JScrollPane scrollPane = new JScrollPane(textAreaLog);
		scrollPane.setBounds(32, 204, 888, 374);
//		textAreaLog.add(scrollPane, BorderLayout.CENTER);
//		scrollPane.setSize(240, 240);
//		scrollPane.setLocationByPlatform(true);
		scrollPane.setVisible(true);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frmIntelligentSemanticTranslator.getContentPane().add(scrollPane);
		// JTextPane
		txtPaneLog.setBounds(720, 130, 200, 67);
		frmIntelligentSemanticTranslator.getContentPane().add(txtPaneLog);
		
		//JTextArea
		textAreaLog.setBounds(51, 210, 906, 241);
//		frmIntelligentSemanticTranslator.getContentPane().add(textAreaLog);
	}

	private String chooseFile(JLabel labelText) {
		String resultLocation = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(frmIntelligentSemanticTranslator);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			labelText.setText(selectedFile.getPath());
			resultLocation = selectedFile.getPath();
		}
		
		return resultLocation;
	}
}
