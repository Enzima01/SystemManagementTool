package user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UserManager {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("\n=== Gerenciamento de Usuarios ===");
			System.out.println("1. Listar usuários");

			System.out.print("Digite: ");
			int opcao = sc.nextInt();
			sc.nextLine();

			if (opcao == 1) {
				listarUsuarios();
			} else {
				System.out.println("Opção invalida!");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
	}

	private static void listarUsuarios() throws Exception {
		String comando = isWindows() ? "net user" : "cat /etc/passwd";
		Process processo = Runtime.getRuntime().exec(comando);
		BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));

		String linha;
		System.out.println("\n--- Usuarios do Sistema ---");
		while ((linha = reader.readLine()) != null) {
			System.out.println(linha);
		}
	}

	private static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}
}