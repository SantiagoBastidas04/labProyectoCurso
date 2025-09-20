/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;

import co.unicauca.labtrabajogrado.access.Factory;
import co.unicauca.labtrabajogrado.access.IUserRepositorio;
import co.unicauca.labtrabajogrado.access.ServiceLocator;
import co.unicauca.labtrabajogrado.domain.User;
import co.unicauca.labtrabajogrado.service.Service;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author Acer
 */
public class GuiEstudiante extends javax.swing.JFrame {

    private JTable tablaEstado;
    private DefaultTableModel modeloTabla;
    private JPanel headerPanel, aceptadoPanel, footerPanel, southContainer;
    private Service service;
    IUserRepositorio userRepository;
    public static String nombre;
    public static String estado;
    public static int contador;
    public static String observaciones;

    /**
     * Creates new form GuiEstudiante
     */
    public GuiEstudiante(int contador, String estado, String observaciones, String nombre) {
        userRepository = ServiceLocator.getInstance().getUserRepository();
        this.service = new Service(userRepository);
        this.nombre = nombre;
        init(contador, estado, observaciones, nombre);
    }

    private void init(int contador,String estado,String observaciones,String nombre) {
        setTitle("Trabajo de Grado - Estudiante");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // HEADER
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(102, 153, 153));

        JLabel titulo = new JLabel("Trabajo de Grado");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(new EmptyBorder(5, 10, 5, 10));
        headerPanel.add(titulo, BorderLayout.WEST);

        JLabel user = new JLabel("Estudiante " + nombre.toUpperCase());
        user.setFont(new Font("Arial", Font.PLAIN, 16));
        user.setBorder(new EmptyBorder(5, 10, 5, 10));
        headerPanel.add(user, BorderLayout.EAST);

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // TABLE
        String versionTexto = switch (contador) {
            case 1 ->
                "En primera evaluación Formato A";
            case 2 ->
                "En segunda evaluación Formato A";
            case 3 ->
                "En tercera evaluación Formato A";
            default ->
                "";
        };

        modeloTabla = new DefaultTableModel(
                new Object[]{"Versión Formato A", "Estado", "Observaciones"}, 0);
        tablaEstado = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 1) {
                    String estadoCelda = (String) getValueAt(row, column);
                    if ("Rechazado".equalsIgnoreCase(estadoCelda)) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                    } else if ("Aprobado".equalsIgnoreCase(estadoCelda)) {
                        c.setBackground(new Color(0, 153, 0));
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };
        tablaEstado.setRowHeight(35);
        modeloTabla.addRow(new Object[]{versionTexto, estado, observaciones});
        JScrollPane scroll = new JScrollPane(tablaEstado);
        getContentPane().add(scroll, BorderLayout.CENTER);

        // PANEL ACEPTADO
        aceptadoPanel = new JPanel(new BorderLayout());
        aceptadoPanel.setBackground(new Color(0, 153, 0));
        aceptadoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        if(contador<2){
        JLabel aceptadoLabel = new JLabel("Aceptado Formato A");
        aceptadoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        aceptadoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aceptadoPanel.add(aceptadoLabel, BorderLayout.CENTER);
        }else{
        JLabel aceptadoLabel = new JLabel("Rechazado Formato A");
        aceptadoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        aceptadoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aceptadoPanel.add(aceptadoLabel, BorderLayout.CENTER);
        aceptadoPanel.setBackground(new Color(204, 0, 0));

        }

        // PANEL UNIVERSIDAD
        footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(102, 153, 153));
        footerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel uni = new JLabel("Universidad del Cauca");
        uni.setFont(new Font("Arial", Font.BOLD, 20));
        uni.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(uni, BorderLayout.CENTER);

        // CONTENEDOR SUR
        southContainer = new JPanel();
        southContainer.setLayout(new BoxLayout(southContainer, BoxLayout.Y_AXIS));
        southContainer.add(Box.createVerticalStrut(50)); // espacio antes del aceptado
        southContainer.add(aceptadoPanel);
        southContainer.add(Box.createVerticalStrut(50)); // espacio entre aceptado y universidad
        southContainer.add(footerPanel);

        getContentPane().add(southContainer, BorderLayout.SOUTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(437, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addContainerGap(276, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(GuiEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SwingUtilities.invokeLater(()->new GuiEstudiante(contador,estado,observaciones,nombre));
            }
        }
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
