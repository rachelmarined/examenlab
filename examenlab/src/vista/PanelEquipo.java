package vista;

import control.ControladorJuego;
import Pokemon.Entrenador;
import Pokemon.Pokemon;
import Pokemon.Tipo;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

public class PanelEquipo extends JPanel {

    private static final String SELECCION_CATALOGO = "catalogo";
    private static final String SELECCION_EQUIPO = "equipo";

    private final VentanaPrincipal ventana;
    private final ControladorJuego control;

    private final DefaultListModel<Pokemon> modeloCatalogo = new DefaultListModel<>();
    private final DefaultListModel<Pokemon> modeloEquipo = new DefaultListModel<>();
    private final JList<Pokemon> listaCatalogo = new JList<>(modeloCatalogo);
    private final JList<Pokemon> listaEquipo = new JList<>(modeloEquipo);

    private final JLabel etiquetaTitulo = new JLabel(" ");
    private final JLabel etiquetaContador = new JLabel("Equipo: 0/" + ControladorJuego.MAX_EQUIPO);

    private final JButton btnAgregar = Estilo.boton("Agregar", Estilo.PRIMARIO);
    private final JButton btnQuitar = Estilo.boton("Quitar", Estilo.PELIGRO);
    private final JButton btnGuardar = Estilo.boton("Guardar equipo", Estilo.NEUTRO);
    private final JButton btnRestaurar = Estilo.boton("Restaurar equipo", Estilo.NEUTRO);
    private final JButton btnCrear = Estilo.boton("Crear Pokémon…", Estilo.EXITO);
    private final JButton btnComenzar = Estilo.boton("Comenzar batalla", Estilo.EXITO);

    private final JLabel fichaNombre = new JLabel("—");
    private final JLabel fichaTipo = new JLabel("—");
    private final JLabel fichaNivel = new JLabel("—");
    private final JLabel fichaPs = new JLabel("—");
    private final JLabel fichaAtaque = new JLabel("—");
    private final JLabel fichaDefensa = new JLabel("—");
    private final JLabel fichaVelocidad = new JLabel("—");
    private final JLabel fichaHabilidad = new JLabel("—");

    private String ultimaSeleccion = SELECCION_CATALOGO;

    public PanelEquipo(VentanaPrincipal ventana) {
        this.ventana = ventana;
        this.control = ventana.getControl();

        setLayout(new BorderLayout(0, 12));
        setBackground(Estilo.FONDO);
        setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);

        configurarListas();
        configurarAcciones();
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);

        etiquetaTitulo.setFont(Estilo.SUBTITULO);
        etiquetaTitulo.setForeground(Estilo.TEXTO);
        JLabel subtitulo = Estilo.titulo(
                "Selecciona del catálogo y arma un equipo de 1 a 6 integrantes (máximo 2 copias por Pokémon).",
                Estilo.PEQUENA, Estilo.NEUTRO);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        textos.add(etiquetaTitulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(subtitulo);

        etiquetaContador.setFont(Estilo.SECCION);
        etiquetaContador.setForeground(Estilo.PRIMARIO);

        encabezado.add(textos, BorderLayout.WEST);
        encabezado.add(etiquetaContador, BorderLayout.EAST);
        return encabezado;
    }

    private JPanel crearCuerpo() {
        JPanel cuerpo = new JPanel(new GridLayout(1, 3, 14, 0));
        cuerpo.setOpaque(false);

        cuerpo.add(crearPanelLista("Catálogo disponible", listaCatalogo));
        cuerpo.add(crearPanelBotones());
        cuerpo.add(crearPanelLista("Mi equipo", listaEquipo));
        return cuerpo;
    }

    private JPanel crearPanelLista(String titulo, JList<Pokemon> lista) {
        JPanel panel = Estilo.tarjeta();
        panel.setLayout(new BorderLayout(0, 8));

        JLabel etiqueta = Estilo.titulo(titulo, Estilo.SECCION, Estilo.TEXTO);
        panel.add(etiqueta, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(Estilo.BORDE));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        btnAgregar.setAlignmentX(CENTER_ALIGNMENT);
        btnQuitar.setAlignmentX(CENTER_ALIGNMENT);
        btnGuardar.setAlignmentX(CENTER_ALIGNMENT);
        btnRestaurar.setAlignmentX(CENTER_ALIGNMENT);
        btnCrear.setAlignmentX(CENTER_ALIGNMENT);

        Dimension tamano = new Dimension(190, 38);
        for (JButton boton : List.of(btnAgregar, btnQuitar, btnGuardar, btnRestaurar, btnCrear)) {
            boton.setMaximumSize(tamano);
            boton.setPreferredSize(tamano);
            boton.setAlignmentX(CENTER_ALIGNMENT);
            panel.add(boton);
            panel.add(Box.createVerticalStrut(10));
        }
        return panel;
    }

    private JPanel crearPie() {
        JPanel pie = new JPanel(new BorderLayout(0, 10));
        pie.setOpaque(false);

        JPanel ficha = Estilo.tarjeta();
        ficha.setLayout(new GridLayout(4, 4, 10, 6));
        ficha.add(caption("Nombre"));
        ficha.add(valor(fichaNombre));
        ficha.add(caption("Tipo"));
        ficha.add(valor(fichaTipo));
        ficha.add(caption("Nivel"));
        ficha.add(valor(fichaNivel));
        ficha.add(caption("PS"));
        ficha.add(valor(fichaPs));
        ficha.add(caption("Ataque"));
        ficha.add(valor(fichaAtaque));
        ficha.add(caption("Defensa"));
        ficha.add(valor(fichaDefensa));
        ficha.add(caption("Velocidad"));
        ficha.add(valor(fichaVelocidad));
        ficha.add(caption("Habilidad especial"));
        ficha.add(valor(fichaHabilidad));

        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);
        JLabel pista = Estilo.titulo("El primer Pokémon de la lista será el primero en combatir.",
                Estilo.PEQUENA, Estilo.NEUTRO);
        acciones.add(pista, BorderLayout.WEST);
        acciones.add(btnComenzar, BorderLayout.EAST);

        pie.add(ficha, BorderLayout.CENTER);
        pie.add(acciones, BorderLayout.SOUTH);
        return pie;
    }

    private JLabel caption(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(Estilo.PEQUENA);
        etiqueta.setForeground(Estilo.NEUTRO);
        return etiqueta;
    }

    private JLabel valor(JLabel etiqueta) {
        etiqueta.setFont(Estilo.NEGRITA);
        etiqueta.setForeground(Estilo.TEXTO);
        return etiqueta;
    }

    private void configurarListas() {
        listaCatalogo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEquipo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCatalogo.setFont(Estilo.NORMAL);
        listaEquipo.setFont(Estilo.NORMAL);
        listaCatalogo.setFixedCellHeight(28);
        listaEquipo.setFixedCellHeight(28);
        listaCatalogo.setSelectionBackground(Estilo.PRIMARIO);
        listaEquipo.setSelectionBackground(Estilo.PRIMARIO);
        listaCatalogo.setSelectionForeground(Color.WHITE);
        listaEquipo.setSelectionForeground(Color.WHITE);

        listaEquipo.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                                                          boolean seleccionado, boolean conFoco) {
                JLabel etiqueta = (JLabel) super.getListCellRendererComponent(
                        lista, valor, indice, seleccionado, conFoco);
                if (valor instanceof Pokemon pokemon) {
                    etiqueta.setText((indice + 1) + ". " + pokemon.getNombre() + " · "
                            + pokemon.getTipo() + " · Nv " + pokemon.getNivel());
                }
                etiqueta.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                return etiqueta;
            }
        });
        listaCatalogo.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                                                          boolean seleccionado, boolean conFoco) {
                JLabel etiqueta = (JLabel) super.getListCellRendererComponent(
                        lista, valor, indice, seleccionado, conFoco);
                if (valor instanceof Pokemon pokemon) {
                    etiqueta.setText(pokemon.getNombre() + " · " + pokemon.getTipo()
                            + " · Nv " + pokemon.getNivel());
                }
                etiqueta.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                return etiqueta;
            }
        });

        listaCatalogo.addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                ultimaSeleccion = SELECCION_CATALOGO;
                actualizarFicha();
                actualizarBotones();
            }
        });
        listaEquipo.addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                ultimaSeleccion = SELECCION_EQUIPO;
                actualizarFicha();
                actualizarBotones();
            }
        });
    }

    private void configurarAcciones() {
        btnAgregar.addActionListener(e -> agregar());
        btnQuitar.addActionListener(e -> quitar());
        btnGuardar.addActionListener(e -> guardar());
        btnRestaurar.addActionListener(e -> restaurar());
        btnCrear.addActionListener(e -> crearPokemon());
        btnComenzar.addActionListener(e -> comenzarBatalla());
        btnComenzar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnComenzar.setPreferredSize(new Dimension(200, 40));
    }

    public void refrescar() {
        Entrenador entrenador = control.getEntrenador();
        if (entrenador == null) {
            ventana.mostrar(VentanaPrincipal.INICIO);
            return;
        }
        etiquetaTitulo.setText(entrenador.getNombre() + ", arma tu equipo");

        List<Pokemon> catalogo = control.getCatalogo();
        if (modeloCatalogo.size() != catalogo.size()) {
            modeloCatalogo.clear();
            for (Pokemon pokemon : catalogo) {
                modeloCatalogo.addElement(pokemon);
            }
        }

        modeloEquipo.clear();
        for (Pokemon pokemon : entrenador.getEquipo().getPokemones()) {
            modeloEquipo.addElement(pokemon);
        }

        etiquetaContador.setText("Equipo: " + entrenador.getEquipo().size()
                + " / " + ControladorJuego.MAX_EQUIPO);
        actualizarFicha();
        actualizarBotones();
    }

    private void actualizarFicha() {
        Pokemon pokemon = pokemonSeleccionado();
        if (pokemon == null) {
            fichaNombre.setText("—");
            fichaTipo.setText("—");
            fichaNivel.setText("—");
            fichaPs.setText("—");
            fichaAtaque.setText("—");
            fichaDefensa.setText("—");
            fichaVelocidad.setText("—");
            fichaHabilidad.setText("—");
            return;
        }
        fichaNombre.setText(pokemon.getNombre());
        fichaTipo.setText(pokemon.getTipo().toString());
        fichaNivel.setText(String.valueOf(pokemon.getNivel()));
        fichaPs.setText(pokemon.getPsActuales() + " / " + pokemon.getPsMax());
        fichaAtaque.setText(String.valueOf(pokemon.getAtaque()));
        fichaDefensa.setText(String.valueOf(pokemon.getDefensa()));
        fichaVelocidad.setText(String.valueOf(pokemon.getVelocidad()));
        fichaHabilidad.setText(pokemon.getNombreHabilidad());
    }

    private Pokemon pokemonSeleccionado() {
        if (SELECCION_CATALOGO.equals(ultimaSeleccion) && listaCatalogo.getSelectedIndex() >= 0) {
            return modeloCatalogo.get(listaCatalogo.getSelectedIndex());
        }
        if (SELECCION_EQUIPO.equals(ultimaSeleccion) && listaEquipo.getSelectedIndex() >= 0) {
            return modeloEquipo.get(listaEquipo.getSelectedIndex());
        }
        if (listaCatalogo.getSelectedIndex() >= 0) {
            return modeloCatalogo.get(listaCatalogo.getSelectedIndex());
        }
        if (listaEquipo.getSelectedIndex() >= 0) {
            return modeloEquipo.get(listaEquipo.getSelectedIndex());
        }
        return null;
    }

    private void actualizarBotones() {
        btnAgregar.setEnabled(listaCatalogo.getSelectedIndex() >= 0);
        btnQuitar.setEnabled(listaEquipo.getSelectedIndex() >= 0);
        btnGuardar.setEnabled(control.hayEquipo());
        btnRestaurar.setEnabled(control.hayRespaldo());
        btnComenzar.setEnabled(control.hayEquipo());
    }

    private void agregar() {
        int indice = listaCatalogo.getSelectedIndex();
        if (indice < 0) {
            avisar("Selecciona un Pokémon del catálogo.");
            return;
        }
        try {
            control.agregarAlEquipo(indice);
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
            return;
        }
        refrescar();
        ultimaSeleccion = SELECCION_EQUIPO;
        if (modeloEquipo.size() > 0) {
            listaEquipo.setSelectedIndex(modeloEquipo.size() - 1);
        }
        actualizarFicha();
        actualizarBotones();
    }

    private void quitar() {
        int indice = listaEquipo.getSelectedIndex();
        if (indice < 0) {
            avisar("Selecciona un Pokémon de tu equipo para quitarlo.");
            return;
        }
        try {
            control.quitarDelEquipo(indice);
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
            return;
        }
        refrescar();
        if (modeloEquipo.size() > 0) {
            listaEquipo.setSelectedIndex(Math.min(indice, modeloEquipo.size() - 1));
        }
        actualizarFicha();
        actualizarBotones();
    }

    private void guardar() {
        try {
            int bytes = control.guardarEquipo();
            JOptionPane.showMessageDialog(this,
                    "Equipo guardado en memoria.\n\nFlujo serializado: " + bytes + " bytes.\n"
                            + "Archivos creados: ninguno.",
                    "Guardar equipo", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
        }
        actualizarBotones();
    }

    private void restaurar() {
        try {
            int integrantes = control.restaurarEquipo();
            refrescar();
            JOptionPane.showMessageDialog(this,
                    "Equipo restaurado desde memoria (" + integrantes + " integrantes).",
                    "Restaurar equipo", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
        }
    }

    private void crearPokemon() {
        JTextField campoNombre = new JTextField();
        JComboBox<Tipo> campoTipo = new JComboBox<>(Tipo.values());

        JPanel formulario = new JPanel(new GridLayout(0, 1, 6, 6));
        formulario.add(new JLabel("Nombre del Pokémon (3 a 10 caracteres):"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Tipo:"));
        formulario.add(campoTipo);

        int opcion = JOptionPane.showConfirmDialog(this, formulario,
                "Crear Pokémon personalizado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            control.agregarAlCatalogo(campoNombre.getText(), (Tipo) campoTipo.getSelectedItem());
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
            return;
        }
        refrescar();
        listaCatalogo.setSelectedIndex(modeloCatalogo.size() - 1);
        ultimaSeleccion = SELECCION_CATALOGO;
        actualizarFicha();
        actualizarBotones();
    }

    private void comenzarBatalla() {
        try {
            control.comenzarBatalla();
        } catch (RuntimeException ex) {
            avisar(ex.getMessage());
            return;
        }
        ventana.irABatalla();
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Batallas Pokémon",
                JOptionPane.WARNING_MESSAGE);
    }
}
