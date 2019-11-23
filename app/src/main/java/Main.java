import business.EESTrading;
import scrapper.AtivoFinanceiroScrapper;
import scrapper.JSONActionsScrapper;
import views.ViewManager;
import views.consoleView.ConsoleViewManager;

public class Main {
    public static void main(String[] args) {
        EESTrading trading = EESTrading.getInstance();
        AtivoFinanceiroScrapper actionsScrapper = new JSONActionsScrapper();
        actionsScrapper.start();

        ViewManager viewManager = new ConsoleViewManager("inicial", trading);
        viewManager.start();

    }
}
