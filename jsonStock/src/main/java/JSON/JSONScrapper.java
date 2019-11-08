package JSON;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class JSONScrapper {
    private Map<String, AtivoFinanceiro> ativosFinanceiros;
    private boolean running;

    public JSONScrapper(){
        //Por ordem alfabética
        this.ativosFinanceiros = new TreeMap<>();
        running = false;
    }

    public Map<String, AtivoFinanceiro> getAtivosFinanceiros() {
        return ativosFinanceiros;
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
                    String json = getJson();
                    JSONObject obj = new JSONObject(json);
                    JSONArray ativos = obj.getJSONArray("stockList");
                    ativos.forEach(jsonObj -> {
                        JSONObject jsonAtivo = (JSONObject) jsonObj;
                        AtivoFinanceiro ativoFinanceiro = new Acao();
                        ativoFinanceiro.setCompany(jsonAtivo.getString("symbol"));
                        ativoFinanceiro.setValue(jsonAtivo.getDouble("price"));
                        addAtivoFinanceiro(ativoFinanceiro);
                    });
                    Thread.sleep(5000);
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
        Document doc = Jsoup.connect("https://financialmodelingprep.com/api/v3/stock/real-time-price")
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
        JSONScrapper js = new JSONScrapper();
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
