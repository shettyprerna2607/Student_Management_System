import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Student class to store student details
class Student {
    String name;
    int rollno;
    float marks;

    public Student(String name, int rollno, float marks) {
        this.name = name;
        this.rollno = rollno;
        this.marks = marks;
    }

    public String toString() {
        return "Name: " + name + ", Roll No: " + rollno + ", Marks: " + marks;
    }
}

// Main GUI class for student management
public class StudentManagementGUI {
    private JFrame frame;
    private JTextField nameField, rollField, marksField, searchField;
    private DefaultTableModel tableModel;
    private ArrayList<Student> studentList = new ArrayList<>();

    public StudentManagementGUI() {
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // 1. Top Panel: for adding new student with GridBagLayout
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(10);
        rollField = new JTextField(10);
        marksField = new JTextField(10);
        JButton addButton = new JButton("Add Student");

        // Row 0 - Name Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        topPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        topPanel.add(nameField, gbc);

        // Roll No Label and Field
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(new JLabel("Roll No:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.3;
        topPanel.add(rollField, gbc);

        // Row 1 - Marks Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        topPanel.add(new JLabel("Marks:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        topPanel.add(marksField, gbc);

        // Add Button spanning two columns, aligned left
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        topPanel.add(addButton, gbc);

        // 2. Center Panel: table to show students
        String[] columns = {"Name", "Roll No", "Marks"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // 3. Bottom Panel: for searching, deleting, and showing topper (FlowLayout with spacing)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton topperButton = new JButton("Show Topper");

        bottomPanel.add(new JLabel("Roll No:"));
        bottomPanel.add(searchField);
        bottomPanel.add(searchButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(topperButton);

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        searchButton.addActionListener(e -> searchStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        topperButton.addActionListener(e -> showTopper());

        frame.setVisible(true);
    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            int roll = Integer.parseInt(rollField.getText().trim());
            float marks = Float.parseFloat(marksField.getText().trim());

            if(name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name cannot be empty.");
                return;
            }

            Student s = new Student(name, roll, marks);
            studentList.add(s);
            tableModel.addRow(new Object[]{name, roll, marks});

            nameField.setText("");
            rollField.setText("");
            marksField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Enter valid inputs!");
        }
    }

    private void searchStudent() {
        String input = searchField.getText().trim();
        try {
            int roll = Integer.parseInt(input);
            for (Student s : studentList) {
                if (s.rollno == roll) {
                    JOptionPane.showMessageDialog(frame, "Found: " + s);
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Enter a valid roll number.");
        }
    }

    private void deleteStudent() {
        String input = searchField.getText().trim();
        try {
            int roll = Integer.parseInt(input);
            for (int i = 0; i < studentList.size(); i++) {
                if (studentList.get(i).rollno == roll) {
                    studentList.remove(i);
                    tableModel.removeRow(i);
                    JOptionPane.showMessageDialog(frame, "Student deleted.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Student not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Enter a valid roll number.");
        }
    }

    private void showTopper() {
        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No students to evaluate.");
            return;
        }

        Student topper = studentList.get(0);
        for (Student s : studentList) {
            if (s.marks > topper.marks) {
                topper = s;
            }
        }
        JOptionPane.showMessageDialog(frame, "Topper: " + topper);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
