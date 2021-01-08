package br.com.alura.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;

public class ClienteTest {

	private HttpServer server;

	@Before
	public void before() {

		server = Servidor.inicializaServidor();
	}
	
	@After
	public void after() {
		server.stop();
	}
	
	@Test
	public void testaQueAConexaoComOServidor() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://www.mocky.io");

		String conteudo = target.path("/v2/52aaf5bbee7ba8c60329fb7b").request().get(String.class);

		assertTrue(conteudo.contains("Rua Vergueiro 3185"));
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/");
		String conteudo = target.path("/carrinhos").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
}
