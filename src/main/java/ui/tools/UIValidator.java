package ui.tools;

import core.utils.CommonTool;

import javax.swing.*;
import java.awt.*;

public class UIValidator {
    private static UIValidator instance;

    public static UIValidator getInstance() {
        if (instance == null) {
            instance = new UIValidator();
        }
        return instance;
    }

    public boolean validate(Logger log, JComponent... components) {
        String message = "Часть полей заполнено некорректно";
        boolean correct = true;
        for (JComponent component : components) {
            if (component instanceof JTextField) {
                if (CommonTool.isNull(((JTextField) component).getText())) {
                    correct = false;
                    component.setBackground(Color.red);
                    continue;
                }
            } else if (component instanceof JComboBox) {
                if (((JComboBox) component).getSelectedIndex() == -1) {
                    correct = false;
                    component.setBackground(Color.red);
                    continue;
                }
            } else if (component instanceof JSpinner) {
                if (!CommonTool.isExist(((JSpinner) component).getValue())) {
                    correct = false;
                    component.setBackground(Color.red);
                    continue;
                }
            }
            component.setBackground(Color.white);
        }

        if (!correct) {
            log.error(message);
        }
        return correct;
    }
}
