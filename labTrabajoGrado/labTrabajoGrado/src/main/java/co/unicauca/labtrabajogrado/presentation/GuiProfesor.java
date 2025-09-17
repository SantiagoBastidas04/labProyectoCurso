/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;
import co.unicauca.labtrabajogrado.access.Factory;
import co.unicauca.labtrabajogrado.access.IFormatoRepositorio;
import co.unicauca.labtrabajogrado.access.IUserRepositorio;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.service.serviceFormatoA;
import co.unicauca.labtrabajogrado.utility.EmailValidator;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author Acer
 */
public class GuiProfesor extends javax.swing.JFrame {
    
    private final serviceFormatoA service;
    private static IFormatoRepositorio  formatoRepositorio;
    public static String email;
    public static String rol;
    // Declaración de componentes
    private JTextField txtTituloTrabajo, txtDirector, txtCodirector, txtObjetivoGeneral;
    private JTextArea txtObjetivosEspecificos;
    private JTextField txtFormatoPdf, txtCartaAceptacion;
    private JComboBox<String> comboModalidad;
    private JTextField txtCorreo;
    private JButton btnBrowseFormato, btnBrowseCarta;
    private JPanel cartaPanel; // panel para carta aceptación
    private JDateChooser dateChooser; // calendario popup

    /**
     * Creates new form GuiProfesor
     */
    public GuiProfesor(String rol , String email) {
        formatoRepositorio  = Factory.getInstance().getFormatoRepository("default");
        this.service = new serviceFormatoA(formatoRepositorio);
        init(rol,email);
    }

    private void init(String rol,String email) {
        setTitle("Formato A - Universidad del Cauca");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // === Header superior ===
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(106, 153, 148));
        panelHeader.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel lblFormatoA = new JLabel("Rol : "+ rol + "  "+"Formato A" );
        lblFormatoA.setForeground(Color.WHITE);
        lblFormatoA.setFont(lblFormatoA.getFont().deriveFont(Font.BOLD, 16f));

        JLabel lblUsuario = new JLabel(email);
        lblUsuario.setForeground(Color.WHITE);
        // Icono usuario
        JLabel lblIcono = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        userPanel.setOpaque(false);
        userPanel.add(lblUsuario);
        userPanel.add(lblIcono);

        panelHeader.add(lblFormatoA, BorderLayout.WEST);
        panelHeader.add(userPanel, BorderLayout.EAST);

        // === Panel lateral (menú desplegable) ===
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(new Color(106, 153, 148));
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblMenu = new JLabel("Menú");
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMenu.setFont(lblMenu.getFont().deriveFont(Font.BOLD, 16f));

         String[] opciones = {"Seleccione", "Asignar Revisiones", "Consultar/Actualizar Proyectos", "Formato B","Ver Formatos Enviados"};
        JComboBox<String> comboMenu = new JComboBox<>(opciones);
        comboMenu.addActionListener(e -> {
        String opcion = (String) comboMenu.getSelectedItem();
    if ("Ver Formatos Enviados".equals(opcion)) {
        new GuiFormatoEnviado(service, "profesor@unicauca.edu.co").setVisible(true);
    }
});

// 👇👇👇 ESTAS SON LAS LÍNEAS NUEVAS QUE LO HACEN MÁS PEQUEÑO Y ESTÉTICO 👇👇👇
        comboMenu.setFont(comboMenu.getFont().deriveFont(12f)); // Texto más pequeño
        comboMenu.setPreferredSize(new Dimension(160, 26));     // Ancho reducido (160), alto compacto (26)
        comboMenu.setMaximumSize(comboMenu.getPreferredSize()); // Evita que se estire
        comboMenu.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5)); // Borde interno para respirar

// Opcional: centrar texto dentro del combo (mejora estética)
        comboMenu.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        panelLateral.add(lblMenu);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 15)));
        panelLateral.add(comboMenu);

        // === Panel formulario ===
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtTituloTrabajo = new JTextField(20);
        txtDirector = new JTextField(20);
        txtCodirector = new JTextField(20);
        txtObjetivoGeneral = new JTextField(20);
        txtCorreo = new JTextField(20);

        txtObjetivosEspecificos = new JTextArea(4, 20);
        txtObjetivosEspecificos.setLineWrap(true);
        txtObjetivosEspecificos.setWrapStyleWord(true);
        JScrollPane scrollObjetivos = new JScrollPane(txtObjetivosEspecificos);

        // Modalidad (combo con listener)
        comboModalidad = new JComboBox<>(new String[]{"Práctica profesional", "Trabajo de investigación"});
        comboModalidad.addActionListener(e -> toggleCartaAceptacion());

        // Calendario popup con JDateChooser
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

        // Browsers
        txtFormatoPdf = new JTextField(15);
        btnBrowseFormato = new JButton("Browse...");
        btnBrowseFormato.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtFormatoPdf.setText(file.getAbsolutePath());
            }
        });

        txtCartaAceptacion = new JTextField(15);
        btnBrowseCarta = new JButton("Browse...");
        btnBrowseCarta.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtCartaAceptacion.setText(file.getAbsolutePath());
            }
        });

        // Panel para carta aceptación (se oculta dinámicamente)
        cartaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cartaPanel.setOpaque(false);
        cartaPanel.add(new JLabel("Carta de aceptación"));
        cartaPanel.add(txtCartaAceptacion);
        cartaPanel.add(btnBrowseCarta);

        // Labels
        JLabel lblTituloTrabajo = new JLabel("Título trabajo de grado*");
        JLabel lblDirector = new JLabel("Director del proyecto de grado*");
        JLabel lblCodirector = new JLabel("Codirector del proyecto de grado");
        JLabel lblFecha = new JLabel("Fecha actual propuesta*");
        JLabel lblObjetivoGeneral = new JLabel("Objetivo general*");
        JLabel lblModalidad = new JLabel("Modalidad*");
        JLabel lblCorreo = new JLabel("Correo del estudiante*");
        JLabel lblObjetivosEspecificos = new JLabel("Objetivos específicos*");
        JLabel lblFormatoPdf = new JLabel("Formato en PDF*");

        // Layout formulario
        // Layout formulario
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblTituloTrabajo)
                                .addComponent(txtTituloTrabajo)
                                .addComponent(lblDirector)
                                .addComponent(txtDirector)
                                .addComponent(lblCodirector)
                                .addComponent(txtCodirector)
                                .addComponent(lblCorreo)
                                .addComponent(txtCorreo)
                                .addComponent(lblObjetivoGeneral)
                                .addComponent(txtObjetivoGeneral)
                                .addComponent(lblObjetivosEspecificos)
                                .addComponent(scrollObjetivos)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblFecha)
                                .addComponent(dateChooser)
                                .addComponent(lblModalidad)
                                .addComponent(comboModalidad)
                                .addComponent(lblFormatoPdf)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFormatoPdf)
                                        .addComponent(btnBrowseFormato))
                                .addComponent(cartaPanel)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTituloTrabajo)
                                .addComponent(lblFecha))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTituloTrabajo)
                                .addComponent(dateChooser))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDirector)
                                .addComponent(lblModalidad))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDirector)
                                .addComponent(comboModalidad))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCodirector)
                                .addComponent(lblFormatoPdf))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCodirector)
                                .addComponent(txtFormatoPdf)
                                .addComponent(btnBrowseFormato))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCorreo))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCorreo))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblObjetivoGeneral))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtObjetivoGeneral))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblObjetivosEspecificos))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(scrollObjetivos)
                                .addComponent(cartaPanel)) // cartaPanel se alinea con el scroll, pero solo aparece si es necesario
        );
        JButton btnSubir = new JButton("Subir");
        btnSubir.setBackground(new Color(106, 153, 148));
        btnSubir.setForeground(Color.WHITE);
        btnSubir.setFont(btnSubir.getFont().deriveFont(Font.BOLD));
        
        btnSubir.addActionListener(this::btnSubirActionPerformed);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnSubir);
        // Contenedor principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(panelLateral, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        toggleCartaAceptacion(); // inicializar visibilidad

        pack();
        setLocationRelativeTo(null);

        
    }

    private void toggleCartaAceptacion() {
        String modalidad = (String) comboModalidad.getSelectedItem();
        if (modalidad != null && modalidad.equals("Práctica profesional")) {
            cartaPanel.setVisible(true);
        } else {
            cartaPanel.setVisible(false);
        }
        cartaPanel.revalidate();
        cartaPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSubir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSubir.setText("ENVIAR");
        btnSubir.setToolTipText("");
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addComponent(btnSubir)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(btnSubir)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        // TODO add your handling code here:
        String titulo = txtTituloTrabajo.getText();
        String director = txtDirector.getText();
        String codirector = txtCodirector.getText();
        String correoEstudiante = txtCorreo.getText();
        String objetivoGeneral = txtObjetivoGeneral.getText();
        String objetivosEspecificos = txtObjetivosEspecificos.getText();
        java.util.Date fecha = dateChooser.getDate();
        String modalidad = (String) comboModalidad.getSelectedItem();
        String formatoPdf = txtFormatoPdf.getText();
        String cartaAceptacion = txtCartaAceptacion.getText();
        
        // Convertir java.util.Date a LocalDate
        LocalDate fechaActual = fecha.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        
        

        // Crear el objeto FormatoA
        
        FormatoA formato = new FormatoA(
                null, // id autogenerado
                titulo,
                modalidad,
                fechaActual,
                director,
                codirector,
                objetivoGeneral,
                objetivosEspecificos,
                formatoPdf,
                cartaAceptacion,
                correoEstudiante
        );
        
        if (!EmailValidator.esCorreoInstitucional(formato.getEmailEstudiante())) {
            JOptionPane.showMessageDialog(this, 
                "El dominio del correo debe ser @unicauca.edu.co");
            return; // salir si no cumple
        }

        // Guardar usando el repositorio
        boolean ok = service.registrarFormato(formato);
        
        if (ok) {
            JOptionPane.showMessageDialog(this, "Formato A guardado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar Formato A", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSubirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiProfesor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GuiProfesor(rol,email).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubir;
    // End of variables declaration//GEN-END:variables
}
