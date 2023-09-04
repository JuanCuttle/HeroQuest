package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.HeroQuest;
import entities.utils.Strings;
import quests.*;

public class QuestSelector extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6345581665543710575L;
	private JPanel contentPane;
	private static QuestEnum[] options;
	private HeroQuest game;

	/**
	 * Create the frame.
	 */
	public QuestSelector() {
		
			/*this.setIconImage(Toolkit.getDefaultToolkit().getImage(
					QuestSelector.class.getResource("/images/Aegons_crown.png")));*/

		
		this.setVisible(true);
		
		try {
			options = QuestEnum.values();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setTitle("Select a quest to play");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 200, 450, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JComboBox<QuestEnum> comboBox = new JComboBox<>();
		for (QuestEnum t : options){
			comboBox.addItem(t);
		}
		
		contentPane.add(comboBox, BorderLayout.CENTER);
		
		JButton btnShow = new JButton("Select");
		btnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				QuestEnum questEnum = (QuestEnum) comboBox.getSelectedItem();
				game = new HeroQuest();
				try {
					switch(questEnum){
						case TheTrial: selectMap(new TheTrial(game));
									break;
						case TheMaze: selectMap(new TheMaze(game));
									break;
						case TheRescueOfSirRagnar: selectMap(new TheRescueOfSirRagnar(game));
									break;
						case LairOfTheOrcWarlord: selectMap(new LairOfTheOrcWarlord(game));
									break;
						case PrinceMagnusGold : selectMap(new PrinceMagnusGold(game));
									break;
						case MelarsMaze : selectMap(new MelarsMaze(game));
									break;
						case LegacyOfTheOrcWarlord : selectMap(new LegacyOfTheOrcWarlord(game));
									break;
						case TheStoneHunter : selectMap(new TheStoneHunter(game));
									break;
						case TheFireMage : selectMap(new TheFireMage(game));
									break;
						default: selectMap(new TheTrial(game));
									break;
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				dispose();
			}
		});
		contentPane.add(btnShow, BorderLayout.WEST);
	}
	
	public void selectMap(BasicMap bm){
		game.setMap(bm);
		GUI frame = new GUI(game);
		frame.setVisible(true);
		
		if (bm instanceof TheTrial){
			frame.textArea.setText(frame.textArea.getText()+"\n"+Strings.THETRIAL.toString());
			//JOptionPane.showMessageDialog(null, Strings.THETRIAL);
		}
	}
	
}

