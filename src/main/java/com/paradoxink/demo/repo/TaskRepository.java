package com.paradoxink.demo.repo;

import com.paradoxink.demo.model.Task;
import com.paradoxink.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUser(User user);

}
