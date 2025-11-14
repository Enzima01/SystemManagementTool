/**
 * 
 * @author Enzima01
 * 
 **/

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

import screens.InfoChoose;
import screens.ProcessControlScreen;
import screens.SystemMonitorScreen;
import screens.UserManagerScreen;

public class MainScreenSMTv6 {

	private JFrame frmFerramentaDeAdministrao;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreenSMTv6 window = new MainScreenSMTv6();
					window.frmFerramentaDeAdministrao.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MainScreenSMTv6() {
		initialize();
	}

	
	private void initialize() {
		frmFerramentaDeAdministrao = new JFrame();
		frmFerramentaDeAdministrao
				.setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreenSMTv6.class.getResource("/images/gear.png")));
		frmFerramentaDeAdministrao.getContentPane().setBackground(Color.BLACK);
		frmFerramentaDeAdministrao.setTitle("Ferramenta de Administração do Sistema | Enzima01");
		frmFerramentaDeAdministrao.setBounds(100, 100, 630, 398);
		frmFerramentaDeAdministrao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFerramentaDeAdministrao.getContentPane().setLayout(null);

		frmFerramentaDeAdministrao.setResizable(false);
		frmFerramentaDeAdministrao.setLocationRelativeTo(null);

		JLabel lblTitle = new JLabel("Ferramenta de Administração do Sistema");
		lblTitle.setForeground(Color.GREEN);
		lblTitle.setFont(new Font("Consolas", Font.BOLD, 20));
		lblTitle.setBounds(95, 39, 494, 31);
		frmFerramentaDeAdministrao.getContentPane().add(lblTitle);

		JButton btnNewButton = new JButton("Monitorar Processamento");
		btnNewButton.setMnemonic('M');
		btnNewButton.setIcon(new ImageIcon(MainScreenSMTv6.class.getResource("/images/hardware.png")));
		btnNewButton.setToolTipText("");
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemMonitorScreen sms = new SystemMonitorScreen();
				sms.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.BOLD, 16));
		btnNewButton.setBounds(10, 95, 286, 105);
		frmFerramentaDeAdministrao.getContentPane().add(btnNewButton);

		JButton btnControlarProcessos = new JButton("Controlar Processos");
		btnControlarProcessos.setMnemonic('C');
		btnControlarProcessos.setIcon(new ImageIcon(MainScreenSMTv6.class.getResource("/images/pc.png")));
		btnControlarProcessos.setForeground(Color.GREEN);
		btnControlarProcessos.setBackground(Color.BLACK);
		btnControlarProcessos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProcessControlScreen pcs = new ProcessControlScreen();
				pcs.setVisible(true);
			}
		});
		btnControlarProcessos.setFont(new Font("Consolas", Font.BOLD, 16));
		btnControlarProcessos.setBounds(321, 95, 285, 105);
		frmFerramentaDeAdministrao.getContentPane().add(btnControlarProcessos);

		JButton btnGerenciarUsurios = new JButton("Gerenciar Usuários");
		btnGerenciarUsurios.setMnemonic('G');
		btnGerenciarUsurios.setIcon(new ImageIcon(MainScreenSMTv6.class.getResource("/images/user.png")));
		btnGerenciarUsurios.setBackground(Color.BLACK);
		btnGerenciarUsurios.setForeground(Color.GREEN);
		btnGerenciarUsurios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManagerScreen ums = new UserManagerScreen();
				ums.setVisible(true);
			}
		});
		btnGerenciarUsurios.setFont(new Font("Consolas", Font.BOLD, 16));
		btnGerenciarUsurios.setBounds(10, 212, 286, 104);
		frmFerramentaDeAdministrao.getContentPane().add(btnGerenciarUsurios);

		JButton btnSobre = new JButton("Info");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoChoose ic = new InfoChoose();
				ic.setVisible(true);
				
			}
		});
		btnSobre.setMnemonic('I');
		btnSobre.setIcon(new ImageIcon(MainScreenSMTv6.class.getResource("/images/info.png")));
		btnSobre.setBackground(Color.BLACK);
		btnSobre.setForeground(Color.GREEN);
		btnSobre.setFont(new Font("Consolas", Font.BOLD, 16));
		btnSobre.setBounds(321, 212, 285, 105);
		frmFerramentaDeAdministrao.getContentPane().add(btnSobre);

		JLabel lblNewLabel = new JLabel("Made by Enzima01");
		lblNewLabel.setIcon(new ImageIcon(MainScreenSMTv6.class.getResource("/images/01_16x16.png")));
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 14));
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setBounds(238, 327, 208, 31);
		frmFerramentaDeAdministrao.getContentPane().add(lblNewLabel);
	}
}
