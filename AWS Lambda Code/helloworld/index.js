console.log('Loading function');

exports.handler = function(event, context) {

var MongoClient = require('mongodb').MongoClient;
//var AWS = require("aws-sdk");

// Connect to the db
MongoClient.connect("mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll", function(err, db) {
    if(!err) {
        console.log("We are connected ..Pankaj..Cheers");
    }else{
        console.log("We are sorry sir")

}

var collection = db.collection('twitter_data');
   // var doc1 = {'hello':'doc1'};
    //  var twitter_doc = {'Key1':event.key1,'Key2':event.key2,'Key3':event.key3};
  var twitter_doc = {'UserID':event.UserID,'UserName':event.UserName,'User_Follower_Count':event.User_Follower_Count,'Source':event.Source,'Location':event.Location,'Tweet_Count':event.Tweet_Count,'Tweet_Text':event.Tweet_Text,'ReTweetCount':event.ReTweetCount};
  
    collection.insert(twitter_doc);

   

});

 
console.log('AWS EC2 version is',AWS.EC2.apiVersions)
    console.log('My app value1 =', event.key1);
    console.log('my ap value2 =', event.key2);
    console.log('my app pankaj value3 =', event.key3);
    context.succeed(event.key1);  // Echo back the first key value
   
};