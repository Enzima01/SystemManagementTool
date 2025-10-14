package screens;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import oshi.SystemInfo;
import oshi.software.os.OSSession;
import oshi.software.os.OperatingSystem;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class UserManagerScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textAreaUsuarios;
	private OperatingSystem os;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UserManagerScreen frame = new UserManagerScreen();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public UserManagerScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserManagerScreen.class.getResource("/images/gear.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Gerenciamento de Usuários");

		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Gerenciamento de Usuários");
		lblTitulo.setIcon(new ImageIcon(UserManagerScreen.class.getResource("/images/user32x32.png")));
		lblTitulo.setForeground(Color.GREEN);
		lblTitulo.setFont(new Font("Consolas", Font.BOLD, 20));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(143, 11, 350, 38);
		contentPane.add(lblTitulo);

		JButton btnListar = new JButton("Listar Usuários");
		btnListar.setMnemonic('L');
		btnListar.setToolTipText("");
		btnListar.setBackground(Color.BLACK);
		btnListar.setForeground(Color.GREEN);
		btnListar.setFont(new Font("Consolas", Font.BOLD, 15));
		btnListar.setBounds(40, 60, 180, 40);
		contentPane.add(btnListar);

		JButton btnCriar = new JButton("Criar Usuário");
		btnCriar.setMnemonic('C');
		btnCriar.setForeground(Color.GREEN);
		btnCriar.setBackground(Color.BLACK);
		btnCriar.setFont(new Font("Consolas", Font.BOLD, 15));
		btnCriar.setBounds(240, 60, 150, 40);
		contentPane.add(btnCriar);

		JButton btnRemover = new JButton("Remover Usuário");
		btnRemover.setMnemonic('R');
		btnRemover.setBackground(Color.BLACK);
		btnRemover.setForeground(Color.GREEN);
		btnRemover.setFont(new Font("Consolas", Font.BOLD, 15));
		btnRemover.setBounds(410, 60, 180, 40);
		contentPane.add(btnRemover);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 120, 550, 310);
		contentPane.add(scrollPane);

		textAreaUsuarios = new JTextArea();
		textAreaUsuarios.setForeground(Color.GREEN);
		textAreaUsuarios.setBackground(Color.BLACK);
		textAreaUsuarios.setEditable(false);
		textAreaUsuarios.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(textAreaUsuarios);

		SystemInfo si = new SystemInfo();
		os = si.getOperatingSystem();

		btnListar.addActionListener(e -> listarUsuarios());
		btnCriar.addActionListener(e -> criarUsuario());
		btnRemover.addActionListener(e -> removerUsuario());
	}

	// Lista usuários
	private void listarUsuarios() {
		List<OSSession> sessoes = os.getSessions();

		if (sessoes.isEmpty()) {
			textAreaUsuarios.setText("Nenhum usuário ativo encontrado.");
			return;
		}

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-20s %-15s %-25s%n", "Usuário", "Host", "Login em"));
		sb.append("---------------------------------------------------------------\n");

		for (OSSession s : sessoes) {
			String loginTime = fmt.format(Instant.ofEpochSecond(s.getLoginTime()));
			sb.append(String.format("%-20s %-15s %-25s%n", s.getUserName(), s.getHost(), loginTime));
		}

		textAreaUsuarios.setText(sb.toString());
	}

	// Criar usuário no sistema
	private void criarUsuario() {
		String nome = JOptionPane.showInputDialog(this, "Digite o nome do novo usuário:");
		if (nome == null || nome.trim().isEmpty())
			return;

		String senha = JOptionPane.showInputDialog(this, "Digite a senha do usuário (opcional):");
		if (senha == null)
			senha = "";

		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		String comando;

		if (isWindows) {
			if (senha.isEmpty())
				comando = String.format("net user %s /add", nome);
			else
				comando = String.format("net user %s %s /add", nome, senha);
		} else {
			comando = senha.isEmpty() ? String.format("sudo useradd %s", nome)
					: String.format("sudo useradd -m -p $(openssl passwd -1 %s) %s", senha, nome);
		}

		executarComando(comando, "Usuário criado com sucesso!", "Erro ao criar usuário!");
	}

	// Remover usuário no sistema
	private void removerUsuario() {
		String nome = JOptionPane.showInputDialog(this, "Digite o nome do usuário a remover:");
		if (nome == null || nome.trim().isEmpty())
			return;

		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
		String comando = isWindows ? String.format("net user %s /delete", nome)
				: String.format("sudo userdel -r %s", nome);

		executarComando(comando, "Usuário removido com sucesso!", "Erro ao remover usuário!");
	}

	// Executar comandos do sistema
	private void executarComando(String comando, String msgSucesso, String msgErro) {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				builder.command("cmd.exe", "/c", comando);
			} else {
				builder.command("bash", "-c", comando);
			}

			Process processo = builder.start();
			int exitCode = processo.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			if (exitCode == 0) {
				JOptionPane.showMessageDialog(this, msgSucesso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, msgErro + "\n\n" + output, "Erro", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
