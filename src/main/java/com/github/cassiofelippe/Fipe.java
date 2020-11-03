package com.github.cassiofelippe;

import static com.github.cassiofelippe.HttpRequestHelper.run;
import static java.lang.String.format;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.BsonArray;

/**
    Executa requisições para FIPE
 */
public class Fipe {
	
	private static final String address = "https://veiculos.fipe.org.br/api/veiculos";
	
	/**
	 * @param params { route: required }
	 */
	public static HttpResponse<String> execute(final Map<String, String> params) {
		final String route = params.get("route");
		final String tabelaReferencia = tabelaReferencia();
		final String tipoVeiculo = params.get("tipoVeiculo");
		
		if ("marcas".equals(route)) {
			
			return marcas(tabelaReferencia, tipoVeiculo);
		} else if ("modelos".equals(route)) {
			return modelos(tabelaReferencia, tipoVeiculo, params.get("marca"));
		} else if ("anos".equals(route)) {
			return anos(tabelaReferencia, tipoVeiculo, params.get("marca"), params.get("modelo"));
		} else if ("veiculo".equals(route)) {
			return veiculo(tabelaReferencia, tipoVeiculo, params.get("marca"), params.get("modelo"), params.get("ano"));
		} else {
			Logger.getGlobal().log(Level.WARNING, "Parâmetro [route] incorreto ou não informado.");
			return null;
		}
		
	}

	public static String tabelaReferencia() {
		return String.valueOf(BsonArray.parse(run("POST", format("%s/ConsultarTabelaDeReferencia", address)).body()).get(0).asDocument().getInt32("Codigo").getValue());
	}
	
	public static HttpResponse<String> marcas(final String tabelaReferencia, final String tipoVeiculo) {
		return run("POST", format("%s/ConsultarMarcas?codigoTabelaReferencia=%s&codigoTipoVeiculo=%s", address, tabelaReferencia, tipoVeiculo));
	}
	
	public static HttpResponse<String> modelos(final String tabelaReferencia, final String tipoVeiculo, final String marca) {
		return run("POST", format("%s/ConsultarModelos?codigoTabelaReferencia=%s&codigoTipoVeiculo=%s&codigoMarca=%s", address, tabelaReferencia, tipoVeiculo, marca));
	}
	
	public static HttpResponse<String> anos(final String tabelaReferencia, final String tipoVeiculo, final String marca, final String modelo) {
		return run("POST", format("%s/ConsultarAnoModelo?codigoTabelaReferencia=%s&codigoTipoVeiculo=%s&codigoMarca=%s&codigoModelo=%s", address, tabelaReferencia, tipoVeiculo, marca, modelo));
	}
	
	public static HttpResponse<String> veiculo(final String tabelaReferencia, final String tipoVeiculo, final String marca, final String modelo, final String ano) {
		return run(
			"POST",
			format("%s/ConsultarValorComTodosParametros?codigoTabelaReferencia=%s&codigoTipoVeiculo=%s&codigoMarca=%s&codigoModelo=%s&anoModelo=%s&tipoConsulta=tradicional&codigoTipoCombustivel=%s",
				address, tabelaReferencia, tipoVeiculo, marca, modelo, ano, "1"
			)
		);
	}
}
