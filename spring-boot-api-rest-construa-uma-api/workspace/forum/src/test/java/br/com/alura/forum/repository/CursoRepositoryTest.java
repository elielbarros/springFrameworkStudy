package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        String nomeCurso = "HTML 5";
        Curso cursoByNome = cursoRepository.findByNome(nomeCurso);
        Assert.assertNotNull(cursoByNome);
        Assert.assertEquals(nomeCurso, cursoByNome.getNome());
    }

    @Test
    public void naoDeveriaCarregarUmCursoCujoNomeNaoEstaCadastrado() {
        String nomeCurso = "JPA";
        Curso cursoByNome = cursoRepository.findByNome(nomeCurso);
        Assert.assertNull(cursoByNome);
    }
}
