package com.b2wdigital.servicos;

import java.util.List;

import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;




import com.b2wdigital.pojo.PlanetOfSwapi;

public class PlanetServiceOfSwapi {
	
	private List<PlanetOfSwapi> lista;
	
	public PlanetServiceOfSwapi() {
		this.lista = listarUsuarios();
	}
	
	
	public List<PlanetOfSwapi> listarUsuarios(){
		
		String uriBase = "https://swapi.co/api/planets";
		JsonPath path = given().header("Accept", "application/json").get(uriBase).andReturn().jsonPath();
		
		List<PlanetOfSwapi> lista = path.getList("results", PlanetOfSwapi.class); //path.getObject("results", ArrayList.class);
		
		
		return lista;
		
	}

	public Integer getQtdAparicoesEmFilmes(String nomePlaneta) {
		for (PlanetOfSwapi p : this.lista) {
			if(p.getName().equalsIgnoreCase(nomePlaneta)) {
				return p.getFilms().size();
			}
		}
		
		return 0;
	}
	
	
	
}
