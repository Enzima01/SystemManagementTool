package screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class AboutScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutScreen frame = new AboutScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AboutScreen() {
		setTitle("Sobre");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutScreen.class.getResource("/images/gear.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 282);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Sobre");
		lblNewLabel.setIcon(new ImageIcon(AboutScreen.class.getResource("/images/info32x32.png")));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(187, 27, 98, 30);
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 20));
		contentPane.add(lblNewLabel);

		JTextPane txtpnDesenvolvidaPorEnzo = new JTextPane();
		txtpnDesenvolvidaPorEnzo.setBackground(Color.BLACK);
		txtpnDesenvolvidaPorEnzo.setForeground(Color.GREEN);
		txtpnDesenvolvidaPorEnzo.setFont(new Font("Consolas", Font.BOLD, 15));
		txtpnDesenvolvidaPorEnzo.setEditable(false);
		txtpnDesenvolvidaPorEnzo.setText(
				"O System Management Tool é uma ferramenta de administração que permite monitorar o sistema, controlar processos e gerenciar usuários.");
		txtpnDesenvolvidaPorEnzo.setBounds(20, 68, 484, 63);
		contentPane.add(txtpnDesenvolvidaPorEnzo);

		JTextPane txtpnDesenvolvidaPorEnzo_1 = new JTextPane();
		txtpnDesenvolvidaPorEnzo_1.setForeground(Color.GREEN);
		txtpnDesenvolvidaPorEnzo_1.setBackground(Color.BLACK);
		txtpnDesenvolvidaPorEnzo_1.setFont(new Font("Consolas", Font.BOLD, 15));
		txtpnDesenvolvidaPorEnzo_1.setText(
				"Desenvolvida por Enzo Henrique Favaro para um projeto da faculdade, o SMT é compatível com Windows e Linux.");
		txtpnDesenvolvidaPorEnzo_1.setBounds(20, 142, 456, 50);
		contentPane.add(txtpnDesenvolvidaPorEnzo_1);
	}
}
