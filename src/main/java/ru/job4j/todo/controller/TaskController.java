package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.category.CategoryService;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.TaskService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping("/add")
    public String getAdd(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/add";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/all";
    }

    @GetMapping("/desc")
    public String getDesc(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
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
            model.addAttribute("message", "Произошла ошибка");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/update/{taskId}")
    public String getUpdate(Model model, @PathVariable int taskId) {
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
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

    @PostMapping("/add")
    public String add(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam("categoryId") List<Integer> categoriesId) {
        task.setUser(user);
        task.getCategories().addAll(categoryService.findByList(categoriesId));
        taskService.save(task);
        task.setCreated(LocalDateTime.now(ZoneId.of(user.getTimezone())));
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("error", "Произошла ошибка");
            return "tasks/update";
        }
        return "redirect:/tasks";
    }
}

