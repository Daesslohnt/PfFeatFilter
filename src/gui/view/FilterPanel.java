package gui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import api.HandlerDB;
import domain.Feat;
import domain.controller.Controller;
import featdb.HandlerDBimpl;

public class FilterPanel extends JPanel {
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private Controller controller;
    private JTextField nameField;
    private JComboBox<String> typeCombo;
    private JComboBox<String> sourceCombo;

    public FilterPanel(Controller controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Feat Filter"));

        // Filter-Optionen Panel
        JPanel filterOptionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name Filter
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterOptionsPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        filterOptionsPanel.add(nameField, gbc);

        // Type Filter
        gbc.gridx = 0;
        gbc.gridy = 1;
        filterOptionsPanel.add(new JLabel("Type:"), gbc);

        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"All", "General", "Combat", "Item Creation", "Metamagic", "Other"});
        filterOptionsPanel.add(typeCombo, gbc);

        // Source Filter
        gbc.gridx = 0;
        gbc.gridy = 2;
        filterOptionsPanel.add(new JLabel("Source:"), gbc);

        gbc.gridx = 1;
        List<String> allSources = new ArrayList<>();
        allSources.add("All");
        allSources.addAll(new HandlerDBimpl().getAllSourceVariants());
        sourceCombo = new JComboBox<>(allSources.toArray(new String[0]));
        filterOptionsPanel.add(sourceCombo, gbc);

        // Filter Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton filterButton = new JButton("Apply Filter");
        filterButton.addActionListener(e -> applyFilters());
        filterOptionsPanel.add(filterButton, gbc);

        // Ergebnis Tabelle
        String[] columnNames = {"Name", "Type", "Prerequisites", "source"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Komponenten hinzufügen
        add(filterOptionsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void applyFilters() {
        String nameFilter = nameField.getText().trim();
        String selectedType = (String) typeCombo.getSelectedItem();
        String selectedSource = (String) sourceCombo.getSelectedItem();
        String typeFilter = "All".equals(selectedType) ? null : selectedType;
        String sourceFilter = "All".equals(selectedSource) ? null : selectedSource;

        List<Feat> filteredFeats = controller.filterFeats(
                nameFilter,
                typeFilter,
                sourceFilter
        );

        updateTable(filteredFeats);
    }

    private void updateTable(List<Feat> feats) {
        tableModel.setRowCount(0); // Tabelle leeren

        for (Feat feat : feats) {
            tableModel.addRow(new Object[]{
                    feat.getName(),
                    feat.getTypeAsString(),
                    feat.getPrerequisitesAsString(),
                    feat.getSource()
            });
        }
    }
}