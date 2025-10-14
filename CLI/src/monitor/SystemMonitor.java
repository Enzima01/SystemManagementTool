package monitor;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class SystemMonitor {
	public static void main(String[] args) {
		try {
			SystemInfo systemInfo = new SystemInfo();
			HardwareAbstractionLayer hardware = systemInfo.getHardware();

			CentralProcessor cpu = hardware.getProcessor();
			GlobalMemory memory = hardware.getMemory();

			long[] prevTicks = cpu.getSystemCpuLoadTicks();
			Thread.sleep(1000);

			double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
			long totalMemory = memory.getTotal();
			long usedMemory = totalMemory - memory.getAvailable();

			System.out.println("\n=== Monitoramento do Sistema ===");
			System.out.printf("Uso de CPU: %.2f%%%n", cpuLoad);
			System.out.printf("Memória: %.2f GB de %.2f GB%n\n", usedMemory / 1e9, totalMemory / 1e9);
		} catch (Exception e) {
			System.out.println("Erro ao monitorar sistema: " + e.getMessage());
		}
	}
}