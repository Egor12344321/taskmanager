package service;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Priority;
import model.Task;
import static service.TaskManager.tasks;

public class FileTaskStorage {
     public void loadTasks(){
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.csv"))){
            String line;
            while ((line = reader.readLine()) != null){
                tasks.add(parseTask(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Создам новый при сохранении.");

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            e.printStackTrace();
                  }
    }
    public void saveTasks(){
         try(BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.csv"))){
            for(Task task : tasks){
                String line = String.join(",",
                        task.getTitle(),
                        task.getDescription(),
                        task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        String.valueOf(task.getPriority().ordinal())

                );
                writer.write(line);
                writer.newLine();
            }
         }  catch (IOException e) {
             System.err.println("Ошибка сохранения задач: " + e.getMessage());
             e.printStackTrace();
         }
    }
    private Task parseTask(String line){
         String[] parts = line.split(",");
         String title = parts[0].trim();
         String description = parts[1].trim();

         int priorityIndex = Integer.parseInt(parts[3].trim());
         Priority priority = Priority.values()[priorityIndex];
        LocalDateTime deadline = LocalDateTime.parse(parts[2],
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Task task = new Task(title, description, priority, deadline);
        return task;
    }
}
