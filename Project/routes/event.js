/**
 * New node file
 */
//var mongodb = require('./mongo');

exports.insertEvent=function(req,res){
	//---Create database connection  - @NehaWani - start
	var eventDataCollection;
	// Retrieve
	var MongoClient = require('mongodb').MongoClient;
	// Connect to the db
	//MongoClient.connect("mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll", function(err, db) {
	//  MongoClient.connect("mongodb://localhost:27017/test", function(err, db){
	//MongoClient.connect("mongodb://myapp:myapp@ds061611.mongolab.com:61611/twitterdb", function(err, db) {
	
	var event = new Object();
	event._id = req.body.eventTag;
	event.name = req.body.eventName;
	event.email = req.body.emailAlias;
	event.startdate = req.body.startdate;
	event.enddate = req.body.enddate;
	event.flag = true;
	//event.eventDesc = req.body.eventDesc;
	var eventUsers = req.body.eventUsers;
	var users = eventUsers.split(',');
	var userArray = new Array();
	for(var i = 0; i < users.length; i++){
		var userObject = new Object();
		userObject.name = users[i];
		userObject.value = 0;
		userArray.push(userObject);
	}
	event.users = userArray;
/*	var usersList = '{';
	for(var i = 0; i < users.length-1; i++){
		usersList = usersList +'"'+users[i]+ '":0 '+',';
	}
	usersList = usersList + '"'+users[users.length-1]+'":0 }';
*/	
	event.target_twitts = req.body.targetTweets;
	event.twitt_limit = req.body.tweetLimit;
//	var eventDoc = JSON.stringify(event);
//	var eventDoc = {'_id': eventTag, 'name': eventName, 'status': false, 'users' : usersList,'target_twitts': targetTweets,'twitt_limit': tweetLimit};
//	var eventDoc = {'_id': eventTag, 'name': eventName, 'status': false, 'users' :  users , 'u': {'neha': 10,'omkar': 10},'target_twitts': targetTweets,'twitt_limit': tweetLimit};
	console.log(event);
	
	MongoClient.connect("mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273", function(err, db) {	
	  if(!err) {
	    console.log("We are connected");
		//twitterDataCollection = db.collection('twitterObj');
	    eventDataCollection = db.collection('event');
// removed data ui fetching code from here		
		
		eventDataCollection.insert(event, function(err, records){
//		if(err != null){
		var record = JSON.stringify(records);
		console.log("Record added as "+record);
		res.render('events');
		//res.render('viewevents');
	 // }
	  });
	  }
	});
};