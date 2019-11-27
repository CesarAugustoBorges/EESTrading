import business.EESTrading;
import scrapper.AtivoFinanceiroScrapper;
import scrapper.AtivoFinanceiroScrapperFactory;
import views.ViewManager;
import views.consoleView.ConsoleViewManager;

public class Main {
    public static void main(String[] args) {
        EESTrading trading = EESTrading.getInstance();
        AtivoFinanceiroScrapperFactory scrapperFactory = new AtivoFinanceiroScrapperFactory();
        AtivoFinanceiroScrapper actionsScrapper = scrapperFactory.newAtivoFinanceiroScrapper("jsonAcoes");
        AtivoFinanceiroScrapper criptoScrapper = scrapperFactory.newAtivoFinanceiroScrapper("jsonCrypto");

        actionsScrapper.start();
        criptoScrapper.start();

        //System.out.println(System.getProperty("os.name"));

        ViewManager viewManager = new ConsoleViewManager("inicial", trading);
        viewManager.start();

    }
}
