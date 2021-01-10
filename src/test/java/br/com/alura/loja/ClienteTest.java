package br.com.alura.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTest {

	private HttpServer server;
	private WebTarget target;
	private Client client;

	@Before
	public void before() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8080");
	}

	@After
	public void after() {
		server.stop();
	}

	@Test
	public void testaQueAConexaoComOServidor() {
		WebTarget target = client.target("http://www.mocky.io");
		String conteudo = target.path("/v2/52aaf5bbee7ba8c60329fb7b").request().get(String.class);
		assertTrue(conteudo.contains("Rua Vergueiro 3185"));
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		Carrinho carrinho = target.path("/carrinhos").request().get(Carrinho.class);
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}

	@Test
	public void testaMetodoPostDeCarrinho() {

		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
		carrinho.setRua("Rua Vergueiro");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

		Response response = target.path("/carrinhos").request().post(entity);
		assertEquals(201, response.getStatus());
	}

	@Test
	public void testaSuporteANovosCarrinhosString() {
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Microfone", 999, 1));
		carrinho.setRua("Rua Vergueiro");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().post(entity);

		assertEquals(201, response.getStatus());

		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);

		assertTrue(conteudo.contains("Microfone"));
	}

	@Test
    public void testaQueSuportaNovosCarrinhos(){
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");

        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
        Response response = target.path("/carrinhos").request().post(entity);
        assertEquals(201, response.getStatus());
    }
    @Test
    public void testaQueBuscarUmCarrinhoTrasUmCarrinho() {
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
        assertEquals("Rua Vergueiro 3185, 8 andar",carrinho.getRua());
    }
	
}
