package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.utils.Strings;
import enums.ImageEnum;

public class CharacterSelection extends JFrame {

	private static final long serialVersionUID = 7096359184586117742L;
	private final JPanel contentPanel = new JPanel();
	
	private String chosenPlayer;
	private final GUI GUI;

	public CharacterSelection(GUI GUI) {
		this.GUI = GUI;

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(Strings.SELECT_YOUR_CHARACTER.toString());
		setBounds(250, 225, 440, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			TextField textField = new TextField();
			textField.addTextListener(arg0 -> {

                chosenPlayer = textField.getText();
                if (chosenPlayer.equals("0")
                        || chosenPlayer.equals("1")
                        || chosenPlayer.equals("2")
                        || chosenPlayer.equals("3")
                        || chosenPlayer.equals("4")){
                    int chosenIndex = Integer.parseInt(chosenPlayer);
                    try {
                        GUI.heroQuest.processCharacterSelection(chosenIndex);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    dispose();
                }
            });
			{
				Label label = new Label(Strings.TYPE_CHARACTER_NUMBER.toString());
				contentPanel.add(label);
			}
			contentPanel.add(textField);
		}
		{
			JButton btnZargon = new JButton("0 - " + Strings.ZARGON);
			btnZargon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					JFrame cardScreen = new JFrame();
					cardScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					
					cardScreen.setTitle(Strings.ZARGON_DESCRIPTION.toString());
					cardScreen.setBounds(800, 200, 800, 600);
					
					ImagePanel ip = new ImagePanel(ImageEnum.ZARGON);
					ip.setBorder(new EmptyBorder(5, 5, 5, 5));
					ip.setLayout(new BorderLayout(0, 0));
					cardScreen.getContentPane().add(ip, BorderLayout.CENTER);
					cardScreen.setVisible(true);
				}
			});
			contentPanel.add(btnZargon);
		}
		{
			{
				JButton btnWizard = new JButton("2 - " + Strings.WIZARD);
				btnWizard.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						JFrame cardScreen = new JFrame();
						cardScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						
						cardScreen.setTitle(Strings.WIZARD.toString());
						cardScreen.setBounds(800, 200, 560, 780);
						
						ImagePanel ip = new ImagePanel(ImageEnum.WIZARD);
						ip.setBorder(new EmptyBorder(5, 5, 5, 5));
						ip.setLayout(new BorderLayout(0, 0));
						cardScreen.getContentPane().add(ip, BorderLayout.CENTER);
						cardScreen.setVisible(true);
					}
				});
				JButton btnNewButton = new JButton("1 - " + Strings.BARBARIAN);
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						JFrame cardScreen = new JFrame();
						cardScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						
						cardScreen.setTitle(Strings.BARBARIAN.toString());
						cardScreen.setBounds(800, 200, 560, 780);
						
						ImagePanel ip = new ImagePanel(ImageEnum.BARBARIAN);
						ip.setBorder(new EmptyBorder(5, 5, 5, 5));
						ip.setLayout(new BorderLayout(0, 0));
						cardScreen.getContentPane().add(ip, BorderLayout.CENTER);
						cardScreen.setVisible(true);
					}
				});
				btnNewButton.addActionListener(arg0 -> {
                });
				contentPanel.add(btnNewButton);
				contentPanel.add(btnWizard);
			}
		}
		{
			JButton btnElf = new JButton("3 - " + Strings.ELF);
			btnElf.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					JFrame cardScreen = new JFrame();
					cardScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					
					cardScreen.setTitle(Strings.ELF.toString());
					cardScreen.setBounds(800, 200, 560, 780);
					
					ImagePanel ip = new ImagePanel(ImageEnum.ELF);
					ip.setBorder(new EmptyBorder(5, 5, 5, 5));
					ip.setLayout(new BorderLayout(0, 0));
					cardScreen.getContentPane().add(ip, BorderLayout.CENTER);
					cardScreen.setVisible(true);
				}
			});
			contentPanel.add(btnElf);
		}
		{
			JButton btnDwarf = new JButton("4 - " + Strings.DWARF);
			btnDwarf.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					JFrame cardScreen = new JFrame();
					cardScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					
					cardScreen.setTitle(Strings.DWARF.toString());
					cardScreen.setBounds(800, 200, 560, 780);
					
					ImagePanel ip = new ImagePanel(ImageEnum.DWARF);
					ip.setBorder(new EmptyBorder(5, 5, 5, 5));
					ip.setLayout(new BorderLayout(0, 0));
					cardScreen.getContentPane().add(ip, BorderLayout.CENTER);
					cardScreen.setVisible(true);
				}
			});
			contentPanel.add(btnDwarf);
		}
	}

}
