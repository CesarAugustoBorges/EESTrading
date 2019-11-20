package views;

import business.AtivoFinanceiro;
import business.CFD;
import business.Utilizador;

import java.util.*;

public class ConsoleView extends View {
    private Scanner scanner = new Scanner(System.in);

    public ConsoleView(){
        super();
    }

    public void layout(String title){
        int width = 50;
        StringBuilder res = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < width; i++)
            sb.append('-');
        sb.append('\n');
        String s = sb.toString();
        res.append(s);
        int i = 0;
        sb = new StringBuilder();
        for(; i < ((width - (title.length()+2)) / 2); i++ )
            sb.append('-');
        sb.append(' ');
        sb.append(title);
        sb.append(' ');
        i+= title.length() + 2;
        for(; i < width; i++)
            sb.append('-');
        sb.append('\n');
        res.append(sb.toString());
        res.append(s);
        System.out.println(res.toString());
    }


    @Override
    public void menuDepositar(Utilizador utilizador) {
        layout("Depositar - " + utilizador.getMoney() + "$");
        System.out.print("Inserir dinheiro: ");
        double valor = getDouble();
        trading.deposit(utilizador, valor);
        menuUtilizador(utilizador);
    }

    @Override
    public void menuWithdraw(Utilizador utilizador) {
        layout("Withdraw - " + utilizador.getMoney() + "$");
        System.out.print("Inserir dinheiro: ");
        double valor = getDouble();
        if(trading.withdraw(utilizador, valor)){
            System.out.println("Dinheiro levantado com sucesso");
        } else {
            System.out.println("ERROR: Não possui essa quantidade");
        }
        menuUtilizador(utilizador);
    }

    public void menuInicial() {
        layout("Menu Inicial");
        System.out.println("1.Login");
        System.out.println("2.Registar");
        System.out.println("3.Sair");

        int option = getSelectedOption();
        switch (option){
            case 1: menuLogin(); break;
            case 2: menuRegistar(); break;
            case 3: System.exit(0); break;
            default: System.out.println("Não é uma opção válida: " + option);
        }
    }

    public void menuRegistar() {
        layout("Menu Inicial");
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        utilizador = trading.regist(username, password);
        if(utilizador != null){
            menuUtilizador(utilizador);
        }
        else{
            System.out.println("Não foi possivel registar o utilizador");
            menuInicial();
        }
    }

    public void menuLogin(){
        layout("Login");
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        utilizador = trading.login(username, password);
        if(utilizador != null){
            menuUtilizador(utilizador);
        }
        else{
            System.out.println("Credenciais incorretas");
            menuInicial();
        }
    }

    /**
     *
     * @param utilizador
     * @param cfd
     */
    public void menuCFDPossuido(Utilizador utilizador, CFD cfd) {
        layout(cfd.getName() + " - " + cfd.getValue() + "$");
        System.out.print("Top Profit: " + (cfd.getTopProfit() == null ? "--" : cfd.getTopProfit()));
        System.out.println("Stop Loss: " + (cfd.getStopLoss() == null ? "--" : cfd.getStopLoss()));
        System.out.println("1.Vender");
        System.out.println("2.Definir Top Profit");
        System.out.println("3.Definir Stop Loss");
        System.out.println("4.Retroceder");

        int option = getSelectedOption();
        switch (option){
            case 1:
                double sould = trading.sell(utilizador, cfd);
                System.out.println("Vendido " + cfd.getName() + " - " + sould + "$");
                menuMeusCFDs();
                break;
            case 2:
                System.out.print("Introduza o Stop Loss: ");
                double valor = getDouble();
                if(trading.setCFDStopLoss(cfd, valor)){
                    menuCFDPossuido(utilizador, cfd);
                    break;
                }else{
                    System.out.println("O stop loss tem de ser menor que o valor do CFD");
                    break;
                }
            case 3:
                System.out.print("Introduza o Top Profit: ");
                double topProfit = getDouble();
                if(trading.setCFDTopProfit(cfd, topProfit)){
                    menuCFDPossuido(utilizador, cfd);
                    break;
                }else{
                    System.out.println("O top profit tem de ser maior que o valor do CFD");
                    break;
                }
            case 4: menuMeusCFDs(); break;
            default:
                System.out.println("Não é uma opção válida: " + option);
                menuCFDPossuido(utilizador, cfd);
        }
    }

    public void menuAtivosDisponiveis(){
        menuAtivosDisponiveis(utilizador, trading.getAtivos());
    }
    /**
     *
     * @param utilizador
     * @param ativos
     */
    public void menuAtivosDisponiveis(Utilizador utilizador, List<AtivoFinanceiro> ativos) {
        layout(utilizador.getUsername() + " $: " + utilizador.getMoney());
        System.out.println("0.Retroceder");
        for(int i = 0; i < ativos.size(); i++){
            AtivoFinanceiro ativo = ativos.get(i);
            //Adicionar o tipo do ativo?
            System.out.println((i+1) + ". " + ativo.getCompany() + "- " + ativo.getValue() + " $" );
        }

        int ativoSelected = getSelectedOption();
        if(ativoSelected > 0 && ativoSelected <= ativos.size())
            menuDeCompraCFD(utilizador, ativos.get(ativoSelected-1));
        else {
            System.out.println("ERROR: Escolha um ativo entre 1 - " + ativos.size());
            menuAtivosDisponiveis(utilizador, ativos);
        }
    }

    /**
     *
     * @param utilizador
     * @param ativo
     */
    public void menuDeCompraCFD(Utilizador utilizador, AtivoFinanceiro ativo) {
        CFD cfd = new CFD();
        cfd.setUtilizador(utilizador);
        cfd.setAtivoFinanceiro(ativo);

        layout(utilizador.getUsername() + " CFD: " + ativo.getCompany() +  " " + ativo.getValue() + " $");
        System.out.print("Valor de compra: ");
        double valor = scanner.nextDouble();
        cfd.setBoughtValue(valor);
        cfd.setUnits(valor / ativo.getValue());
        System.out.print("Deseja definir um Top Profit? [Y/N] ");
        String answer = scanner.next();
        if(answer.equals("Y")){
            System.out.print("Introduza o valor: ");
            double topProfit = scanner.nextDouble();
            cfd.setTopProfit(topProfit);
        }
        System.out.print("Deseja definir um Stop Profit= [Y/N] ");
        answer = scanner.next();
        if(answer.equals("Y")){
            System.out.print("Introduza o valor: ");
            double stoploss = scanner.nextDouble();
            cfd.setStopLoss(stoploss);
        }

        if(trading.buy(utilizador, cfd)){
            System.out.println("CFD bought sucessfully");
        } else {
            System.out.println("CFD not bought");
        }

        menuAtivosDisponiveis();
    }


    public void menuMeusCFDs(){
        List<CFD> cfds = trading.getCFDsOfUser(utilizador);
        menuMeusCFDs(utilizador, cfds);
    }
    /**
     *
     * @param utilizador
     * @param cfds
     */
    public void menuMeusCFDs(Utilizador utilizador, List<CFD> cfds) {
        layout("Meus CFDs");
        for(int i = 0; i < cfds.size(); i++){
            System.out.println((i+1)+ "."+ cfds.get(i).getName() + " - " + cfds.get(i).getValue() + " $");
        }
        int option = getSelectedOption();
        if(option > 0 && option <= cfds.size()){
            menuCFDPossuido(utilizador, cfds.get(option-1));
        }
        else {
            System.out.println("Não existe esse CFD");
            menuMeusCFDs();
        }
    }

    /**
     *
     * @param utilizador
     */
    public void menuUtilizador(Utilizador utilizador) {
        layout("Olá " + utilizador.getUsername());
        System.out.println("1.Comprar CFD");
        System.out.println("2.Ver portfolio");
        System.out.println("3.Ver transições antigas");
        System.out.println("4.Depositar dinheiro");
        System.out.println("5.Levantar dinheiro");
        System.out.println("6.Sair");

        int option = getSelectedOption();
        switch (option){
            case 1: menuAtivosDisponiveis(); break;
            case 2: menuMeusCFDs(); break;
            case 3: break; //fazer
            case 4: menuDepositar(utilizador); break;
            case 5: menuWithdraw(utilizador); break;
            case 6:
                this.utilizador = null;
                menuInicial();
            default:
                System.out.println("ERROR: Não é uma opção válida");
                menuUtilizador(utilizador);
        }
    }

    private int getSelectedOption(){
        try {
            return scanner.nextInt();
        } catch (Exception e){
            clearInput();
            System.out.println("ERROR: Insira apenas um número na consola");
            return getSelectedOption();
        }
    }

    private double getDouble(){
        try{
            return scanner.nextDouble();
        } catch (Exception e){
            clearInput();
            System.out.println("ERROR: Insira um número válido na comsola");
            return getDouble();
        }
    }

    private void clearInput(){
        scanner.next();
    }
}