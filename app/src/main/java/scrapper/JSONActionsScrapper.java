package scrapper;

import business.Acao;
import business.AtivoFinanceiro;
import business.CFD;
import business.EESTrading;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class JSONActionsScrapper extends JSONAtivoFinanceiroScrapper{
	private double testValue = 0.10;

	public JSONActionsScrapper(){
		super("https://financialmodelingprep.com/api/v3/stock/real-time-price");
	}


	@Override
	protected void handleJSON(JSONObject obj) {
		JSONArray ativos = obj.getJSONArray("stockList");
		ativos.forEach(jsonObj -> {
			numberOfStocks++;
			JSONObject jsonAtivo = (JSONObject) jsonObj;
			AtivoFinanceiro ativoFinanceiro = new Acao();
			ativoFinanceiro.setCompany(jsonAtivo.getString("symbol"));
			ativoFinanceiro.setValue(Math.floor(jsonAtivo.getDouble("price") * 100) / 100);
			addAtivoFinanceiro(ativoFinanceiro);
		});
		Acao acao = new Acao();
		acao.setCompany("TEST");
		acao.setValue(testValue);
		addAtivoFinanceiro(acao);
		testValue += 0.01;
	}

	public static void main(String[] args){
		JSONActionsScrapper js = new JSONActionsScrapper();
		js.start();
		Scanner s = new Scanner(System.in);
		while (js.isRunning()){
			String next = s.next();
			if(next.toUpperCase().equals("STOP")){
				js.setRunning(false);
			}
		}
		//System.out.println(js.toString());
	}
}