package views.consoleView;

import business.CFD;
import business.CFDVendido;
import business.EESTrading;
import business.Utilizador;

import java.util.List;

public class ViewTransacoesAntigas extends ConsoleView {
    private List<CFDVendido> cfds;
    public ViewTransacoesAntigas(EESTrading trading, Utilizador utilizador, List<CFDVendido> cfds) {
        super(trading, utilizador);
        this.cfds = cfds;
    }

    @Override
    public String render() {
        layout("Transações antigas");
        double total = 0;
        for(CFDVendido cfd : cfds){
            System.out.println(cfd);
            total += cfd.getSoldValue();
        }
        printMessage("\nTotal vendido : " + total);
        printMessage("Pressione o ENTER para sair");
        scanner.nextLine();
        return UTILIZADOR;
    }
}
