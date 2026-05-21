package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    JTextField taskField;
    JButton addButton;
    JTextArea taskArea;

    public MainFrame() {
        setTitle("Todo App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        taskField = new JTextField(20);
        addButton = new JButton("Add Task");
        taskArea = new JTextArea(20, 30);
        taskArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskArea);
        add(taskField);
        add(addButton);
        add(scrollPane);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Task = taskField.getText();
                taskArea.append(Task + "\n");
            }
        });
        setVisible(true);
    }
}