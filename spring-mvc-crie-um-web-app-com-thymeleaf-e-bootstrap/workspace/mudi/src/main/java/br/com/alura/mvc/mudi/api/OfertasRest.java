package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@PostMapping
	public Oferta criaOferta(@Valid @RequestBody RequisicaoNovaOferta requisicao) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(requisicao.getPedidoId());
		
		if(!pedidoOptional.isPresent()) {
			return null;
		}
		
		Pedido pedido = pedidoOptional.get();
		
		Oferta novaOferta = requisicao.toOferta();
		novaOferta.setPedido(pedido);
		pedido.getOfertas().add(novaOferta);
		pedidoRepository.save(pedido);
		
		return novaOferta;
	}
}
