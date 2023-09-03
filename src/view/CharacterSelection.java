package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.utils.Strings;

public class CharacterSelection extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7096359184586117742L;
	private final JPanel contentPanel = new JPanel();
	
	private String personagemEscolhida;
	@SuppressWarnings("unused")
	private AtorJogador atorJogador;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		try {
			CharacterSelection frame = new CharacterSelection(new AtorJogador());
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the frame.
	 */
	public CharacterSelection(AtorJogador atorJogador) {
		this.atorJogador = atorJogador;
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(Strings.SELECTYOURCHAR.toString());
		setBounds(250, 225, 440, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			TextField textField = new TextField();
			textField.addTextListener(new TextListener() {
				public void textValueChanged(TextEvent arg0) {
					
					personagemEscolhida = textField.getText();
					if (personagemEscolhida.equals("0")
							|| personagemEscolhida.equals("1")
							|| personagemEscolhida.equals("2")
							|| personagemEscolhida.equals("3")
							|| personagemEscolhida.equals("4")){
						int escolhida = Integer.parseInt(personagemEscolhida);
						try {
							atorJogador.heroQuest.selecionarPersonagemEscolhida(escolhida);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						dispose();
					}
				}
			});
			{
				Label label = new Label(Strings.TYPECHARNUMBER.toString());
				contentPanel.add(label);
			}
			contentPanel.add(textField);
		}
		{
			JButton btnZargon = new JButton("0 - " + Strings.ZARGON.toString());
			btnZargon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					JFrame cardScreen = new JFrame();
					cardScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					
					cardScreen.setTitle(Strings.ZARGONDESCRIPTION.toString());
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
				JButton btnWizard = new JButton("2 - " + Strings.WIZARD.toString());
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
				JButton btnNewButton = new JButton("1 - " + Strings.BARBARIAN.toString());
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
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				contentPanel.add(btnNewButton);
				contentPanel.add(btnWizard);
			}
		}
		{
			JButton btnElf = new JButton("3 - " + Strings.ELF.toString());
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
			JButton btnDwarf = new JButton("4 - " + Strings.DWARF.toString());
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
