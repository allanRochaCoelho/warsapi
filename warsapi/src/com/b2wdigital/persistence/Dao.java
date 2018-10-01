package com.b2wdigital.persistence;

import com.b2wdigital.codecs.PlanetaCodec;
import com.b2wdigital.pojo.Planeta;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

public class Dao implements AutoCloseable{

	private MongoClient cliente;
	private MongoDatabase bancoDeDados;
	private MongoCollection<Planeta> planetas;
	
	public Dao() {
		arbrirConexao ();
	}
	
	private void arbrirConexao () {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		PlanetaCodec pCodec = new PlanetaCodec(codec);
		CodecRegistry registro = CodecRegistries.fromRegistries(
											MongoClient.getDefaultCodecRegistry(), 
											CodecRegistries.fromCodecs(pCodec)
										);
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();

		this.cliente = new MongoClient("localhost:27017", options);
		this.bancoDeDados = this.cliente.getDatabase("test");
		this.planetas = doMongoCollection();
	}
	
	private MongoCollection doMongoCollection() {
		return this.bancoDeDados.getCollection("planetas", Planeta.class);
	}
	

	public Planeta buscaPlanetaPorId(ObjectId id)  throws  IllegalArgumentException,  RuntimeException{
		
		try {
			Planeta p = this.planetas.find(Filters.eq("_id", id)).first();
		
			if(p==null) {
				throw new RuntimeException("não foi encontrado nenhum planeta com o id informado");
			}
			
			return p;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("O identificador informado é inválido");
		}
		
	}
	
	
	public void cadastraPlaneta(Planeta p) {
		
		this.planetas.insertOne(p);
        
	}
	
	public List<Planeta> listaPlanetas(){
		List<Planeta> listaPlanetas = new ArrayList<Planeta>();
		
        MongoCursor<Planeta> resultado = this.planetas.find().iterator();
        
        listaPlanetas = corsorToList(resultado);
		
		return listaPlanetas;
	}

	
//	public Planeta buscaPlanetaPorId(ObjectId id) throws  IllegalArgumentException,  RuntimeException{
//        Planeta p = null;
//		
//        p = validaPlanetaExistente(id);
//		
//		
//        return p;
//	}

	public List<Planeta> buscaPlanetaPorNome(String nome) {
        
		List<Planeta> lista =  new ArrayList<>();
		MongoCursor<Planeta> resultado = this.planetas.find( Filters.regex("nome", Pattern.compile(nome, Pattern.CASE_INSENSITIVE))).iterator();
		

		lista = corsorToList(resultado);
		
		return lista;		
	}
	
	public void removePlaneta(ObjectId id)  throws  IllegalArgumentException,  RuntimeException{

		Planeta p = buscaPlanetaPorId(id);
		
		
		
		this.planetas.deleteOne(Filters.eq("_id", id));
	}
	
	public void atualizaPlaneta(Planeta p) throws IllegalArgumentException,  RuntimeException{

		Planeta planeta = buscaPlanetaPorId(p.getId());
		
		this.planetas.updateOne(Filters.eq("_id", p.getId()), new Document("$set", p));
	}
	

	private List<Planeta> corsorToList(MongoCursor<Planeta> resultado) {
		List<Planeta> lista = new ArrayList<>();
		
		while(resultado.hasNext()) {
			lista.add(resultado.next());
        }
		
		return lista;
	}
	
	
	@Override
	public void close() throws Exception {
        this.cliente.close();
	}


	
}
