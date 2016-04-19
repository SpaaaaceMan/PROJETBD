package etapes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


/**This is the object to use twiter4j API.
 * @author Thomas
 *
 */
public class TwitterHandler {
	
	/**
	 * The OAuthConsumerKey
	 */
	private String OAuthConsumerKey;
	/**
	 * The OAuthConsumerSecret (it shouldn't be appearing somewhere but well...)
	 */
	private String OAuthConsumerSecret;
	/**
	 * The OAuthAccessToken
	 */
	private String OAuthAccessToken;
	/**
	 * The OAuthAccessTokenSecret (it shouldn't be appearing somewhere but well...)
	 */
	private String OAuthAccessTokenSecret;
	
	/**The twitter object
	 * 
	 */
	private Twitter twitter;
	
	/**The constructor with keys provided
	 * @param OAuthConsumerKey OAuthConsumerKey
	 * @param OAuthConsumerSecret OAuthConsumerSecret (it shouldn't be appearing somewhere but well...)
	 * @param OAuthAccessToken OAuthAccessToken
	 * @param OAuthAccessTokenSecret OAuthAccessTokenSecret (it shouldn't be appearing somewhere but well...)
	 */
	public TwitterHandler (String OAuthConsumerKey, String OAuthConsumerSecret
			, String OAuthAccessToken, String OAuthAccessTokenSecret) {
		
		this.OAuthConsumerKey       = OAuthConsumerKey;
		this.OAuthConsumerSecret    = OAuthConsumerSecret;
		this.OAuthAccessToken       = OAuthAccessToken;
		this.OAuthAccessTokenSecret = OAuthAccessTokenSecret;
		
		connect ();
		
	}
	
	/**The default constructor with my own keys provided.
	 * 
	 */
	public TwitterHandler () {
		
		this.OAuthConsumerKey       = "lTaMcd8utKwMCXbBTyNqsgZsz";
		this.OAuthConsumerSecret    = "IEXIf7AQp0C0XiefYiTC14xurcFbSvlWqNcSHfaZQp8D4Pl4Re";
		this.OAuthAccessToken       = "2496888542-Lr7DdmQxvm6bVOdb0z2DYRHgenlJLarkPuhDPd6";
		this.OAuthAccessTokenSecret = "9fK9gFDfIMLdmOyAkwDtvcNZBvRClVsM8SRlzQSwViwcF";
		
		connect ();
	}
	
	/**The function to connect to the twitter API
	 * @return true if succeeded, false otherwise.
	 */
	private Boolean connect () {
		System.out.println("Connection...");
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(OAuthConsumerKey);
		builder.setOAuthConsumerSecret(OAuthConsumerSecret);
		
		builder.setOAuthAccessToken(OAuthAccessToken);
		builder.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
		Configuration configuration = builder.build();
		
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		
		System.out.println("Connected...");
		this.twitter = twitter;
		return true;
	}
	
	/**A cool sleep function (with some "." appearing in the console)
	 * @param timeToSleep the time to sleep
	 */
	private void sleep (int timeToSleep) {
		try {
			for (int i = 0; i < timeToSleep; ++i) {
				Thread.sleep(1000);
				System.out.print(".");
			}
			System.out.println();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**The main step function to look for status with a word.
	 * @param word The word to look for
	 * @param nbStatus The number of status to get
	 * @return The data found
	 * @throws IOException some File exceptions
	 */
	public List<Map<String, String>> searchBy (String word, int nbStatus) throws IOException {

		long sinceId = 0l;
		
		Query query = new Query(word);
		query.setCount(100);
		int cpt = 0;
		List<Map<String, String>> tweets = new ArrayList<Map<String, String>>();
		ArrayList<String> motsInutiles = new ArrayList<String>();
		Etape3 nettoyeur = new Etape3("files/motsinutiles.txt");
		motsInutiles = nettoyeur.Nettoyage();
		boolean inutile = false;
		try { 
			while (cpt < nbStatus){

				query.setSinceId(sinceId);
				QueryResult resultQuery = twitter.search(query);
				sinceId = resultQuery.getMaxId();
				for (Status status : resultQuery.getTweets()) {
					int max = 0;
					Map<String, String> data = new HashMap<String, String>();
					
					data.put("Date", status.getCreatedAt().toString()); 
					data.put("ScreenName", "@" + status.getUser().getScreenName()); 
					
					String[] splitedText = status.getText().split(" ");
					
					for (int i = 0; i < splitedText.length; ++i) {
						
						for (int j = 0; j < motsInutiles.size(); ++j){
							
							if (splitedText[i].equals(motsInutiles.get(j))){
								inutile = true;
								break;
							}
							
						}
						
						if (!inutile){
							data.put("Word" + i, splitedText[i]);
						}
						
						inutile = false;
						
						if (i > max){
							max = i;
						}
						
					}		
					
					//max -= motsEnMoins;
					
					data.put("Limite", String.valueOf(max));
					
					tweets.add(data);
					
					System.out.println(status.getCreatedAt().toString() + 
							    " @" + status.getUser().getScreenName() +
							    " "  + status.getText());
					
					if (nbStatus == cpt) {
						break;
					}
					
					cpt++;
					
					if (cpt % 100 == 0){
						break;
					}
				}
				
				if (cpt % 100 == 0) {	
					// Sleep for 15 seconds = 60 requests per 15 minutes
					System.out.println("Waiting 15 seconds for the next 100 status");
					this.sleep(15);
				}
			}
		}
		catch (TwitterException e1) {
			e1.printStackTrace();
		}

        /*query.setCount(100);
        query.setSince("2011-01-01");*/
		
		/*data.put("MAX", String.valueOf(max));
		tweets.add(data);*/
		

		//System.out.println("Count : " + resultQuery.getTweets().size()) ;
	
		return tweets;
	}
	
	/**The getter for the Twitter
	 * @return the Twitter object
	 */
	public Twitter getTwitter() {
		return twitter;
	}	

	/*public static void main(String[] args) {
		System.out.println("Connection...");
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("lTaMcd8utKwMCXbBTyNqsgZsz");
		builder.setOAuthConsumerSecret("IEXIf7AQp0C0XiefYiTC14xurcFbSvlWqNcSHfaZQp8D4Pl4Re");
		
		builder.setOAuthAccessToken("2496888542-Lr7DdmQxvm6bVOdb0z2DYRHgenlJLarkPuhDPd6");
		builder.setOAuthAccessTokenSecret("9fK9gFDfIMLdmOyAkwDtvcNZBvRClVsM8SRlzQSwViwcF");
		Configuration configuration = builder.build();
		
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		
		System.out.println("Connected...");
        
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
		
        //query.setCount(100);
        //query.setSince("2011-01-01");
		

		//System.out.println("Count : " + resultQuery.getTweets().size()) ;
		
		System.out.println("Done !");
	}*/
}
