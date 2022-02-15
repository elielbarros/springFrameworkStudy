package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @RequestMapping("/topicos")
    public List<TopicoDto> lista(String nomeCurso) {

        // OBS.: Se eu tenho variaveis com nomes iguais, em topico e em curso
        // Posso diferencia-los usando underline, para destacar que quero uma
        // Informação que vem do relacionamento
        // Por exemplo
        return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso));
    }

}
