package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Funcionario;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		Projeto projeto = new ProjetoDAO().busca(1l);
		return projeto.toJson();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(Projeto projeto) {
		new ProjetoDAO().adiciona(projeto);
		URI uri = URI.create("/projetos/" + projeto.getId());
		return Response.created(uri).build();
	}
	
	@Path("{id}/funcionarios/{funcionarioId}")
	@DELETE
	@Produces(MediaType.APPLICATION_XML)
	public Response remove(@PathParam("id") long id, @PathParam("funcionarioId") long funcionarioId) {
		Projeto projeto = new ProjetoDAO().busca(id);
		projeto.remove(funcionarioId);
		return Response.ok().build();
	}
	
	@Path("{id}/funcionarios/{funcionarioId}")
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	public Response altera(String conteudo ,@PathParam("id")long id, @PathParam("produtoId") long produtoId) {
		Projeto projeto = new ProjetoDAO().busca(id);
		Funcionario funcionario = (Funcionario) new XStream().fromXML(conteudo);
		projeto.troca(funcionario);
		
		return Response.ok().build();
	}
	
	@Path("{id}/funcionarios/{funcionarioId}/quantidade")
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	public Response alteraQuantidade(String conteudo ,@PathParam("id")long id, @PathParam("produtoId") long produtoId) {
		Projeto projeto = new ProjetoDAO().busca(id);
		Funcionario funcionario = (Funcionario) new XStream().fromXML(conteudo);
		projeto.trocaQuantidade(funcionario);
		
		return Response.ok().build();
	}
}
