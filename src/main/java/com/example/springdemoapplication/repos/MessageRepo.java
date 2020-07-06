package com.example.springdemoapplication.repos;

import com.example.springdemoapplication.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Kirill Verevkin
 */
public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

}
