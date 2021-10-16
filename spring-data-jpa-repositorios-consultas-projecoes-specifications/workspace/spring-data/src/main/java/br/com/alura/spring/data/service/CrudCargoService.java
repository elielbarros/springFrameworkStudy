package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {
	private final CargoRepository cargoRepository;
	
	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
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
		Iterable<Cargo> cargos = cargoRepository.findAll();
		for(Cargo cargo : cargos) {
			System.out.println("Id: " + cargo.getId() + " | Descricao: " + cargo.getDescricao());
		}
	}

	private void deletar(Scanner scanner) {
		System.out.println("Escolha o id do cargo que voce deseja deletar");
		int idEscolhido = scanner.nextInt();
		cargoRepository.deleteById(idEscolhido);
		System.out.println("Deletado");
	}

	private void criar(Scanner scanner) {
		System.out.println("Descricao do cargo");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		System.out.println("Salvo");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Escolha o id do cargo que deseja atualizar");
		int idEscolhido = scanner.nextInt();
		System.out.println("Escolha a nova descricao");
		String novaDescricao = scanner.next();
		
		Cargo updatedCargo = new Cargo();
		updatedCargo.setId(idEscolhido);
		updatedCargo.setDescricao(novaDescricao);
		
		cargoRepository.save(updatedCargo);
		System.out.println("Atualizado");
	}
}
