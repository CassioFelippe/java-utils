package com.forte.utils;

import static org.junit.Assert.assertEquals;

import org.bson.Document;
import org.junit.Test;

public class JsonHelperTest {

	@Test
    public void testAddAttribute() {
		JsonHelper.setFieldToJson("{}", "name", "Cassio");
    }
	
	@Test
	public void testReplaceAttribute() {
		final Document json = Document.parse("{'height':174}");
		
		assertEquals(Integer.valueOf(174), json.get("height", Integer.class));
		
		JsonHelper.setFieldToJson(json, "height", Double.valueOf(1.74));
		
		assertEquals(Double.valueOf(1.74), json.get("height", Double.class));
	}
	
	@Test
	public void testSetAttributeRecursively() {
		final Document json = Document.parse("{'height':{'cm':174.0,'m':1.74}}");
		
		assertEquals(Double.valueOf(174.0), json.get("height", Document.class).get("cm", Double.class));
		
		JsonHelper.setFieldToJson(json, "height.cm", Integer.valueOf(174));
		
		assertEquals(Integer.valueOf(174), json.get("height", Document.class).get("cm", Integer.class));
	}
	
}
