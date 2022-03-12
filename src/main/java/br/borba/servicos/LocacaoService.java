package br.borba.servicos;

import static br.borba.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.borba.entidades.Filme;
import br.borba.entidades.Locacao;
import br.borba.entidades.Usuario;
import br.borba.exception.FilmeSemEstoqueException;
import br.borba.exception.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}
	
	public Locacao alugarFilmeComTratamentoErro(Usuario usuario, Filme filme) 
			throws FilmeSemEstoqueException, LocadoraException {
		
		if(filme == null) {
			throw new LocadoraException("Filme vazio");
		}
		
		if(filme.getEstoque() <= 0) {
			throw new FilmeSemEstoqueException("Filme sem estoque");
		}
		
		if(usuario == null ) {
			throw new LocadoraException("Usu�rio vazio");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}