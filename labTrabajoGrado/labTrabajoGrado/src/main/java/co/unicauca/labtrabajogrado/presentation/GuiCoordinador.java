/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;

import co.unicauca.labtrabajogrado.access.IFormatoRepositorio;
import co.unicauca.labtrabajogrado.access.ServiceLocator;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.service.serviceFormatoA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class GuiCoordinador extends javax.swing.JFrame {
   private static String rol;
   private static String email;
   private final serviceFormatoA service;
   private static IFormatoRepositorio  formatoRepositorio;
   private JPanel panelFilas; 
   private JButton btnEnviar;

    /**
     * Creates new form GuiCoordinador
     */
    public GuiCoordinador(String rol , String Email) {
        formatoRepositorio  = ServiceLocator.getInstance().getFormatoRepository();
        this.service = new serviceFormatoA(formatoRepositorio);
        this.rol = rol;
        this.email = Email;
        initComponents(rol);
        cargarDatos();
    }
    private void initComponents(String rol) {
        setTitle("Proyectos de Grado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(content);

        // Barra superior
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(184, 216, 210));
        JLabel lblTitulo = new JLabel("  Proyectos de Grado");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        topBar.add(lblTitulo, BorderLayout.WEST);

        JLabel lblUser = new JLabel( rol+" ▼ ");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        userPanel.add(lblUser);
        topBar.add(userPanel, BorderLayout.EAST);

        content.add(topBar, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel lblEvaluacion = new JLabel("Evaluación de Proyecto");
        lblEvaluacion.setFont(new Font("Arial", Font.BOLD, 16));
        lblEvaluacion.setBorder(new EmptyBorder(5, 0, 5, 0));
        centerPanel.add(lblEvaluacion, BorderLayout.NORTH);

        // Encabezado columnas
        JPanel header = new JPanel(new GridLayout(1, 5));
        header.add(new JLabel("Proyecto", SwingConstants.CENTER));
        header.add(new JLabel("Estado", SwingConstants.CENTER));
        header.add(new JLabel("Aprobar", SwingConstants.CENTER));
        header.add(new JLabel("Rechazar", SwingConstants.CENTER));
        header.add(new JLabel("Observaciones", SwingConstants.CENTER));
        centerPanel.add(header, BorderLayout.CENTER);

        // Panel filas dinámicas
        panelFilas = new JPanel();
        panelFilas.setLayout(new GridLayout(0, 1, 0, 5));
        JScrollPane scroll = new JScrollPane(panelFilas);
        centerPanel.add(scroll, BorderLayout.SOUTH);

        // Botón enviar
        btnEnviar = new JButton("Enviar evaluaciones");
        btnEnviar.setBackground(Color.BLUE);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setPreferredSize(new Dimension(200, 40));

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnEnviar);

        content.add(centerPanel, BorderLayout.CENTER);
        content.add(btnPanel, BorderLayout.SOUTH);

        // Pie
        JPanel footer = new JPanel();
        footer.setBackground(new Color(184, 216, 210));
        JLabel lblFooter = new JLabel("Universidad del Cauca");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 14));
        footer.add(lblFooter);
        content.add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarDatos() {
        List<FormatoA> formatos = service.listarFormatos();
        panelFilas.removeAll();

        for (FormatoA f : formatos) {
            JPanel fila = new JPanel(new GridLayout(1, 5));

            // columna 1: título
            JLabel lblTitulo = new JLabel(f.getTituloProyecto(), SwingConstants.CENTER);

            // columna 2: estado coloreado
            JLabel lblEstado = new JLabel(f.getEstado(), SwingConstants.CENTER);
            lblEstado.setOpaque(true);
            String estado = f.getEstado();
            if ("Aprobado".equalsIgnoreCase(estado)) {
                lblEstado.setBackground(new Color(0, 128, 0));
                lblEstado.setForeground(Color.WHITE);
            } else if ("Rechazado".equalsIgnoreCase(estado)) {
                lblEstado.setBackground(Color.RED);
                lblEstado.setForeground(Color.WHITE);
            } else if ("En revisión".equalsIgnoreCase(estado)) {
                lblEstado.setBackground(Color.BLUE);
                lblEstado.setForeground(Color.WHITE);
            } else {
                lblEstado.setBackground(Color.WHITE);
                lblEstado.setForeground(Color.BLACK);
            }

            // columna 3 y 4: radio buttons
            ButtonGroup group = new ButtonGroup();
            JRadioButton rbAprobar = new JRadioButton();
            JRadioButton rbRechazar = new JRadioButton();
            rbAprobar.setHorizontalAlignment(SwingConstants.CENTER);
            rbRechazar.setHorizontalAlignment(SwingConstants.CENTER);
            group.add(rbAprobar);
            group.add(rbRechazar);

            // columna 5: observaciones
            JTextField txtObs = new JTextField();

            fila.add(lblTitulo);
            fila.add(lblEstado);
            fila.add(rbAprobar);
            fila.add(rbRechazar);
            fila.add(txtObs);

            panelFilas.add(fila);
        }

        panelFilas.revalidate();
        panelFilas.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")/*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GuiCoordinador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiCoordinador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiCoordinador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiCoordinador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiCoordinador(rol,email).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
