
/*

Name :Madhura Barve
Date : 1 Apr 2015

*/

console.log('Loading function');



exports.handler = function(event, context) {

var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');

var database;

//var url = 'mongodb://pdighe:Dar21968@ds049171.mongolab.com:49171/poll';
var url = 'mongodb://NehaWani:NehaWani@ds061268.mongolab.com:61268/cmpe273';


MongoClient.connect(url, function(err, db) {
  assert.equal(null, err);
  console.log("Connected correctly to server.");
database=db;
  //db.close();
var collection = db.collection('event');


  var twitter_doc = {'UserID':event.UserID,'UserName':event.UserName,'User_Follower_Count':event.User_Follower_Count,'Source':event.Source,'Location':event.Location,'Tweet_Count':event.Tweet_Count,'Tweet_Text':event.Tweet_Text,'ReTweetCount':event.ReTweetCount};




var str='';

str=''+event.key3;

console.log(event.Users.length);


var length=event.Users.length;

/*for(var i = 0; i &lt; length; i++) {

console.log(event.Users[i].name+" "+event.Users[i].value);
collection.update({"_id":event.TagID,"users.name":event.Users[i].name},{$set:{"users.$.value":event.Users[i].value}});

}*/





//collection.update({"_id":event.TagID,"users.name":event.TagName},{$set:{"users.$.value":event.TagValue}});

context.succeed(event.TagID);

});





    console.log('value1 =', event.TagID);
    console.log('value2 =', event.TagName);
    console.log('value3 =', event.TagValue);
   // context.succeed(event.key1);  // Echo back the first key value
   //  context.fail('Something went wrong');
};