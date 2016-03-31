import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class Etape0 {
	private final static Logger logger = Logger.getLogger(Etape0.class);

	public static void main(String[] args) {
		logger.info("Retrieving tweets...");
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("lTaMcd8utKwMCXbBTyNqsgZsz");
		builder.setOAuthConsumerSecret("IEXIf7AQp0C0XiefYiTC14xurcFbSvlWqNcSHfaZQp8D4Pl4Re");
		
		builder.setOAuthAccessToken("2496888542-Lr7DdmQxvm6bVOdb0z2DYRHgenlJLarkPuhDPd6");
		builder.setOAuthAccessTokenSecret("9fK9gFDfIMLdmOyAkwDtvcNZBvRClVsM8SRlzQSwViwcF");
		Configuration configuration = builder.build();
		
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		
		System.out.println("key:" + twitter.getConfiguration().getOAuthConsumerKey());
		System.out.println("secret: " + twitter.getConfiguration().getOAuthConsumerSecret());
        
		String recherche = "";

		BufferedReader input = new BufferedReader (new InputStreamReader(System.in));

		System.out.println("Recherche de tweet contenant : ");

		try {
			recherche = input.readLine();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

		Query query = new Query(recherche);
		
		
		try {
			QueryResult resultQuery = twitter.search(query);
			for (Status status : resultQuery.getTweets()) {
				System.out.println(status.getCreatedAt() + 
						" : @" + status.getUser().getScreenName() + 
						" : " + status.getText());
			}
		}
		catch (TwitterException e1) {
			e1.printStackTrace();
		}
		
        /*query.setCount(100);
        query.setSince("2011-01-01");*/
		

		//System.out.println("Count : " + resultQuery.getTweets().size()) ;
		
        logger.info("done! ");
	}
	
}
