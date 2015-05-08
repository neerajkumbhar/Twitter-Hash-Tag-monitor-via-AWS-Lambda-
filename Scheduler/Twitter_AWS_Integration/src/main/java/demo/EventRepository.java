package demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import entities.Event;



@Component
public interface EventRepository extends MongoRepository<Event, String>{
	
	//@Query("db.event.find({status:true});")
   // List<Event> getAllActiveEvent();
	

}
