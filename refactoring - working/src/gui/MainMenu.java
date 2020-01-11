package gui;

import business.App;
import exceptions.UsernameInvalidException;

public class MainMenu extends GUI{ //extract method, Consolidate Duplicate Conditional Fragments,

    private MainMenu(){}

    public static void printMenu(){
        System.out.println("1 - USER INFO");
        System.out.println("2 - OPEN BUY POSITION");
        System.out.println("3 - OPEN SALE POSITION");
        System.out.println("4 - ADD FUNDS");
        System.out.println("5 - CHECK PORTFOLIO");
        System.out.println("6 - MY DEALS");
        System.out.println("7 - NOTIFICATIONS");
        System.out.println("8 - DELETE ACCOUNT");
        System.out.println("9 - END SESSION");
    }

    public static void showMenu(App app) {

        printMenu();

        String option = readLine();
        switch (option){
            case "1":
                UserInfo(app);
                break;
            case "2":
                BuyPosition(app);
                break;
            case "3":
                SellPosition(app);
                break;
            case "4":
                AddFunds(app);
                break;
            case "5":
                app.prettyPortfolio();
                continuar();
                break;
            case "6":
                System.out.println(app.myDeals());
                continuar();
                break;
            case "7":
                System.out.println(app.checkUserNotifications());
                continuar();
                break;
            case "8":
                DeleteAccount(app);
                break;
            case "9":
                EndSession(app);
                break;
            case "0":
                System.out.println(app.listMarketStocks());
                continuar();
                showMenu(app);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    private static void EndSession(App app) {
        app.logOut();
        continuar();
        GUI.showMenu();
    }

    private static void DeleteAccount(App app) {
        try {
            app.deleteUser();
            System.out.println("Account Deleted!\n");
            continuar();
        } catch (UsernameInvalidException e) {
            e.printStackTrace();
            showMenu(app);
        }
        showMenu(app);
    }

    private static void AddFunds(App app) {
        addFundsMenu();
        continuar();
        showMenu(app);
    }

    private static void SellPosition(App app) {
        System.out.println(app.stocksOnSale());
        if(!app.stocksOnSale().isEmpty()) {
            openSalePositionMenu();
        }
        else{
            System.out.println("You don't have nothing to sell at the moment! :(");
        }
        continuar();
        showMenu(app);
    }

    private static void BuyPosition(App app) {
        System.out.println(app.listMarketStocks());
        openBuyPositionMenu();
        continuar();
        showMenu(app);
    }

    private static void UserInfo(App app) {
        System.out.println(app.getUserInfo());
        continuar();
        showMenu(app);
    }
}
