/**
 * New node file
 */

var query;

exports.showEvent=function(req,res){
	//---Create database connection  - @NehaWani - start
	var eventDataCollection;
	// Retrieve
	var MongoClient = require('mongodb').MongoClient;
	
	var url = require('url');
    var url_parts = url.parse(req.url, true);
    query = url_parts.query;
    req.session.queryid = query.id;
    console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+query.id);
    console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+req.url);
    
	MongoClient.connect("mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273", function(err, db) {
		eventDataCollection = db.collection('event');
		
		var obj = eventDataCollection.find();
		obj.toArray(function(e,docs){
		console.log(docs);
	//	$window.localStorage.token = docs;
		req.params.event = docs;
	        res.render('index', {
				title: 'Event List',
	            //twitter_data : JSON.stringify(docs)
				events_list : docs,
				event_name : "#"+query.id
			}); 
		});
	});
};


exports.getstring=function(req, res){
	console.log("in function getstring ");
	var user_array = new Array();
	var eventDataCollection;
	// Retrieve
	var MongoClient = require('mongodb').MongoClient;
	
	var url = require('url');
	var url_parts = url.parse(req.url, true);
  query = url_parts.query;
  console.log("Get Count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+query.id);
  console.log("Get Count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+req.url);
	
  MongoClient.connect("mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273", function(err, db) {
  eventDataCollection = db.collection('event');
		
		var id = '#'+req.session.queryid;
		console.log("session value" +id);
		
		//var obj = eventDataCollection.find({_id:id});
		var obj = eventDataCollection.find();
		//console.log("****************" +obj._id);
		obj.toArray(function(e,docs){
		console.log(docs);
	//	$window.localStorage.token = docs;
		req.params.event = docs;
//	        res.render('index', {
//				title: 'Event List',
//	            //twitter_data : JSON.stringify(docs)
//				events_list : docs,
//				event_name : "#"+query.id
//			}); 
//	      
		var temp;
		 docs.forEach(function(event){
			var counter = 0; 
			// temp=event.users;
		//var user_array = new Array();
		
		
		if(event._id == id){
			event.users.forEach(function(user){ 
				console.log("user name *************" +user.name);	
				
				temp=user.name;
				var userelement = new Object();
				userelement.name = user.name;
				userelement.value = user.value;
				
				user_array[counter]=userelement;
				console.log("user_array ******" + user_array[counter].name);
				counter++;
				
				
				
				console.log("user.name ******" + temp);
//				<td> <%= user.name %> </td>
//				<td id= "<%= user.name %>" > <%= user.value %> </td>
//			
			 });
		}
		 });	
		//console.log("****" +docs[0].id);
		//res.send(temp);
		 res.send(user_array);
		
		});
	});
  
  //res.send("gi");

};
	

	
exports.getCount=function(req,res){
	
	 console.log("in Get Count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+query.id);
	//---Create database connection  - @NehaWani - start
	var eventDataCollection;
	// Retrieve
//	var MongoClient = require('mongodb').MongoClient;
//	
//	var url = require('url');
//    var url_parts = url.parse(req.url, true);
//    query = url_parts.query;
//    console.log("Get Count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+query.id);
//    console.log("Get Count >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+req.url);
//    
//	MongoClient.connect("mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273", function(err, db) {
//		eventDataCollection = db.collection('event');
//		
//		var obj = eventDataCollection.find({_id:query.id});
//		obj.toArray(function(e,docs){
//		console.log(docs);
//	//	$window.localStorage.token = docs;
//		req.params.event = docs;
//		res.send( 
//	        	{
//				title: 'Event',
//	            //twitter_data : JSON.stringify(docs)
//				event : docs,
//				event_name : "#"+query.id
//			}); 
//		});
//	});
};




//function getstring(req, res)
//{	
//		console.log("in function getstring ");
//		var string = ["e", "e", "ff"];
//		//var n = Math.floor(Math.random() * string.length);
//		var obj = n
//		res.send(string[0]);
//}