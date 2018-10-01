package com.b2wdigital.pojo;

import java.util.List;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bson.types.ObjectId;

//import com.b2wdigital.adapters.ObjectIdAdapter;


//
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class Planeta {

	//@XmlJavaTypeAdapter(ObjectIdAdapter.class)
	private ObjectId id;
	private String nome;
	private List<String> terreno;
	private List<String> clima;
	
	private Integer aparicoesEmFilmes;
	
	public Planeta() {
		
	}
	
	public Planeta(String nome, List<String> terreno, List<String> clima) {
		this.nome = nome;
		this.terreno = terreno;
		this.clima = clima;
	}

	public Planeta geraId() {
		this.id = new ObjectId();
		return this;
	}

	public String getStringId() {
		return id.toHexString();
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getTerreno() {
		return terreno;
	}

	public void setTerreno(List<String> terreno) {
		this.terreno = terreno;
	}

	public List<String> getClima() {
		return clima;
	}

	public void setClima(List<String> clima) {
		this.clima = clima;
	}

	public Integer getAparicoesEmFilmes() {
		return aparicoesEmFilmes;
	}

	public void setAparicoesEmFilmes(Integer aparicoesEmFilmes) {
		this.aparicoesEmFilmes = aparicoesEmFilmes;
	}

	@Override
	public String toString() {
		return this.id+") nome: " + this.nome+", terreno: "+this.terreno+", clima: "+this.clima;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Planeta planeta = (Planeta) obj;
		
		if(!this.nome.equals(planeta.getNome())) {
			return Boolean.FALSE;
		}
		
		if(!this.clima.equals(planeta.getClima())) {
			return Boolean.FALSE;
		}
		
		if(!this.terreno.equals(planeta.getTerreno())) {
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
}
