package put.ai;

import javax.swing.*;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.awt.*;
import java.util.ArrayList;

public class LayoutUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// DROOLS
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;
	
	
	// Main UI components
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private JPanel startPanel = new JPanel();
	private JPanel contentPanel = new JPanel();
	
	// Drools interactive
	private JPanel answerPanel = new JPanel();
	private JLabel currentQuestion = new JLabel();
	private ArrayList<Answer> currentAnswers;
	private ArrayList<JRadioButton> currentOptions;

	public LayoutUI() {
		super("BuyingGame");
		initialize();
	}
	
	public void changeState(
			Question question
	) {
		this.currentQuestion.setText(question.getQuestion());
		this.currentAnswers = question.getAnswers();
		this.currentOptions = new ArrayList<JRadioButton>();
		ButtonGroup buttonGroup = new ButtonGroup();
		
		// Update panel
		this.contentPanel.remove(this.answerPanel);
		this.answerPanel = new JPanel();
		this.contentPanel.add(this.answerPanel);
		this.contentPanel.revalidate();
		
		for (Answer answer : question.getAnswers()) {
			JRadioButton button = new JRadioButton(answer.getAnswer());
			buttonGroup.add(button);
			currentOptions.add(button);
			answerPanel.add(button);
		}
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280, 720);
		this.setLocationRelativeTo(null);
		Container container = getContentPane();
		container.setBackground(new Color(27,27,27));
		this.setLayout(new BorderLayout());
		
		this.contentPanel.setBackground(getForeground());
		this.contentPanel.setLayout(new BorderLayout());
		
		this.cardLayout = new CardLayout();
		this.cardPanel = new JPanel(cardLayout);
		this.cardPanel.add(startPanel, "StartPanel");
		this.cardPanel.add(contentPanel, "ContentPanel");
		
		this.add(cardPanel, BorderLayout.CENTER);
		
		
		this.displayStartView();
		this.setVisible(true);
	}
	
	private void displayStartView() {
		JButton startButton = new JButton();
		startButton.setText("Rozpocznij");
		startButton.setPreferredSize(new Dimension(400, 100));
		startButton.addActionListener(e -> {
			try {
	            // load up the knowledge base
		        this.ks = KieServices.Factory.get();
	    	    this.kContainer = ks.getKieClasspathContainer();
	        	this.kSession = kContainer.newKieSession("ksession-rules");

	        	kSession.insert(this);
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
		questionPanel.add(this.currentQuestion);
				
		// Answers
		this.answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
				
		// Buttons
		JPanel buttonPanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(e -> {
			JRadioButton selectedButton = currentOptions
					.stream()
					.filter(btn -> btn.isSelected())
					.findAny()
					.orElse(null);
			
			Answer answer = currentAnswers
					.stream()
					.filter(ans -> selectedButton.getText().equals(ans.getAnswer()))
					.findAny()
					.orElse(null);
			
			kSession.insert(answer);
            kSession.fireAllRules();
		});
		buttonPanel.add(submitButton);
			
		this.contentPanel.add(questionPanel, BorderLayout.NORTH);
		this.contentPanel.add(this.answerPanel, BorderLayout.CENTER);
		this.contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.cardLayout.next(this.cardPanel);
	}
}
