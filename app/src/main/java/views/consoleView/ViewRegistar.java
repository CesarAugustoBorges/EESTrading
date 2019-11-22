package views.consoleView;

import business.EESTrading;
import business.Utilizador;

public class ViewRegistar extends ConsoleView {
    public ViewRegistar(EESTrading trading, Utilizador utilizador) {
        super(trading, utilizador);
    }

    @Override
    public String render() {
        layout("Menu Inicial");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        utilizador = trading.regist(username, password);
        if(utilizador != null){
            return UTILIZADOR;
        }
        else{
            System.out.println("Não foi possivel registar o utilizador");
            return INICIAL;
        }
    }
}
