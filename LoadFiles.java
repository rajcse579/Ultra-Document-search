/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UltraDocumentSearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author saikumar-kabaaliraaa
 */
public class LoadFiles implements Runnable{
    Thread loadFileThread;
    private FrontInterface mainFrame;
    private JPanel result;
    private File file;
    String saveText = "";
    boolean isDeep;
    private int resultFileCount = 0;
    public LoadFiles(FrontInterface mainFrame,JPanel result,File file,boolean isDeep){
        this.mainFrame = mainFrame;
        this.result = result;
        this.file = file;
        this.isDeep = isDeep;
        this.loadFileThread = new Thread(this,"load files");
        this.loadFileThread.start();
    }
    @Override
    public void run() {
        if(isDeep){
             saveText = interDFSCall(file);
        }
        else{
            loadWithin();
        }
        result.repaint();
        mainFrame.search.setEnabled(true);
        System.out.println("saikumar");
        mainFrame.setVisible(true);
        mainFrame.setSaveTextFromLoadFiles(saveText);
        JOptionPane.showMessageDialog(null,"File Loading Completed!!!","File Loading",JOptionPane.WARNING_MESSAGE);
    }

    private String interDFSCall(File file) {
        String names[] = file.list();
        String list="";
        for(String i:names){
            System.out.println(i);
            try{
                File dorf = new File(file.getPath()+"\\"+i);
                if(dorf.isDirectory()){
                    list = list+interDFSCall(dorf);
                }
                else{
                    if(isReadable(i)){
                        list+=file.getPath()+"\\"+i+"\n";
                        result.add(new URLLabel(file.getPath()+"\\"+i,file.getPath()+"\\"+i));
                        result.repaint();
                        resultFileCount++;
                    }
                }
                mainFrame.resultLabel.setText("No. of Results : "+resultFileCount);
                mainFrame.setVisible(true);
            }
            catch(Exception e){
                //JOptionPane.showMessageDialog(null,"Some Errors Occured during file reading!!!","File Read Error",JOptionPane.WARNING_MESSAGE);
            }
        }
        return list;
    }

    private void loadWithin() {
        String names[] = file.list();
        String list="";
        for(String i:names){
            File dorf = new File(file.getPath()+"\\"+i);
            if(!(dorf.isDirectory()) && isReadable(i)){
                System.out.println(i);
                list+=file.getPath()+"\\"+i+"\n";
                result.add(new URLLabel(file.getPath()+"\\"+i,file.getPath()+"\\"+i));
                result.repaint();
                resultFileCount++;
            }
            mainFrame.resultLabel.setText("No. of Results : "+resultFileCount);
            mainFrame.setVisible(true);
        }
        saveText = list;
    }
    private boolean isReadable(String fileName){
        String fileType = "Undetermined";
        final File file = new File(fileName);
        try{
          fileType = Files.probeContentType(file.toPath());
        }
        catch (IOException ioException){
          System.out.println("ERROR: Unable to determine file type for " + fileName+ " due to exception " + ioException);
        }
        System.out.println(fileType);
        if(fileType==null){
                fileName = fileName.toLowerCase();
                if(fileName.endsWith(".java") || fileName.endsWith(".c") || fileName.endsWith(".c") || fileName.endsWith(".js") || fileName.endsWith(".css") || fileName.endsWith(".py"))
                return true;
                else return false;
        }
        String s[] = fileType.split("/");
        if(s[0].equalsIgnoreCase("text"))
        return true;
        else if(s[1].equalsIgnoreCase("pdf"))
        return true;
        else return false;
    }
}
