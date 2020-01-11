package business;

import java.util.List;

public class Checker extends Thread {

    private App app;

    public Checker(App app){this.app = app;}

    public void updateBuy(MarketStock mstk,float cfd_buy){
        mstk.notifyBuyPositionObservingStock();
        mstk.setCfd_buy(cfd_buy);
        app.stockDAO.put(mstk.getId_stock(), mstk);
    }

    public void updateSale(MarketStock mstk,float cfd_sale){
        mstk.notifySalePositionObservingStock();
        mstk.setCfd_sale(cfd_sale);
        app.stockDAO.put(mstk.getId_stock(), mstk);
    }

    public void updateSellAndBuy(MarketStock mstk,float cfd_sale,float cfd_buy){
        mstk.notifyBuyPositionObservingStock();
        mstk.notifySalePositionObservingStock();
        mstk.setCfd_buy(cfd_buy);
        mstk.setCfd_sale(cfd_sale);
        app.stockDAO.put(mstk.getId_stock(), mstk);
    }

    @Override
    public void run(){ //REFACTORED EXTRACT METHOD !!
        do {
            System.out.println("\n> Wait While Stock Prices Are Being Updated...");
            List<MarketStock> lmstk = app.listMarketStocks();

            for (MarketStock mstk : lmstk) {

                float cfd_buy = API.getStockCfdBuy(mstk.getName());
                float cfd_sale = API.getStockCfdSale(mstk.getName());

                if (mstk.getCfd_Buy() != cfd_buy && mstk.getCfd_Sale() == cfd_sale ) {
                    updateBuy(mstk,cfd_buy);
                }
                else if (mstk.getCfd_Sale() != cfd_sale && mstk.getCfd_Buy() == cfd_buy) {
                    updateSale(mstk,cfd_sale);
                }
                else if (mstk.getCfd_Sale() != cfd_sale && mstk.getCfd_Buy() != cfd_buy) {
                    updateSellAndBuy(mstk,cfd_sale,cfd_buy);
                }
            }
            try {
                System.out.println("> Stock prices updated!\n");
                sleep(1 * 60 * 1000); // update after 2min
            } catch (InterruptedException e) {
                System.out.println("Couldn't update the stock prices... :(");
            }
        } while (true);
    }
}
