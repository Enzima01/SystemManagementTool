package screens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.software.os.OperatingSystem;

public class InfoHardware extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoHardware frame = new InfoHardware();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InfoHardware() {
		setTitle("Informações do Dispositivo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoHardware.class.getResource("/images/gear.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 470);
		setResizable(false);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblTitulo = new JLabel("Informações do Dispositivo");
		lblTitulo.setIcon(new ImageIcon(InfoHardware.class.getResource("/images/info32x32.png")));
		lblTitulo.setForeground(Color.GREEN);
		lblTitulo.setFont(new Font("Consolas", Font.BOLD, 22));
		lblTitulo.setBounds(138, 19, 453, 40);
		contentPane.add(lblTitulo);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.GREEN);
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		textArea.setLineWrap(false);
		textArea.setText(getSystemInfo());
		javax.swing.SwingUtilities.invokeLater(() -> textArea.setCaretPosition(0));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(20, 70, 590, 350);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		// Mostra as informações
		textArea.setText(getSystemInfo());
	}

	/**
	 * Coleta as informações do sistema usando OSHI e comandos nativos (fallback).
	 */
	private String getSystemInfo() {
		StringBuilder sb = new StringBuilder();
		SystemInfo si = new SystemInfo();

		// SISTEMA OPERACIONAL
		OperatingSystem os = si.getOperatingSystem();
		sb.append("=== SISTEMA OPERACIONAL ===\n");
		sb.append(os).append("\n");
		sb.append("Versão: ").append(os.getVersionInfo()).append("\n");
		sb.append("Arquitetura: ").append(System.getProperty("os.arch")).append("\n\n");

		// PLACA-MÃE
		ComputerSystem system = si.getHardware().getComputerSystem();
		String fabricante = fixValue(system.getBaseboard().getManufacturer());
		String modelo = fixValue(system.getBaseboard().getModel());
		String versao = fixValue(system.getBaseboard().getVersion());
		String serial = fixValue(system.getBaseboard().getSerialNumber());

		// Se o OSHI falhou, tenta via comando do sistema
		if (modelo.equals("Não identificado") || serial.equals("Não identificado")) {
			String[] boardInfo = getMotherboardInfoFromSystem();
			if (boardInfo != null) {
				fabricante = fixValue(boardInfo[0]);
				modelo = fixValue(boardInfo[1]);
				serial = fixValue(boardInfo[2]);
			}
		}

		sb.append("=== PLACA-MÃE ===\n");
		sb.append(modelo).append(" ");
		sb.append(fabricante).append("\n");
		
		sb.append("Versão: ").append(versao).append("\n");
		sb.append("Número de série: ").append(serial).append("\n\n");

		// CPU
		CentralProcessor cpu = si.getHardware().getProcessor();
		sb.append("=== CPU ===\n");
		sb.append(cpu.getProcessorIdentifier().getName()).append("\n");
		sb.append("Núcleos: ").append(cpu.getPhysicalProcessorCount()).append(" | ");
		sb.append("Threads: ").append(cpu.getLogicalProcessorCount()).append("\n\n");

		// MEMÓRIA
		GlobalMemory memory = si.getHardware().getMemory();
		sb.append("=== MEMÓRIA RAM ===\n");
		sb.append(String.format("Total: %.2f GB%n%n", memory.getTotal() / (1024.0 * 1024 * 1024)));

		// DISCOS (HDs / SSDs)
		sb.append("=== UNIDADES DE ARMAZENAMENTO ===\n");
		si.getHardware().getDiskStores().forEach(disk -> {
			sb.append("Nome: ").append(disk.getName()).append("\n");
			sb.append("Modelo: ").append(fixValue(disk.getModel())).append("\n");
			sb.append(String.format("Tamanho: %.2f GB%n", disk.getSize() / (1024.0 * 1024 * 1024)));

			// Heurística para identificar SSD x HDD
			String modeloDisk = disk.getModel().toLowerCase();
			if (modeloDisk.contains("ssd") || modeloDisk.contains("nvme")) {
				sb.append("Tipo: SSD\n");
			} else if (modeloDisk.contains("hdd") || modeloDisk.contains("st") || modeloDisk.contains("wd")
					|| modeloDisk.contains("seagate")) {
				sb.append("Tipo: HDD\n");
			} else {
				sb.append("Tipo: Não identificado\n");
			}

			// Interface (estimativa)
			if (modeloDisk.contains("nvme")) {
				sb.append("Interface: NVMe\n");
			} else if (modeloDisk.contains("usb")) {
				sb.append("Interface: USB\n");
			} else if (modeloDisk.contains("sata")) {
				sb.append("Interface: SATA\n");
			} else {
				sb.append("Interface: Desconhecida\n");
			}

			sb.append("\n");
		});

		// GPU
		sb.append("=== GPU ===\n");
		for (GraphicsCard gpu : si.getHardware().getGraphicsCards()) {
			sb.append(gpu.getName()).append("\n");
			sb.append("Memória: ").append(String.format("%.1f GB", gpu.getVRam() / (1024.0 * 1024 * 1024))).append("\n");
			sb.append(gpu.getVersionInfo()).append("\n");
		}

		return sb.toString();

	}

	/**
	 * Substitui valores inválidos por "Não identificado".
	 */
	private String fixValue(String value) {
		if (value == null || value.isBlank() || value.equalsIgnoreCase("unknown")
				|| value.equalsIgnoreCase("Default string") || value.equalsIgnoreCase("To Be Filled By O.E.M.")) {
			return "Não identificado";
		}
		return value;
	}

	/**
	 * Executa comandos nativos do sistema para obter informações da placa-mãe.
	 * Funciona em Windows e Linux.
	 */
	private String[] getMotherboardInfoFromSystem() {
		String os = System.getProperty("os.name").toLowerCase();
		try {
			Process process;
			if (os.contains("win")) {
				// Windows
				process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c",
						"wmic baseboard get Manufacturer,Product,SerialNumber /format:list" });
			} else {
				// Linux
				process = Runtime.getRuntime()
						.exec(new String[] { "bash", "-c", "cat /sys/class/dmi/id/board_{vendor,name,serial}" });
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			String fabricante = "Não identificado";
			String modelo = "Não identificado";
			String serial = "Não identificado";

			while ((line = reader.readLine()) != null) {
				if (line.toLowerCase().contains("manufacturer") || line.toLowerCase().contains("vendor")) {
					fabricante = line.split("[:=]")[1].trim();
				} else if (line.toLowerCase().contains("product") || line.toLowerCase().contains("name")) {
					modelo = line.split("[:=]")[1].trim();
				} else if (line.toLowerCase().contains("serial")) {
					serial = line.split("[:=]")[1].trim();
				}
			}
			reader.close();
			return new String[] { fabricante, modelo, serial };
		} catch (Exception e) {
			return null;
		}
	}
}
