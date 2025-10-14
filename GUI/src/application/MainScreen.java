package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import screens.AboutScreen;
import screens.ProcessControlScreen;
import screens.SystemMonitorScreen;
import screens.UserManagerScreen;

public class MainScreen {

	private JFrame frmFerramentaDeAdministrao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frmFerramentaDeAdministrao.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFerramentaDeAdministrao = new JFrame();
		frmFerramentaDeAdministrao
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreen.class.getResource("/images/gear.png")));
		frmFerramentaDeAdministrao.getContentPane().setBackground(Color.BLACK);
		frmFerramentaDeAdministrao.setTitle("Ferramenta de Administração do Sistema");
		frmFerramentaDeAdministrao.setBounds(100, 100, 535, 392);
		frmFerramentaDeAdministrao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFerramentaDeAdministrao.getContentPane().setLayout(null);

		frmFerramentaDeAdministrao.setResizable(false);
		frmFerramentaDeAdministrao.setLocationRelativeTo(null);

		JLabel lblTitle = new JLabel("Ferramenta de Administração do Sistema");
		lblTitle.setForeground(Color.GREEN);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 20));
		lblTitle.setBounds(46, 38, 424, 31);
		frmFerramentaDeAdministrao.getContentPane().add(lblTitle);

		JButton btnNewButton = new JButton("Monitorar Processamento");
		btnNewButton.setMnemonic('M');
		btnNewButton.setIcon(new ImageIcon(MainScreen.class.getResource("/images/hardware.png")));
		btnNewButton.setToolTipText("");
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemMonitorScreen sms = new SystemMonitorScreen();
				sms.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.BOLD, 13));
		btnNewButton.setBounds(10, 103, 234, 92);
		frmFerramentaDeAdministrao.getContentPane().add(btnNewButton);

		JButton btnControlarProcessos = new JButton("Controlar Processos");
		btnControlarProcessos.setMnemonic('C');
		btnControlarProcessos.setIcon(new ImageIcon(MainScreen.class.getResource("/images/pc.png")));
		btnControlarProcessos.setForeground(Color.GREEN);
		btnControlarProcessos.setBackground(Color.BLACK);
		btnControlarProcessos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcessControlScreen pcs = new ProcessControlScreen();
				pcs.setVisible(true);
			}
		});
		btnControlarProcessos.setFont(new Font("Consolas", Font.BOLD, 16));
		btnControlarProcessos.setBounds(275, 103, 234, 92);
		frmFerramentaDeAdministrao.getContentPane().add(btnControlarProcessos);

		JButton btnGerenciarUsurios = new JButton("Gerenciar Usuários");
		btnGerenciarUsurios.setMnemonic('G');
		btnGerenciarUsurios.setIcon(new ImageIcon(MainScreen.class.getResource("/images/user.png")));
		btnGerenciarUsurios.setBackground(Color.BLACK);
		btnGerenciarUsurios.setForeground(Color.GREEN);
		btnGerenciarUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManagerScreen ums = new UserManagerScreen();
				ums.setVisible(true);
			}
		});
		btnGerenciarUsurios.setFont(new Font("Consolas", Font.BOLD, 16));
		btnGerenciarUsurios.setBounds(10, 218, 234, 92);
		frmFerramentaDeAdministrao.getContentPane().add(btnGerenciarUsurios);

		JButton btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutScreen as = new AboutScreen();
				as.setVisible(true);
			}
		});
		btnSobre.setMnemonic('S');
		btnSobre.setIcon(new ImageIcon(MainScreen.class.getResource("/images/info.png")));
		btnSobre.setBackground(Color.BLACK);
		btnSobre.setForeground(Color.GREEN);
		btnSobre.setFont(new Font("Consolas", Font.BOLD, 16));
		btnSobre.setBounds(275, 218, 234, 92);
		frmFerramentaDeAdministrao.getContentPane().add(btnSobre);

		JLabel lblNewLabel = new JLabel("Made by Enzima01");
		lblNewLabel.setIcon(new ImageIcon(MainScreen.class.getResource("/images/01_16x16.png")));
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 14));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(193, 328, 153, 14);
		frmFerramentaDeAdministrao.getContentPane().add(lblNewLabel);
	}
}
