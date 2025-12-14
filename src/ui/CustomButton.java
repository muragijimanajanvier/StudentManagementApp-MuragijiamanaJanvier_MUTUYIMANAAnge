package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom JButton with rounded corners, hover effects, and multiple style options.
 * Provides a modern, flat design alternative to standard Swing buttons.
 */
public class CustomButton extends JButton {
    /**
	 * Serialization ID for version control
	 */
	private static final long serialVersionUID = 1L;
	
    // Color states for different button interactions
    private Color primaryColor;    // Default/normal state color
    private Color hoverColor;      // Mouse hover state color
    private Color pressedColor;    // Mouse pressed state color
    private int cornerRadius = 8;  // Radius for rounded corners
    
    /**
     * Creates a primary styled button with default settings.
     * @param text The button display text
     */
    public CustomButton(String text) {
        this(text, "primary"); // Default to primary style
    }
    
    /**
     * Creates a button with specified style.
     * @param text The button display text
     * @param style One of: "primary", "secondary", "danger", "success", "info", "light"
     */
    public CustomButton(String text, String style) {
        super(text);
        setStyle(style);  // Apply color scheme based on style
        initButton();     // Initialize button properties
    }
    
    /**
     * Sets color scheme based on style name.
     * @param style The style identifier (case-insensitive)
     */
    private void setStyle(String style) {
        switch (style.toLowerCase()) {
            case "secondary":
                primaryColor = new Color(66, 133, 244); // Google blue
                hoverColor = new Color(51, 103, 214);
                pressedColor = new Color(42, 85, 176);
                break;
            case "danger":
                primaryColor = new Color(219, 68, 55); // Red
                hoverColor = new Color(197, 57, 45);
                pressedColor = new Color(183, 28, 28);
                break;
            case "success":
                primaryColor = new Color(15, 157, 88); // Green
                hoverColor = new Color(13, 137, 76);
                pressedColor = new Color(11, 117, 65);
                break;
            case "info":
                primaryColor = new Color(59, 89, 152); // Facebook blue
                hoverColor = new Color(47, 71, 122);
                pressedColor = new Color(35, 53, 91);
                break;
            case "light":
                primaryColor = new Color(255, 255, 255, 220); // Semi-transparent white
                hoverColor = new Color(255, 255, 255, 240);
                pressedColor = new Color(255, 255, 255, 200);
                setForeground(new Color(41, 128, 185)); // Blue text for contrast
                break;
            default: // "primary" - default blue theme
                primaryColor = new Color(41, 128, 185); // Ocean blue
                hoverColor = new Color(52, 152, 219);   // Lighter hover
                pressedColor = new Color(34, 112, 165); // Darker pressed
                break;
        }
    }
    
    /**
     * Initializes common button properties and mouse listeners.
     */
    private void initButton() {
        // Remove default Swing styling
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        
        // Set default text color to white if not already set
        if (getForeground() == null || getForeground() == Color.BLACK) {
            setForeground(Color.WHITE);
        }
        
        // Typography and spacing
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Pointer cursor on hover
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        
        // Mouse listener for hover and press effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint(); // Redraw with hover color
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                repaint(); // Return to normal color
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                repaint(); // Show pressed state
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                repaint(); // Return to normal/hover state
            }
        });
    }
    
    /**
     * Custom painting for rounded rectangle and dynamic colors.
     * @param g The Graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determine current color based on button state
        Color currentColor = primaryColor;
        boolean hovered = getModel().isRollover(); // Check if mouse is over button
        boolean pressed = getModel().isPressed();  // Check if button is pressed
        
        if (pressed) {
            currentColor = pressedColor;
        } else if (hovered) {
            currentColor = hoverColor;
        }
        
        // Draw rounded rectangle background
        g2.setColor(currentColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        
        // Add subtle shadow for depth (except when pressed)
        if (!pressed) {
            g2.setColor(new Color(0, 0, 0, 20)); // Semi-transparent black
            g2.fill(new RoundRectangle2D.Float(0, 1, getWidth(), getHeight(), cornerRadius, cornerRadius));
        }
        
        // Paint text and icon on top of background
        super.paintComponent(g);
    }
    
    /**
     * Sets minimum preferred size for consistent button dimensions.
     * @return Preferred dimension (minimum 120x40 pixels)
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width = Math.max(size.width, 120); // Minimum width
        size.height = 40; // Fixed height
        return size;
    }
}