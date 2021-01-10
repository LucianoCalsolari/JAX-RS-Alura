package br.com.alura.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

public class ProjetoDAOTest {

	private HttpServer server;
	private Client client;
	private WebTarget target;
	private Projeto projeto;

	@Before
	public void before() {

		client = ClientBuilder.newClient();
		target = client.target("http://localhost:8080");

		projeto = target.path("/projetos/1").request().get(Projeto.class);
		server = Servidor.inicializaServidor();
	}

	@After
	public void after() {
		server.stop();
	}

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

		String conteudo = target.path("/projetos").request().get(String.class);

		assertTrue(conteudo.contains("Minha loja"));
	}

	@Test
	public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
		assertEquals(1L, projeto.getId(), 0);

	}

}
