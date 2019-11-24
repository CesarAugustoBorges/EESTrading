package scrapper;

import business.Acao;
import business.AtivoFinanceiro;
import business.EESTrading;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public abstract class JSONAtivoFinanceiroScrapper implements AtivoFinanceiroScrapper{
    private Map<String, AtivoFinanceiro> ativosFinanceiros;
    private List<AtivoFinanceiro> changed;
    private boolean running;
    private String url;
    private EESTrading trading;

    // USED MORE FOR DEBUGGING PORPUSES
    protected int numberOfStocks;
    protected int numberOfStocksChanges;

    public JSONAtivoFinanceiroScrapper(String url){
        this.url = url;

        trading = EESTrading.getInstance();
        ativosFinanceiros = new HashMap<>();
        List<AtivoFinanceiro> temp = trading.getAtivos();
        for(AtivoFinanceiro a : temp)
            ativosFinanceiros.put(a.getCompany(), a);
        running = false;
        changed = new LinkedList<>();

        numberOfStocks = 0;
        numberOfStocks = 0;
    }

    public synchronized List<AtivoFinanceiro> getAtivosFinanceiros() {
        return new LinkedList<>(ativosFinanceiros.values());
    }

    public synchronized AtivoFinanceiro getAtivoFinanceiro(String company) {
        return ativosFinanceiros.get(company);
    }


    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
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
                    handleJSON(obj);
                    if(changed.size() > 0){
                        trading.putAtivosFinanceiros(changed);
                        changed = new LinkedList<>();
                    }
                    Thread.sleep(10000);
                    //System.out.println("Numbers of Stocks analised: " + numberOfStocks + " , stocks changed: " +  numberOfStocksChanges);
                }
            } catch (Exception e){
                setRunning(false);
                e.printStackTrace();
            }
        }).start();
    }

    protected abstract void handleJSON(JSONObject obj);

    public void stop(){
        if(!isRunning()) System.out.println("Scrapper wasn't running");
        setRunning(false);
    }

    private String getJson() throws Exception{
        Document doc = Jsoup.connect(url)
                .ignoreContentType(true)
                .get();
        Element info = doc.body();
        return info.text();
    }

    protected void addAtivoFinanceiro(AtivoFinanceiro ativoFinanceiro){
        if(!ativosFinanceiros.containsKey(ativoFinanceiro.getCompany())){
            ativosFinanceiros.put(ativoFinanceiro.getCompany(), ativoFinanceiro);
            changed.add(ativoFinanceiro);
        } else {
            if(ativosFinanceiros.get(ativoFinanceiro.getCompany()).getValue() != ativoFinanceiro.getValue()){
                numberOfStocksChanges++;
                changed.add(ativoFinanceiro);
                //System.out.println(ativoFinanceiro.getCompany() + ": oldValue -> " +
                //		ativosFinanceiros.get(ativoFinanceiro.getCompany()).getValue() + " newValue -> " +
                //		ativoFinanceiro.getValue());
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

}
