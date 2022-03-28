import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class addGameForm extends JFrame
{
    private JPanel addGamePanel;
    private JButton btnSearchMods;
    private JButton btnSave;

    //private JComboBox comboBoxEngine;

    private JTextField txtName;
    private JComboBox comboBoxEngine;
    private JCheckBox checkSeparateEngine;
    private JButton btnSelectCustomEngine;
    private JTextField txtCustomEnginePath;
    private JTextArea txtAreaDescription;
    private JPanel setupPanel;
    private JPanel pathPanel;
    private JPanel namePanel;
    private JPanel descriptionPanel;
    private JComboBox comboBoxIWAD;

    public addGameForm(String title)
    {
        super(title);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(addGamePanel);

        comboBoxEngine.addItem("GZDoom");
        comboBoxEngine.addItem("Zandronum");
        comboBoxEngine.addItem("custom");

        if(FileStructure.getIWADs() != null)
        {
            for(File f : FileStructure.getIWADs())
            {
                comboBoxIWAD.addItem(f.getName());
            }
        }




        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtName.getText().isEmpty() && !comboBoxEngine.getSelectedItem().toString().isEmpty())
                {
                    FileStructure.createMainFileStructure();

                    if(comboBoxEngine.getSelectedItem().toString() == "custom" && checkSeparateEngine.isSelected())
                    {
                        MessageBox.showInfo("Separate engine is meant for the standard engines. If you selected \"custom\" this option is not available", "Attention");
                    }
                    else
                    {
                        if(FileStructure.createInstallation(txtName.getText(), comboBoxEngine.getSelectedItem().toString(), checkSeparateEngine.isSelected(), Objects.equals(comboBoxEngine.getSelectedItem().toString(), "custom"), txtAreaDescription.getText()))
                        {
                            mainForm.listModel.addElement(txtName.getText());
                            dispose();
                        }
                        else
                        {
                            txtName.setText(null);
                            txtName.grabFocus();
                        }
                    }

                }
                else
                {
                    txtName.grabFocus();
                }
            }
        });

        comboBoxEngine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxEngine.getSelectedItem().toString() == "custom")
                {
                    txtCustomEnginePath.setEnabled(true);
                    btnSelectCustomEngine.setEnabled(true);
                }
                else
                {
                    txtCustomEnginePath.setEnabled(false);
                    btnSelectCustomEngine.setEnabled(false);
                }
            }
        });

        this.pack();
    }

    public static void addGame()
    {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not load UI theme");
        }

        JFrame addGameFrame = new addGameForm("Add Game");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/icons/app_ico_plus.png");


        addGameFrame.setIconImage(icon);
        addGameFrame.setMinimumSize(new Dimension(400, 400));
        addGameFrame.setVisible(true);
    }
}
