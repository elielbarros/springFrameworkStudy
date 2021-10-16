package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.CrudUnidadeTrabalhoService;
import br.com.alura.spring.data.service.RelatoriosService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private boolean system = true;

	private final CrudCargoService crudCargoService;
	private final CrudFuncionarioService crudFuncionarioService;
	private final CrudUnidadeTrabalhoService crudUnidadeTrabalhoService;
	private final RelatoriosService relatoriosService;

	public SpringDataApplication(CrudCargoService crudCargoService, CrudFuncionarioService crudFuncionarioService, CrudUnidadeTrabalhoService crudUnidadeTrabalhoService, RelatoriosService relatoriosService) {
		this.crudCargoService = crudCargoService;
		this.crudFuncionarioService = crudFuncionarioService;
		this.crudUnidadeTrabalhoService = crudUnidadeTrabalhoService;
		this.relatoriosService = relatoriosService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Escolha qual vc deseja criar:");
		System.out.println("0 - Cargo");
		System.out.println("1 - Funcionario");
		System.out.println("2 - Unidade de Trabalho");
		System.out.println("3 - Buscar funcionario por nome");

		String cargo_funcionario_unidade = "";

		int escolha = scanner.nextInt();

		switch (escolha) {
		case 0:
			cargo_funcionario_unidade = "Cargo";
			break;
		case 1:
			cargo_funcionario_unidade = "Funcionario";
			break;
		case 2:
			cargo_funcionario_unidade = "Unidade de Trabalho";
			break;
		case 3:
			relatoriosService.inicial(scanner);
			break;
		default:
			System.out.println("Opcao invalida! Tente novamente");
			break;
		}

		while (system) {
			System.out.println(System.lineSeparator() + "Qual acao vc quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Criar " + cargo_funcionario_unidade);
			System.out.println("2 - Atualizar " + cargo_funcionario_unidade);
			System.out.println("3 - Deletar " + cargo_funcionario_unidade);
			System.out.println("4 - Visualizar " + cargo_funcionario_unidade);
			int action = scanner.nextInt();
			if (action == 0) {
				system = false;
			} else {
				switch (escolha) {
				case 0:
					system = crudCargoService.inicial(scanner, action);
					break;
				case 1:
					system = crudFuncionarioService.inicial(scanner, action);
					break;
				case 2:
					system = crudUnidadeTrabalhoService.inicial(scanner, action);
					break;
				default:
					system = false;
					break;
				}
			}
		}
	}

}
