package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import model.Task;
import java.util.ArrayList;
import controller.TaskController;
import model.Task;

public class MainFrame extends JFrame {
    JTextField taskField;
    JButton addButton;
    JTextArea taskArea;
    JButton deleteButton;
    JButton completeButton;
    JButton updateButton;
    JTable taskTable;
    DefaultTableModel tableModel;
    ArrayList<Task> taskList;
    TaskController controller;

    public MainFrame() {
        setTitle("Todo App");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        taskList = new ArrayList<>();
        controller = new TaskController();

        taskField = new JTextField(25);
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        completeButton = new JButton("Complete Task");
        updateButton = new JButton("Update Task");

        String[] columnNames = { "Task", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        for (Task task : controller.loadTasksFromDatabase()) {
            tableModel.addRow(new Object[] { task.getName(), task.getStatus() });
            controller.addTask(task);
        }

        Panel topPanel = new Panel();
        topPanel.add(taskField);

        Panel bottomPanel = new Panel();
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(completeButton);
        bottomPanel.add(updateButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        addButton.setBackground(new Color(52, 152, 219));
        deleteButton.setBackground(new Color(241, 196, 15));
        completeButton.setBackground(new Color(46, 204, 113));
        updateButton.setBackground(new Color(231, 76, 60));

        addButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
        completeButton.setForeground(Color.WHITE);
        updateButton.setForeground(Color.WHITE);

        addButton.setFocusPainted(false);
        updateButton.setFocusPainted(false);
        completeButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);

        Font font = new Font("Arial", Font.BOLD, 16);
        taskField.setFont(font);
        taskTable.setFont(font);
        addButton.setFont(font);
        deleteButton.setFont(font);
        completeButton.setFont(font);
        updateButton.setFont(font);

        taskTable.setRowHeight(30);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskField.getText();

                if (taskName.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter a task!");
                } else {
                    tableModel.addRow(new Object[] { taskName, "Pending" });
                    Task taskObject = new Task(taskName, "Pending");
                    controller.addTask(taskObject);
                    controller.saveTaskToDatabase(taskObject);
                    taskField.setText("");
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    Task task = controller.getTaskList().get(selectedRow);
                    controller.deleteTaskFromDatabase(task.getId());
                    controller.deleteTask(selectedRow);
                    tableModel.removeRow(selectedRow);
                }
            }
        });
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    Task task = controller.getTaskList().get(selectedRow);
                    controller.completeTaskInDatabase(task.getId());
                    tableModel.setValueAt("Completed", selectedRow, 1);

                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                String updatedTask = taskField.getText();

                if (selectedRow != -1) {
                    Task task = controller.getTaskList().get(selectedRow);
                    controller.updateTaskInDatabase(task.getId(), updatedTask);

                    tableModel.setValueAt(updatedTask, selectedRow, 0);
                    controller.updateTask(selectedRow, updatedTask);
                }
            }
        });
        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                String task = tableModel.getValueAt(selectedRow, 0).toString();
                taskField.setText(task);
            }
        });
        setVisible(true);
    }
}