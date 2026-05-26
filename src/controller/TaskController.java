package controller;

import model.Task;

import java.sql.Connection;
import java.util.ArrayList;
import database.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaskController {
    private ArrayList<Task> taskList;

    public TaskController() {
        taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void deleteTask(int index) {
        taskList.remove(index);
    }

    public void completeTask(int index) {
        taskList.get(index).setStatus("Completed");
    }

    public void updateTask(int index, String newName) {
        taskList.get(index).setName(newName);
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void saveTaskToDatabase(Task task) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO tasks (task_name, status) VALUES (?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, task.getName());
            pst.setString(2, task.getStatus());
            pst.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> loadTasksFromDatabase() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM tasks";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("task_name");
                String status = rs.getString("status");
                System.out.println(
                        "DB -> id=" + id +
                                ", name=" + name +
                                ", status=" + status);
                tasks.add(new Task(id, name, status));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return tasks;
    }

    public void deleteTaskFromDatabase(int id) {

        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeTaskInDatabase(int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "UPDATE tasks SET status=? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "Completed");
            pst.setInt(2, id);
            pst.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTaskInDatabase(int id, String newName) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "UPDATE tasks SET task_name=? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, newName);
            pst.setInt(2, id);
            pst.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
