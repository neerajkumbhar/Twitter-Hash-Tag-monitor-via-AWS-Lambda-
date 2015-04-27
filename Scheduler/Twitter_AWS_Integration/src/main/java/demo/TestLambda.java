package demo;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;

import entities.TweeterObj;

import javax.swing.plaf.synth.Region;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj on 4/9/2015.
 */
@Component
public class TestLambda {

	// @Autowired
	// private TweeterData tweeter_repo;

	/*
	 * public static void main(String[] args){
	 * 
	 * System.out.println("Hello...");
	 * 
	 * BasicAWSCredentials awsCreds = new
	 * BasicAWSCredentials("",
	 * "");
	 * 
	 * // com.amazonaws.regions.Region region=new
	 * com.amazonaws.regions.Region("us-west-2");
	 * 
	 * 
	 * AWSLambdaClient awsLambdaClient=new AWSLambdaClient(awsCreds);
	 * awsLambdaClient.setEndpoint("lambda.us-west-2.amazonaws.com/");
	 * 
	 * InvokeRequest invokeRequest=new InvokeRequest();
	 * invokeRequest.setFunctionName("HelloWorld"); invokeRequest.setPayload(
	 * "{\"key1\":\"value100001ooolll\",\"key2\":\"value200001\",\"key3\":\"value300001\"}"
	 * );
	 * 
	 * awsLambdaClient.invoke(invokeRequest);
	 * 
	 * System.out.println("Service name" + awsLambdaClient.getServiceName());
	 * 
	 * 
	 * }
	 */

	@Scheduled(fixedRate = 5000)
	public void get_twitter_date() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("")
				.setOAuthConsumerSecret(
						"")
				.setOAuthAccessToken(
						"")
				.setOAuthAccessTokenSecret(
						"");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		String hashtag="#cmpe273sjsu";
		int wantedTweets = 10;
		long lastSearchID = Long.MAX_VALUE;
		long firstQueryID;
		int remainingTweets = wantedTweets;
		Query query = new Query(hashtag);
		List<Status> tweets = new ArrayList<Status>();

		/*
		 * try { System.out.println("Screen name"+twitter.getScreenName()); }
		 * catch (IllegalStateException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (TwitterException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			//Query query = new Query("#sjsu");
			//QueryResult result;
			//result = twitter.search(query);
			//List<Status> tweets = result.getTweets();

			//System.out.println("Count of Twits" + tweets.size());
		//	System.out
			//		.println("**************************************************************************");
			// for (Status tweet : tweets) {
			// System.out.println("@" + tweet.getUser().getScreenName() + " - "
			// + tweet.getText());
			// }
			 while(remainingTweets > 0)
			  {
			    remainingTweets = wantedTweets - tweets.size();
			    if(remainingTweets > 100)
			    {
			      query.count(100);
			    }
			    else
			    {
			     query.count(remainingTweets); 
			    }
			    QueryResult result = twitter.search(query);
			    tweets.addAll(result.getTweets());
			    Status s = tweets.get(tweets.size()-1);
			    firstQueryID = s.getId();
			    query.setMaxId(firstQueryID);
			    remainingTweets = wantedTweets - tweets.size();
			  }

			  System.out.println("tweets.size() "+tweets.size() );
			 
			

			BasicAWSCredentials awsCreds = new BasicAWSCredentials(
					"AKIAJXH7VU3TDJN5USGQ",
					"O2p5VEatYW/F62krrbja3xP6VikPHsGSM8H1Hm4q");

		

			AWSLambdaClient awsLambdaClient = new AWSLambdaClient(awsCreds);
			awsLambdaClient.setEndpoint("lambda.us-west-2.amazonaws.com/");

			InvokeRequest invokeRequest = new InvokeRequest();
			invokeRequest.setFunctionName("HelloWorld");
		//	invokeRequest.setPayload("{\"UserID\":\"" + tweets.+ "\",\"key2\":\"#SJSU\",\"key3\":\"value300001\"}");
			
			

			 int i=0;
			  for(Status tweet:tweets){
				  i++;
				//System.out.println("Twitt text"+i+"Text:"+s.getText());  
				  StringBuffer payload=new StringBuffer();
				 // System.out.println("Geolocation"+tweet.getPlace().getCountry());
				 // System.out.println("Geolocation"+tweet.);
				  payload.append("{\"UserID\":\""+tweet.getUser().getId()+"\",\"UserName\":\""+tweet.getUser().getScreenName()+"\",\"User_Follower_Count\":\""+tweet.getUser().getFollowersCount()+"\",\"Location\":\""+tweet.getUser().getLocation()+"\",\"Tweet_Count\":\""+tweet.getUserMentionEntities().length+"\",\"Tweet_Text\":\""+tweet.getText()+"\",\"Hashtag\":\""+hashtag+"\"}");
				//  payload.append("{"UserID":""+tweet.getUser().getId());
				 // System.out.println(payload.toString());
				  
				  String payload_json_string=forJSON(payload.toString());
				 // invokeRequest.setPayload("{\"UserID\":\""+tweet.getUser().getId()+"\",\"UserName\":\""+tweet.getUser().getScreenName()+"\",\"User_Follower_Count\":\""+tweet.getUser().getFollowersCount()+"\",\"Location\":\""+tweet.getUser().getLocation()+"\",\"Tweet_Count\":\""+tweet.getUserMentionEntities().length+"\",\"Tweet_Text\":\""+tweet.getText()+"\",\"Hashtag\":\""+hashtag+"\"}");//+"\",\"ReTweetCount\":\""+tweet.getRetweetCount()+"\",\"Hashtag\":\""+hashtag+"\"}");
				 // System.out.println("New Parsed JSON"+payload_json_string);
				  System.out.println("************************************");
				  invokeRequest.setPayload(payload_json_string);
				  awsLambdaClient.invoke(invokeRequest); // it will invoke AWS lambda	 
				  System.out.println("Invoking the Lambda function");
			  }
			

			System.out.println("Service name"
					+ awsLambdaClient.getServiceName());

			// System.out.println("Tweeter Count is +"tweeter_repo.);

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}

		/*
		 * List<Status> statuses; try { statuses = twitter.getHomeTimeline();
		 * 
		 * System.out.println("Showing home timeline."); for (Status status :
		 * statuses) { System.out.println(status.getUser().getName() + ":" +
		 * status.getText()); } } catch (TwitterException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}
	
	public static String forJSON_new(String aText){
		
		aText.replace("\\", "");
		
		
		
		return aText;
	}
	
	 public static String forJSON(String aText){
		    final StringBuilder result = new StringBuilder();
		    StringCharacterIterator iterator = new StringCharacterIterator(aText);
		    char character = iterator.current();
		    while (character != StringCharacterIterator.DONE){
		      /*if( character == '\"' ){
		        result.append("\\\"");
		      }
		      else*/ 
		    	  if(character == '\\'){
		        result.append("\\\\");
		      }
		      else if(character == '/'){
		        result.append("\\/");
		      }
		      else if(character == '\b'){
		        result.append("\\b");
		      }
		      else if(character == '\f'){
		        result.append("\\f");
		      }
		      else if(character == '\n'){
		        result.append("\\n");
		      }
		      else if(character == '\r'){
		        result.append("\\r");
		      }
		      else if(character == '\t'){
		        result.append("\\t");
		      }
		      else {
		        //the char is not a special one
		        //add it to the result as is
		        result.append(character);
		      }
		      character = iterator.next();
		    }
		    return result.toString();    
		  }
}
