package vista;

import Pokemon.Pokemon;
import logica.Batalla;
import logica.ReglasBatalla;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

public class PanelResultado extends JPanel {

    private final VentanaPrincipal ventana;
    private final JLabel etiquetaTitulo = new JLabel("Resultado");
    private final JLabel etiquetaSubtitulo = new JLabel(" ");
    private final JTextArea areaResumen = new JTextArea();

    public PanelResultado(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout());
        setBackground(Estilo.FONDO);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(crearTarjeta(), BorderLayout.CENTER);
    }

    private JPanel crearTarjeta() {
        JPanel tarjeta = Estilo.tarjeta();
        tarjeta.setLayout(new BorderLayout(0, 14));

        etiquetaTitulo.setFont(Estilo.TITULO);
        etiquetaTitulo.setForeground(Estilo.TEXTO);
        etiquetaSubtitulo.setFont(Estilo.NORMAL);
        etiquetaSubtitulo.setForeground(Estilo.NEUTRO);

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);
        encabezado.add(etiquetaTitulo);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(etiquetaSubtitulo);

        areaResumen.setEditable(false);
        areaResumen.setFont(Estilo.NORMAL);
        areaResumen.setBackground(Color.WHITE);
        areaResumen.setForeground(Estilo.TEXTO);
        areaResumen.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        areaResumen.setLineWrap(true);
        areaResumen.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaResumen);
        scroll.setBorder(BorderFactory.createLineBorder(Estilo.BORDE));

        JButton btnNueva = Estilo.boton("Nueva partida", Estilo.PRIMARIO);
        btnNueva.setPreferredSize(new Dimension(190, 40));
        btnNueva.addActionListener(e -> ventana.nuevaPartida());

        JButton btnSalir = Estilo.boton("Salir", Estilo.PELIGRO);
        btnSalir.setPreferredSize(new Dimension(120, 40));
        btnSalir.addActionListener(e -> ventana.salir());

        JPanel acciones = new JPanel(new GridLayout(1, 2, 12, 0));
        acciones.setOpaque(false);
        acciones.add(btnNueva);
        acciones.add(btnSalir);

        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.add(acciones, BorderLayout.EAST);

        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(scroll, BorderLayout.CENTER);
        tarjeta.add(pie, BorderLayout.SOUTH);
        return tarjeta;
    }

    public void refrescar() {
        Batalla batalla = ventana.getControl().getBatalla();
        if (batalla == null) {
            return;
        }
        switch (batalla.getEstado()) {
            case VICTORIA:
                etiquetaTitulo.setText("¡Victoria, " + batalla.getNombreJugador() + "!");
                etiquetaTitulo.setForeground(Estilo.EXITO);
                etiquetaSubtitulo.setText("Dejaste al rival sin Pokémon en pie.");
                break;
            case DERROTA:
                etiquetaTitulo.setText("Derrota de " + batalla.getNombreJugador());
                etiquetaTitulo.setForeground(Estilo.PELIGRO);
                etiquetaSubtitulo.setText(batalla.getNombreRival() + " ganó la batalla.");
                break;
            case EMPATE:
                etiquetaTitulo.setText("Empate");
                etiquetaTitulo.setForeground(Estilo.ADVERTENCIA);
                etiquetaSubtitulo.setText("Ninguno de los dos equipos logró imponerse.");
                break;
            default:
                etiquetaTitulo.setText("Batalla finalizada");
                etiquetaTitulo.setForeground(Estilo.TEXTO);
                etiquetaSubtitulo.setText(" ");
                break;
        }
        areaResumen.setText(construirResumen(batalla));
        areaResumen.setCaretPosition(0);
    }

    private String construirResumen(Batalla batalla) {
        StringBuilder resumen = new StringBuilder();
        resumen.append("RESUMEN DE LA PARTIDA\n");
        resumen.append("──────────────────────────────────────────────\n\n");
        resumen.append("Entrenador: ").append(batalla.getNombreJugador()).append('\n');
        resumen.append("Rival: ").append(batalla.getNombreRival()).append('\n');
        resumen.append("Rondas jugadas: ").append(batalla.getRonda())
                .append(" de ").append(ReglasBatalla.MAX_RONDAS).append('\n');
        resumen.append("Resultado: ").append(batalla.getEstado().getDescripcion()).append("\n\n");
        resumen.append(batalla.getResumen()).append("\n\n");
        resumen.append("Equipo de ").append(batalla.getNombreJugador()).append(":\n");
        resumen.append(listarEquipo(batalla.getEquipoJugador())).append("\n\n");
        resumen.append("Equipo de ").append(batalla.getNombreRival()).append(":\n");
        resumen.append(listarEquipo(batalla.getEquipoRival()));
        return resumen.toString();
    }

    private String listarEquipo(List<Pokemon> equipo) {
        StringBuilder lista = new StringBuilder();
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon pokemon = equipo.get(i);
            lista.append("  ").append(i + 1).append(". ").append(pokemon.getNombre())
                    .append(" · ").append(pokemon.getTipo())
                    .append(" · ").append(pokemon.getPsActuales()).append("/")
                    .append(pokemon.getPsMax()).append(" PS");
            if (pokemon.estaDebilitado()) {
                lista.append(" (debilitado)");
            }
            lista.append('\n');
        }
        return lista.toString();
    }
}
