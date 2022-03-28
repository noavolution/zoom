import javax.swing.*;

public class MessageBox
{
    public static boolean showConfirm(String msg, String title)
    {
        JFrame msgFrame = new JFrame(title);
        return JOptionPane.showConfirmDialog(msgFrame, msg) == 0;
    }

    public static void showInfo(String msg, String title)
    {
        JFrame msgFrame = new JFrame(title);
        JOptionPane.showMessageDialog(msgFrame,msg);
    }
}
