package scrapper;

import business.Acao;
import business.AtivoFinanceiro;
import business.CFD;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class JSONActionsScrapper implements AtivoFinanceiroScrapper {

	private Map<String, AtivoFinanceiro> ativosFinanceiros;
	private boolean running;
	private String url;

	private int numberOfStocks;
	private int numberOfStocksChanges;

	public JSONActionsScrapper(){
		url = "https://financialmodelingprep.com/api/v3/stock/real-time-price";
		ativosFinanceiros = new TreeMap<>();
		running = false;

		numberOfStocks = 0;
		numberOfStocks = 0;
	}

	public List<AtivoFinanceiro> getAtivosFinanceiros() {
		return new LinkedList<>(ativosFinanceiros.values());
	}

	public AtivoFinanceiro getAtivoFinanceiro(String company) {
		return ativosFinanceiros.get(company);
	}


	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void start(){
		new Thread(() -> {
			try{
				setRunning(true);
				while(isRunning()) {
					numberOfStocks = 0;
					numberOfStocksChanges = 0;
					String json = getJson();
					JSONObject obj = new JSONObject(json);
					JSONArray ativos = obj.getJSONArray("stockList");
					ativos.forEach(jsonObj -> {
						numberOfStocks++;
						JSONObject jsonAtivo = (JSONObject) jsonObj;
						AtivoFinanceiro ativoFinanceiro = new Acao();
						ativoFinanceiro.setCompany(jsonAtivo.getString("symbol"));
						ativoFinanceiro.setValue(jsonAtivo.getDouble("price"));
						addAtivoFinanceiro(ativoFinanceiro);
					});
					Thread.sleep(5000);
					System.out.println("Numbers of Stocks analised: " + numberOfStocks + " , stocks changed: " +  numberOfStocksChanges);
					//System.out.println(toString());
				}
			} catch (Exception e){
				setRunning(false);
				e.printStackTrace();
			}
		}).start();
	}

	public void stop(){
		if(!isRunning()) System.out.println("Scrapper wasn't running");
		setRunning(false);
	}

	private String getJson() throws Exception{
		Document doc = Jsoup.connect(url)
				.ignoreContentType(true)
				.get();
		Element info = doc.body();
		String infoText = info.text();
		return infoText;
	}

	private void addAtivoFinanceiro(AtivoFinanceiro ativoFinanceiro){
		if(!ativosFinanceiros.containsKey(ativoFinanceiro.getCompany())){
			ativosFinanceiros.put(ativoFinanceiro.getCompany(), ativoFinanceiro);
		} else {
			if(ativosFinanceiros.get(ativoFinanceiro.getCompany()).getValue() != ativoFinanceiro.getValue()){
				numberOfStocksChanges++;
				System.out.println(ativoFinanceiro.getCompany() + ": oldValue -> " +
						ativosFinanceiros.get(ativoFinanceiro.getCompany()).getValue() + " newValue -> " +
						ativoFinanceiro.getValue());
			}
			ativosFinanceiros.replace(ativoFinanceiro.getCompany(), ativoFinanceiro);
		}
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(String company : ativosFinanceiros.keySet()){
			sb.append(company);
			sb.append(": ");
			sb.append(ativosFinanceiros.get(company).getValue());
			sb.append("\n");
		}
		return sb.toString();
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