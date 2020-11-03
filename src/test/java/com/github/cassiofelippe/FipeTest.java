package com.github.cassiofelippe;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.bson.BsonArray;
import org.bson.Document;
import org.junit.Test;

public class FipeTest {

	@Test
	public void testConsultaMarcasVeiculoCarro() throws Exception {
		final Map<String, String> params = new HashMap<>();
		
		params.put("route", "marcas");
		params.put("tipoVeiculo", "1");
		
		final String response = Fipe.execute(params).body();
		assertTrue(BsonArray.parse(response).size() > 0);
	}
	
	@Test
	public void testConsultaModelosMarcaChevroletVeiculoCarro() throws Exception {
		final Map<String, String> params = new HashMap<>();
		
		params.put("route", "modelos");
		params.put("marca", "23");
		params.put("tipoVeiculo", "1");
		
		final String response = Fipe.execute(params).body();
		
		assertTrue(Document.parse(response).getList("Modelos", Document.class).size() > 0);
	}
	
	@Test
	public void testConsultaAnosModeloCorsaMarcaChevroletVeiculoCarro() throws Exception {
		final Map<String, String> params = new HashMap<>();
		
		params.put("route", "anos");
		params.put("marca", "23");
		params.put("modelo", "4379");
		params.put("tipoVeiculo", "1");
		
		final String response = Fipe.execute(params).body();
		
		assertTrue(BsonArray.parse(response).size() > 0);
	}
	
	@Test
	public void testConsultaVeiculoAno2012ModeloCorsaMarcaChevroletVeiculoCarro() throws Exception {
		final Map<String, String> params = new HashMap<>();
		
		params.put("route", "veiculo");
		params.put("marca", "23");
		params.put("modelo", "4379");
		params.put("ano", "2012");
		params.put("tipoVeiculo", "1");
		
		final String response = Fipe.execute(params).body();
		
		assertTrue(BsonArray.parse(response).size() > 0);
	}
}
