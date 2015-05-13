/**
 * New node file
 */
//var mongodb = require('./mongo');

exports.listEvents=function(req,res){
	//---Create database connection  - @NehaWani - start
	var eventDataCollection;
	// Retrieve
	var MongoClient = require('mongodb').MongoClient;
	
	MongoClient.connect("mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273", function(err, db) {
		eventDataCollection = db.collection('event');
		var obj = eventDataCollection.find();
		obj.toArray(function(e,docs){
		console.log(docs);
	        res.render('viewevents', {
				title: 'Event List',
	            //twitter_data : JSON.stringify(docs)
				events_list : docs
			}); 
		});
	});
};