package vista;

import logica.ValidadorNombre;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class PanelInicio extends JPanel {

    private final VentanaPrincipal ventana;
    private final JTextField campoNombre = new JTextField(18);
    private final JLabel etiquetaError = new JLabel(" ");

    public PanelInicio(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Estilo.FONDO);
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout(16, 0));
        encabezado.setBackground(Estilo.PRIMARIO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(26, 32, 26, 32));

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JLabel titulo = Estilo.titulo("BATALLAS POKÉMON", Estilo.TITULO, Color.WHITE);
        JLabel subtitulo = Estilo.titulo("Laboratorio de Programación II · UNITEC",
                Estilo.NORMAL, new Color(0xD8E3FF));
        JLabel examen = Estilo.titulo("Examen 1 – Parcial 1", Estilo.PEQUENA, new Color(0xB9CBFF));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(subtitulo);
        textos.add(examen);

        encabezado.add(new LogoPokeball(), BorderLayout.WEST);
        encabezado.add(textos, BorderLayout.CENTER);
        return encabezado;
    }

    private JPanel crearCuerpo() {
        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(Estilo.FONDO);

        JPanel tarjeta = Estilo.tarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setPreferredSize(new Dimension(430, 320));

        JLabel titulo = Estilo.titulo("Registra tu entrenador", Estilo.SUBTITULO, Estilo.TEXTO);
        JLabel ayuda = Estilo.titulo("Entre 3 y 10 caracteres. Solo letras, números y espacios.",
                Estilo.PEQUENA, Estilo.NEUTRO);
        JLabel etiquetaCampo = Estilo.titulo("Nombre del entrenador", Estilo.SECCION, Estilo.TEXTO);

        campoNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campoNombre.setFont(Estilo.NORMAL);
        campoNombre.addActionListener(e -> continuar());

        etiquetaError.setFont(Estilo.PEQUENA);
        etiquetaError.setForeground(Estilo.PELIGRO);
        etiquetaError.setAlignmentX(LEFT_ALIGNMENT);

        JButton botonContinuar = Estilo.boton("Continuar", Estilo.PRIMARIO);
        botonContinuar.setAlignmentX(LEFT_ALIGNMENT);
        botonContinuar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        botonContinuar.addActionListener(e -> continuar());

        JButton botonReglas = Estilo.boton("Cómo jugar", Estilo.NEUTRO);
        botonReglas.addActionListener(e -> ventana.mostrarInstrucciones());
        JButton botonSalir = Estilo.boton("Salir", Estilo.PELIGRO);
        botonSalir.addActionListener(e -> ventana.salir());

        JPanel filaSecundaria = new JPanel();
        filaSecundaria.setLayout(new BoxLayout(filaSecundaria, BoxLayout.X_AXIS));
        filaSecundaria.setOpaque(false);
        filaSecundaria.setAlignmentX(LEFT_ALIGNMENT);
        filaSecundaria.add(botonReglas);
        filaSecundaria.add(Box.createHorizontalStrut(10));
        filaSecundaria.add(botonSalir);
        filaSecundaria.add(Box.createHorizontalGlue());

        titulo.setAlignmentX(LEFT_ALIGNMENT);
        ayuda.setAlignmentX(LEFT_ALIGNMENT);
        etiquetaCampo.setAlignmentX(LEFT_ALIGNMENT);

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(ayuda);
        tarjeta.add(Box.createVerticalStrut(22));
        tarjeta.add(etiquetaCampo);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(campoNombre);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(etiquetaError);
        tarjeta.add(Box.createVerticalStrut(14));
        tarjeta.add(botonContinuar);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(filaSecundaria);

        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.insets = new Insets(20, 20, 20, 20);
        cuerpo.add(tarjeta, restricciones);
        return cuerpo;
    }

    private void continuar() {
        String error = ValidadorNombre.validar(campoNombre.getText());
        if (error != null) {
            etiquetaError.setText(error);
            campoNombre.requestFocusInWindow();
            return;
        }
        etiquetaError.setText(" ");
        try {
            ventana.getControl().crearEntrenador(campoNombre.getText());
            ventana.irAEquipo();
        } catch (RuntimeException ex) {
            etiquetaError.setText(ex.getMessage());
        }
    }
}
