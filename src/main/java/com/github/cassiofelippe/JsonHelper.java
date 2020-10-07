package com.github.cassiofelippe;

import org.bson.Document;

/**
    Auxiliar para setar campos do json
    
    @author Cassio L Z F Felippe cassioluis12@gmail.com
 */
public class JsonHelper {
	
	/**
	 * Seta o valor de um atributo de um json, recursivamente, se necessário
	 * Pode ser usado conforme os exemplos abaixo:
	 * {'nome':'Cassio','nascimento':{'data':'1998-05-15','local':'Chapecó-SC'}}
	 * Podendo ter key como: 'nome', 'nascimento', 'nascimento.local', etc.
	 * 
	 * @param object JSON a ser alterado
	 * @param key Atributo a ser alterado
	 * @param value Valor a ser setado
	 * @return
	 */
	public static Document setFieldToJson(final Document object, final String key, final Object value) {
		if (key.contains(".")) {
            final String[] fields = key.split("\\.");
            
            Document child = object;
            
            for (int i = 0; i < fields.length; i++) {
        		if (i >= fields.length - 1) {
        			child.append(fields[i], value);
        			break;
        		}
        			
    			if (child.get(fields[i]) == null) {
    				child = child.append(fields[i], new Document()); 
    			}
    			
    			child = child.get(fields[i], Document.class);
            }
        } else {
        	object.append(key, value);
        }

		return object;
	}

	public static Document setFieldToJson(final String object, final String key, final Object value) {
		return setFieldToJson(Document.parse(object), key, value);
	}
}
