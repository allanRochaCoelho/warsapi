package com.b2wdigital.servicos;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.b2wdigital.persistence.Dao;
import com.b2wdigital.pojo.Planeta;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PlanetaServiceTest {

	Planeta p;
	Random aleatorio = new Random();
	
	@Test
	public void adicionaPlaneta() {
		String nomePlaneta = geraNomePlaneta("naboo");
		this.p = new Planeta(nomePlaneta, Arrays.asList("rock", "desert", "mountain", "barren"), Arrays.asList("temperate", "arid"));
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/";
		
		given().contentType("application/json").body(this.p).when().post(uriPlanetas).andReturn().jsonPath();
		
		Dao d = new Dao();
		
		Planeta planeta = d.buscaPlanetaPorNome(nomePlaneta).get(0);
		
		assertEquals(this.p, planeta);
		
	}
	
	@Test
	public void atualizaPlaneta() {
		Dao d = new Dao();
		
		cadastraPlaneta(d, "Geonosis");
		
		this.p.setNome(this.p.getNome().replaceAll("Teste de API", "Teste de API - alter"));
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/"+this.p.getId();
		
		given().contentType("application/json").body(this.p).when().put(uriPlanetas).andReturn().jsonPath();
		
		
		Planeta planeta = d.buscaPlanetaPorNome(p.getNome()).get(0);
		
		assertEquals(this.p, planeta);
		
	}

	@Test(expected=RuntimeException.class)
	public void atualizaPlanetaInexistente() {
		this.p.setNome(this.p.getNome().replaceAll("Teste de API", "Teste de API - alter"));
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/"+new ObjectId().toHexString();
		
		given().contentType("application/json").body(this.p).when().put(uriPlanetas).andReturn().jsonPath();
		
	}
	
	@Test
	public void buscaPorId() {
		Dao d = new Dao();
		
		cadastraPlaneta(d, "Bespin");
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/"+this.p.getId();
		
		JsonPath jsonp = given().contentType("application/json").when().get(uriPlanetas).andReturn().jsonPath();
		
		Planeta planeta = jsonp.getObject("", Planeta.class);
		
		assertEquals(planeta, this.p);		
	}
	
	@Test//(expected=RuntimeException.class)
	public void buscaPorIdInexistente() {
		Dao d = new Dao();
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/"+new ObjectId().toHexString();
		
		Response r = given().contentType("application/json").when().get(uriPlanetas);//.andReturn().jsonPath();
		
		Integer erro = r.getStatusCode(); 
		Integer retornoEsperado = 500;
		
		assertEquals(erro, retornoEsperado);		
	}
	
	@Test
	public void buscaPorNome() {
		Dao d = new Dao();
		
		cadastraPlaneta(d, "Kamino");
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/nome/teste";
		
		JsonPath jsonp = given().contentType("application/json").when().get(uriPlanetas).andReturn().jsonPath();
		
		List<Planeta> lista = jsonp.getList("", Planeta.class);
		
		Boolean maiorZero = (lista.size()>0);
		assertEquals(Boolean.TRUE, maiorZero);	
	}
		
	@Test
	public void buscaPorNomeInexistente() {		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/nome/vulcano";
		
		Response r =  given().contentType("application/json").when().get(uriPlanetas);//.andReturn().jsonPath();
		Integer erro = r.getStatusCode(); 
		Integer retornoEsperado = 500;
		
		assertEquals(erro, retornoEsperado);	
	}
	
	@Test(expected=RuntimeException.class)
	public void deletaPlaneta() {
		Dao d = new Dao();
		
		cadastraPlaneta(d, "Endor");
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/"+this.p.getId();
		
		given().contentType("application/json").when().delete(uriPlanetas).andReturn().jsonPath();
		
		this.p = d.buscaPlanetaPorId(this.p.getId());
		
		assertFalse(Boolean.TRUE);	
		
		
	}
	
	@Test
	public void deletaPlanetaInexistente() {
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/"+new ObjectId().toHexString();
		
		Response r =  given().contentType("application/json").when().delete(uriPlanetas);
		
		Integer erro = r.getStatusCode(); 
		Integer retornoEsperado = 500;
		
		assertEquals(erro, retornoEsperado);	
		
		
	}
	
	@Test
	public void listaPlanetas() {
		Dao d = new Dao();
		
		cadastraPlaneta(d, "Kamino");
		
		String uriPlanetas = "http://localhost:8080/warsapi/api/planetas/";
		
		JsonPath jsonp = given().contentType("application/json").when().get(uriPlanetas).andReturn().jsonPath();
		
		List<Planeta> lista = jsonp.getList("", Planeta.class);
		
		Boolean maiorZero = (lista.size()>0);
		assertEquals(Boolean.TRUE, maiorZero);	
	}
	
	private void cadastraPlaneta(Dao d, String nome) {
		this.p = new Planeta(geraNomePlaneta(nome), Arrays.asList("rock", "desert", "mountain", "barren"), Arrays.asList("temperate", "arid"));
		d.cadastraPlaneta(p);
	}
	
	
	private String geraNomePlaneta(String nome) {
		String nomePlaneta = nome+" Teste de API "+this.aleatorio.nextInt();
		return nomePlaneta;
	}
	
}
