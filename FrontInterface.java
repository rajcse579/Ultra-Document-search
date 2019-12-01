package UltraDocumentSearch;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class FrontInterface extends JFrame implements ActionListener,ItemListener,MouseListener{
    private TextField dispPath;
    private JTextArea descriptionText;
    private TextField searchWord;
    JRadioButton lSearch,dSearch;
    private ButtonGroup bg;
    private String saveText = "";
    JButton search;
    JButton browse;
    JLabel resultLabel;
    AdvancedOption advanceOptions;
    private SearchFiles searchThread = null;
    private LoadFiles loadFiles = null;
    private JPanel frontResultpanel;
    private JButton advancedOpt;
    private String dispPathDesc = "The selected path will be displayed here";
    private String descriptionTextDesc = "Explains about components in this Ultra Document Search";
    private String searchWordDesc = "You can enter a word or sentence, to search in the documents, which are in your selected target directory";
    private String lSearchDesc = "If you select this Lateral Search button, it will perform search in documents which are in the target directory only";
    private String dSearchDesc = "If you select this Deep Search button, it will perform search in documents which are in sub-directories and sub-sub-directories of this target directory";
    private String searchDesc = "Searching will start if you click this button. If a document contains the search word, then that document path will be displayed in result along with a number,i.e no.of times that word is present in particular document";
    private String browseDesc = "You can choose a target directory,from which you want to perform search on documents";
    private String advancedOptDesc = "Click to open more advanced options that provides more features to perform constrained search";
    public FrontInterface(){
        Container frontPanel = getContentPane();
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
        
        JPanel jpSelect = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel sDir = new JLabel("Select Target Directory :   ");
        sDir.setFont(new Font("Helvetica",Font.BOLD,15));
        jpSelect.add(sDir);
        
        dispPath = new TextField("you didn't choose any path",60);
        dispPath.setFont(new Font("Helvetica",Font.BOLD,15));
        dispPath.setEditable(false);
        dispPath.addMouseListener(this);
        jpSelect.add(dispPath);
        
        browse = new JButton("Browse...");
        browse.addMouseListener(this);
        browse.addActionListener(this);
        browse.setToolTipText("Select some Directory...");
        jpSelect.add(browse);
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lSearch = new JRadioButton("Lateral Search",true);
        lSearch.setFont(new Font("Helvetica",Font.BOLD,15));
        lSearch.setToolTipText("It searches with in this directory only!");
        lSearch.addMouseListener(this);
        lSearch.addItemListener(this);
        dSearch = new JRadioButton("Deep Search");
        dSearch.setFont(new Font("Helvetica",Font.BOLD,15));
        dSearch.setToolTipText("It searches through all sub directories also!!!");
        dSearch.addMouseListener(this);
        
        bg = new ButtonGroup();
        bg.add(lSearch);
        bg.add(dSearch);
        
        JPanel advancedOptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        advancedOpt = new JButton("Advanced Options");
        advancedOpt.addMouseListener(this);
        advancedOpt.addActionListener(this);
        advancedOptPanel.add(advancedOpt);
        
        radioPanel.add(lSearch);
        radioPanel.add(dSearch);
        radioPanel.add(advancedOptPanel);
        
        JPanel jpSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel eSearch = new JLabel("Enter Search Word :           ");
        eSearch.setFont(new Font("Helvetica",Font.BOLD,15));
        jpSearch.add(eSearch);
        
        searchWord = new TextField("",60);
        searchWord.setFont(new Font("Helvetica",Font.BOLD,15));
        searchWord.addMouseListener(this);
        jpSearch.add(searchWord);
        
        search = new JButton("Search...");
        search.addMouseListener(this);
        search.addActionListener(this);
        search.setToolTipText("Perform Search Here!!!");
        jpSearch.add(search);
        
        
        
        JPanel resPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resultLabel = new JLabel("No. of Results : 0");
        resultLabel.setFont(new Font("Helvetica",Font.BOLD,15));
        resPanel.add(resultLabel);
        
        JPanel backResultPanel= new JPanel();
        frontResultpanel = new JPanel();
        //frontResultpanel.setBackground(Color.red);
        frontResultpanel.setLayout(new BoxLayout(frontResultpanel,BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(frontResultpanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 890, 435);
        JPanel contentPane = new JPanel(null);
        //contentPane.setBackground(Color.blue);
        contentPane.setPreferredSize(new Dimension(780, 435));
        contentPane.add(scrollPane);
        backResultPanel.add(contentPane);
        
        
        jp.add(jpSelect);
        jp.add(radioPanel);
        jp.add(jpSearch);
        jp.add(resPanel);
        
        jp.add(backResultPanel);
        //jp.add(scr);
        
        
        descriptionText = new JTextArea();
        descriptionText.setEditable(false);
        descriptionText.setLineWrap(true);
        descriptionText.setFont(new Font("Helvetica",Font.BOLD,15));
        advanceOptions = new AdvancedOption();
        
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jp,descriptionText);
        jsp.setDividerLocation(800);
        frontPanel.add("Center", jsp);
        jsp.setEnabled(false);
        
        setSize(1100,600);
        setResizable(false);
        setTitle("Ultra Document Search");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String a[]){
        new FrontInterface();
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Advanced Options")){
            advanceOptions = new AdvancedOption(advanceOptions.getAndText(),advanceOptions.getIsCaseSensitive(),searchWord,search);
        }
        else if(e.getActionCommand().equals("Browse..."))
            this.openDirectory();
        else if(((searchWord.isEditable() && !(searchWord.getText().trim().isEmpty()))||(!(advanceOptions.getAndText().trim().isEmpty()))) && !saveText.isEmpty()){
            search.setEnabled(false);
            frontResultpanel.removeAll();
            frontResultpanel.repaint();
            this.setVisible(true);
            if(!(searchThread==null))
            	searchThread.stopAllThreads();
            searchThread = new SearchFiles(search,searchWord,saveText,frontResultpanel,this);
            searchThread.performSearch();
        }
            
    }
    private void openDirectory(){
        JFileChooser directory = new JFileChooser();
        directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = directory.showOpenDialog(this);
        if(i == JFileChooser.APPROVE_OPTION){
            if(searchThread != null && searchThread.isAnyThreadAlive()){
                searchThread.stopAllThreads();
            }
            if(loadFiles != null && loadFiles.loadFileThread.isAlive()){
                loadFiles.loadFileThread.stop();
            }
            saveText = "";
            search.setEnabled(false);
            frontResultpanel.removeAll();
            frontResultpanel.repaint();
            this.setVisible(true);
            System.out.println("removed");
            File f = directory.getSelectedFile();
            String selectedPath = f.getPath();
            dispPath.setText(selectedPath);
            if(lSearch.isSelected())
                displayFileAndDirectoriesWithinPath(dispPath.getText());
            else
                displayFileAndDirectoriesThroughoutPath(dispPath.getText());
        }
        
    }
     @Override
    public void itemStateChanged(ItemEvent e) {
        //System.out.println("sai");
        if(!dispPath.getText().trim().equalsIgnoreCase("you didn't choose any path")){
            if(searchThread != null && searchThread.isAnyThreadAlive()){
                searchThread.stopAllThreads();
            }
            if(loadFiles != null && loadFiles.loadFileThread.isAlive()){
                loadFiles.loadFileThread.stop();
            }
            saveText="";
            search.setEnabled(false);
            frontResultpanel.removeAll();
            frontResultpanel.repaint();
            this.setVisible(true);
            if(lSearch.getModel().isSelected())
                displayFileAndDirectoriesWithinPath(dispPath.getText());
            else
                displayFileAndDirectoriesThroughoutPath(dispPath.getText());
        }
    }
    
    //for lateral search
    private void displayFileAndDirectoriesWithinPath(String selectedPath) {
        File file = new File(selectedPath);
        loadFiles = new LoadFiles(this,frontResultpanel,file,false);
        frontResultpanel.repaint();
        this.setVisible(true);
    }
    //for deep search
    private void displayFileAndDirectoriesThroughoutPath(String selectedPath) {
        File file = new File(selectedPath);
        loadFiles = new LoadFiles(this,frontResultpanel,file,true);
        frontResultpanel.repaint();
        this.setVisible(true);
    }
    public void setSaveTextFromLoadFiles(String saveText){
        this.saveText = saveText;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	if(e.getSource().equals(browse))
    		this.openDirectory();
    	else if(e.getSource().equals(dispPath))
    		this.openDirectory();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    	if(e.getSource().equals(search)){
    		descriptionText.setText(searchDesc);
    	}
    	else if(e.getSource().equals(lSearch)){
    		descriptionText.setText(lSearchDesc);
    	}
    	else if(e.getSource().equals(dSearch)){
    		descriptionText.setText(dSearchDesc);
    	}
    	else if(e.getSource().equals(dispPath)){
    		descriptionText.setText(dispPathDesc);
    	}
    	else if(e.getSource().equals(descriptionText)){
    		descriptionText.setText(descriptionTextDesc);
    	}
    	else if(e.getSource().equals(advancedOpt)){
    		descriptionText.setText(advancedOptDesc);
    	}
    	else if(e.getSource().equals(searchWord)){
    		descriptionText.setText(searchWordDesc);
    	}
    	else if(e.getSource().equals(browse)){
    		descriptionText.setText(browseDesc);
    	}
    }

    @Override
    public void mouseExited(MouseEvent e) {
    	
    }

    
}
/*

how to open a file.

import java.util.Desktop;
Desktop.getDesktop().open(File obj);

*/