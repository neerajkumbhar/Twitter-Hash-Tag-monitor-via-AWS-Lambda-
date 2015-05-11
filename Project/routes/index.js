var express = require('express');
var router = express.Router();
var events = require('./event');
var viewEvents = require('./viewevent');

/* GET Dashboard page */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Twitter Dashboard' });
});

/* GET Dashboard page */
router.get('/dashboard', function(req, res, next) {
  res.render('index', { title: 'Twitter Dashboard' });
});

/* GET Create Events page*/
router.get('/events', function(req,res){
	res.render('events');
});

/* POST Create Events page*/
router.post('/events',events.insertEvent);
	
/*GET My Events page*/
router.get('/viewevents',viewEvents.listEvents);


module.exports = router;
