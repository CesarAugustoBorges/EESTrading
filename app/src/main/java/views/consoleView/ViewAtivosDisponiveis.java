package views.consoleView;

import business.AtivoFinanceiro;
import business.EESTrading;
import business.Utilizador;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAtivosDisponiveis extends ConsoleView {
    private List<AtivoFinanceiro> ativos;
    public ViewAtivosDisponiveis(EESTrading trading, Utilizador utilizador, List<AtivoFinanceiro> ativoFinanceiros) {
        super(trading, utilizador);
        this.ativos = ativoFinanceiros;
    }

    @Override
    public String render() {
        layout("Ativos Financeiros");
        System.out.println("0.Retroceder");
        System.out.println("1.Ver tudo (crypto moedas e ações)");
        System.out.println("2.Ver ações");
        System.out.println("3.Ver crypto moedas");
        System.out.println("4.Filtrar");

        int option = getSelectedOption();
        switch (option){
            case 0: return UTILIZADOR;
            case 1:
                break;
            case 2:
                this.ativos = this.ativos.stream().filter(ativoFinanceiro -> {
                    return ativoFinanceiro.getType().equals("Acao");
                }).collect(Collectors.toList());
                break;
            case 3:
                this.ativos = this.ativos.stream().filter(ativoFinanceiro -> {
                    return ativoFinanceiro.getType().equals("Moeda");
                }).collect(Collectors.toList());
                break;
            case 4:
                boolean done = false;
                while(!done){
                    System.out.println("0.Retroceder");
                    System.out.println("1.Procurar por sigla");
                    System.out.println("2.Procurar por preço minímo");
                    System.out.println("3.Procurar por preço máximo");
                    System.out.println("4.Ver");

                    int filterOption = getSelectedOption();
                    switch (filterOption){
                        case 0: return ATIVOS_DISPONIVEIS;
                        case 1:
                            System.out.print("Introduza o filtro: ");
                            String filtro = scanner.next().toUpperCase();
                            this.ativos = this.ativos.stream().filter(a -> a.getCompany().startsWith(filtro))
                                    .collect(Collectors.toList());
                            break;
                        case 2:
                            System.out.print("Introduza o preço mínimo: ");
                            double precoMin = getDouble();
                            this.ativos = this.ativos.stream().filter(a -> a.getValue() >= precoMin)
                                    .collect(Collectors.toList());
                            break;
                        case 3:
                            System.out.println("Introduza o preço máximo: ");
                            double precoMax = getDouble();
                            this.ativos = this.ativos.stream().filter(a -> a.getValue() <= precoMax)
                                    .collect(Collectors.toList());
                            break;
                        case 4: done = true; break;
                        default: System.out.println("Opção não encontrada1"); break;
                    }
                }
                break;
            default:
                System.out.println("Opção nao encontrada");
                return ATIVOS_DISPONIVEIS;

        }

        layout(utilizador.getUsername() + " $: " + utilizador.getMoney());
        System.out.println("0.Retroceder");
        for(int i = 0; i < ativos.size(); i++){
            AtivoFinanceiro ativo = ativos.get(i);
            //Adicionar o tipo do ativo?
            System.out.println((i+1) + ". " + ativo.getCompany() + "- " + ativo.getValue() + " $" );
        }

        int ativoSelected = getSelectedOption();
        if(ativoSelected == 0) return ATIVOS_DISPONIVEIS;
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
