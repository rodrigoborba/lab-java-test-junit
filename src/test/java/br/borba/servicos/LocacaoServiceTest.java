package br.borba.servicos;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.borba.entidades.Filme;
import br.borba.entidades.Locacao;
import br.borba.entidades.Usuario;
import br.borba.exception.FilmeSemEstoqueException;
import br.borba.exception.LocadoraException;
import br.borba.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException expectedError = ExpectedException.none();
	
	@Test
	public void teste() {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 2, 5.0);
		
		//acao
		Locacao locacao = locacaoService.alugarFilme(user, filme);
		
		//verificacao
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		
	}
	
	/**
	 * usa assetThat
	 */
	@Test
	public void testeThat() {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 2, 5.0);
		
		//acao
		Locacao locacao = locacaoService.alugarFilme(user, filme);
		
		//verificacao
		Assert.assertThat(locacao.getValor(), is(equalTo(5.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
	}
	
	/**
	 * usa rules:
	 * executa todas assetivas, mesmo com falha na primeira
	 */
	@Test
	public void testeCheck() {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 2, 5.0);
		
		//acao
		Locacao locacao = locacaoService.alugarFilme(user, filme);
		
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(6.0)));
		error.checkThat(locacao.getValor(), is(equalTo(7.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
	}
	
	@Test
	public void testeCheckComTratamentoErroSucesso() throws Exception {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 1, 5.0);
		
		//acao
		Locacao locacao = locacaoService.alugarFilmeComTratamentoErro(user, filme);
		
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeCheckComTratamentoErroFalhaElegante() throws FilmeSemEstoqueException, LocadoraException {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 0, 5.0);
		
		//acao
		locacaoService.alugarFilmeComTratamentoErro(user, filme);
		
	}
	
	@Test
	public void testeCheckComTratamentoErroFalhaRobustoSucesso() {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 0, 5.0);
		
		//acao
		try {
			locacaoService.alugarFilmeComTratamentoErro(user, filme);
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
		
	}
	
	@Test
	public void testeCheckComTratamentoErroFalhaRobustoAssertFail() {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 2, 5.0);
		
		//acao
		try {
			locacaoService.alugarFilmeComTratamentoErro(user, filme);
			Assert.fail("Deveria ter lançado exceção");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
		
	}
	
	@Test
	public void testeCheckComTratamentoErroFalhaRobustoSemUsuarioError() throws FilmeSemEstoqueException {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = null;
		Filme filme = new Filme("filme 1 ", 1, 5.0);
		
		//acao
		try {
			locacaoService.alugarFilmeComTratamentoErro(user, filme);
			
			Assert.fail();
			
		}  catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuário vazio"));
		}
		
	}
	
	@Test
	public void testeCheckComTratamentoErroFalhaRobustoAlternativaMaisRecente() throws Exception {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = new Filme("filme 1 ", 2, 5.0);
		
		expectedError.expect(Exception.class);
		expectedError.expectMessage("Filme sem estoque");
		
		//acao
		locacaoService.alugarFilmeComTratamentoErro(user, filme);

		
	}
	
	@Test
	public void testeCheckComTratamentoErroFalhaRobustoAlternativaMaisRecenteSemFilmeError() 
			throws FilmeSemEstoqueException, LocadoraException {
		
		//cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario user = new Usuario();
		Filme filme = null;
		
		expectedError.expect(LocadoraException.class);
		expectedError.expectMessage("Filme vazio");
		
		//acao
		locacaoService.alugarFilmeComTratamentoErro(user, filme);

		
	}

}
