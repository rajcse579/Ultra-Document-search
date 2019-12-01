package UltraDocumentSearch;

import java.awt.*;
import javax.swing.*;
public class UDSMain extends JFrame{
	private JLabel loading;
	public UDSMain(){
		//ImageIcon icon = new ImageIcon("C:\\Users\\hp\\Desktop\\My Project work\\UDS.png");
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("UDS.png").getImage().getScaledInstance(460, 350, Image.SCALE_DEFAULT));
		loading = new JLabel(imageIcon);
		add(loading);
		
		this.setBounds(500, 200, 460, 350);
		setUndecorated(true);
        setResizable(false);
        setVisible(true);
        
	}
	
	public static void main(String[] args) {
		
		UDSMain uds = new UDSMain();
		
	}

}
