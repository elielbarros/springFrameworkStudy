package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamicoService {
	private final FuncionarioRepository funcionarioRepository;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public RelatorioFuncionarioDinamicoService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		System.out.println("Digite o nome");
		String nome = scanner.next();
		
		if(nome.equalsIgnoreCase("null")) {
			nome = null;
		}
		
		System.out.println("Digite o cpf");
		String cpf = scanner.next();
		
		if(cpf.equalsIgnoreCase("null")) {
			cpf = null;
		}
		
		System.out.println("Digite o salario");
		Double salario = scanner.nextDouble();
		
		if(salario == 0) {
			salario = null;
		}
		
		System.out.println("Digite a data de contratacao");
		String data = scanner.next();
		
		LocalDate dataContratacao;
		if(data.equalsIgnoreCase("null")) {
			dataContratacao = null;
		} else {
			dataContratacao = LocalDate.parse(data, formatter);
		}
		
		List<Funcionario> listFuncionarios = funcionarioRepository.findAll(
				Specification.where(
						SpecificationFuncionario.nome(nome)
						.or(SpecificationFuncionario.cpf(cpf))
						.or(SpecificationFuncionario.salario(salario))
						.or(SpecificationFuncionario.dataContratacao(dataContratacao))
						), Sort.by(Sort.Direction.DESC, "nome"));
		
		for (Funcionario funcionario : listFuncionarios) {
			Integer id = funcionario.getId();
			String nome2 = funcionario.getNome();
			String cpf2 = funcionario.getCpf();
			Double salario2 = funcionario.getSalario();
			LocalDate dataContratacao2 = funcionario.getDataContratacao();
			StringBuilder infoFuncionario = new StringBuilder("ID: ");
			String resultStringBuilder = infoFuncionario.append(id).append(" | Nome: ").append(nome2).append(" | CPF: ").append(cpf2).append(" | Salario: ").append(salario2).append(" | Data contratacao: ").append(dataContratacao2).toString();
			System.out.println(resultStringBuilder);
		}
	}
}
