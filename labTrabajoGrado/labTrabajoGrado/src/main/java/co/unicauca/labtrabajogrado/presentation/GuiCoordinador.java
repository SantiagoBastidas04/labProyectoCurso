/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;

import co.unicauca.labtrabajogrado.access.IEvaluacionFormatoRepositorio;
import co.unicauca.labtrabajogrado.access.IFormatoRepositorio;
import co.unicauca.labtrabajogrado.access.ServiceLocator;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import co.unicauca.labtrabajogrado.domain.FormatoA;
import co.unicauca.labtrabajogrado.domain.enumEstadoProyecto;
import co.unicauca.labtrabajogrado.service.ServiceEvaluacionFormato;
import co.unicauca.labtrabajogrado.service.serviceFormatoA;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class GuiCoordinador extends javax.swing.JFrame {

    private static String rol;
    private static String email;
    private final serviceFormatoA service;
    private final ServiceEvaluacionFormato serviceEvaluacion;
    private static IFormatoRepositorio formatoRepositorio;
    private static IEvaluacionFormatoRepositorio evaluacionRepositorio;
    private JTable tablaFormatos;
    private JButton btnEnviar;

    public GuiCoordinador(String rol, String Email) {

        formatoRepositorio = ServiceLocator.getInstance().getFormatoRepository();
        evaluacionRepositorio = ServiceLocator.getInstance().getEvaluacionRepository();

        this.service = new serviceFormatoA(formatoRepositorio);
        this.serviceEvaluacion = new ServiceEvaluacionFormato(evaluacionRepositorio);

        GuiCoordinador.rol = rol;
        GuiCoordinador.email = Email;

        setTitle("Proyectos de Grado - Evaluación de Proyecto");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents(rol, email);
        cargarDatos();
    }

    private void initComponents(String rol, String email) {
        setLayout(new BorderLayout());

        // Encabezado
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(90, 150, 150));

        JLabel lblTitulo = new JLabel("Evaluación de Proyecto");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblCorreo = new JLabel("Rol " + rol.toUpperCase() + " " + email + " ");
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        header.add(lblCorreo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Tabla
        tablaFormatos = new JTable();
        tablaFormatos.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tablaFormatos);
        add(scrollPane, BorderLayout.CENTER);

        // Botón enviar
        btnEnviar = new JButton("Enviar evaluaciones");
        btnEnviar.setBackground(Color.BLUE);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnEnviar);
        add(panelBoton, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> enviarEvaluaciones());
    }

    private void cargarDatos() {
        List<FormatoA> formatos = service.listarPendientes();

        String[] columnas = {"Proyecto", "Estado", "Aprobar", "Rechazar", "Observaciones"};
        Object[][] datos = new Object[formatos.size()][5];

        for (int i = 0; i < formatos.size(); i++) {
            FormatoA f = formatos.get(i);
            datos[i][0] = f;

            long codigoFormato = f.getId();
            var ultimaEval = serviceEvaluacion.obtenerUltima(codigoFormato);

            datos[i][1] = ultimaEval != null ? ultimaEval.getEstado().name() : "Pendiente";
            datos[i][2] = Boolean.FALSE;
            datos[i][3] = Boolean.FALSE;
            datos[i][4] = "";
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2 || columnIndex == 3) {
                    return Boolean.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 2;
            }
        };

        tablaFormatos.setModel(modelo);

        // Render Proyecto
        tablaFormatos.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof FormatoA) {
                    setText(((FormatoA) value).getTituloProyecto());
                } else {
                    super.setValue(value);
                }
            }
        });

        // Render Estado con colores
        tablaFormatos.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = value != null ? value.toString() : "";
                if ("APROBADO".equalsIgnoreCase(estado)) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.WHITE);
                } else if ("RECHAZADO".equalsIgnoreCase(estado)) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        modelo.addTableModelListener(e -> {
            if (e.getColumn() == 2 || e.getColumn() == 3) {
                int row = e.getFirstRow();
                Boolean aprobar = (Boolean) modelo.getValueAt(row, 2);
                Boolean rechazar = (Boolean) modelo.getValueAt(row, 3);
                if (aprobar && rechazar) {
                    if (e.getColumn() == 2) {
                        modelo.setValueAt(false, row, 3);
                    } else {
                        modelo.setValueAt(false, row, 2);
                    }
                }
            }
        });

        tablaFormatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaFormatos.rowAtPoint(e.getPoint());
                int columna = tablaFormatos.columnAtPoint(e.getPoint());
                if (columna == 0) {
                    FormatoA f = (FormatoA) modelo.getValueAt(fila, 0);
                    abrirPDF(f.getFormatoPdf());
                }
            }
        });
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

    private void enviarEvaluaciones() {
        if (tablaFormatos.isEditing()) {
            tablaFormatos.getCellEditor().stopCellEditing();
        }
        DefaultTableModel modelo = (DefaultTableModel) tablaFormatos.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            FormatoA f = (FormatoA) modelo.getValueAt(i, 0);
            Boolean aprobar = (Boolean) modelo.getValueAt(i, 2);
            Boolean rechazar = (Boolean) modelo.getValueAt(i, 3);
            String observaciones = (String) modelo.getValueAt(i, 4);

            if (Boolean.TRUE.equals(aprobar) || Boolean.TRUE.equals(rechazar)) {
                enumEstadoProyecto estado = Boolean.TRUE.equals(aprobar)
                        ? enumEstadoProyecto.APROBADO
                        : enumEstadoProyecto.RECHAZADO;

                List<EvaluacionFormato> historial = serviceEvaluacion.obtenerHistorial(f.getId());
                int intento = historial.size() + 1;

                if (intento > 3 && estado == enumEstadoProyecto.RECHAZADO) {
                    JOptionPane.showMessageDialog(this,
                            "No se puede rechazar más de 3 veces. Proyecto rechazado definitivamente.");
                    continue;
                }

                boolean ok = serviceEvaluacion.evaluarFormato(f, estado, observaciones);

                if (ok) {
                    modelo.setValueAt(estado.name(), i, 1);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo registrar la evaluación para el proyecto: " + f.getTituloProyecto());
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Evaluaciones enviadas correctamente.");
        cargarDatos();
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
                new GuiCoordinador(rol, email).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
