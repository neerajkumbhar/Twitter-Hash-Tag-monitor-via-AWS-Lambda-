package demo;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;






import entities.Event;
import entities.TweeterObj;

import javax.swing.plaf.synth.Region;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pankaj on 4/9/2015.
 */
@Component
public class TestLambda {
	
	@Autowired
	private EventRepository event_repo;
	
	

	/*public static void main(String[] args) {

		System.out.println("Hello...");
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(
				"",
				"");
		// com.amazonaws.regions.Region region=new
		// com.amazonaws.regions.Region("us-west-2");
		AWSLambdaClient awsLambdaClient = new AWSLambdaClient(awsCreds);
		awsLambdaClient.setEndpoint("lambda.us-west-2.amazonaws.com/");
		InvokeRequest invokeRequest = new InvokeRequest();
		invokeRequest.setFunctionName("updateTwitterData");
		invokeRequest
				.setPayload("{\"key1\":\"value100001ooolll\",\"key2\":\"value200001\"s,\"key3\":\"value300001\"}");
		awsLambdaClient.invoke(invokeRequest);
		System.out.println("Service name" + awsLambdaClient.getServiceName());

	}*/

	//This method is scheduled to read the current events , when  it gets the events in mongo db it will fetch the data from by using Twitter 4j API
	@Scheduled(fixedRate = 5000)
	public void get_twitter_date() {
		
		String hashtag = "";
		 int limit=10;
		int twit_count=0;

		if(event_repo!=null){
			if(event_repo.count()>0){
			/*Event ev=new Event();
			String[] users={"pankaj_dighe","pdighe"};
			ev.setName("testEvent");
			ev.setStatus(false);
			ev.setTag("#sjsu909");
			ev.setTarget_twitts(10);
			ev.setUsers(users);
			System.out.println("Event Repo is not NULL");
			event_repo.save(ev);
			*/
				
																
				List all_event=event_repo.findAll();
				
				//List all_event_1=event_repo.getAllActiveEvent();
				
				//System.out.println("Active event count"+all_event_1.size());
				
		
				for(int k=0;k<all_event.size();k++){
					
					
					Event ev=(Event)all_event.get(k);
					
				//S	Event ev1=(Event)all_event
					
					
					System.out.println("Executing Query for tag..."+ev.getTag());
					hashtag=ev.getTag();
					
					List<Status> tweets = new ArrayList<Status>();
					Query query = new Query(hashtag);
			        limit=ev.getTwitt_limit();
			        query.count(limit);
			        
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
					
					
					try {
						Map<String ,RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();
						 RateLimitStatus rate_status = rateLimitStatus.get("/search/tweets");
						  //  System.out.println("Endpoint: " + endpoint);
						    System.out.println(" Rate is Limit: " + rate_status.getLimit());
						    System.out.println(" Rate Limit remaining is: " + rate_status.getRemaining());
					//	System.out.println("Rate Limit Status is "+twitter.getRateLimitStatus("/search/tweets"));
						    
						    if(rate_status.getRemaining()>rate_status.getLimit()){
						
				            QueryResult result;
				            do {
				            	result = twitter.search(query);
				                tweets = result.getTweets();
				                for (Status tweet : tweets) {
				                   // System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
				                    
				                  
				                    twit_count++;
				                }
				                
				                if(twit_count>=limit)
				                	break;
				            } while ((query = result.nextQuery()) != null);
						

						System.out.println("tweets.size() " + tweets.size());

						BasicAWSCredentials awsCreds = new BasicAWSCredentials(
								"",
								"");

						AWSLambdaClient awsLambdaClient = new AWSLambdaClient(awsCreds);
						awsLambdaClient.setEndpoint("lambda.us-west-2.amazonaws.com/");

						InvokeRequest invokeRequest = new InvokeRequest();
						invokeRequest.setFunctionName("HelloWorld");
						// invokeRequest.setPayload("{\"UserID\":\"" + tweets.+
						// "\",\"key2\":\"#SJSU\",\"key3\":\"value300001\"}");

						int i = 0;
						for (Status tweet : tweets) {
							i++;
							StringBuffer payload = new StringBuffer();
							payload.append("{\"UserID\":\"" + tweet.getUser().getId()
									+ "\",\"UserName\":\""
									+ tweet.getUser().getScreenName()
									+ "\",\"User_Follower_Count\":\""
									+ tweet.getUser().getFollowersCount()
									+ "\",\"Location\":\"" + tweet.getUser().getLocation()
									+ "\",\"Tweet_Count\":\""
									+ tweet.getUserMentionEntities().length
									+ "\",\"Tweet_Text\":\"" + tweet.getText()
									+ "\",\"Hashtag\":\"" + hashtag + "\"}");
							
							
							// payload.append("{"UserID":""+tweet.getUser().getId());
							// System.out.println(payload.toString());

							String payload_json_string = forJSON(payload.toString());
							// invokeRequest.setPayload("{\"UserID\":\""+tweet.getUser().getId()+"\",\"UserName\":\""+tweet.getUser().getScreenName()+"\",\"User_Follower_Count\":\""+tweet.getUser().getFollowersCount()+"\",\"Location\":\""+tweet.getUser().getLocation()+"\",\"Tweet_Count\":\""+tweet.getUserMentionEntities().length+"\",\"Tweet_Text\":\""+tweet.getText()+"\",\"Hashtag\":\""+hashtag+"\"}");//+"\",\"ReTweetCount\":\""+tweet.getRetweetCount()+"\",\"Hashtag\":\""+hashtag+"\"}");
							// System.out.println("New Parsed JSON"+payload_json_string);
						//	System.out.println("************************************");
							invokeRequest.setPayload(payload_json_string);
							//awsLambdaClient.invoke(invokeRequest); // it will invoke AWS
																	// lambda
							//System.out.println("Invoking the Lambda function");
						}

						//System.out.println("Service name"+ awsLambdaClient.getServiceName());

				}
						    
					} catch (TwitterException te) {
						te.printStackTrace();
						System.out.println("Failed to search tweets: " + te.getMessage());
						//System.exit(-1);
					}
					
					
				}

		}	
			
		}else{
			
			System.out.println("Event REPO is NULL");
		}
		
	      
	}

	public static String forJSON_new(String aText) {

		aText.replace("\\", "");

		return aText;
	}

	public static String forJSON(String aText) {
		final StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			/*
			 * if( character == '\"' ){ result.append("\\\""); } else
			 */
			if (character == '\\') {
				result.append("\\\\");
			} else if (character == '/') {
				result.append("\\/");
			} else if (character == '\b') {
				result.append("\\b");
			} else if (character == '\f') {
				result.append("\\f");
			} else if (character == '\n') {
				result.append("\\n");
			} else if (character == '\r') {
				result.append("\\r");
			} else if (character == '\t') {
				result.append("\\t");
			} else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
}
