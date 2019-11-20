import business.Utilizador;
import views.ConsoleView;
import views.View;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        view.menuUtilizador(new Utilizador("César", "123",0.0));
    }
}
