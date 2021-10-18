package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {
	private Boolean system = true;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println(System.lineSeparator() + "Qual acao vc quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionario por nome");
			System.out.println("2 - Busca funcionario por nome, data contratacao e salario maior");
			System.out.println("3 - Busca funcionario por data contratacao maior");
			System.out.println("4 - Busca funcionario por salario");
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioDataMaior(scanner);
				break;
			case 4:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
			
		}
	}
	
	private void buscaFuncionarioNome(Scanner scanner) {
		//System.out.println("Qual pagina vc deseja visualizar");
		//Integer page = scanner.nextInt() - 1;
		
		//Pageable pageable = PageRequest.of(page, 1, Sort.unsorted()); 
		
		System.out.println("Qual nome deseja pesquisar");
		String name = scanner.next();
		List<Funcionario> listFuncionario = funcionarioRepository.findByNome(name);
		//List<Funcionario> listFuncionario = funcionarioRepository.findByNome(name, pageable);
		
		for(Funcionario funcionario : listFuncionario) {
			System.out.println(funcionario.getNome());
		}
	}
	
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome");
		String nome = scanner.next();
		
		System.out.println("Qual data contratacao");
		String dataContratacao = scanner.next();
		LocalDate localDate = LocalDate.parse(dataContratacao, formatter);
		
		System.out.println("Qual salario");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> listaFuncionario = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		
		for(Funcionario funcionario : listaFuncionario) {
			String nome2 = funcionario.getNome();
			double salario2 = funcionario.getSalario();
			LocalDate dataContratacao2 = funcionario.getDataContratacao();
			StringBuilder infoFuncionario = new StringBuilder("Nome: ");
			String infoFuncionarioString = infoFuncionario.append(nome2).append(" | Salario: ").append(salario2).append(" | Data contratacao: ").append(dataContratacao2).toString();
			System.out.println(infoFuncionarioString);
		}
	}
	
	private void buscaFuncionarioDataMaior(Scanner scanner) {
		System.out.println("Informe data no formato dd/mm/yyyy");
		String dataContratacao = scanner.next();
		LocalDate localDate = LocalDate.parse(dataContratacao, formatter);
		List<Funcionario> listFuncionarios = funcionarioRepository.findDataContratacaoMaior(localDate);
		
		for(Funcionario funcionario : listFuncionarios) {
			String nome = funcionario.getNome();
			LocalDate dataContratacao2 = funcionario.getDataContratacao();
			
			StringBuilder infoFuncionario = new StringBuilder();
			String infoFuncionarioString = infoFuncionario.append("Nome: ").append(nome).append(" | Data contratacao: ").append(dataContratacao2).toString();
			System.out.println(infoFuncionarioString);
		}
		
	}
	
	private void buscaFuncionarioSalario() {
		List<FuncionarioProjecao> listFuncionariosProjecao = funcionarioRepository.findFuncionarioSalario();
		
		for (FuncionarioProjecao funcionarioProjecao : listFuncionariosProjecao) {
			Integer id = funcionarioProjecao.getId();
			String nome = funcionarioProjecao.getNome();
			Double salario = funcionarioProjecao.getSalario();
			StringBuilder infoFuncionario = new StringBuilder("ID: ");
			String infoFuncionarioString = infoFuncionario.append(id).append(" | Nome: ").append(nome).append(" | Salario: ").append(salario).toString();
			System.out.println(infoFuncionarioString);
		}
	}
}
