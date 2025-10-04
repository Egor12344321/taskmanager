import java.util.Scanner;
import service.TaskManager;
import service.FileTaskStorage;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager(scanner);
        FileTaskStorage fileTaskStorage = new FileTaskStorage();
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.out.println("=== ПЛАНИРОВЩИК ЗАДАЧ ===");
            while (true) {
                System.out.println("1. Добавить задачу");
                System.out.println("2. Показать все задачи");
                System.out.println("3. Отметить задачу выполненной");
                System.out.println("4. Фильтрация задач");
                System.out.println("5. Отредактировать задачу");
                System.out.println("6. Удалить задачу");
                System.out.println("7. Выгрузить задачи в файл");
                System.out.println("8. Получить задачи из файла");
                System.out.println("9. Выход");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1:
                        try {
                            TaskManager.createTask(scanner);
                        } catch (Exception e) {
                            System.err.println("Ошибка: " + e.getMessage());
                        }
                        break;
                    case 2:
                        if (TaskManager.tasks.isEmpty()){
                            System.out.println("Список задач пуст");
                        }else{
                            System.out.println("Список задач:");
                            TaskManager.showTask();
                        }
                        break;
                    case 3:
                        if (TaskManager.tasks.isEmpty()){
                            System.out.println("Список задач пуст");
                            break;
                        }else{
                            System.out.println("\n--- ОТМЕТКА ВЫПОЛНЕНИЯ ---");
                            TaskManager.showTask();
                            System.out.print("Номер задачи для отметки (0 для отмены): ");
                            int taskNumber = scanner.nextInt();
                            if (taskNumber < 1 || taskNumber > TaskManager.tasks.size()) {
                                System.out.println("Отменено");
                                break;
                            } else {
                                TaskManager.tasks.get(taskNumber - 1).markCompleted();
                                System.out.println("✅ Задача отмечена выполненной:");
                                TaskManager.printTask(TaskManager.tasks.get(taskNumber - 1));
                                break;
                            }
                        }
                    case 4:
                        if (TaskManager.tasks.isEmpty()){
                            System.out.println("Список задач пуст");
                            break;
                        }else {
                            System.out.println("Выберите какие задачи показать (true - выполненнные, false - текущие)");
                            boolean complete = scanner.nextBoolean();
                            TaskManager.showTasksByStatus(complete);
                        }
                        break;
                    case 5:
                        if (TaskManager.tasks.isEmpty()){
                            System.out.println("Список задач пуст");
                            break;
                        }else {
                            TaskManager.editTask(scanner);
                        }
                        break;
                    case 6:
                        if (TaskManager.tasks.isEmpty()){
                            System.out.println("Список задач пуст");
                            break;
                        }else {
                            TaskManager.deleteTask(scanner);
                        }
                        break;
                    case 7:
                        if (TaskManager.tasks.isEmpty()){
                            System.out.println("Список задач пуст");
                            break;
                        }else {
                            fileTaskStorage.saveTasks();
                            System.out.println("Задачи успешно выгружены");
                        }
                        break;
                    case 8:
                        fileTaskStorage.loadTasks();
                        System.out.println("Задачи из файла выгружены");
                        break;
                    case 9:
                        System.out.println("Выход из программы");
                        return;
                    default:
                        System.out.println("Неверный ввод!");
                }
            }
        } catch (Exception e) {
            System.err.println("ОШИБКА: " + e.getMessage());
        }
    }


}