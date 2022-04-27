package servicesTeste;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTeste {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {

		//scenery
		Usuario usuario1 = new Usuario("Usuario 1");
		Filme filme = new Filme("Blindes", 3, 5.0);
		LocacaoService service = new LocacaoService();
		Locacao locacao;

		//action
		locacao = service.alugarFilme(usuario1, filme);
		
		//validation
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));

	}
	
	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		//scenery
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Blindes", 0, 5.0);
		LocacaoService service = new LocacaoService();
		
		//action
		service.alugarFilme(usuario, filme);
		
		//validation
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque2() {
		//scenery
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Blindes", 0, 5.0);
		
		//action
		try {
			service.alugarFilme(usuario, filme);
			Assert.fail("should launch a exception");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("out of stock film"));
		}
		
		//validation
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque3() throws Exception {
		//scenery
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Blindes", 0, 5.0);
		LocacaoService service = new LocacaoService();
		exception.expect(Exception.class);
		exception.expectMessage("out of stock film");
		
		//action
		service.alugarFilme(usuario, filme);
		
		//validation
	}

}
