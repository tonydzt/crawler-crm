package com.teamdev.jxbrowser.chromium;

import com.teamdev.jxbrowser.chromium.EditorCommand;

import javax.swing.JMenuItem;


public class CommandMenuItem
        extends JMenuItem {
    private final EditorCommand command;

    public CommandMenuItem(String commandName, EditorCommand command) {
        super(commandName);
        this.command = command;
    }

    public EditorCommand getCommand() {
        return command;
    }
}
