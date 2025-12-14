package ui;

import model.Student;
import service.StudentService;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

@SuppressWarnings("serial")
public class StudentPanel extends JPanel {
    private StudentService studentService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel statusLabel;
    
    // Blue theme colors
    private final Color HEADER_BG = new Color(52, 152, 219);      // Bright blue
    private final Color HEADER_FG = Color.WHITE;
    private final Color TABLE_BG = new Color(240, 248, 255);      // Alice blue
    private final Color TABLE_GRID = new Color(200, 220, 240);    // Light blue grid
    @SuppressWarnings("unused")
	private final Color ROW_HOVER = new Color(220, 237, 255);     // Light blue hover
    private final Color ROW_SELECTED = new Color(189, 224, 254);  // Selected row blue
    private final Color ALT_ROW = new Color(245, 250, 255);       // Alternate row
    private final Color TITLE_COLOR = new Color(25, 118, 210);    // Dark blue title
    private final Color STATUS_BG = new Color(30, 139, 195);      // Status bar blue
    
    private String[] columns = {"ID", "Name", "Age", "Email", "Course", "Grade"};
    
    public StudentPanel() {
        this.studentService = new StudentService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        
        initComponents();
        loadStudents();
    }
    
    private void initComponents() {
        // Top panel with title - Blue gradient
        JPanel topPanel = new GradientPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel title = new JLabel("🎓University Student Management system ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setIcon(new ImageIcon("university_icon.png")); // Optional icon
        topPanel.add(title, BorderLayout.WEST);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        
        JLabel searchLabel = new JLabel("🔍 Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchLabel.setForeground(Color.WHITE);
        
        searchField = new JTextField(18);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setBackground(new Color(255, 255, 255, 200));
        
        JButton searchButton = new CustomButton("Search", "light");
        JButton clearButton = new CustomButton("Clear", "light");
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel with table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240), 2));
        centerPanel.setBackground(Color.WHITE);
        
        // Create table model
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 2) return Integer.class;
                return String.class;
            }
        };
        
        // Create styled table
        studentTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                
                // Alternate row colors
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : ALT_ROW);
                }
                
                // Highlight specific columns with different colors
                if (column == 0) { // ID column
                    c.setBackground(new Color(230, 240, 255));
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                } else if (column == 2) { // Age column
                    c.setBackground(new Color(240, 248, 255));
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                } else if (column == 3) { // Email column
                    c.setForeground(new Color(0, 102, 204)); // Blue email text
                } else if (column == 5) { // Grade column
                    String grade = getValueAt(row, column).toString().toUpperCase();
                    if (grade.contains("A")) {
                        c.setForeground(new Color(0, 150, 0)); // Green for A
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (grade.contains("B")) {
                        c.setForeground(new Color(255, 140, 0)); // Orange for B
                    } else if (grade.contains("C")) {
                        c.setForeground(new Color(255, 69, 0)); // Red-Orange for C
                    } else if (grade.contains("D") || grade.contains("F")) {
                        c.setForeground(Color.RED); // Red for D/F
                    }
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                }
                
                return c;
            }
        };
        
        // Configure table appearance
        studentTable.setRowHeight(35);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentTable.setForeground(new Color(50, 50, 50));
        studentTable.setBackground(TABLE_BG);
        studentTable.setGridColor(TABLE_GRID);
        studentTable.setShowGrid(true);
        studentTable.setIntercellSpacing(new Dimension(1, 1));
        studentTable.setSelectionBackground(ROW_SELECTED);
        studentTable.setSelectionForeground(Color.BLACK);
        
        // Configure table header
        JTableHeader header = studentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(HEADER_BG);
        header.setForeground(HEADER_FG);
        header.setReorderingAllowed(false);
        
        // Custom header renderer for each column
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                label.setBackground(HEADER_BG);
                label.setForeground(HEADER_FG);
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)
                ));
                label.setHorizontalAlignment(JLabel.CENTER);
                
                // Add subtle gradient effect to header
                label.setOpaque(true);
                
                return label;
            }
        });
        
        // Set column specific alignments and widths
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        studentTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        studentTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        studentTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        
        // Set column widths
        TableColumnModel columnModel = studentTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);   // ID
        columnModel.getColumn(1).setPreferredWidth(180);  // Name
        columnModel.getColumn(2).setPreferredWidth(60);   // Age
        columnModel.getColumn(3).setPreferredWidth(200);  // Email
        columnModel.getColumn(4).setPreferredWidth(150);  // Course
        columnModel.getColumn(5).setPreferredWidth(70);   // Grade
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(TABLE_BG);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel with blue theme
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TABLE_GRID));
        
        JButton addButton = new CustomButton("➕ Add Student", "primary");
        JButton editButton = new CustomButton("✏️ Edit Student", "secondary");
        JButton deleteButton = new CustomButton("🗑️ Delete Student", "danger");
        JButton refreshButton = new CustomButton("🔄 Refresh", "info");
        JButton viewButton = new CustomButton("👁️ View Details", "success");
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        
        // Status bar - Blue theme
        statusLabel = new JLabel(" Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(STATUS_BG);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, HEADER_BG),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        add(statusLabel, BorderLayout.SOUTH);
        
        // Add action listeners
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        viewButton.addActionListener(e -> viewStudentDetails());
        refreshButton.addActionListener(e -> loadStudents());
        searchButton.addActionListener(e -> searchStudents());
        clearButton.addActionListener(e -> {
            searchField.setText("");
            loadStudents();
        });
        
        // Double-click to view details
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewStudentDetails();
                }
            }
        });
        
        // Hover effect for table rows
        studentTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = studentTable.rowAtPoint(e.getPoint());
                if (row >= 0 && !studentTable.isRowSelected(row)) {
                    studentTable.setRowSelectionInterval(row, row);
                }
            }
        });
    }
    
    private void loadStudents() {
        try {
            List<Student> students = studentService.getAllPersons();
            tableModel.setRowCount(0);
            
            for (Student student : students) {
                Object[] row = {
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail(),
                    student.getCourse(),
                    student.getGrade()
                };
                tableModel.addRow(row);
            }
            
            // Format current time nicely
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm:ss a");
            String time = sdf.format(new java.util.Date());
            
            statusLabel.setText(" 📊 Total Students: " + students.size() + 
                              " | 📅 " + new java.text.SimpleDateFormat("EEE, MMM dd yyyy").format(new java.util.Date()) +
                              " | ⏰ Last updated: " + time);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "<html><font color='red'>❌ Error loading students:</font><br>" + e.getMessage() + "</html>", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchStudents() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadStudents();
            return;
        }
        
        try {
            List<Student> students = studentService.searchStudents(keyword);
            tableModel.setRowCount(0);
            
            for (Student student : students) {
                Object[] row = {
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail(),
                    student.getCourse(),
                    student.getGrade()
                };
                tableModel.addRow(row);
            }
            
            statusLabel.setText(" 🔍 Found " + students.size() + " student(s) for: '" + keyword + "'");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Search error: " + e.getMessage());
        }
    }
    
    private void addStudent() {
        // Create styled input dialog
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        
        JTextField nameField = createStyledTextField();
        JTextField ageField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JTextField courseField = createStyledTextField();
        JTextField gradeField = createStyledTextField();
        
        panel.add(createLabel("Full Name:"));
        panel.add(nameField);
        panel.add(createLabel("Age:"));
        panel.add(ageField);
        panel.add(createLabel("Email:"));
        panel.add(emailField);
        panel.add(createLabel("Course:"));
        panel.add(courseField);
        panel.add(createLabel("Grade (A, B+, etc):"));
        panel.add(gradeField);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "➕ Add New Student", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                Student student = new Student();
                student.setName(nameField.getText());
                student.setAge(Integer.parseInt(ageField.getText()));
                student.setEmail(emailField.getText());
                student.setCourse(courseField.getText());
                student.setGrade(gradeField.getText().toUpperCase());
                
                studentService.addPerson(student);
                loadStudents();
                
                JOptionPane.showMessageDialog(this,
                    "<html><font color='#2E7D32'><b>✅ Success!</b></font><br>" +
                    "Student <b>" + student.getName() + "</b> has been added.</html>",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "<html><font color='#C62828'>❌ Error:</font><br>" + e.getMessage() + "</html>",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "<html><font color='#FF9800'>⚠️ Please select a student to edit</font></html>",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        
        try {
            Student student = studentService.getPerson(studentId);
            if (student == null) {
                JOptionPane.showMessageDialog(this, "Student not found!");
                return;
            }
            
            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBackground(Color.WHITE);
            
            JTextField nameField = createStyledTextField(student.getName());
            JTextField ageField = createStyledTextField(String.valueOf(student.getAge()));
            JTextField emailField = createStyledTextField(student.getEmail());
            JTextField courseField = createStyledTextField(student.getCourse());
            JTextField gradeField = createStyledTextField(student.getGrade());
            
            panel.add(createLabel("Full Name:"));
            panel.add(nameField);
            panel.add(createLabel("Age:"));
            panel.add(ageField);
            panel.add(createLabel("Email:"));
            panel.add(emailField);
            panel.add(createLabel("Course:"));
            panel.add(courseField);
            panel.add(createLabel("Grade:"));
            panel.add(gradeField);
            
            int option = JOptionPane.showConfirmDialog(this, panel, 
                "✏️ Edit Student: " + studentName, 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                student.setName(nameField.getText());
                student.setAge(Integer.parseInt(ageField.getText()));
                student.setEmail(emailField.getText());
                student.setCourse(courseField.getText());
                student.setGrade(gradeField.getText().toUpperCase());
                
                studentService.updatePerson(student);
                loadStudents();
                
                JOptionPane.showMessageDialog(this,
                    "<html><font color='#2E7D32'><b>✅ Updated!</b></font><br>" +
                    "Student <b>" + student.getName() + "</b> has been updated.</html>",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "<html><font color='#C62828'>❌ Error:</font><br>" + e.getMessage() + "</html>",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "<html><font color='#FF9800'>⚠️ Please select a student to delete</font></html>",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        
        // Custom confirmation dialog
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        JLabel warningLabel = new JLabel(
            "<html><font color='#C62828' size='4'>⚠️ Confirm Delete</font><br><br>" +
            "Are you sure you want to delete:<br>" +
            "<b>" + studentName + "</b> (ID: " + studentId + ")<br><br>" +
            "<font color='#666'><i>This action cannot be undone.</i></font></html>");
        warningLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(warningLabel, BorderLayout.CENTER);
        
        int confirm = JOptionPane.showConfirmDialog(this, panel, "Confirm Delete", 
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                studentService.deletePerson(studentId);
                loadStudents();
                
                JOptionPane.showMessageDialog(this,
                    "<html><font color='#2E7D32'>✅ Student deleted successfully</font></html>",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "<html><font color='#C62828'>❌ Error:</font><br>" + e.getMessage() + "</html>",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewStudentDetails() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) return;
        
        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        String studentName = (String) tableModel.getValueAt(selectedRow, 1);
        
        try {
            Student student = studentService.getPerson(studentId);
            if (student != null) {
                // Create styled details panel
                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBackground(Color.WHITE);
                panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                // Header
                JLabel header = new JLabel("🎓 Student Details");
                header.setFont(new Font("Segoe UI", Font.BOLD, 18));
                header.setForeground(TITLE_COLOR);
                header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 220, 240)));
                
                // Details in HTML for better formatting
                String details = String.format("""
                    <html>
                    <style>
                    .label { color: #555; font-weight: bold; width: 100px; display: inline-block; }
                    .value { color: #333; }
                    .section { margin-bottom: 15px; }
                    </style>
                    <div class='section'>
                    <div><span class='label'>ID:</span> <span class='value'>%d</span></div>
                    <div><span class='label'>Name:</span> <span class='value'>%s</span></div>
                    <div><span class='label'>Age:</span> <span class='value'>%d years</span></div>
                    <div><span class='label'>Email:</span> <span class='value' style='color:#2196F3'>%s</span></div>
                    </div>
                    <div class='section'>
                    <div><span class='label'>Course:</span> <span class='value'>%s</span></div>
                    <div><span class='label'>Grade:</span> <span class='value' style='color:%s;font-weight:bold'>%s</span></div>
                    </div>
                    </html>
                    """,
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getEmail(),
                    student.getCourse(),
                    getGradeColor(student.getGrade()),
                    student.getGrade());
                
                JLabel detailsLabel = new JLabel(details);
                detailsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                
                panel.add(header, BorderLayout.NORTH);
                panel.add(detailsLabel, BorderLayout.CENTER);
                
                JOptionPane.showMessageDialog(this, panel, 
                    "Student Details: " + studentName, 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading details: " + e.getMessage());
        }
    }
    
    // Helper methods for styling
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
    
    private JTextField createStyledTextField() {
        return createStyledTextField("");
    }
    
    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private String getGradeColor(String grade) {
        if (grade == null) return "#333";
        grade = grade.toUpperCase();
        if (grade.contains("A")) return "#2E7D32"; // Green
        if (grade.contains("B")) return "#FB8C00"; // Orange
        if (grade.contains("C")) return "#F4511E"; // Red-Orange
        if (grade.contains("D") || grade.contains("F")) return "#C62828"; // Red
        return "#333"; // Default
    }
    
    // Custom panel for gradient background
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            Color color1 = new Color(41, 128, 185); // Dark blue
            Color color2 = new Color(52, 152, 219); // Light blue
            
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}