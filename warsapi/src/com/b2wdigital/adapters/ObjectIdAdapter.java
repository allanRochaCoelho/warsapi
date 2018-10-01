//package com.b2wdigital.adapters;
//
//import javax.xml.bind.annotation.adapters.XmlAdapter;
//
//import org.bson.types.ObjectId;
//
//public class ObjectIdAdapter extends XmlAdapter<String, ObjectId>{
//
//	
//	@Override
//	public String marshal(ObjectId objectId) throws Exception {
//		return objectId.toHexString();
//	}
//
//	@Override
//	public ObjectId unmarshal(String id) throws Exception {
//		return new ObjectId(id);
//	}
//
//}
