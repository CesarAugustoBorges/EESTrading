package views.consoleView;

import business.AtivoFinanceiro;
import business.AtivoFinanceiroFavorito;
import business.EESTrading;
import business.Utilizador;

import java.util.List;


public class ViewFavoritos extends ConsoleView {
    private List<AtivoFinanceiroFavorito> favoritos;


    public ViewFavoritos(EESTrading trading, Utilizador utilizador) {
        super(trading, utilizador);
        this.favoritos = utilizador.getFavoritos();
    }

    @Override
    public String render() {
        System.out.println("0. Retroceder");
        printPage(0, favoritos);
        int option = -1;
        while (option < 0 || (option > favoritos.size() && favoritos.size() > 0)){
            option = getOptionInPage(favoritos);
        }
        if(option == 0) return UTILIZADOR;
        ConsoleViewMediator.setSelectedAtivo(favoritos.get(option-1).getCompany());
        return ATIVO_FINANCEIRO;
    }
}
