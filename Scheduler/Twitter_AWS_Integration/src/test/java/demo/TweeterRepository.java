package demo;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface TweeterRepository extends MongoRepository<TweeterRepository, String> {

}
