package vista;

import Pokemon.Pokemon;
import logica.AccionBatalla;
import logica.Batalla;
import logica.EstadoBatalla;
import logica.ReglasBatalla;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

public class PanelBatalla extends JPanel {

    private static final int USOS_HABILIDAD = 3;
    private static final int USOS_RECUPERACION = 2;

    private final VentanaPrincipal ventana;

    private final JLabel etiquetaJugador = new JLabel(" ");
    private final JLabel etiquetaRival = new JLabel(" ");
    private final JLabel etiquetaRonda = new JLabel("Ronda 1 / " + ReglasBatalla.MAX_RONDAS);
    private final JLabel etiquetaEstado = new JLabel(" ");

    private final TarjetaPokemon tarjetaJugador = new TarjetaPokemon();
    private final TarjetaPokemon tarjetaRival = new TarjetaPokemon();

    private final JTextArea areaRegistro = new JTextArea();
    private final JButton btnAtacar = Estilo.boton("Atacar", Estilo.PRIMARIO);
    private final JButton btnHabilidad = Estilo.boton("Habilidad", new Color(0x7B3FE4));
    private final JButton btnRecuperar = Estilo.boton("Recuperar", Estilo.EXITO);
    private final JButton btnCambiar = Estilo.boton("Cambiar", new Color(0x44546A));
    private final JComboBox<OpcionPokemon> comboSustituto = new JComboBox<>();

    public PanelBatalla(VentanaPrincipal ventana) {
        this.ventana = ventana;

        setLayout(new BorderLayout(0, 12));
        setBackground(Estilo.FONDO);
        setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearAcciones(), BorderLayout.SOUTH);

        btnAtacar.addActionListener(e -> ejecutar(AccionBatalla.ATACAR));
        btnHabilidad.addActionListener(e -> ejecutar(AccionBatalla.HABILIDAD));
        btnRecuperar.addActionListener(e -> ejecutar(AccionBatalla.RECUPERAR));
        btnCambiar.addActionListener(e -> ejecutar(AccionBatalla.CAMBIAR));
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = Estilo.tarjeta();
        encabezado.setLayout(new BorderLayout());

        etiquetaJugador.setFont(Estilo.SECCION);
        etiquetaJugador.setForeground(Estilo.PRIMARIO);
        etiquetaRival.setFont(Estilo.SECCION);
        etiquetaRival.setForeground(Estilo.PELIGRO);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);
        etiquetaRonda.setFont(Estilo.SECCION);
        etiquetaRonda.setForeground(Estilo.TEXTO);
        etiquetaRonda.setAlignmentX(CENTER_ALIGNMENT);
        etiquetaEstado.setFont(Estilo.PEQUENA);
        etiquetaEstado.setForeground(Estilo.NEUTRO);
        etiquetaEstado.setAlignmentX(CENTER_ALIGNMENT);
        centro.add(etiquetaRonda);
        centro.add(etiquetaEstado);

        encabezado.add(etiquetaJugador, BorderLayout.WEST);
        encabezado.add(centro, BorderLayout.CENTER);
        encabezado.add(etiquetaRival, BorderLayout.EAST);
        return encabezado;
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setOpaque(false);

        JPanel campo = new JPanel(new GridLayout(1, 3, 16, 0));
        campo.setOpaque(false);
        campo.setPreferredSize(new Dimension(0, 210));
        campo.add(tarjetaJugador);

        JLabel versus = new JLabel("VS", JLabel.CENTER);
        versus.setFont(Estilo.TITULO);
        versus.setForeground(Estilo.NEUTRO);
        campo.add(versus);
        campo.add(tarjetaRival);

        areaRegistro.setEditable(false);
        areaRegistro.setFont(Estilo.MONO);
        areaRegistro.setLineWrap(true);
        areaRegistro.setWrapStyleWord(true);
        areaRegistro.setBackground(new Color(0x0F172A));
        areaRegistro.setForeground(new Color(0xD7E3F4));
        areaRegistro.setCaretColor(Color.WHITE);
        areaRegistro.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JScrollPane scroll = new JScrollPane(areaRegistro);
        scroll.setBorder(BorderFactory.createLineBorder(Estilo.BORDE));
        scroll.getViewport().setBackground(new Color(0x0F172A));

        centro.add(campo, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        return centro;
    }

    private JPanel crearAcciones() {
        JPanel acciones = Estilo.tarjeta();
        acciones.setLayout(new GridLayout(1, 5, 10, 0));

        comboSustituto.setFont(Estilo.NORMAL);
        comboSustituto.setPreferredSize(new Dimension(160, 36));

        acciones.add(btnAtacar);
        acciones.add(btnHabilidad);
        acciones.add(btnRecuperar);
        acciones.add(comboSustituto);
        acciones.add(btnCambiar);
        return acciones;
    }

    public void refrescar() {
        Batalla batalla = ventana.getControl().getBatalla();
        if (batalla == null) {
            ventana.irAEquipo();
            return;
        }
        etiquetaJugador.setText("Tú · " + batalla.getNombreJugador());
        etiquetaRival.setText(batalla.getNombreRival());
        etiquetaRonda.setText("Ronda " + batalla.getRonda() + " / " + ReglasBatalla.MAX_RONDAS);
        etiquetaEstado.setText("Estado: " + batalla.getEstado().getDescripcion());

        tarjetaJugador.setEntrenador(batalla.getNombreJugador());
        tarjetaRival.setEntrenador(batalla.getNombreRival());
        tarjetaJugador.mostrar(batalla.getActivoJugador());
        tarjetaRival.mostrar(batalla.getActivoRival());

        areaRegistro.setText(String.join("\n", batalla.getMensajes()));
        areaRegistro.setCaretPosition(areaRegistro.getDocument().getLength());

        cargarSustitutos(batalla);
        actualizarBotones(batalla);
    }

    private void cargarSustitutos(Batalla batalla) {
        int anterior = comboSustituto.getSelectedIndex();
        comboSustituto.removeAllItems();
        for (int indice : batalla.getIndicesCambioValidos()) {
            Pokemon pokemon = batalla.getEquipoJugador().get(indice);
            comboSustituto.addItem(new OpcionPokemon(indice,
                    (indice + 1) + ". " + pokemon.getNombre()));
        }
        if (anterior >= 0 && anterior < comboSustituto.getItemCount()) {
            comboSustituto.setSelectedIndex(anterior);
        }
    }

    private void actualizarBotones(Batalla batalla) {
        List<AccionBatalla> validas = batalla.accionesValidas();
        Pokemon activo = batalla.getActivoJugador();

        btnAtacar.setEnabled(validas.contains(AccionBatalla.ATACAR));
        btnHabilidad.setEnabled(validas.contains(AccionBatalla.HABILIDAD));
        btnHabilidad.setText("Habilidad (" + activo.getUsosHabilidad() + "/"
                + USOS_HABILIDAD + ")");
        btnRecuperar.setEnabled(validas.contains(AccionBatalla.RECUPERAR));
        btnRecuperar.setText("Recuperar (" + activo.getUsosRecuperacion() + "/"
                + USOS_RECUPERACION + ")");
        btnCambiar.setEnabled(validas.contains(AccionBatalla.CAMBIAR));
        comboSustituto.setEnabled(validas.contains(AccionBatalla.CAMBIAR));
    }

    private void ejecutar(AccionBatalla accion) {
        Batalla batalla = ventana.getControl().getBatalla();
        if (batalla == null) {
            ventana.irAEquipo();
            return;
        }

        int indiceCambio = -1;
        if (accion == AccionBatalla.CAMBIAR) {
            OpcionPokemon opcion = (OpcionPokemon) comboSustituto.getSelectedItem();
            if (opcion == null) {
                avisar("No tienes otro Pokémon disponible para cambiar.");
                return;
            }
            indiceCambio = opcion.indice();
        }

        try {
            batalla.ejecutarRonda(accion, indiceCambio);
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
            refrescar();
            return;
        }
        resolverReemplazos();
    }

    private void resolverReemplazos() {
        Batalla batalla = ventana.getControl().getBatalla();
        while (batalla.getEstado() == EstadoBatalla.ELEGIR_REEMPLAZO) {
            List<Integer> indices = batalla.getIndicesCambioValidos();
            if (indices.isEmpty()) {
                break;
            }
            OpcionPokemon[] opciones = new OpcionPokemon[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                int indice = indices.get(i);
                Pokemon pokemon = batalla.getEquipoJugador().get(indice);
                opciones[i] = new OpcionPokemon(indice, (indice + 1) + ". " + pokemon.getNombre()
                        + " — " + pokemon.getPsActuales() + "/" + pokemon.getPsMax() + " PS");
            }
            Object seleccion = JOptionPane.showInputDialog(this,
                    "Tu Pokémon se debilitó. Elige el reemplazo:",
                    "Elegir reemplazo", JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);
            try {
                if (seleccion == null) {
                    batalla.elegirReemplazo(indices.get(0));
                } else {
                    batalla.elegirReemplazo(((OpcionPokemon) seleccion).indice());
                }
            } catch (RuntimeException ex) {
                avisar(ex.getMessage());
            }
        }

        refrescar();
        if (batalla.getEstado().esFinal()) {
            ventana.irAResultado();
        }
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Batallas Pokémon",
                JOptionPane.WARNING_MESSAGE);
    }

    private record OpcionPokemon(int indice, String texto) {
        @Override
        public String toString() {
            return texto;
        }
    }

    private static class TarjetaPokemon extends JPanel {

        private final JLabel entrenador = new JLabel(" ");
        private final JLabel nombre = new JLabel("—");
        private final JLabel detalle = new JLabel(" ");
        private final JProgressBar barra = new JProgressBar(0, 100);

        TarjetaPokemon() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Estilo.TARJETA);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Estilo.BORDE, 1),
                    BorderFactory.createEmptyBorder(12, 16, 12, 16)));

            entrenador.setFont(Estilo.PEQUENA);
            entrenador.setForeground(Estilo.NEUTRO);
            nombre.setFont(Estilo.SUBTITULO);
            nombre.setForeground(Estilo.TEXTO);
            detalle.setFont(Estilo.PEQUENA);
            detalle.setForeground(Estilo.NEUTRO);

            barra.setStringPainted(true);
            barra.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
            barra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
            barra.setPreferredSize(new Dimension(200, 24));
            barra.setBorder(BorderFactory.createLineBorder(Estilo.BORDE));

            add(entrenador);
            add(Box.createVerticalStrut(6));
            add(nombre);
            add(Box.createVerticalStrut(4));
            add(detalle);
            add(Box.createVerticalStrut(14));
            add(barra);
            add(Box.createVerticalGlue());
        }

        void mostrar(Pokemon pokemon) {
            nombre.setText(pokemon.getNombre() + " · " + pokemon.getTipo());
            detalle.setText("Nivel " + pokemon.getNivel()
                    + (pokemon.estaDebilitado() ? " · Debilitado" : ""));

            int porcentaje = pokemon.getPsMax() <= 0 ? 0
                    : (int) Math.round(pokemon.getPsActuales() * 100.0 / pokemon.getPsMax());
            barra.setValue(porcentaje);
            barra.setForeground(Estilo.colorSalud(porcentaje));
            barra.setString(pokemon.getPsActuales() + " / " + pokemon.getPsMax() + " PS");
        }

        void setEntrenador(String etiqueta) {
            entrenador.setText(etiqueta);
        }
    }
}
