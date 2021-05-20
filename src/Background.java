import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class Background {

    public static void main(String[] args) throws IOException {

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        Image image = ImageIO.read(Objects.requireNonNull(Background.class.getResource("icon.png")));

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(image, "Wallpaper Changer", popup);
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem exitItem = new MenuItem("   Exit   ");
        exitItem.addActionListener(e -> System.exit(0));
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }

        try {
            int currentHour = new Date(System.currentTimeMillis()).getHours();
            int currentMinute = new Date(System.currentTimeMillis()).getMinutes();

            int leftHour = 10 - currentHour;    // Download image at 10 AM

            if (leftHour > 0) {
                int newHourInMilliseconds = leftHour * 60 * 60 * 1000;
                int newMinuteInMilliseconds = currentMinute * 60 * 1000;

                int totalWaitTime = newHourInMilliseconds - newMinuteInMilliseconds;

                Thread.sleep(totalWaitTime);
            }

            ImageParser.parseImage();

            showTitle();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Closes the program when its job finished
//        System.exit(1);
    }

    static void showTitle() {
        JDialog jDialog = new JDialog();
        jDialog.setLocationRelativeTo(jDialog);
        jDialog.setUndecorated(true);
        jDialog.setAlwaysOnTop(false);
        jDialog.setOpacity(0.5f);
        jDialog.getContentPane().setBackground(Color.BLACK);
        jDialog.setType(Window.Type.UTILITY);   // Hide the program from alt + tab

        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Arial", Font.ITALIC, 16));
        jTextArea.setBackground(Color.BLACK);
        jTextArea.setForeground(Color.WHITE);
        jTextArea.setEditable(false);
        jTextArea.setText(" " + ImageParser.title + " ");

        jTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // JDialog size and location
        jDialog.setSize(jTextArea.getPreferredSize().width + 20, jTextArea.getPreferredSize().height + 20);
        jDialog.setLocation(1536 - jTextArea.getPreferredSize().width, 805);

        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
        jDialog.add(jTextArea);
        jDialog.pack();
        jDialog.setVisible(true);   // Show title
    }
}
