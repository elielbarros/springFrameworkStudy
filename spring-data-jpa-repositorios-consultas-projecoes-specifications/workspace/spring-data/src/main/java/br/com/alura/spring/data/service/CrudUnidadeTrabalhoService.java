package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {
	
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	
	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
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
			visualizar();
			return true;
		default:
			return false;
		}
	}

	private void visualizar() {
		Iterable<UnidadeTrabalho> unidadesTrabalho = unidadeTrabalhoRepository.findAll();
		for(UnidadeTrabalho unidadeTrabalho : unidadesTrabalho) {
			System.out.println(String.format("ID: %i | Descricao: %s | Endereco: %s", unidadeTrabalho.getId(), unidadeTrabalho.getDescricao(), unidadeTrabalho.getEndereco()));
		}
	}

	private void deletar(Scanner scanner) {
		System.out.println("Escolha o id do cargo que voce deseja deletar");
		int idEscolhido = scanner.nextInt();
		unidadeTrabalhoRepository.deleteById(idEscolhido);
		System.out.println("Deletado");
	}

	private void atualizar(Scanner scanner) {
		System.out.println("Escolha o id do cargo que deseja atualizar");
		int idEscolhido = scanner.nextInt();
		String novaDescricao;
		String novoEndereco;
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		
		System.out.println("Deseja mudar a descricao: 0 - Nao | 1 - Sim");
		int escolhaDescricao = scanner.nextInt();
		
		if(escolhaDescricao == 1) {
			System.out.println("Escolha a nova descricao");
			novaDescricao = scanner.next();
			unidadeTrabalho.setDescricao(novaDescricao);
		}
		
		System.out.println("Deseja mudar o endereco: 0 - Nao | 1 - Sim");
		int escolhaEndereco = scanner.nextInt();
		if(escolhaEndereco == 1) {
			novoEndereco = scanner.next();
			unidadeTrabalho.setEndereco(novoEndereco);
		}
		
		unidadeTrabalho.setId(idEscolhido);
		
		unidadeTrabalhoRepository.save(unidadeTrabalho);
		System.out.println("Atualizado");
	}

	private void criar(Scanner scanner) {
		System.out.println("Informe a descricao");
		String descricao = scanner.next();
		
		System.out.println("Informe o endereco");
		String endereco = scanner.next();
		
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setDescricao(descricao);
		unidadeTrabalho.setEndereco(endereco);
		
		unidadeTrabalhoRepository.save(unidadeTrabalho);
		System.out.println("Criado");
	}

}
