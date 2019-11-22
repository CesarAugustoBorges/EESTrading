package views.consoleView;

import business.EESTrading;
import business.Utilizador;
import views.IView;

public class ViewInicial extends ConsoleView {

    public ViewInicial(EESTrading trading, Utilizador utilizador) {
        super(trading, utilizador);
    }

    @Override
    public String render() {
        layout("Menu Inicial");
        System.out.println("1.Login");
        System.out.println("2.Registar");
        System.out.println("3.Sair");

        int option = getSelectedOption();
        switch (option){
            case 1: return LOGIN;
            case 2: return REGISTAR;
            case 3: return null;
            default: System.out.println("Não é uma opção válida: " + option);
        }
        return INICIAL;
    }
}
