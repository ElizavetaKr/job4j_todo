package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.task.TaskService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping("/add")
    public String getAdd() {
        return "tasks/add";
    }

    @GetMapping("/all")
    public String getAll() {
        return "tasks/all";
    }

    @GetMapping("/update/{taskId}")
    public String getUpdate(Model model, @PathVariable int taskId) {
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/update";
    }

    @GetMapping("/desc")
    public String getDesc() {
        return "tasks/desc";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNew());
        return "tasks/new";
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findDone());
        return "tasks/done";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update/{taskId}")
    public String update(Model model, @ModelAttribute Task task, @PathVariable int taskId) {
        Optional<Task> taskOpt = taskService.findById(taskId);
        if (taskOpt.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        task.setId(taskId);
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("error", "Произошла ошибка");
            return "tasks/update";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/desc/{taskId}")
    public String desc(Model model, @PathVariable int taskId) {
        Optional<Task> task = taskService.findById(taskId);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/desc";
    }

    @GetMapping("/delete/{taskId}")
    public String delete(Model model, @PathVariable int taskId) {
        boolean isDeleted = taskService.deleteById(taskId);
        if (!isDeleted) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/complete/{taskId}")
    public String completeTask(Model model, @PathVariable int taskId) {
        boolean isUpdated = taskService.updateDone(taskId);
        if (!isUpdated) {
            model.addAttribute("error", "Произошла ошибка");
            return "tasks/update";
        }
        return "redirect:/tasks";
    }
}

