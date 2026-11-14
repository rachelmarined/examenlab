package vista;

import Pokemon.Entrenador;
import Pokemon.Pokemon;
import control.ControladorJuego;
import logica.Batalla;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

    public static final String INICIO = "inicio";
    public static final String EQUIPO = "equipo";
    public static final String BATALLA = "batalla";
    public static final String RESULTADO = "resultado";

    private static final String INSTRUCCIONES = """
            BATALLAS POKÉMON

            1. Escribe tu nombre (entre 3 y 10 caracteres: letras, números y espacios).
            2. Arma un equipo de 1 a 6 Pokémon desde el catálogo. Puedes tener hasta
               dos copias del mismo Pokémon y guardar/restaurar el equipo en memoria.
            3. En cada ronda elige: Atacar, Habilidad especial, Recuperar o Cambiar.
               - Atacar: potencia 10.
               - Habilidad especial: 3 usos por batalla (potencia y efecto según el tipo).
               - Recuperar: 25 % de los PS máximos, 2 usos por batalla.
               - Cambiar: siempre se resuelve antes que el resto de acciones.
            4. El Pokémon más rápido actúa primero; si hay empate, actúa el jugador.
            5. Efectividad: Fuego > Planta, Planta > Agua, Agua > Fuego, Eléctrico > Agua.
            6. Gana quien deje al rival sin Pokémon. Si llegan a 50 rondas, gana quien
               tenga más PS restantes.

            En el menú Pruebas puedes ver las demostraciones de Clonable, Recursividad
            y Serializable solicitadas en el examen.
            """;

    private final ControladorJuego control = new ControladorJuego();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel raiz = new JPanel(cardLayout);

    private final PanelInicio panelInicio;
    private final PanelEquipo panelEquipo;
    private final PanelBatalla panelBatalla;
    private final PanelResultado panelResultado;

    public VentanaPrincipal() {
        super("Batallas Pokémon · Laboratorio de Programación II");

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                salir();
            }
        });
        setSize(1060, 780);
        setMinimumSize(new Dimension(960, 700));
        setLocationRelativeTo(null);

        panelInicio = new PanelInicio(this);
        panelEquipo = new PanelEquipo(this);
        panelBatalla = new PanelBatalla(this);
        panelResultado = new PanelResultado(this);

        raiz.setBackground(Estilo.FONDO);
        raiz.add(panelInicio, INICIO);
        raiz.add(panelEquipo, EQUIPO);
        raiz.add(panelBatalla, BATALLA);
        raiz.add(panelResultado, RESULTADO);

        setJMenuBar(crearMenu());
        setContentPane(raiz);
        mostrar(INICIO);
    }

    private JMenuBar crearMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu menuJuego = new JMenu("Juego");
        JMenuItem itemInstrucciones = new JMenuItem("Instrucciones…");
        itemInstrucciones.addActionListener(e -> mostrarInstrucciones());
        JMenuItem itemReiniciar = new JMenuItem("Reiniciar catálogo");
        itemReiniciar.addActionListener(e -> {
            control.reiniciarCatalogo();
            panelEquipo.refrescar();
        });
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salir());
        menuJuego.add(itemInstrucciones);
        menuJuego.add(itemReiniciar);
        menuJuego.addSeparator();
        menuJuego.add(itemSalir);

        JMenu menuPruebas = new JMenu("Pruebas");
        JMenuItem itemClon = new JMenuItem("Verificar copia (Clonable)");
        itemClon.addActionListener(e -> probarClonacion());
        JMenuItem itemRecursion = new JMenuItem("Suma de PS (Recursividad)");
        itemRecursion.addActionListener(e -> probarRecursividad());
        JMenuItem itemSerial = new JMenuItem("Estado de la serialización (Serializable)");
        itemSerial.addActionListener(e -> probarSerializacion());
        menuPruebas.add(itemClon);
        menuPruebas.add(itemRecursion);
        menuPruebas.add(itemSerial);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemAcerca = new JMenuItem("Acerca de…");
        itemAcerca.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Batallas Pokémon\nLaboratorio de Programación II · UNITEC\nExamen 1 – Parcial 1\n\n"
                        + "Java Swing · POO · Serializable · Clonable · Recursividad",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        menuAyuda.add(itemAcerca);

        barra.add(menuJuego);
        barra.add(menuPruebas);
        barra.add(menuAyuda);
        return barra;
    }

    public ControladorJuego getControl() {
        return control;
    }

    public void mostrar(String nombre) {
        cardLayout.show(raiz, nombre);
    }

    public void irAEquipo() {
        panelEquipo.refrescar();
        mostrar(EQUIPO);
    }

    public void irABatalla() {
        panelBatalla.refrescar();
        mostrar(BATALLA);
    }

    public void irAResultado() {
        panelResultado.refrescar();
        mostrar(RESULTADO);
    }

    public void nuevaPartida() {
        control.nuevaPartida();
        irAEquipo();
    }

    public void salir() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas salir de Batallas Pokémon?",
                "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public void mostrarInstrucciones() {
        JOptionPane.showMessageDialog(this, INSTRUCCIONES, "Instrucciones",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void probarClonacion() {
        if (control.getCatalogo().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El catálogo está vacío.",
                    "Clonable", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Pokemon original = control.getCatalogo().get(0);
        Pokemon copia = original.clone();
        int psOriginalAntes = original.getPsActuales();
        copia.recibirDaño(30);

        String mensaje = "Se clonó a " + original.getNombre() + " con clone().\n"
                + "No hace falta copia profunda porque Pokemon no tiene listas ni objetos mutables.\n\n"
                + "Original → PS: " + original.getPsActuales() + "/" + original.getPsMax() + "\n"
                + "Copia → PS: " + copia.getPsActuales() + "/" + copia.getPsMax() + "\n\n"
                + "Los PS del original siguen en " + psOriginalAntes
                + ", así que modificar la copia no afecta al catálogo.";
        JOptionPane.showMessageDialog(this, mensaje, "Clonable", JOptionPane.INFORMATION_MESSAGE);
    }

    private void probarRecursividad() {
        if (!control.hayEntrenador()) {
            JOptionPane.showMessageDialog(this, "Primero registra un entrenador.",
                    "Recursividad", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Entrenador entrenador = control.getEntrenador();
        if (entrenador.getEquipo().size() == 0) {
            JOptionPane.showMessageDialog(this, "Agrega al menos un Pokémon a tu equipo.",
                    "Recursividad", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int suma = Batalla.sumaPS(entrenador.getEquipo().getPokemones(), 0);
        String mensaje = "Método recursivo sumaPS(equipo, 0) para " + entrenador.getNombre() + "\n\n"
                + "Equipo: " + entrenador.getEquipo().size() + " Pokémon\n"
                + "Suma de PS restantes: " + suma + "\n\n"
                + "Caso base: índice >= tamaño del equipo → 0.\n"
                + "Avance: PS del Pokémon actual + suma del resto del equipo.\n\n"
                + "Este valor se usa para el desempate de las 50 rondas.";
        JOptionPane.showMessageDialog(this, mensaje, "Recursividad", JOptionPane.INFORMATION_MESSAGE);
    }

    private void probarSerializacion() {
        String mensaje;
        if (control.hayRespaldo()) {
            mensaje = "Hay un equipo guardado en memoria.\n\n"
                    + "Bytes del flujo serializado: " + control.tamanoRespaldo() + "\n"
                    + "Implementación: ByteArrayOutputStream + ObjectOutputStream\n"
                    + "Lectura: ByteArrayInputStream + ObjectInputStream\n"
                    + "No se crean archivos ni se usan bases de datos.";
        } else {
            mensaje = "Todavía no hay un equipo guardado.\n\n"
                    + "Usa el botón «Guardar equipo» en la pantalla de equipo.\n"
                    + "La serialización se ejecuta solo en memoria.";
        }
        JOptionPane.showMessageDialog(this, mensaje, "Serializable", JOptionPane.INFORMATION_MESSAGE);
    }
}
