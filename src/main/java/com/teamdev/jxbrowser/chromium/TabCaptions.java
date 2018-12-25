package com.teamdev.jxbrowser.chromium;

import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class TabCaptions
        extends JPanel {
    private TabCaption selectedTab;
    private JPanel tabsPane;
    private JPanel buttonsPane;

    public TabCaptions() {
        createUI();
    }

    private void createUI() {
        setLayout(new BoxLayout(this, 0));
        setBackground(Color.DARK_GRAY);
        add(createItemsPane());
        add(createButtonsPane());
        add(Box.createHorizontalGlue());
    }

    private JComponent createItemsPane() {
        tabsPane = new JPanel();
        tabsPane.setOpaque(false);
        tabsPane.setLayout(new BoxLayout(tabsPane, 0));
        return tabsPane;
    }

    private JComponent createButtonsPane() {
        buttonsPane = new JPanel();
        buttonsPane.setOpaque(false);
        buttonsPane.setLayout(new BoxLayout(buttonsPane, 0));
        return buttonsPane;
    }

    public void addTab(TabCaption item) {
        tabsPane.add(item);
    }

    public void removeTab(TabCaption item) {
        tabsPane.remove(item);
    }

    public void addTabButton(TabButton button) {
        buttonsPane.add(button);
    }

    public TabCaption getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(TabCaption selectedTab) {
        this.selectedTab = selectedTab;
        this.selectedTab.setSelected(true);
    }
}
