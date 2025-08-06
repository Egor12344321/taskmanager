package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime deadline;
    private boolean isCompleted;


    // Конструктор

    public Task(String title, String description, model.Priority priority, LocalDateTime deadline){
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("Название не может быть пустым!");
        }
        if (deadline.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Дедлайн не может быть в прошлом!");
        }
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public model.Priority getPriority() {
        return priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(model.Priority priority) {
        this.priority = priority;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %s | Приоритет: %s | Дедлайн: %s | Описание: %s",
                isCompleted ? "✓" : " ",
                title,
                priority,
                deadline.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                description
        );
    }
}
