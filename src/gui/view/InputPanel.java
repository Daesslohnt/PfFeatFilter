package gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Character;
import domain.controller.Controller;

public class InputPanel extends JPanel {
    private JComboBox<String> raceField;
    private JComboBox<String> characterClassField;
    private JSpinner levelSpinner, strSpinner, dexSpinner, conSpinner, intSpinner, wisSpinner, chaSpinner;
    private JSpinner fortSpinner, refSpinner, willSpinner;
    private JSpinner babSpinner;
    private Controller controller;

    public InputPanel(Controller controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(0, 2, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Charakter-Eingabe"));

        // Rasse Eingabe
        raceField = new JComboBox<>(Character.getAllRaces());
        characterClassField = new JComboBox<>(Character.getAllClasses());

        levelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));

        // Attribute Spinner (Wertebereich 1-30, Standard 10, Schrittweite 1)
        strSpinner = createAttributeSpinner();
        dexSpinner = createAttributeSpinner();
        conSpinner = createAttributeSpinner();
        intSpinner = createAttributeSpinner();
        wisSpinner = createAttributeSpinner();
        chaSpinner = createAttributeSpinner();

        // Andere Werte Spinner
        fortSpinner = new JSpinner(new SpinnerNumberModel(0, -10, 20, 1));
        refSpinner = new JSpinner(new SpinnerNumberModel(0, -10, 20, 1));
        willSpinner = new JSpinner(new SpinnerNumberModel(0, -10, 20, 1));
        babSpinner = new JSpinner(new SpinnerNumberModel(0, -10, 20, 1));

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveCharacter(
                        (String) raceField.getSelectedItem(),
                        (String) characterClassField.getSelectedItem(),
                        (int) levelSpinner.getValue(),
                        (int) strSpinner.getValue(),
                        (int) dexSpinner.getValue(),
                        (int) conSpinner.getValue(),
                        (int) intSpinner.getValue(),
                        (int) wisSpinner.getValue(),
                        (int) chaSpinner.getValue(),
                        (int) fortSpinner.getValue(),
                        (int) refSpinner.getValue(),
                        (int) willSpinner.getValue(),
                        (int) babSpinner.getValue()
                );
            }
        });

        // Komponenten hinzufügen
        add(new JLabel("Rasse:"));
        add(raceField);

        add(new JLabel("Klasse:"));
        add(characterClassField);

        add(new JLabel("Level:"));
        add(levelSpinner);

        add(new JLabel("Stärke (STR):"));
        add(strSpinner);

        add(new JLabel("Geschick (DEX):"));
        add(dexSpinner);

        add(new JLabel("Konstitution (CON):"));
        add(conSpinner);

        add(new JLabel("Intelligenz (INT):"));
        add(intSpinner);

        add(new JLabel("Weisheit (WIS):"));
        add(wisSpinner);

        add(new JLabel("Charisma (CHA):"));
        add(chaSpinner);

        add(new JLabel("Zähigkeitswurf (Fort):"));
        add(fortSpinner);

        add(new JLabel("Reflexwurf (Ref):"));
        add(refSpinner);

        add(new JLabel("Willenswurf (Will):"));
        add(willSpinner);

        add(new JLabel("Grundangriffsbonus (BAB):"));
        add(babSpinner);

        add(new JLabel()); // Platzhalter
        add(submitButton);
    }

    private JSpinner createAttributeSpinner() {
        return new JSpinner(new SpinnerNumberModel(10, 1, 30, 1));
    }

    public void clearFields() {
        levelSpinner.setValue(1);
        strSpinner.setValue(10);
        dexSpinner.setValue(10);
        conSpinner.setValue(10);
        intSpinner.setValue(10);
        wisSpinner.setValue(10);
        chaSpinner.setValue(10);
        fortSpinner.setValue(0);
        refSpinner.setValue(0);
        willSpinner.setValue(0);
        babSpinner.setValue(0);
    }
}