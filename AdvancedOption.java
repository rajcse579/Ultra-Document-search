/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UltraDocumentSearch;

/**
 *
 * @author Saikumar-Kabaaliraaa
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdvancedOption extends JFrame implements ActionListener{
    
    private JCheckBox caseSensitive;
    private JLabel andSearchLabel;
    private JTextField andSearchWords;
    
    private String andText="";
    private boolean isCaseSensitive=false;
    private TextField parentSearchWord;
    private JButton ok,cancel;
    private JButton search;
    public AdvancedOption(){
    	
    }
    public AdvancedOption(String andText,boolean isCaseSensitive,TextField searchWord,JButton search){
        this.andText = andText;
        this.isCaseSensitive = isCaseSensitive;
        this.parentSearchWord = searchWord;
        this.search = search;
        Container frontPanel = getContentPane();
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
        
        JPanel caseSPanel = new JPanel(new FlowLayout());
        caseSensitive = new JCheckBox("Case Sensitive",isCaseSensitive);
        caseSPanel.add(caseSensitive);
        jp.add(caseSPanel);
        
        JPanel andLabelPanel = new JPanel(new FlowLayout());
        andSearchLabel = new JLabel("Enter multiple words to search in files:(Use delimiter '#' between search words)");
        andSearchLabel.setFont(new Font("Helvetica",Font.BOLD,13));
        andLabelPanel.add(andSearchLabel);
        jp.add(andLabelPanel);
        
        JPanel andTextPanel = new JPanel(new FlowLayout());
        andSearchWords = new JTextField(andText,30);
        andSearchWords.setFont(new Font("Helvetica",Font.BOLD,15));
        andSearchWords.setToolTipText("Enter multiple words to search and they must separated by '#'. If a document has all words, then only result will have that document name");
        andTextPanel.add(andSearchWords);
        jp.add(andTextPanel);
        
        JPanel okOrCancelPanel = new JPanel(new GridLayout(1,2,300,0));
        cancel = new JButton("Cancel");
        cancel.setToolTipText("Changes will not be saved!");
        cancel.addActionListener(this);
        okOrCancelPanel.add(cancel);
        
        ok = new JButton("Ok");
        ok.setToolTipText("Click to SAVE changes!!!");
        ok.addActionListener(this);
        okOrCancelPanel.add(ok);
        
        jp.add(okOrCancelPanel);
        
        
        frontPanel.add(jp);
        this.setBounds(100, 100, 520, 200);
        setResizable(false);
        setTitle("Advanced Options");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand().equals("Ok")){
            setAndText(andSearchWords.getText().trim());
            setIsCaseSensitive(caseSensitive.isSelected());
            System.out.println(andSearchWords.getText()+"--"+caseSensitive.isSelected());
            
            search.setEnabled(true);
        }
        if(!getAndText().trim().equals(""))
            this.parentSearchWord.setEditable(false);
        else
            this.parentSearchWord.setEditable(true);
        this.dispose();
    }
    public void setAndText(String str){
        this.andText = str;
    }
    public void setIsCaseSensitive(boolean isCaseSensitive){
        this.isCaseSensitive = isCaseSensitive;
    }
    public String getAndText(){
        return this.andText;
    }
    public boolean getIsCaseSensitive(){
        return this.isCaseSensitive;
    }
}
