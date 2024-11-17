package org.example.trello2.service;

import org.example.trello2.model.TaskList;
import org.example.trello2.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    // Todos os GETS
    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    public TaskList getTaskListById(Long id) {
        Optional<TaskList> taskListOptional = taskListRepository.findById(id);
        return taskListOptional.orElse(null); // ou lançar uma exceção, se preferir
    }

    public Map<String, List<TaskList>> listarTarefasPorColuna() {
        Map<String, List<TaskList>> tarefasPorColuna = new HashMap<>();
        tarefasPorColuna.put("A Fazer", taskListRepository.findByStatus("A Fazer"));
        tarefasPorColuna.put("Em Progresso", taskListRepository.findByStatus("Em Progresso"));
        tarefasPorColuna.put("Concluído", taskListRepository.findByStatus("Concluído"));
        return tarefasPorColuna;
    }

    // Todos os POSTS
    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public TaskList salvarTarefa(TaskList taskList) {
        return taskListRepository.save(taskList); // salva a tarefa no repositório
    }

    // Todos os PUTS
    public TaskList updateTaskList(Long id, TaskList taskListDetails) {
        TaskList taskListToUpdate = getTaskListById(id);
        if (taskListToUpdate != null) {
            taskListToUpdate.setTitulo(taskListDetails.getTitulo()); // ajuste conforme os campos do seu modelo
            return taskListRepository.save(taskListToUpdate);
        }
        return null; // ou lançar uma exceção
    }
  
    public TaskList editarTarefa(Long id, TaskList taskListDetails) {
        TaskList taskListToUpdate = getTaskListById(id);
        if (taskListToUpdate != null) {
            taskListToUpdate.setTitulo(taskListDetails.getTitulo());
            taskListToUpdate.setDescricao(taskListDetails.getDescricao());
            taskListToUpdate.setPrioridade(taskListDetails.getPrioridade());
            taskListToUpdate.setDataLimite(taskListDetails.getDataLimite());
            return taskListRepository.save(taskListToUpdate);
        }
        return null;
    }
    
    
    public TaskList moverTarefa(Long id) {
        TaskList taskListToUpdate = getTaskListById(id);
        if (taskListToUpdate != null) {
            String statusAtual = taskListToUpdate.getStatus();
            
            switch (statusAtual) {
                case "A Fazer":
                    taskListToUpdate.setStatus("Em Progresso");
                    break;
                case "Em Progresso":
                    taskListToUpdate.setStatus("Concluído");
                    break;
                case "Concluído":
                    // Não alteramos o status se já está concluído
                    return taskListToUpdate; 
                default:
                    throw new IllegalArgumentException("Status desconhecido: " + statusAtual);
            }
            
            return taskListRepository.save(taskListToUpdate);
        }
        return null; // ou lançar uma exceção personalizada
    }       
    
    
    // Todos os DELETES
    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }
}
