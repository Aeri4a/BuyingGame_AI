package put.ai;

import javax.swing.*;
import java.awt.*;

public class LayoutUI extends JFrame {
	private static final long serialVersionUID = 1L;

	public LayoutUI() {
		super("BuyingGame");
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		Container container = getContentPane();
		container.setBackground(new Color(27,27,27));
		
		
		displayStartView();
		setVisible(true);
	}
	
	private void displayStartView() {
		
	}
}
