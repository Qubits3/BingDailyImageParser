import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class Background {

    public static void main(String[] args) throws IOException {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        //Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Metin\\Desktop\\icon.png");
        Image image = ImageIO.read(Background.class.getResource("icon.jpg"));

        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(image, "Wallpaper Changer", popup);
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            int currentHour = new Date(System.currentTimeMillis()).getHours();
            int currentMinute = new Date(System.currentTimeMillis()).getMinutes();

            int leftHour = 10 - currentHour;

            if (leftHour >= 0) {
                int newHourInMilliseconds = leftHour * 60 * 60 * 1000;
                int newMinuteInMilliseconds = currentMinute * 60 * 1000;

                int totalWaitTime = newHourInMilliseconds - newMinuteInMilliseconds;

                Thread.sleep(totalWaitTime);
            }

            ImageParser.parseImage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(1);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

}
