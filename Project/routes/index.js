var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('dashboard', { title: 'Express' });
});

/* GET Hello World page. */
router.get('/helloworld', function(req, res) {
    res.render('helloWorld', { title: 'Hello, World!' })
});

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
});
//---Create database connection  - @NehaWani - end

// Get twitter data from db - @NehaWani - start
/* GET Tweets page*/
router.get('/tweets', function(req, res) {
   // var db = req.db;
/*     twitterDataCollection.find({},{},function(e,docs){
	console.log(docs);
        res.render('helloWorld', {
			title: 'Hello, World!',
            //twitter_data : JSON.stringify(docs)
			twitter_data : docs
		});
    }); */
	
	var obj = twitterDataCollection.find();
	obj.toArray(function(e,docs){
	console.log(docs);
        res.render('tweets', {
			title: 'Tweets',
            //twitter_data : JSON.stringify(docs)
			twitter_data : docs
		});
	});
});
// Get twitter data from db - @NehaWani - end

/*
 * GET userlist.
 */
/* router.get('/userlist', function(req, res) {
    var db = req.db;
    db.collection('twitter_data').find().toArray(function (err, items) {
        res.json(items);
    });
}); */

router.get('/helloworld', function(req, res) {
    res.render('helloWorld', { title: 'Hello, World!' })
});

/* GET Charts*/
router.get('/charts', function(req,res){
	res.render('charts');
});

/* GET Dashboard page*/
router.get('/dashboard', function(req,res){
	res.render('dashboard');
});


module.exports = router;