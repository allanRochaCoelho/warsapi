package com.b2wdigital.pojo;

import java.net.URL;
import java.util.List;



public class PlanetOfSwapi {

	private String name; 
	private String diameter; 
	private String rotation_period; 
	private String orbital_period; 
	private String gravity;
	private String population; 
	private String climate;  
	private String terrain; 
	private String surface_water;
	private List<URL> residents; 
	private List<URL> films; 
	private URL url;
	private String created;
	private String edited;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDiameter() {
		return diameter;
	}
	
	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}
	
	public String getRotation_period() {
		return rotation_period;
	}
	
	public void setRotation_period(String rotation_period) {
		this.rotation_period = rotation_period;
	}
	
	public String getOrbital_period() {
		return orbital_period;
	}
	
	public void setOrbital_period(String orbital_period) {
		this.orbital_period = orbital_period;
	}
	
	public String getGravity() {
		return gravity;
	}
	
	public void setGravity(String gravity) {
		this.gravity = gravity;
	}
	
	public String getPopulation() {
		return population;
	}
	
	public void setPopulation(String population) {
		this.population = population;
	}
	
	public String getClimate() {
		return climate;
	}
	
	public void setClimate(String climate) {
		this.climate = climate;
	}
	
	public String getTerrain() {
		return terrain;
	}
	
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	
	public String getSurface_water() {
		return surface_water;
	}
	
	public void setSurface_water(String surface_water) {
		this.surface_water = surface_water;
	}
	
	public List<URL> getResidents() {
		return residents;
	}
	
	public void setResidents(List<URL> residents) {
		this.residents = residents;
	}
	
	public List<URL> getFilms() {
		return films;
	}
	
	public void setFilms(List<URL> films) {
		this.films = films;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	} 
	
	
	
}
