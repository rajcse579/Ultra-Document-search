/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UltraDocumentSearch;

import java.awt.TextField;
import java.io.IOException;
import java.util.zip.DataFormatException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author saikumar-kabaaliraaa
 */
public class SearchFiles implements Runnable{
    Thread mainSearchThread,childSearchThread1,childSearchThread2;
    private JButton search;
    private TextField searchWord;
    private JPanel result;
    private String saveText;
    private FrontInterface mainFrame;
    private int resultFileCount = 0;
    private String keyword[],paths[];
    
    private int complexIndex = -1;
    private boolean isExceptionOccur = false;
    public SearchFiles(JButton jb,TextField tf,String st,JPanel ta,FrontInterface mainFrame){
        mainSearchThread = new Thread(this,"main");
        childSearchThread1 = new Thread(this,"child1");
        childSearchThread2 = new Thread(this,"child2");
        search = jb;
        searchWord = tf;
        saveText = st;
        result = ta;
        this.mainFrame = mainFrame;
        if(searchWord.isEditable()){
        	keyword = new String[1];
        	keyword[0] = searchWord.getText().trim();
        }
        else{
        	keyword = mainFrame.advanceOptions.getAndText().split("#");
        }
        paths = saveText.split("\n");
    }
    public void performSearch(){
        result.removeAll();
        result.repaint();
        startAllThreads();
        //this.notify();
    }
    public void startAllThreads(){
    	mainSearchThread.start();
        childSearchThread1.start();
        childSearchThread2.start();
        System.out.print("all threads started!!");
    }
    public void stopAllThreads(){
        mainSearchThread.stop();
        childSearchThread1.stop();
        childSearchThread2.stop();
    }
    public boolean isAnyThreadAlive(){
        if(mainSearchThread.isAlive() || childSearchThread1.isAlive() || childSearchThread2.isAlive())
            return true;
        else return false;
    }
    private synchronized int getNextComplexIndex(){
        complexIndex++;
        if(complexIndex>=paths.length)
            return -1;
        
        return complexIndex;
    }
    @Override
    public void run() {
        ReadingPDF readPDF = new ReadingPDF();
        ReadingTXT readTXT = new ReadingTXT();
        int index = getNextComplexIndex();
        while(index>=0){
            int no_of_matches=0;
            String filePath;
            synchronized(this){
                filePath = paths[index];
            }
            String suffix = filePath.charAt(filePath.length()-3)+""+filePath.charAt(filePath.length()-2)+""+filePath.charAt(filePath.length()-1);
            suffix = suffix.toLowerCase();
            try {
            	//System.out.println(filePath+"-"+keyword[0]+"--"+mainFrame.advanceOptions.getIsCaseSensitive());
                no_of_matches = (suffix.equals("pdf"))?readPDF.readPDF(filePath,keyword,mainFrame.advanceOptions.getIsCaseSensitive()):readTXT.readTXT(filePath, keyword,mainFrame.advanceOptions.getIsCaseSensitive());
                synchronized(this){
                    if(no_of_matches>-2){
                    	if(no_of_matches>0)
                        result.add(new URLLabel(filePath+"("+no_of_matches+")" ,filePath));
                        else if(no_of_matches==-1)
                        result.add(new URLLabel(filePath,filePath));
                        result.repaint();
                        resultFileCount++;
                    }
                    mainFrame.resultLabel.setText("No. of Results : "+resultFileCount);
                    mainFrame.setVisible(true);
                }
            } catch (IOException | DataFormatException | NullPointerException e){
                synchronized(this){
                    isExceptionOccur = true;
                }
            }
            index = this.getNextComplexIndex(); 
        } 
        synchronized(this){
            if((mainSearchThread.isAlive() && !childSearchThread1.isAlive() && !childSearchThread2.isAlive())
                    || (!mainSearchThread.isAlive() && childSearchThread1.isAlive() && !childSearchThread2.isAlive())
                    || (!mainSearchThread.isAlive() && !childSearchThread1.isAlive() && childSearchThread2.isAlive())){
                search.setEnabled(true);
                System.out.print("entered syn+if syn!!");
                JOptionPane.showMessageDialog(null,"Search Completed!!!","Search",JOptionPane.WARNING_MESSAGE);
                if(isExceptionOccur){
                    JOptionPane.showMessageDialog(null,"Some Errors Occured during file reading!!!","File Read Error",JOptionPane.WARNING_MESSAGE);
                }
            }
            System.out.print("leaved syn!!");
        }
        
        
    }   
}
