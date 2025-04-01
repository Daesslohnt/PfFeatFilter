package gui;

import javax.swing.*;
import domain.controller.Controller;
import gui.view.InputPanel;
import gui.view.FilterPanel;

public class app {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
            // Controller erstellen
            Controller controller = new Controller();

            // Hauptfenster
            JFrame frame = new JFrame("Character App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 600);

            // Panels erstellen
            InputPanel inputPanel = new InputPanel(controller);
            FilterPanel filterPanel = new FilterPanel(controller);

            // Split-Pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(inputPanel);
            splitPane.setRightComponent(filterPanel);
            splitPane.setDividerLocation(400);

            frame.add(splitPane);
            frame.setVisible(true);
        });
    }
}
