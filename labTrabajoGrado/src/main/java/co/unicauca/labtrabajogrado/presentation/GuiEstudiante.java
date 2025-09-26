/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;

import co.unicauca.labtrabajogrado.access.IEvaluacionFormatoRepositorio;
import co.unicauca.labtrabajogrado.access.ServiceLocator;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.service.ServiceEvaluacionFormato;
import co.unicauca.labtrabajogrado.service.serviceFormatoA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GuiEstudiante extends JFrame {

    private JTable tablaFormatos;
    private IEvaluacionFormatoRepositorio formatoRepositorio;
    private ServiceEvaluacionFormato serviceEvaluacion;
    private serviceFormatoA serviceFormatoA;
    private static String email;

    public GuiEstudiante( String emailEstudiante) {
        this.serviceFormatoA = new serviceFormatoA(ServiceLocator.getInstance().getFormatoRepository());
        this.formatoRepositorio = ServiceLocator.getInstance().getEvaluacionRepository();
        this.serviceEvaluacion = new ServiceEvaluacionFormato(formatoRepositorio);
        this.email = emailEstudiante;

        setTitle("Proyectos del Estudiante");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Encabezado
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(90, 150, 150));
        JLabel lblTitulo = new JLabel("Proyectos enviados");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblCorreo = new JLabel(email);
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        header.add(lblCorreo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Tabla
        tablaFormatos = new JTable();
        tablaFormatos.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(tablaFormatos);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
    List<FormatoA> formatos = serviceFormatoA.listarPorEmailEstudiante(email);
    
    if (formatos.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay proyectos registrados para este estudiante.");
    }

    String[] columnas = {"Proyecto", "Modalidad", "Estado", "Observaciones"};
    Object[][] datos = new Object[formatos.size()][4];

    for (int i = 0; i < formatos.size(); i++) {
        FormatoA f = formatos.get(i);
        System.out.println(">>> Proyecto: " + f.getTituloProyecto() + " - Modalidad: " + f.getModalidad());

        List<EvaluacionFormato> evaluaciones = serviceEvaluacion.listarPorCodigoFormato(f.getId());

        datos[i][0] = f.getTituloProyecto();
        datos[i][1] = f.getModalidad();

        if (evaluaciones == null || evaluaciones.isEmpty()) {
            datos[i][2] = "Pendiente";
            datos[i][3] = "En revisión";
        } else {
            EvaluacionFormato ultimaEval = evaluaciones.get(evaluaciones.size() - 1);
            int intento = ultimaEval.getIntento();
            String estadoRevision;

            switch (intento) {
                case 1 -> estadoRevision = "Primera revisión";
                case 2 -> estadoRevision = "Segunda revisión";
                case 3 -> estadoRevision = "Tercera revisión";
                default -> estadoRevision = "Revisión #" + intento;
            }

            datos[i][2] = estadoRevision + " - " + ultimaEval.getEstado();
            datos[i][3] = ultimaEval.getObservaciones();
        }
    }

    DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tablaFormatos.setModel(modelo);
    modelo.fireTableDataChanged();
}

    private void abrirPDF(String ruta) {
        try {
            File pdf = new File(ruta);
            if (pdf.exists()) {
                Desktop.getDesktop().open(pdf);
            } else {
                JOptionPane.showMessageDialog(this, "El archivo no existe: " + ruta);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el PDF: " + ex.getMessage());
        }
    }


   /*
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
 */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        }
        );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
