package com.b2wdigital.codecs;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.b2wdigital.pojo.Planeta;

public class PlanetaCodec implements CollectibleCodec<Planeta>{

	private Codec<Document> codec;
 	
	public void encode(BsonWriter writer, Planeta planeta, EncoderContext encoderContext) {
		Document document = new Document();
		document.put("_id", planeta.getId());
		document.put("nome", planeta.getNome());
		document.put("clima", planeta.getClima());
		document.put("terreno", planeta.getTerreno());
		
		codec.encode(writer, document, encoderContext);
	}
	
	public PlanetaCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	public Class<Planeta> getEncoderClass() {
		return Planeta.class;
	}

	public Planeta decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		
		Planeta p = new Planeta();
		
		p.setId(document.getObjectId("_id"));
		p.setNome(document.getString("nome"));
		
		
		p.setClima( (ArrayList<String>) document.get("clima"));
		p.setTerreno( (ArrayList<String>) document.get("terreno"));
		
		return p;
	}

	public Planeta generateIdIfAbsentFromDocument(Planeta planeta) {
		return documentHasId(planeta) ? planeta.geraId() : planeta;// 1 - planeta.criaId()
	}

	public boolean documentHasId(Planeta planeta) {
		return planeta.getId() == null;
	}

	public BsonValue getDocumentId(Planeta planeta) {
		if(!documentHasId(planeta)) {
			throw new IllegalStateException("planeta sem Id");
		}
		return new BsonString(planeta.getId().toHexString()	);
	}

}
