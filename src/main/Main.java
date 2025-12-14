package main;

import ui.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set the application to use the operating system's native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); // Print error but continue running
        }
        
        // Use Swing's event dispatch thread for thread-safe GUI operations
        SwingUtilities.invokeLater(() -> {
            // Console startup messages
            System.out.println("🚀 Starting University Student Management System...");
            System.out.println("📊 Database: university_db");
            System.out.println("📋 Table: students");
            
            // Create and display the main application window
            new MainFrame();
        });
    }
}