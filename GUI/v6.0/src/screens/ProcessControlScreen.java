package screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
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
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class ProcessControlScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textAreaProcessos;
	private OperatingSystem os;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ProcessControlScreen frame = new ProcessControlScreen();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ProcessControlScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProcessControlScreen.class.getResource("/images/gear.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Controle de Processos");

		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Controle de Processos");
		lblTitulo.setIcon(new ImageIcon(ProcessControlScreen.class.getResource("/images/pc32x32.png")));
		lblTitulo.setForeground(Color.GREEN);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Consolas", Font.BOLD, 20));
		lblTitulo.setBounds(150, 10, 300, 30);
		contentPane.add(lblTitulo);

		JButton btnListar = new JButton("Listar Processos");
		btnListar.setMnemonic('L');
		btnListar.setBackground(Color.BLACK);
		btnListar.setForeground(Color.GREEN);
		btnListar.setFont(new Font("Consolas", Font.BOLD, 15));
		btnListar.setBounds(60, 60, 200, 40);
		contentPane.add(btnListar);

		JButton btnEncerrar = new JButton("Encerrar Processo");
		btnEncerrar.setMnemonic('E');
		btnEncerrar.setForeground(Color.GREEN);
		btnEncerrar.setBackground(Color.BLACK);
		btnEncerrar.setFont(new Font("Consolas", Font.BOLD, 15));
		btnEncerrar.setBounds(320, 60, 200, 40);
		contentPane.add(btnEncerrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 120, 530, 300);
		contentPane.add(scrollPane);

		textAreaProcessos = new JTextArea();
		textAreaProcessos.setBackground(Color.BLACK);
		textAreaProcessos.setForeground(Color.GREEN);
		textAreaProcessos.setEditable(false);
		textAreaProcessos.setFont(new Font("Consolas", Font.PLAIN, 12));
		scrollPane.setViewportView(textAreaProcessos);

		SystemInfo si = new SystemInfo();
		os = si.getOperatingSystem();

		btnListar.addActionListener(e -> listarProcessos());
		btnEncerrar.addActionListener(e -> encerrarProcesso());
	}

	private void listarProcessos() {
		List<OSProcess> processos = os.getProcesses();

		if (processos == null || processos.isEmpty()) {
			textAreaProcessos.setText("Nenhum processo encontrado.\nTente executar como superusuário (sudo).");
			return;
		}

		// Ordenar manualmente por uso de CPU
		List<OSProcess> top = processos.stream()
				.sorted(Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed()).limit(20)
				.collect(Collectors.toList());

		String lista = top.stream()
				.map(p -> String.format("PID: %-6d | Nome: %-20s | CPU: %.1f%% | Mem: %.1f MB", p.getProcessID(),
						p.getName(), p.getProcessCpuLoadCumulative() * 100, p.getResidentSetSize() / 1024d / 1024d))
				.collect(Collectors.joining("\n"));

		textAreaProcessos.setText(lista);
	}

	private void encerrarProcesso() {
		String input = JOptionPane.showInputDialog(this, "Digite o nome ou o PID do processo:", "Encerrar Processo", JOptionPane.QUESTION_MESSAGE);

		if (input == null || input.isBlank())
			return;

		boolean sucesso = false;

		try {
			// PID
			long pid = Long.parseLong(input.trim());
			sucesso = encerrarPorPID(pid);
		} catch (NumberFormatException e) {
			// Nome
			List<OSProcess> processos = os.getProcesses();
			for (OSProcess proc : processos) {
				if (proc.getName().equalsIgnoreCase(input.trim())) {
					sucesso = encerrarPorPID(proc.getProcessID());
					if (sucesso)
						break;
				}
			}
		}

		if (sucesso)
			JOptionPane.showMessageDialog(this, "Processo encerrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(this, "Falha ao encerrar processo.", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	private boolean encerrarPorPID(long pid) {
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			Process proc;
			if (osName.contains("win")) {
				proc = Runtime.getRuntime().exec("taskkill /PID " + pid + " /F");
			} else {
				proc = Runtime.getRuntime().exec("kill -9 " + pid);
			}
			proc.waitFor();
			return proc.exitValue() == 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
