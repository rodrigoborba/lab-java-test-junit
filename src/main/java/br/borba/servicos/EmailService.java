package br.borba.servicos;

import br.borba.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);

}
