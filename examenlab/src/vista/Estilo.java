package vista;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public final class Estilo {

    public static final Color FONDO = new Color(0xF1F5FB);
    public static final Color TARJETA = Color.WHITE;
    public static final Color PRIMARIO = new Color(0x2B5FD9);
    public static final Color PRIMARIO_OSCURO = new Color(0x1E43A0);
    public static final Color EXITO = new Color(0x2E9E5B);
    public static final Color ADVERTENCIA = new Color(0xE0A100);
    public static final Color PELIGRO = new Color(0xD64545);
    public static final Color NEUTRO = new Color(0x6B7A90);
    public static final Color TEXTO = new Color(0x1F2A37);
    public static final Color BORDE = new Color(0xD5DEEA);

    public static final Font TITULO = new Font("Segoe UI", Font.BOLD, 30);
    public static final Font SUBTITULO = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font SECCION = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font PEQUENA = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font NEGRITA = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font MONO = new Font("Consolas", Font.PLAIN, 13);

    private Estilo() {
    }

    public static JButton boton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(NEGRITA);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 1),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        return boton;
    }

    public static JLabel titulo(String texto, Font fuente, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        return etiqueta;
    }

    public static JPanel tarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        return panel;
    }

    public static Color colorSalud(int porcentaje) {
        if (porcentaje > 50) {
            return EXITO;
        }
        if (porcentaje > 20) {
            return ADVERTENCIA;
        }
        return PELIGRO;
    }
}
