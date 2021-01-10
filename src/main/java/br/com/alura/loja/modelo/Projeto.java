package br.com.alura.loja.modelo;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Projeto {

	public Projeto() {
		super();
	}
	private long id;
	private String nome;
	private int anoDeInicio;
	private List<Funcionario> funcionarios;
	
	public Projeto(long id, String nome, int anoDeInicio) {
		this.id = id;
		this.nome = nome;
		this.anoDeInicio = anoDeInicio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getAnoDeInicio() {
		return anoDeInicio;
	}
	public void setAnoDeInicio(int anoDeInicio) {
		this.anoDeInicio = anoDeInicio;
	}
	public String toXML() {
		return new XStream().toXML(this);
	}
	public String toJson() {
		return new Gson().toJson(this);
	}
	public void remove(long id) {
		for (Iterator iterator = funcionarios.iterator(); iterator.hasNext();) {
			Funcionario funcionario = (Funcionario) iterator.next();
			if (funcionario.getId() == id) {
				iterator.remove();
			}
		}
	}
	public void troca(Funcionario funcionario) {
		remove(funcionario.getId());
		adiciona(funcionario);
	}
	public Projeto adiciona(Funcionario funcionario) {
		funcionarios.add(funcionario);
		return this;
	}
	public void trocaQuantidade(Funcionario funcionario) {
		for (Iterator iterator = funcionarios.iterator(); iterator.hasNext();) {
			Produto p = (Produto) iterator.next();
			if (p.getId() == funcionario.getId()) {
				p.setQuantidade(funcionario.getQuantidade());
				return;
			}
		}
	}
	
}
