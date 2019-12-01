package UltraDocumentSearch;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main implements Runnable{

	public static void main(String[] args) {
		
		Thread mainThread = new Thread(new Main(),"UDS Thread");
		mainThread.start();
	}
	
	public void run(){
		UDSMain uds = new UDSMain();
		try{
			Thread.currentThread().sleep(2500);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error occured in Starting UDS!!!","Error",JOptionPane.WARNING_MESSAGE);
		}
		uds.setVisible(false);
		FrontInterface frontLook = new FrontInterface();
		ImageIcon icon = new ImageIcon("FINAL.jpg");
		frontLook.setIconImage(icon.getImage());
	}

}
