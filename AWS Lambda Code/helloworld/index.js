
/*
Author: Madhura Barve
Date: 1 April 2015

*/

console.log('Loading function');

exports.handler = function(event, context) {

var MongoClient = require('mongodb').MongoClient;

MongoClient.connect("mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll", function(err, db) {
    if(!err) {
        console.log("We are connected ..Pankaj..Cheers");
    }else{
        console.log("We are sorry sir")

}

var collection = db.collection('twitter_data');
   
    
  var twitter_doc = {'UserID':event.UserID,'UserName':event.UserName,'User_Follower_Count':event.User_Follower_Count,'Source':event.Source,'Location':event.Location,'Tweet_Count':event.Tweet_Count,'Tweet_Text':event.Tweet_Text,'ReTweetCount':event.ReTweetCount};
  
    collection.insert(twitter_doc);

   

});

 

    console.log('My app value1 =', event.key1);
    console.log('my ap value2 =', event.key2);
    console.log('my app pankaj value3 =', event.key3);
    context.succeed(event.key1);  
   
};