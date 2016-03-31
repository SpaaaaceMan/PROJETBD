import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


public class Etape0 {
	
	private String OAuthConsumerKey;
	private String OAuthConsumerSecret;
	private String OAuthAccessToken;
	private String OAuthAccessTokenSecret;
	
	private Twitter twitter;
	
	public Etape0 (String OAuthConsumerKey, String OAuthConsumerSecret
			, String OAuthAccessToken, String OAuthAccessTokenSecret) {
		
		this.OAuthConsumerKey       = OAuthConsumerKey;
		this.OAuthConsumerSecret    = OAuthConsumerSecret;
		this.OAuthAccessToken       = OAuthAccessToken;
		this.OAuthAccessTokenSecret = OAuthAccessTokenSecret;
		
		connect ();
		
	}
	
	public Etape0 () {
		
		this.OAuthConsumerKey       = "lTaMcd8utKwMCXbBTyNqsgZsz";
		this.OAuthConsumerSecret    = "IEXIf7AQp0C0XiefYiTC14xurcFbSvlWqNcSHfaZQp8D4Pl4Re";
		this.OAuthAccessToken       = "2496888542-Lr7DdmQxvm6bVOdb0z2DYRHgenlJLarkPuhDPd6";
		this.OAuthAccessTokenSecret = "9fK9gFDfIMLdmOyAkwDtvcNZBvRClVsM8SRlzQSwViwcF";
		
		connect ();
	}
	
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
	
	public List<Map<String, String>> searchBy () {
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
		int cmpt = 0;
		List<Map<String, String>> tweets = new ArrayList<Map<String, String>>();
		try {
			while (cmpt < 10000){

				QueryResult resultQuery = twitter.search(query);
				for (Status status : resultQuery.getTweets()) {
					
					Map<String, String> data = new HashMap<String, String>();
					
					data.put("Date", status.getCreatedAt().toString()); 
					data.put("ScreenName", "@" + status.getUser().getScreenName()); 
					data.put("Text", status.getText());
					tweets.add(data);
				}
				cmpt++;
			}
		}
		catch (TwitterException e1) {
			e1.printStackTrace();
		}

        /*query.setCount(100);
        query.setSince("2011-01-01");*/
		

		//System.out.println("Count : " + resultQuery.getTweets().size()) ;
		
		System.out.println("Done !" + cmpt);
		return tweets;		
	}

	public static void main(String[] args) {
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
		
        /*query.setCount(100);
        query.setSince("2011-01-01");*/
		

		//System.out.println("Count : " + resultQuery.getTweets().size()) ;
		
		System.out.println("Done !");
	}

	public Twitter getTwitter() {
		return twitter;
	}	
}
