package application;

import java.util.Scanner;

import info.About;
import monitor.SystemMonitor;
import process.ProcessControl;
import user.UserManager;

public class MainAdminTool {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        while (true) {
	            System.out.println("\n=== Ferramenta de Administração ===");
	            System.out.println("1. Monitorar sistema");
	            System.out.println("2. Controlar processos");
	            System.out.println("3. Gerenciar usuários");
	            System.out.println("4. Sobre");
	            System.out.println("0. Sair");
	            System.out.print("Escolha: ");
	            
	            int opcao = scanner.nextInt();
	            scanner.nextLine();

	            switch (opcao) {
	                case 1 -> SystemMonitor.main(null);
	                case 2 -> ProcessControl.main(null);
	                case 3 -> UserManager.main(null);
	                case 4 -> About.main(null);
	                case 0 -> {
	                    System.out.println("Saindo...");
	                    scanner.close();
	                    return;
	                }
	                default -> System.out.println("Opção inválida!");
	            }
	        }
	    }
	}
