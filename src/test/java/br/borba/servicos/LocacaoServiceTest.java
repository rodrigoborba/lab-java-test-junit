package br.borba.servicos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.borba.entidades.Filme;
import br.borba.entidades.Locacao;
import br.borba.entidades.Usuario;
import br.borba.utils.DataUtils;

public class LocacaoServiceTest {
	
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

}
