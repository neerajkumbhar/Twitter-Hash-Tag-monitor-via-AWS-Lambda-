/**
 * New node file
 */
exports.getconnection=function(){
	//---Create database connection  - @NehaWani - start
	var twitterDataCollection;
	// Retrieve
	var MongoClient = require('mongodb').MongoClient;
	// Connect to the db
	MongoClient.connect("mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll", function(err, db) {
	//  MongoClient.connect("mongodb://localhost:27017/test", function(err, db){
	//MongoClient.connect("mongodb://myapp:myapp@ds061611.mongolab.com:61611/twitterdb", function(err, db) {
	  if(!err) {
	    console.log("We are connected");
		//twitterDataCollection = db.collection('twitterObj');
		twitterDataCollection = db.collection('twitter_data');
		
	  }
	  return twitterDataCollection;

	});
}
//---Create database connection  - @NehaWani - end