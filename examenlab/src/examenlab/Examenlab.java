package examenlab;

import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Examenlab {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("No se pudo aplicar el tema Nimbus: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
