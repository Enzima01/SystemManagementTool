package screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoChoose extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoChoose frame = new InfoChoose();
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
	public InfoChoose() {
		setTitle("Escolha a Informação");
        setIconImage(Toolkit.getDefaultToolkit().getImage(InfoHardware.class.getResource("/images/gear.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JButton btnNewButton = new JButton("Sobre");
        btnNewButton.setMnemonic('S');
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		InfoSMT as = new InfoSMT();
				as.setVisible(true);
				dispose();
        	}
        });
        btnNewButton.setIcon(new ImageIcon(InfoChoose.class.getResource("/images/info32x32.png")));
        btnNewButton.setBackground(Color.BLACK);
        btnNewButton.setForeground(Color.GREEN);
        btnNewButton.setFont(new Font("Consolas", Font.BOLD, 20));
        btnNewButton.setBounds(86, 81, 263, 65);
        contentPane.add(btnNewButton);
        
        JButton btnInfoDispositivo = new JButton("Hardware Info");
        btnInfoDispositivo.setMnemonic('H');
        btnInfoDispositivo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		InfoHardware ih = new InfoHardware();
        		ih.setVisible(true);
        		dispose();
        	}
        });
        btnInfoDispositivo.setIcon(new ImageIcon(InfoChoose.class.getResource("/images/hardware.png")));
        btnInfoDispositivo.setBackground(Color.BLACK);
        btnInfoDispositivo.setForeground(Color.GREEN);
        btnInfoDispositivo.setFont(new Font("Consolas", Font.BOLD, 20));
        btnInfoDispositivo.setBounds(86, 163, 263, 65);
        contentPane.add(btnInfoDispositivo);
        
        JLabel lblEscolha = new JLabel("Escolha:");
        lblEscolha.setForeground(Color.GREEN);
        lblEscolha.setFont(new Font("Consolas", Font.BOLD, 20));
        lblEscolha.setBounds(171, 26, 253, 31);
        contentPane.add(lblEscolha);
	}
}
