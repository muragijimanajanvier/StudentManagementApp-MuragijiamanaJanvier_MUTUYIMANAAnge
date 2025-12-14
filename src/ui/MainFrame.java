package ui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings({ "unused", "serial" })
public class MainFrame extends JFrame {
    
    public MainFrame() {
        // Window setup
        setTitle("University Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null); // Center window
        
        createMenuBar(); // Add menu bar
        
        // Try to set window icon
        try {
            setIconImage(new ImageIcon("icon.png").getImage());
        } catch (Exception e) {
            // Icon not found - continue without it
        }
        
        // Add main content panel (StudentPanel)
        StudentPanel studentPanel = new StudentPanel();
        add(studentPanel);
        
        setVisible(true); // Make window visible
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu with Exit option
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // Close application
        fileMenu.add(exitItem);
        
        // Help menu with About dialog
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "University Student Management System\nVersion 1.0\n\n" +
                "Manages student records for the university.\n" +
                "Database: university_db\nTable: students",
                "About", JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);
        
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar); // Set menu bar for frame
    }
}