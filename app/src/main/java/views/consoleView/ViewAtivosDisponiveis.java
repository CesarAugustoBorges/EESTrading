package views.consoleView;

import business.AtivoFinanceiro;
import business.EESTrading;
import business.Utilizador;

import java.util.List;

public class ViewAtivosDisponiveis extends ConsoleView {
    private List<AtivoFinanceiro> ativos;
    public ViewAtivosDisponiveis(EESTrading trading, Utilizador utilizador, List<AtivoFinanceiro> ativoFinanceiros) {
        super(trading, utilizador);
        this.ativos = ativoFinanceiros;
    }

    @Override
    public String render() {
        layout(utilizador.getUsername() + " $: " + utilizador.getMoney());
        System.out.println("0.Retroceder");
        for(int i = 0; i < ativos.size(); i++){
            AtivoFinanceiro ativo = ativos.get(i);
            //Adicionar o tipo do ativo?
            System.out.println((i+1) + ". " + ativo.getCompany() + "- " + ativo.getValue() + " $" );
        }

        int ativoSelected = getSelectedOption();
        if(ativoSelected == 0) return UTILIZADOR;
        if(ativoSelected > 0 && ativoSelected <= ativos.size()) {
            if(isUpdated()){
                boolean yes = yesOrNoQuestion("Alguns ativos financeiros foram atualizados, quer continuar?");
                if(!yes) return ATIVOS_DISPONIVEIS;
            }

            ConsoleViewManager.setSelectedAtivo(ativos.get(ativoSelected - 1).getCompany());
            return COMPRA_CFD;
        }
        else {
            System.out.println("ERROR: Escolha um ativo entre 1 - " + ativos.size());
            return ATIVOS_DISPONIVEIS;
        }
    }
}
