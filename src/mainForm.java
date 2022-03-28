import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class mainForm extends JFrame
{
    private JPanel mainPanel;
    private JList listGames;
    private JPanel pnlRight;
    private JPanel pnlLeft;
    private JButton btnAddGame;
    private JButton btnDeleteGame;
    private JTabbedPane gameTabView;
    private JPanel gameTabOverview;
    private JPanel gameTabSettings;
    private JButton btnPlay;
    private JToolBar barControlGame;
    private JButton btnStop;
    private JButton btnOpenGameFolder;
    private JLabel lblEntryName;
    private JPanel barPanel;
    private JTextArea txtAreaShowDescription;
    private JLabel lblGameLogo;
    private JTextPane txtPaneShowDescription;
    private JMenuBar mainMenuBar = new JMenuBar();

    class MenuListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand())
            {
                case "MENU_ITEM_SETTINGS_CLICK":
                    //txtAreaShowDescription.setText("test1");
                    break;
                case "MENU_ITEM_EXIT_CLICK":
                    System.exit(0);
                    break;
                default:
                    break;

            }

        }
    }

    private MenuListener menuListener = new MenuListener();
    public static DefaultListModel listModel = FileStructure.listInstallations();

    public mainForm(String title)
    {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        FileStructure.createMainFileStructure();

        JMenu menuBarMenu = new JMenu("Menu");
        setJMenuBar(mainMenuBar);
        mainMenuBar.add(menuBarMenu);

        JMenuItem menuItemSettings = new JMenuItem("Settings");
        menuItemSettings.setActionCommand("MENU_ITEM_SETTINGS_CLICK");
        menuItemSettings.addActionListener(menuListener);

        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.setActionCommand("MENU_ITEM_EXIT_CLICK");
        menuItemExit.addActionListener(menuListener);

        menuBarMenu.add(menuItemSettings);
        menuBarMenu.add(menuItemExit);

        listGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listGames.setModel(listModel);

        Font font = new Font("default", Font.PLAIN, 30);
        lblEntryName.setFont(font);



        this.pack();
        btnAddGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGameForm.addGame();
            }
        });

        listGames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!listGames.isSelectionEmpty())
                {
                    Icon lblIcon = new ImageIcon("src/icons/" + FileStructure.getInstallationProp(listGames.getSelectedValue().toString(), "inst.engine").toLowerCase() + "_ico.png");
                    Icon gameIcon = new ImageIcon("src/images/heretic.png");

                    lblEntryName.setIcon(lblIcon);
                    lblEntryName.setText((String) listGames.getSelectedValue());

                    lblGameLogo.setIcon(gameIcon);

                    txtAreaShowDescription.setText(FileStructure.getInstallationProp(listGames.getSelectedValue().toString(), "inst.description"));
                    System.out.println((String) listGames.getSelectedValue());
                }
                else
                {
                    listGames.setSelectedIndex(0);
                }
            }
        });

        btnDeleteGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!listGames.isSelectionEmpty())
                {
                    if(MessageBox.showConfirm("Are you sure?", "Really delete installation?"))
                    {
                        FileStructure.deleteInstallation((String) listGames.getSelectedValue());
                        //listGames.remove(listGames.getSelectedIndex());
                        listModel.remove(listGames.getSelectedIndex());
                        listGames.setSelectedIndex(0);
                        lblEntryName.setText("no installation selected");
                        lblEntryName.setIcon(null);
                    }
                }
                else
                {
                    MessageBox.showInfo("No game selected", "Info");
                }
            }
        });


        //listGames.setSelectedIndex(0);
    }

    public static void main(String[] args) throws IOException {
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not load UI theme");
        }

        JFrame mainFrame = new mainForm("ZOOM");
        mainFrame.setMinimumSize(new Dimension(1000, 800));
        Image icon = Toolkit.getDefaultToolkit().getImage("src/icons/app_ico.png");


        mainFrame.setIconImage(icon);
        mainFrame.setVisible(true);
    }
}
