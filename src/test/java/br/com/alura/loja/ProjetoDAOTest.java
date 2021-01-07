package br.com.alura.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

public class ProjetoDAOTest {

	@Test
	public void deveRetornarNomePeloIdDeNovoProjeto() {

		ProjetoDAO dao = new ProjetoDAO();
		Projeto projeto = new Projeto(2l, "Loja T", 2013);
		dao.adiciona(projeto);

		assertEquals("Loja T", dao.busca(2L).getNome());
	}

	@Test
	public void deveRemoverUmProjeto() {

		ProjetoDAO dao = new ProjetoDAO();

		dao.remove(1l);

		assertNull(dao.busca(1l));
	}
	
	@Test
	public void testaQueAConexaoComURI() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");

		String conteudo = target.path("/projetos").request().get(String.class);

		assertTrue(conteudo.contains("Minha loja"));
	}
	
}
