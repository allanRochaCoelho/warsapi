package com.b2wdigital.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import org.junit.Test;

import com.b2wdigital.pojo.Planeta;


public class DaoTest {

	Dao d = new Dao();
	Planeta p;
	
	@Test
	public void cadastraPlaneta() {
		this.p = new Planeta("Geonosis", Arrays.asList("rock", "desert", "mountain", "barren"), Arrays.asList("temperate", "arid"));
		d.cadastraPlaneta(p);
		
		Planeta planeta = d.buscaPlanetaPorId(p.getId());
		
		assertEquals(p, planeta);
		
	}
	
	
	@Test
	public void atualizaPlanetaCadastratoAnteriormente() {
		this.p = new Planeta("Geonosis to alter", Arrays.asList("rock", "desert", "mountain", "barren"), Arrays.asList("temperate", "arid"));
		d.cadastraPlaneta(p);
		p.setNome("Geonosis");
		
		d.atualizaPlaneta(p);		
		
		Planeta planeta = d.buscaPlanetaPorId(p.getId());
		
		assertEquals(p, planeta);
	}
	
	
	
	@Test
	public void listaPlanetas() {
		List<Planeta> lista = d.listaPlanetas();
		
		Boolean maiorZero = (lista.size()>0);

		assertEquals(Boolean.TRUE, maiorZero);		
	}
	
	@Test
	public void buscaPlanetaPorIdExistente() {
		this.p = new Planeta("Geonosis to search", Arrays.asList("rock", "desert", "mountain", "barren"), Arrays.asList("temperate", "arid"));
		d.cadastraPlaneta(p);
		
		Planeta planeta = d.buscaPlanetaPorId(p.getId());
		
		Boolean naoNullo = (planeta!=null);

		assertEquals(Boolean.TRUE, naoNullo);	
		
	}
	
	@Test(expected=RuntimeException.class)
	public void buscaPlanetaPorIdInexistente() {
		ObjectId id = new ObjectId();
		
		this.p = d.buscaPlanetaPorId(id);
		
		assertFalse(Boolean.TRUE);	
		
	}
	
	@Test(expected=RuntimeException.class)
	public void buscaPlanetaPorIdNulo() {
		Planeta planeta = d.buscaPlanetaPorId(null);

		assertFalse(Boolean.TRUE);		
	}
	
	@Test
	public void buscaPlanetaPorNomeExistente() {
		List<Planeta> lista = d.buscaPlanetaPorNome("Geonosis");
		

		Boolean maiorZero = (lista.size()>0);

		assertEquals(Boolean.TRUE, maiorZero);	
	}
	
	@Test
	public void buscaPlanetaPorNomeInexistente() {
		List<Planeta> lista = d.buscaPlanetaPorNome("Qo'noS");//o planeta dos klingons
		

		Boolean zero = (lista.size()==0);

		assertEquals(Boolean.TRUE, zero);	
	}
	
	@Test
	public void removePlanetaExistente() {
		this.p = new Planeta("Geonosis to remove", Arrays.asList("rock", "desert", "mountain", "barren"), Arrays.asList("temperate", "arid"));
		d.cadastraPlaneta(p);

		d.removePlaneta(p.getId());
		assertTrue(Boolean.TRUE);
	}
	
	@Test(expected=RuntimeException.class)
	public void removePlanetaInexistente() {
		d.removePlaneta(p.getId());
		assertFalse(Boolean.TRUE);	
	}

	@Test(expected=RuntimeException.class)
	public void atualizaPlanetaIdInvalido() {
		d.atualizaPlaneta(p);
		assertFalse(Boolean.TRUE);	
		
	}
}
