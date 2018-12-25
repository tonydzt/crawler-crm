package com.teamdev.jxbrowser.chromium;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

public class AboutDialog extends JDialog
{
  public AboutDialog(Frame owner)
  {
    super(owner, "About Etoip Crawler", true);
    initContent();
    initKeyStroke();
    setResizable(false);
    pack();
    setLocationRelativeTo(owner);
    setDefaultCloseOperation(2);
  }
  
  private void initContent() {
    JTextPane aboutText = new JTextPane();
    aboutText.setContentType("text/html");
    aboutText.setText("<html><font face='Arial' size='3'><font size='6'>ETOIP crawler</font><br><br><b>Version 3.0.1 </b><br><br>This application is created for used internally purposes only.<br>&copy; 2016 Etoip Intellectual Property Agency Ltd. All rights reserved.<br><br>Powered by <a color='#3d82f8' href='http://www.etoip.com' style='text-decoration:none'>ETOIP</a>.");
    aboutText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    aboutText.setEditable(false);
    aboutText.addHyperlinkListener(new HyperlinkListener()
    {
      @Override
      public void hyperlinkUpdate(HyperlinkEvent event)
      {
        if (event.getEventType().equals(EventType.ACTIVATED)) {
          try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(event.getURL().toURI());
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
      }
    });
    add(aboutText, "Center");
  }
  
  private void initKeyStroke() {
    addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) {
          dispose();
        }
      }
    });
    JRootPane rootPane = getRootPane();
    KeyStroke keyStroke = KeyStroke.getKeyStroke(27, 0, false);
    rootPane.getInputMap(2).put(keyStroke, "ESCAPE");
    rootPane.getActionMap().put("ESCAPE", new AbstractAction() {
      private static final long serialVersionUID = 6693635607417495802L;
      
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
  }
}
