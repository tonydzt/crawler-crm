package com.teamdev.jxbrowser.chromium;

import com.teamdev.jxbrowser.chromium.resources.Resources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class JSConsole extends JPanel {
  private JTextArea console;
  private final Browser browser;
  private final ExecutorService executor;
  
  JSConsole(Browser browser)
  {
    this.browser = browser;
    executor = Executors.newCachedThreadPool();
    setLayout(new BorderLayout());
    add(createTitle(), "North");
    add(createConsoleOutput(), "Center");
    add(createConsoleInput(), "South");
  }
  
  private JComponent createConsoleInput() {
    JPanel result = new JPanel(new BorderLayout());
    result.setBackground(Color.WHITE);
    
    JLabel label = new JLabel(">> ");
    label.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 0));
    
    final JTextField consoleInput = new JTextField();
    consoleInput.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
    consoleInput.addActionListener(e -> executor.submit(() -> {
      final String script = consoleInput.getText();
      JSValue jsValue = browser.executeJavaScriptAndReturnValue(script);
      final String executionResult = jsValue.toString();
      SwingUtilities.invokeLater(() -> {
        JSConsole.this.updateConsoleOutput(script, executionResult);
        consoleInput.setText("");
      });
    }));
    result.add(label, "West");
    result.add(consoleInput, "Center");
    return result;
  }
  
  private JComponent createConsoleOutput() {
    console = new JTextArea();
    console.setFont(new Font("Consolas", Font.PLAIN, 12));
    console.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    console.setEditable(false);
    console.setWrapStyleWord(true);
    console.setLineWrap(true);
    console.setText("");
    JScrollPane scrollPane = new JScrollPane(console);
    scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    return scrollPane;
  }
  
  private JComponent createTitle() {
    JPanel panel = new JPanel(new BorderLayout());
    
    panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    panel.add(createTitleLabel(), "West");
    panel.add(createCloseButton(), "East");
    return panel;
  }
  
  private static JComponent createTitleLabel() {
    return new JLabel("JavaScript Console");
  }
  
  private JComponent createCloseButton() {
    JButton closeButton = new JButton();
    closeButton.setOpaque(false);
    closeButton.setToolTipText("Close JavaScript Console");
    closeButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    closeButton.setPressedIcon(Resources.getIcon("META-INF/close-pressed.png"));
    closeButton.setIcon(Resources.getIcon("META-INF/close.png"));
    closeButton.setContentAreaFilled(false);
    closeButton.setFocusable(false);
    closeButton.addActionListener(e -> firePropertyChange("JSConsoleClosed", false, true));
    return closeButton;
  }
  
  private void updateConsoleOutput(String script, String executionResult) {
    displayScript(script);
    displayExecutionResult(executionResult);
    console.setCaretPosition(console.getText().length());
  }
  
  private void displayExecutionResult(String result) {
    console.append(result);
    console.append("\n");
  }
  
  private void displayScript(String script) {
    console.append(">> ");
    console.append(script);
    console.append("\n");
  }
}
