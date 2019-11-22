import business.EESTrading;
import business.Utilizador;
import scrapper.AtivoFinanceiroScrapper;
import scrapper.JSONActionsScrapper;
import views.ConsoleView;
import views.View;
import views.ViewManager;
import views.consoleView.ConsoleViewManager;

public class Main {
    public static void main(String[] args) {
        /*AtivoFinanceiroScrapper actionsScrapper = new JSONActionsScrapper();

        actionsScrapper.start();
        ConsoleView view = new ConsoleView();
        view.menuInicial();*/
        EESTrading trading = new EESTrading();
        ViewManager viewManager = new ConsoleViewManager("inicial", trading);
        viewManager.start();
    }
}
