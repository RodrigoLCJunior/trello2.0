package org.example.trello2.repository;

import java.util.List;

import org.example.trello2.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

    List<TaskList> findByStatus(String string);
}
