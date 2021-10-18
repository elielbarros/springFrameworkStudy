package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
			UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.cargoRepository = cargoRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public boolean inicial(Scanner scanner, int action) {
		switch (action) {
		case 1:
			criar(scanner);
			return true;
		case 2:
			atualizar(scanner);
			return true;
		case 3:
			deletar(scanner);
			return true;
		case 4:
			visualizar(scanner);
			return true;
		default:
			return false;
		}
	}

	private void visualizar(Scanner scanner) {
		System.out.println("Qual pagina vc deseja visualizar");
		Integer page = scanner.nextInt() - 1;
		
		Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "nome"));
		
		Page<Funcionario> funcionariosList = funcionarioRepository.findAll(pageable);
		
		System.out.println("Numero de paginas: " + funcionariosList);
		System.out.println("Pagina atual: " + funcionariosList.getNumber());
		System.out.println("Quantidade total de elementos : " + funcionariosList.getTotalElements());

		for (Funcionario funcionario : funcionariosList) {
			Integer id = funcionario.getId();
			String nome = funcionario.getNome();
			String cpf = funcionario.getCpf();
			Double salario = funcionario.getSalario();
			LocalDate dataContratacao = funcionario.getDataContratacao();
			StringBuilder infoFuncionario = new StringBuilder("ID: ");
			String resultStringBuilder = infoFuncionario.append(id).append(" | Nome: ").append(nome).append(" | CPF: ").append(cpf).append(" | Salario: ").append(salario).append(" | Data contratacao: ").append(dataContratacao).toString();
			System.out.println(resultStringBuilder);
		}

	}

	private void deletar(Scanner scanner) {
		System.out.println("Informe Id do funcionario a ser deletado");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Deletado");
	}

	private void atualizar(Scanner scanner) {
		System.out.println("Informe o id do funcionario a ser atualizado");
		Integer id = scanner.nextInt();

		System.out.println("Informe o nome do funcionario");
		String nome = scanner.next();

		System.out.println("Informe CPF");
		String cpf = scanner.next();

		System.out.println("Informe salario");
		//Double salario = scanner.nextDouble();
		Double salario = scanner.nextDouble();

		System.out.println("Informe data de contratacao");
		String dataContratacao = scanner.next();

		System.out.println("Informe id do cargo");
		Integer cargoId = scanner.nextInt();

		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());

		funcionarioRepository.save(funcionario);
		System.out.println("Alterado");
	}

	private void criar(Scanner scanner) {
		System.out.println("Informe o nome do funcionario");
		String nome = scanner.next();

		System.out.println("Informe CPF");
		String cpf = scanner.next();

		System.out.println("Informe salario");
		Double salario = scanner.nextDouble();

		System.out.println("Informe data de contratacao");
		String dataContratacao = scanner.next();

		System.out.println("Informe id do cargo");
		Integer cargoId = scanner.nextInt();

		System.out.println("Informe o(s) id da(s) unidade(s) que o funcionario podera ter");
		List<UnidadeTrabalho> unidades = unidade(scanner);

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionario.setUnidadeTrabalho(unidades);

		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");
	}

	private List<UnidadeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();

		while (isTrue) {
			System.out.println("Para sair digite 0");
			
			Integer unidadeId = scanner.nextInt();

			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}

		return unidades;
	}
}
