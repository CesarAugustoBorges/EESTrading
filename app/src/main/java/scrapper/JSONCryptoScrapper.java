package scrapper;

import business.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class JSONCryptoScrapper extends JSONAtivoFinanceiroScrapper {

	public JSONCryptoScrapper(){
		super("https://financialmodelingprep.com/api/v3/cryptocurrencies");
	}

	@Override
	protected void handleJSON(JSONObject obj) {
		JSONArray ativos = obj.getJSONArray("cryptocurrenciesList");
		ativos.forEach(jsonObj -> {
			numberOfStocks++;
			JSONObject jsonAtivo = (JSONObject) jsonObj;
			AtivoFinanceiro ativoFinanceiro = new Moeda("",0.0);
			ativoFinanceiro.setCompany(jsonAtivo.getString("name"));
			ativoFinanceiro.setValue(jsonAtivo.getDouble("price"));
			addAtivoFinanceiro(ativoFinanceiro);
		});
	}

	public static void main(String[] args){
		AtivoFinanceiroScrapper js = new JSONCryptoScrapper();
		js.start();
		Scanner s = new Scanner(System.in);
		while (js.isRunning()){
			String next = s.next();
			if(next.toUpperCase().equals("STOP")){
				js.setRunning(false);
			}
		}
	}
}