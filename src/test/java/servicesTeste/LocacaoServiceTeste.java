package servicesTeste;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTeste {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void before() {
		service = new LocacaoService();
	}

	@Test
	public void testeLocacao() throws Exception {

		// scenery
		List<Filme> filmes = new ArrayList<Filme>();
		Usuario usuario1 = new Usuario("Usuario 1");
		Filme filme = new Filme("Blindes", 3, 5.0);
		//Filme filme2 = new Filme("Blindes 2", 3, 5.0);
		Locacao locacao;
		filmes.add(filme);
		//filmes.add(filme2);

		// action
		locacao = service.alugarFilme(usuario1, filmes);

		// validation
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));

	}

	// elegant shape
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// scenery
		List<Filme> filmes = new ArrayList<Filme>();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Blindes", 0, 5.0);

		filmes.add(filme);
		// action
		service.alugarFilme(usuario, filmes);

		// validation
	}

	// robust shape
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// scenery
		List<Filme> filmes = new ArrayList<Filme>();
		Filme filme = new Filme("Blindes", 1, 5.0);
		
		filmes.add(filme);
		// action
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("should launch a exception");
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("user empty"));
		}
	}

	// new shape
	@Test
	public void testeLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// scenery
		Usuario usuario = new Usuario("Usuario 1");
		exception.expect(LocadoraException.class);
		exception.expectMessage("film empty");

		// action
		service.alugarFilme(usuario, null);
	}

}
