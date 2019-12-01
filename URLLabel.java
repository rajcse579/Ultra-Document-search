/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UltraDocumentSearch;

/**
 *
 * @author saikumar-kabaaliraaa
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class URLLabel extends JLabel {

    private String url;
    
    public URLLabel(String label, String url) {
        super(label);
        this.url = url;
        //this is used to underline the text
        Font font = this.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.setFont(font.deriveFont(attributes));
        setForeground(Color.BLUE.darker());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new URLOpenAdapter());
    }

    private class URLOpenAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(new File(url));
                } catch (IOException t) {
                    JOptionPane.showMessageDialog(null,"Some Error here in File Opening!!!","File Open Error",JOptionPane.WARNING_MESSAGE);
                }
            }
            else
                JOptionPane.showMessageDialog(null,"Your system doesn't support this feature!!!","Cannot Open File",JOptionPane.WARNING_MESSAGE);
        }
    }
    
}