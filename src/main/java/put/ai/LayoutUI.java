package put.ai;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LayoutUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String FONT_NAME = "Futura";
	private final Color buttonColor = new Color(43, 44, 39);
	
	// DROOLS
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;
	
	// Main UI components
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JPanel startPanel = new JPanelWithBackground("./assets/bg.png");
	private JPanel contentPanel = new JPanelWithBackground("./assets/bg.png");
	private JPanel endPanel = new JPanelWithBackground("./assets/bg.png");
	private GridBagConstraints gbc = new GridBagConstraints();
	private Border buttonBorder = BorderFactory.createLineBorder(Color.BLACK);
	
	// Drools interactive
	private JLabel solutionText = new JLabel("");
	private JPanel answerPanel = new JPanel();
	private JLabel currentQuestion = new JLabel();

	public LayoutUI() {
		super("BuyingGame");
		initialize();
	}
	
	public void showQuestion(
			String questionContent,
			ArrayList<Answer> questionAnswers
	) {
		this.currentQuestion.setText(questionContent);

		this.answerPanel.removeAll();
		this.answerPanel.revalidate();
		
		gbc.gridy = 0;
		for (Answer answer : questionAnswers) {
			JButton button = new JButton(answer.getContent());
			button.setPreferredSize(new Dimension(400, 60));
			button.setFocusPainted(false);
			button.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
			button.setBorder(buttonBorder);
			button.setBackground(this.buttonColor);
			button.setForeground(Color.WHITE);
			button.addActionListener(e -> {
				kSession.insert(answer);
	            kSession.fireAllRules();
			});
			
			answerPanel.add(button, gbc);
			gbc.gridy++;
		}
	}
	
	public void showSolution(String solution) {
		this.solutionText.setText(solution);
		this.cardLayout.next(this.cardPanel);
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		this.startPanel.setLayout(new GridBagLayout());
		
		this.contentPanel.setLayout(new GridBagLayout());
				
		this.endPanel.setLayout(new GridBagLayout());
		JLabel solutionTextHeader = new JLabel("Game found!");
		solutionTextHeader.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.endPanel.add(solutionTextHeader, gbc);
		gbc.gridy = 1;
		this.solutionText.setFont(new Font(FONT_NAME, Font.BOLD, 20));
		this.endPanel.add(this.solutionText, gbc);
		
		this.cardLayout = new CardLayout();
		this.cardPanel = new JPanel(cardLayout);
		this.cardPanel.add(startPanel, "StartPanel");
		this.cardPanel.add(contentPanel, "ContentPanel");
		this.cardPanel.add(endPanel, "EndPanel");
		
		this.add(cardPanel, BorderLayout.CENTER);
		
		this.displayStartView();
		this.setVisible(true);
	}
	
	private void displayStartView() {
		JButton startButton = new JButton();
		startButton.setText("I WANT TO BUY A GAME!");
		startButton.setBorder(buttonBorder);
		startButton.setFocusPainted(false);
		startButton.setBackground(this.buttonColor);
		startButton.setForeground(Color.WHITE);
		startButton.setPreferredSize(new Dimension(400, 100));
		startButton.setFont(new Font(FONT_NAME, Font.BOLD, 25));
//		startButton.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//            	e.getComponent().setBackground(Color.GREEN);
//            }
//        });
		startButton.addActionListener(e -> {
			try {
	            // load up the knowledge base
		        this.ks = KieServices.Factory.get();
	    	    this.kContainer = ks.getKieClasspathContainer();
	        	this.kSession = kContainer.newKieSession("ksession-rules");

	        	displayContentView();
	        	kSession.setGlobal("gui", this);
	            kSession.fireAllRules();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
		});
		startPanel.add(startButton);
	}
	
	private void displayContentView() {
		// Question
		JPanel questionPanel = new JPanel();
		questionPanel.setBackground(new Color(0, 0, 0, 90));
		this.currentQuestion.setFont(new Font(FONT_NAME, Font.BOLD, 25));
		this.currentQuestion.setForeground(Color.WHITE);
		questionPanel.add(this.currentQuestion);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.contentPanel.add(questionPanel, gbc);
		
		this.answerPanel.setLayout(new GridBagLayout());
		gbc.gridy = 1;
		this.contentPanel.add(this.answerPanel, gbc);
		
		this.cardLayout.next(this.cardPanel);
	}
	
	public class JPanelWithBackground extends JPanel {

		  private Image backgroundImage;

		  public JPanelWithBackground(String fileName) {
			  try {
				  backgroundImage = ImageIO.read(new File(fileName));	  
			  } catch (IOException io) {
				  
			  }
		    
		  }

		  public void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    g.drawImage(backgroundImage, 0, 0, 1000, 500, this);
		  }
		}
}
