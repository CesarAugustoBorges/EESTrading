package scrapper;

public class AtivoFinanceiroScrapperFactory {

	public AtivoFinanceiroScrapper newJSONActions() {
		return new JSONActionsScrapper();
	}

	public AtivoFinanceiroScrapper newJSONCrypto() {
		// TODO - implement AtivoFinanceiroScrapperFactory.newJSONCrypto
		return new JSONCryptoScrapper();
	}

}