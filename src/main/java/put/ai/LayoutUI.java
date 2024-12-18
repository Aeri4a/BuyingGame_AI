package put.ai;

import javax.swing.*;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.awt.*;
import java.util.ArrayList;

public class LayoutUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final String FONT_NAME = "Futura";
	
	// DROOLS
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;
	
	// Main UI components
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JPanel startPanel = new JPanel();
	private JPanel contentPanel = new JPanel();
	private JPanel endPanel = new JPanel();
	private GridBagConstraints gbc = new GridBagConstraints();
	
	// Drools interactive
	private JLabel solutionText = new JLabel("");
	private JPanel answerPanel = new JPanel();
	private JLabel currentQuestion = new JLabel();
	private ArrayList<Answer> currentAnswers;

	public LayoutUI() {
		super("BuyingGame");
		initialize();
	}
	
	public void showQuestion(
			String questionContent,
			ArrayList<Answer> questionAnswers
	) {
		this.currentQuestion.setText(questionContent);
		this.currentAnswers = questionAnswers;
		
		// Update panel
		this.contentPanel.remove(this.answerPanel);
		this.answerPanel = new JPanel();
		this.answerPanel.setLayout(new GridBagLayout());
		this.contentPanel.revalidate();
		this.answerPanel.revalidate();
		this.contentPanel.add(this.answerPanel);
	
		
//		this.solutionText.setFont(new Font(FONT_NAME, Font.BOLD, 20)); NO NEED
		
		// Centering
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.contentPanel.add(this.answerPanel, gbc);
		
		gbc.gridy = 1;
		for (Answer answer : questionAnswers) {
			JButton button = new JButton(answer.getContent());
			button.setPreferredSize(new Dimension(400, 80));
			button.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
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
		this.setSize(720, 480);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		Container container = getContentPane();
		container.setBackground(new Color(27,27,27));
		this.setLayout(new BorderLayout());
		
//		this.startPanel.setBackground(new Color(27,27,27));
		this.startPanel.setLayout(new GridBagLayout());
		
//		this.contentPanel.setBackground(new Color(27,27,27));
		this.contentPanel.setLayout(new GridBagLayout());
//		this.contentPanel.setLayout(new BorderLayout());
				
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
		this.cardPanel.add(contentPanel, "ContentPanel"); // THIS
		this.cardPanel.add(endPanel, "EndPanel");
		
		this.add(cardPanel, BorderLayout.CENTER);
		
		this.displayStartView();
		this.setVisible(true);
	}
	
	private void displayStartView() {
		JButton startButton = new JButton();
		startButton.setText("Rozpocznij");
		startButton.setPreferredSize(new Dimension(200, 100));
		startButton.addActionListener(e -> {
			try {
	            // load up the knowledge base
		        this.ks = KieServices.Factory.get();
	    	    this.kContainer = ks.getKieClasspathContainer();
	        	this.kSession = kContainer.newKieSession("ksession-rules");

	        	kSession.setGlobal("gui", this);
	            kSession.fireAllRules();
	            displayContentView();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
		});
		startPanel.add(startButton);
	}
	
	private void displayContentView() {
		// Question
		JPanel questionPanel = new JPanel();
		this.currentQuestion.setFont(new Font(FONT_NAME, Font.PLAIN, 25));
		questionPanel.add(this.currentQuestion);
				
		// Answers
		this.answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
				
		// Buttons -> OLD
//		JPanel buttonPanel = new JPanel();
//		JButton submitButton = new JButton("Submit");
//		submitButton.addActionListener(e -> {
//			JRadioButton selectedButton = currentOptions
//					.stream()
//					.filter(btn -> btn.isSelected())
//					.findAny()
//					.orElse(null);
//			
//			Answer answer = currentAnswers
//					.stream()
//					.filter(ans -> selectedButton.getText().equals(ans.getContent()))
//					.findAny()
//					.orElse(null);
//			
//			kSession.insert(answer);
//            kSession.fireAllRules();
//		});
//		buttonPanel.add(submitButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.contentPanel.add(questionPanel, gbc);
		gbc.gridy = 1;
		this.contentPanel.add(this.answerPanel, gbc);
//		gbc.gridy = 2;
//		this.contentPanel.add(buttonPanel, gbc);
			
		this.cardLayout.next(this.cardPanel);
	}
}
