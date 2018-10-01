package com.b2wdigital.servicos;

import java.util.List;

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

import org.bson.types.ObjectId;

import com.b2wdigital.persistence.Dao;
import com.b2wdigital.pojo.PlanetOfSwapi;
import com.b2wdigital.pojo.Planeta;
import com.google.gson.Gson;

@Path("/planetas")
public class PlanetaService {

	private Gson gson = new Gson();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaPorId(@PathParam("id") String id){
		Planeta p = null;
		
		try (Dao d = new Dao()){
			p = d.buscaPlanetaPorId(new ObjectId(id));
			PlanetServiceOfSwapi ps = new PlanetServiceOfSwapi();

			p.setAparicoesEmFilmes(ps.getQtdAparicoesEmFilmes(p.getNome()));//QtdAparicoesEmFilme(listaWs, p)
			
		} catch (IllegalArgumentException e) {
			String erro = e.getMessage();
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		} catch (RuntimeException e) {
			String erro = e.getMessage();
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		} catch (Exception e) {
			String erro = e.getMessage();
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		}
		
		return Response.ok(p).build();
	}
	
	@GET
	@Path("nome/{nome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaPorNome(@PathParam("nome") String nome){
		List<Planeta> lista = null;
		
		try (Dao d = new Dao()){
			lista = d.buscaPlanetaPorNome(nome);
			PlanetServiceOfSwapi ps = new PlanetServiceOfSwapi();
			
			for (Planeta p : lista) {
				p.setAparicoesEmFilmes(ps.getQtdAparicoesEmFilmes(p.getNome()));//QtdAparicoesEmFilme(listaWs, p)
			}
			
			if(lista.size()==0) {
				String retorno = "não foi encontrado nenhum planeta com o nome informado.";
				return Response.serverError().entity(gson.toJson(retorno)).build();
			}

		} catch (Exception e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		} 
		
		
		return Response.ok(lista).build();
	}
	
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletaPlaneta(@PathParam("id") String id){
		
		
		
		try (Dao d = new Dao();){
			d.removePlaneta(new ObjectId(id));
		}catch (IllegalArgumentException e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		} catch (RuntimeException e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		} catch (Exception e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		}
		

		return Response.ok(gson.toJson("excluido com sucesso")).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarPlanetas(){
		List<Planeta> lista = null;
		
		try (Dao d = new Dao();){
			lista = d.listaPlanetas();
			PlanetServiceOfSwapi ps = new PlanetServiceOfSwapi();
			
			if(lista.size()==0) {
				String retorno = "no momento não existem planetas cadastrados.";
				return Response.serverError().entity(gson.toJson(retorno)).build();
			}	
			
			for (Planeta p : lista) {
				p.setAparicoesEmFilmes(ps.getQtdAparicoesEmFilmes(p.getNome()));//QtdAparicoesEmFilme(listaWs, p)
			}
			
		} catch (Exception e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		}
		
		return  Response.ok(lista).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adicionaPlaneta(Planeta p){
		String msg = "cadastrado com sucesso"; 
		
		try (Dao d = new Dao();){			
		
			d.cadastraPlaneta(p);
		} catch (Exception e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		}


		return Response.ok(gson.toJson(msg)).build();		
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizaPlaneta(@PathParam("id") String id,Planeta p){
		String msg = "alterado com sucesso";
		
		try (Dao d = new Dao();) {
			
			p.setId(new ObjectId(id));
			d.atualizaPlaneta(p);
		} catch (Exception e) {
			return Response.serverError().entity(gson.toJson(e.getMessage())).build();
		}
		
		return Response.ok(gson.toJson(msg)).build();
		
	}
	
	
}
