package views.consoleView;

import business.CFD;
import business.CFDVendido;
import business.EESTrading;
import business.Utilizador;

import java.time.LocalDateTime;
import java.util.List;

public class ViewTransacoesAntigas extends ConsoleView {
    private List<CFDVendido> cfds;
    public ViewTransacoesAntigas(EESTrading trading, Utilizador utilizador, List<CFDVendido> cfds) {
        super(trading, utilizador);
        this.cfds = cfds;
        this.cfds.sort((cfd1, cfd2) -> {
            LocalDateTime d1 = cfd1.getDataVenda();
            LocalDateTime d2 = cfd2.getDataVenda();
            return d1.isBefore(d2) ? -1 : d1.isEqual(d2) ?  0 : 1;
        });
    }

    @Override
    public String render() {
        layout("Transações antigas");
        double total = 0;
        double profit = 0;
        for(CFDVendido cfd : cfds){
            System.out.println(cfd);
            total += cfd.getSoldValue();
            profit += cfd.getProfit();
        }
        System.out.println();
        printMessage("Total vendido : " + total);
        printMessage("Lucro total: " + profit);
        printMessage("Pressione o ENTER para sair");
        scanner.nextLine();
        return UTILIZADOR;
    }
}
