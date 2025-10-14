package process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ProcessControl {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("\n=== Controle de Processos ===");
			System.out.println("1. Listar processos");
			System.out.println("2. Encerrar processo por nome ou PID");
			System.out.print("Escolha: ");
			int opcao = sc.nextInt();
			sc.nextLine();

			if (opcao == 1) {
				listarProcessos();
			} else if (opcao == 2) {
				System.out.print("Digite o nome ou PID do processo: ");
				String alvo = sc.nextLine();
				encerrarProcesso(alvo);
			} else {
				System.out.println("Opcao invalida!");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

	private static void listarProcessos() throws Exception {
		String comando = isWindows() ? "tasklist" : "ps -e";
		Process processo = Runtime.getRuntime().exec(comando);
		BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

		String linha;
		System.out.println("\n--- Processos Ativos ---");
		while ((linha = reader.readLine()) != null) {
			System.out.println(linha);
		}
	}

	private static void encerrarProcesso(String alvo) throws Exception {
		String comando;
		if (isWindows()) {
			comando = "taskkill /F /IM " + alvo + " /T";
		} else {
			comando = "pkill -f " + alvo;
		}

		Process processo = Runtime.getRuntime().exec(comando);
		processo.waitFor();

		System.out.println("Processo " + alvo + " encerrado (se existia).");
	}

	private static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}
}