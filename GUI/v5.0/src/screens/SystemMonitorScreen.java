package screens;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class SystemMonitorScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCpuUsage;
	private JLabel lblRamUsage;
	private JLabel lblUptime;

	private SystemInfo systemInfo;
	private CentralProcessor cpu;
	private GlobalMemory memory;
	private OperatingSystem os;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				SystemMonitorScreen frame = new SystemMonitorScreen();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public SystemMonitorScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SystemMonitorScreen.class.getResource("/images/gear.png")));
		setTitle("Monitoramento do Sistema");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 518, 250);
		setResizable(false);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("Monitoramento do Sistema");
		lblTitulo.setIcon(new ImageIcon(SystemMonitorScreen.class.getResource("/images/hardware.png")));
		lblTitulo.setForeground(Color.GREEN);
		lblTitulo.setFont(new Font("Consolas", Font.BOLD, 18));
		lblTitulo.setBounds(10, 11, 482, 30);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTitulo);

		lblCpuUsage = new JLabel("CPU: --- %");
		lblCpuUsage.setForeground(Color.GREEN);
		lblCpuUsage.setFont(new Font("Consolas", Font.BOLD, 17));
		lblCpuUsage.setBounds(100, 79, 376, 25);
		contentPane.add(lblCpuUsage);

		lblRamUsage = new JLabel("RAM: --- %");
		lblRamUsage.setForeground(Color.GREEN);
		lblRamUsage.setFont(new Font("Consolas", Font.BOLD, 17));
		lblRamUsage.setBounds(100, 109, 376, 25);
		contentPane.add(lblRamUsage);

		lblUptime = new JLabel("Uptime: ---");
		lblUptime.setForeground(Color.GREEN);
		lblUptime.setFont(new Font("Consolas", Font.BOLD, 17));
		lblUptime.setBounds(100, 139, 376, 25);
		contentPane.add(lblUptime);

		// Inicializa OSHI
		systemInfo = new SystemInfo();
		cpu = systemInfo.getHardware().getProcessor();
		memory = systemInfo.getHardware().getMemory();
		os = systemInfo.getOperatingSystem();

		// Atualizar as informações a cada segundo
		Timer timer = new Timer(1000, e -> atualizarDados());
		timer.start();
	}

	private void atualizarDados() {

		try {
			// CPU
			long[] prevTicks = cpu.getSystemCpuLoadTicks();
			Thread.sleep(1000);
			double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
			lblCpuUsage.setText(String.format("CPU: %.1f %%", cpuLoad));

			// RAM
			long totalMemory = memory.getTotal();
			long availableMemory = memory.getAvailable();
			long usedMemory = totalMemory - availableMemory;
			double usedPercent = ((double) usedMemory / totalMemory) * 100;

			lblRamUsage.setText(String.format("RAM: %.2f GB de %.2f GB (%.1f %%)", usedMemory / 1e9, totalMemory / 1e9, usedPercent));

			// Tempo de atividade
			long uptime = os.getSystemUptime();
			lblUptime.setText("Uptime: " + formatarTempo(uptime));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao monitorar sistema: " + e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String formatarTempo(long segundos) {
		long horas = segundos / 3600;
		long minutos = (segundos % 3600) / 60;
		long segs = segundos % 60;
		return String.format("%02dh %02dm %02ds", horas, minutos, segs);
	}
}
