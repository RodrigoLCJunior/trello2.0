package org.example.trello2.controller;

import org.example.trello2.model.TaskList;
import org.example.trello2.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasklists")
public class TaskListController {

    private final TaskListService taskListService;

    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping
    public ResponseEntity<List<TaskList>> getAllTaskLists() {
        List<TaskList> taskLists = taskListService.getAllTaskLists();
        return ResponseEntity.ok(taskLists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskList> getTaskListById(@PathVariable Long id) {
        TaskList taskList = taskListService.getTaskListById(id);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/listar-por-coluna")
    public ResponseEntity<Map<String, List<TaskList>>> listarTarefasPorColuna() {
        Map<String, List<TaskList>> tarefasPorColuna = taskListService.listarTarefasPorColuna();
        return ResponseEntity.ok(tarefasPorColuna);
    }


    @PostMapping
    public ResponseEntity<TaskList> criarTarefa(@RequestBody TaskList novaTaskList) {
        novaTaskList.setStatus("A Fazer"); // define o status padrão
        TaskList tarefaCriada = taskListService.salvarTarefa(novaTaskList);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskList> updateTaskList(@PathVariable Long id, @RequestBody TaskList taskListDetails) {
        TaskList updatedTaskList = taskListService.updateTaskList(id, taskListDetails);
        return ResponseEntity.ok(updatedTaskList);
    }

    @PutMapping("/{id}/editar")
    public ResponseEntity<TaskList> editarTarefa(@PathVariable Long id, @RequestBody TaskList taskListDetails) {
        TaskList tarefaEditada = taskListService.editarTarefa(id, taskListDetails);
        if (tarefaEditada != null) {
            return ResponseEntity.ok(tarefaEditada);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}/mover")
    public ResponseEntity<TaskList> moverTarefa(@PathVariable Long id) {
        TaskList tarefaMovida = taskListService.moverTarefa(id);
        if (tarefaMovida != null) {
            return ResponseEntity.ok(tarefaMovida);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable Long id) {
        taskListService.deleteTaskList(id);
        return ResponseEntity.noContent().build();
    }
}
