package views.consoleView;

import business.AtivoFinanceiro;
import business.EESTrading;
import business.Utilizador;

public class ViewAtivo extends ConsoleView {
    private AtivoFinanceiro ativoFinanceiro;

    public ViewAtivo(EESTrading trading, Utilizador utilizador, AtivoFinanceiro ativoFinanceiro) {
        super(trading, utilizador);
        this.ativoFinanceiro = ativoFinanceiro;
    }

    @Override
    public String render() {
        System.out.println("0.Retroceder");
        System.out.println("1.Comprar");
        boolean isFavorito = utilizador.hasFavorito(ativoFinanceiro);
        if(isFavorito)
            System.out.println("2.Remover dos favoritos");
        else System.out.println("2.Adicionar a favoritos");

        int option = getSelectedOption();

        switch (option){
            case 0: return UTILIZADOR;
            case 1:
                ConsoleViewMediator.setSelectedAtivo(ativoFinanceiro.getCompany());
                return COMPRA_CFD;
            case 2:
                if(isFavorito){
                    boolean yes = yesOrNoQuestion("Remover " + ativoFinanceiro.getCompany() + " dos favoritos?");
                    if (yes){
                        utilizador.removeFavorito(ativoFinanceiro);
                        trading.update(utilizador);
                    }
                }else {
                    boolean yes = yesOrNoQuestion("Adicionar " + ativoFinanceiro.getCompany() + " aos favoritos?");
                    if (yes) {
                        System.out.print("Introduza o valor: ");
                        double value2Notify = getDouble();
                        utilizador.addFavorito(ativoFinanceiro, value2Notify);
                        trading.update(utilizador);
                    }
                }
                return ATIVOS_DISPONIVEIS;
            default: return ATIVO_FINANCEIRO;
        }
    }
}
