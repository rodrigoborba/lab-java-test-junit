package br.borba.servicos;

import br.borba.entidades.Usuario;

public interface SPCService {

	public boolean possuiNegativacao(Usuario usuario) throws Exception;
}
