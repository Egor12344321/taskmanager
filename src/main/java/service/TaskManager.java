package service;

import model.Priority;
import model.Task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static final List<Task> tasks = new ArrayList<>();
    private final Scanner scanner;
    public TaskManager(Scanner scanner) {
        this.scanner = scanner;
    }
    public static void printTask(Task task) {
        String status = task.isCompleted() ? "✓" : " ";
        String output = String.format(
                "[%s] %s | Приоритет: %s | Дедлайн: %s | Описание: %s",
                status,
                task.getTitle(),
                task.getPriority(),
                task.getDeadline().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                task.getDescription()
        );

        System.out.println(output);
    }

    public static void showTask() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. ", i + 1);
            printTask(tasks.get(i));
        }
    }
    public static void deleteTask(Scanner  scanner) {

        if (tasks.isEmpty()) {
            System.out.println("Нет задач для удаления.");
            return;
        } else {
            System.out.println("\n--- Удаление задачи ---");
            System.out.println("Выберите задачу для удаления:");
            showTask();
            try {
                int choice1 = scanner.nextInt();
                scanner.nextLine();
                if (choice1 < 1 || choice1 > tasks.size()) {
                    System.out.println("Ошибка: задача с таким номером не существует!");
                } else {
                    tasks.remove(choice1 - 1);
                    System.out.println("Задача успешно удалена.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: необходимо ввести число!");
                scanner.next();
            }
        }
    }
    public static void showTasksByStatus(boolean completed) {
        String header = completed ? "\n--- ВЫПОЛНЕННЫЕ ЗАДАЧИ ---" : "\n--- ТЕКУЩИЕ ЗАДАЧИ ---";
        System.out.println(header);

        boolean hasTasks = false;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.isCompleted() == completed) {
                System.out.printf("%d. ", i + 1);
                printTask(task);
                hasTasks = true;
            }
        }

        if (!hasTasks) {
            System.out.println("Задачи не найдены");
        }
    }

    public static void editTask(Scanner scanner){
        System.out.println("\n--- Редактирование задачи ---");
        System.out.println("Выберите задачу для редактирования:");
        showTask();
        try {
            int choice2 = scanner.nextInt();
            if (choice2 < 1 || choice2 > tasks.size()) {
                System.out.println("Ошибка: задача с таким номером не существует!");
            } else {
                tasks.remove(choice2 - 1);
                scanner.nextLine();
                createTask(scanner);
                System.out.println("Задача отредактирована");
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка: необходимо ввести число!");
            scanner.next();
        }

    }


    public static void createTask(Scanner scanner) {
        System.out.print("Название: ");
        String title = scanner.nextLine();

        System.out.print("Описание: ");
        String description = scanner.nextLine();

        System.out.println("Приоритет (1-Высокий, 2-Средний, 3-Низкий):");
        System.out.print("Ваш выбор: ");
        Priority priority = switch (scanner.nextInt()) {
            case 1 -> Priority.HIGH;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.LOW;
            default -> Priority.MEDIUM;
        };
        scanner.nextLine();

        System.out.print("Введите дедлайн (ГГГГ-ММ-ДД ЧЧ:ММ): ");
        String input = scanner.nextLine();
        LocalDateTime deadline = LocalDateTime.parse(input,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Task task = new Task(
                title,
                description,
                priority,
                deadline
        );
        tasks.add(task);
        System.out.println("✅ Задача добавлена!");
        printTask(task);
    }
}