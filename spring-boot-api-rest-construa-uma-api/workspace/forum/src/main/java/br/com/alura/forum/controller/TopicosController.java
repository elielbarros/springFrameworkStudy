package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public Page<TopicoDto> lista(
            @RequestParam(required = false) String nomeCurso,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao
    ) {

        // OBS.: Se eu tenho variaveis com nomes iguais, em topico e em curso
        // Posso diferencia-los usando underline, para destacar que quero uma
        // Informação que vem do relacionamento
        // Por exemplo
        //return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso));

        if (nomeCurso == null) {
            return TopicoDto.converter(topicoRepository.findAll(paginacao));
        } else {
            return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso, paginacao));
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> topicoById = topicoRepository.findById(id);
        if (topicoById.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
