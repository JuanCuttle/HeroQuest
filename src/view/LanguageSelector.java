package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.utils.Strings;

public class LanguageSelector extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public LanguageSelector(GUI gui) {
		setTitle(Strings.LANGSELECT.toString());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JComboBox<LanguageEnum> comboBox = new JComboBox<>();
		
		comboBox.addItem(LanguageEnum.Portugues);
		comboBox.addItem(LanguageEnum.English);
		
		contentPane.add(comboBox, BorderLayout.CENTER);
		
		JButton btnShow = new JButton(Strings.SELECT.toString());
		btnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				gui.setLanguage((LanguageEnum) comboBox.getSelectedItem());
				gui.atualizarBotoesLingua();
				dispose(); // Remove JFrame
			}
		});
		contentPane.add(btnShow, BorderLayout.WEST);
	}

}
