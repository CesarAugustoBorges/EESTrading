package views;

import business.AtivoFinanceiro;
import business.CFD;
import business.Utilizador;

import java.util.*;

public class ConsoleView extends View {
    private Scanner scanner = new Scanner(System.in);

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

    }

    @Override
    public void menuWithdraw(Utilizador utilizador) {

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
            default: System.out.println("Não é uma opção válida");
        }
    }

    public void menuRegistar() {
        layout("Menu Inicial");
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password");
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
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password");
        String password = scanner.next();
        utilizador = trading.login(username, password);
        if(utilizador != null){
            menuUtilizador(utilizador);
        }
        else{
            System.out.println("Não foi possivel registar o utilizador");
            menuInicial();
        }
    }

    /**
     *
     * @param utilizador
     * @param cfd
     */
    public void menuCFDPossuido(Utilizador utilizador, CFD cfd) {
        // TODO - implement ConsoleView.menuCFDPossuido
        throw new UnsupportedOperationException();
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
            cfd.setTakeProfit(topProfit);
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
        // TODO - implement ConsoleView.menuMeusCFDs
        throw new UnsupportedOperationException();
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
            case 4: break;
            default: System.out.println("ERROR: Não é uma opção válida");
        }
    }

    public static void main(String[] args) {
        View view = new ConsoleView();
        view.menuInicial();
    }

    private int getSelectedOption(){
        try {
            return scanner.nextInt();
        } catch (Exception e){
            System.out.println("ERROR: Insira apenas um número na consola");
            return getSelectedOption();
        }
    }

    private double getDouble(){
        try{
            return scanner.nextDouble();
        } catch (Exception e){
            System.out.println("ERROR: Insira um número válido na comsola");
            return getDouble();
        }
    }
}