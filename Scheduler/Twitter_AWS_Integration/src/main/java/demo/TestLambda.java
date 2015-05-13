package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.StringCharacterIterator;
import java.util.*;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;

import entities.Event;

import javax.print.Doc;

/**
 * Created by Pankaj on 4/9/2015.
 */
@Component
public class TestLambda {

    //@Autowired
    //private EventRepository event_repo;


    //public static void main(String[] args) {

		
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(
				"",
				"");
		
		AWSLambdaClient awsLambdaClient = new AWSLambdaClient(awsCreds);
		awsLambdaClient.setEndpoint("lambda.us-west-2.amazonaws.com/");
		InvokeRequest invokeRequest = new InvokeRequest();
		
		invokeRequest.setFunctionName("handleTweetEvent");
		
		String p="p";
		int pval=99;
		String d="d";
		int dval=77;
		invokeRequest.setPayload("{\"TagID\":\"#sjsu\",\"TagName\":\"d\",\"Users\":[{\"name\":\""+p+"\",\"value\":\""+pval+"\"},{\"name\":\""+d+"\",\"value\":\""+dval+"\"}]}");
		
		String s="[{\"name\":\"p\",\"value\":\"88\"},{\"name\":\"d\",\"value\":\"100\"}]";
		
		
		
		awsLambdaClient.invoke(invokeRequest);
		System.out.println("Service name" + awsLambdaClient.getServiceName());*/


       /* final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll"));
        final MongoDatabase blogDatabase = mongoClient.getDatabase("poll");
         final MongoCollection<Document> postsCollection;
       postsCollection = blogDatabase.getCollection("event");
       System.out.println("Count of documents is"+postsCollection.count());

        List<Document> events=postsCollection.find().into(new ArrayList<Document>());

        System.out.println("Count of documents in List Document is"+events.size());
        String id="#sjsu";

        Bson event_filter= new Document().append("_id","#sjsu");


        //Document post = postsCollection.find(eq("_id", id)).first();
        Document event_one = postsCollection.find(event_filter).first();

       // Document event_users=event_one.get("users");
        List<Document> event_users = (List<Document>) event_one.get("users");

        //event_one.toJson()

        System.out.println("Document is..."+event_one.get("users"));
        System.out.println("Document size is..."+event_users.size());*/

      /*  try {

            JSONArray jsonArray=new JSONArray();

            JSONObject obj2 = new JSONObject();
            obj2.put("name","Pankaj");
         

            JSONObject obj3 = new JSONObject();
            obj3.put("name","Pankaj");
            jsonArray.put(obj3);

            JSONObject obj = new JSONObject();
            obj.put("id","#sjsu");
            obj.put("users",jsonArray);

            System.out.println( obj.toString());


            System.out.println();

        }
        catch (Exception c){}*/
    //}

    @Scheduled(fixedRate = 5000)
    public void get_twitter_date() {

        System.out.println("Events not null");


        // final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll"));

        final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273"));
        //  final MongoDatabase pollDatabase = mongoClient.getDatabase("poll");
        final MongoDatabase pollDatabase = mongoClient.getDatabase("cmpe273");
        final MongoCollection<Document> eventCollection = pollDatabase.getCollection("event");//null;

        InputStream input;
        Properties properties = null;
        String hashtag = "";
        int limit = 10;
        int twit_count = 0;

        try {

            input = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(input);
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        
        if (eventCollection != null) {
           
           
            if (eventCollection.count() > 0) {

                System.out.println("Event Count is.." + eventCollection.count());

                List<Document> all_events = eventCollection.find().into(new ArrayList<Document>());

                for (int k = 0; k < all_events.size(); k++) {

                    Document event = (Document) all_events.get(k);

                    System.out.println("Flag is _____________"+event.getBoolean("flag"));

                    String email_alias=event.getString("email");

                    if(event.getBoolean("flag")){



                        Integer target_twitts=10;
                        Integer twitt_limit=10;

                        try {
                             target_twitts = Integer.parseInt(event.get("target_twitts").toString());
                             twitt_limit = Integer.parseInt(event.get("twitt_limit").toString());
                        }catch (Exception e){
                            e.printStackTrace();

                        }

                        limit=twitt_limit;

                        System.out.println("Target twitts"+target_twitts);
                        System.out.println("Tweet Limit is"+twitt_limit);

                    List<Document> event_users = (List<Document>) event.get("users");

                    System.out.println("Count of Event users ________________" + event_users.size());
                    ArrayList<String> users = new ArrayList<String>();

                    Map<String, Integer> users_map = new HashMap<String, Integer>();


                    for (int f = 0; f < event_users.size(); f++) {
                        Document user = event_users.get(f);

                        System.out.println("Event users are ..." + user.get("name"));
                        users.add(user.get("name").toString().trim());
                        users_map.put(user.get("name").toString().trim(), 0);
                    }

                    System.out.println("Executing Query for tag..." + event.get("_id"));
                    hashtag = event.get("_id").toString();

                     
                    List<Status> tweets = new ArrayList<Status>();
                    Query query = new Query(hashtag);

                    limit = 40;
                    query.count(limit);
                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey(properties.getProperty("tweet.consumer"))
                            .setOAuthConsumerSecret(properties.getProperty("tweet.consumersecret"))
                            .setOAuthAccessToken(properties.getProperty("tweet.accesstoken"))
                            .setOAuthAccessTokenSecret(properties.getProperty("tweet.accesstokensecret"));

                    TwitterFactory tf = new TwitterFactory(cb.build());
                    Twitter twitter = tf.getInstance();


                    try {
                        Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();
                        RateLimitStatus rate_status = rateLimitStatus.get("/search/tweets");
                        System.out.println(" Rate is Limit: " + rate_status.getLimit());
                        System.out.println(" Rate Limit remaining is: " + rate_status.getRemaining());

                        if (rate_status.getRemaining() <= rate_status.getLimit()) {

                            QueryResult result;
                            do {
                                result = twitter.search(query);
                                tweets = result.getTweets();
                                for (Status tweet : tweets) {
                                    System.out.println("@" + tweet.getUser().getScreenName() + " - "); //+ //tweet.getText());
                                    String tweeter_username = tweet.getUser().getScreenName();
                                    if (users_map.containsKey(tweeter_username)) {
                                        

                                        System.out.println("_________________________Tweeter" + tweeter_username);
                                        Integer i = (Integer) users_map.get(tweeter_username);
                                        i++;
                                        users_map.put(tweeter_username, i);

                                    }


                                    twit_count++;
                                }

                                if (twit_count >= limit)
                                    break;
                            } while ((query = result.nextQuery()) != null);


                            System.out.println("tweets.size() " + tweets.size());

                            System.out.println("Map is " + users_map);


                            BasicAWSCredentials awsCreds = new BasicAWSCredentials(properties.getProperty("aws.authkey"), properties.getProperty("aws.authtoken"));

                            AWSLambdaClient awsLambdaClient = new AWSLambdaClient(awsCreds);
                            awsLambdaClient.setEndpoint("lambda.us-west-2.amazonaws.com/");

                            InvokeRequest invokeRequest = new InvokeRequest();
                            invokeRequest.setFunctionName("handleTweetEvent");

                            try {

                                JSONArray jsonArray = new JSONArray();

                                for (int a = 0; a < users_map.size(); a++) {

                                    JSONObject obj3 = new JSONObject();
                                    obj3.put("name", users.get(a));
                                    obj3.put("value", users_map.get(users.get(a)));


                                    jsonArray.put(obj3);

                                }


                                JSONObject aws_payload = new JSONObject();
                                aws_payload.put("TagID", hashtag);
                                aws_payload.put("Users", jsonArray);

                                System.out.println(aws_payload.toString());


                                invokeRequest.setPayload(aws_payload.toString());
                                awsLambdaClient.invoke(invokeRequest);

                                Integer sum=0;

                                for (Integer f : users_map.values()) {
                                    sum += f;
                                }

                                System.out.println("Sum of the Twitt count is"+sum);


                                if(target_twitts==sum) {
                                    System.out.println("Sending an email....");
                                    EmailSender emailSender = new EmailSender();
                                    emailSender.send_email(email_alias, "Event Goal has been achieved, Please stop the recruitment", "Event Goal has been achieved, Please stop the recruitment");

                                    eventCollection.updateOne(new BasicDBObject("_id", hashtag),new Document("$set", new Document("flag", false)));

                                    System.out.println("Updating the document....");
                                }


                            } catch (Exception c) {

                                c.printStackTrace();
                            }


                            int i = 0;


                            

                        }

                    } catch (TwitterException te) {
                        te.printStackTrace();
                        System.out.println("Failed to search tweets: " + te.getMessage());
                        
                    }

                   
                }
                }

            }

        } else {

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
