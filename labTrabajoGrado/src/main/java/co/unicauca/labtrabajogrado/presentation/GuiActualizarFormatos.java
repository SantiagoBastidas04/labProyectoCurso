/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;

import co.unicauca.labtrabajogrado.access.ServiceLocator;
import co.unicauca.labtrabajogrado.domain.EvaluacionFormato;
import co.unicauca.labtrabajogrado.service.ServiceEvaluacionFormato;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

/**
 *
 * @author Acer
 */
public class GuiActualizarFormatos extends javax.swing.JFrame {
    
    private JTable tablaFormatos;
    private ServiceEvaluacionFormato service;
    private String emailProfesor;
    private List<EvaluacionFormato> formatos;
    /**
     * Creates new form GuiActualizarFormatos
     */
     public GuiActualizarFormatos(ServiceEvaluacionFormato service, String emailProfesor) {
        this.service = service;
        this.emailProfesor = emailProfesor;

        setTitle("Actualizar Formatos Asignados");
        setSize(900, 400);
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
        JLabel lblTitulo = new JLabel("Formatos asignados para actualización");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblCorreo = new JLabel(emailProfesor);
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        header.add(lblCorreo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        tablaFormatos = new JTable();
        tablaFormatos.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(tablaFormatos);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        // trae los formatos asignados al profesor
        formatos = service.listarFormatosRechazados(emailProfesor);

        // Modelo personalizado con botón
        tablaFormatos.setModel(new AbstractTableModel() {
            private final String[] columnas = {"Título", "Modalidad", "Estado", "Observaciones", "Acción"};

            @Override
            public int getRowCount() {
                return formatos.size();
            }

            @Override
            public int getColumnCount() {
                return columnas.length;
            }

            @Override
            public String getColumnName(int column) {
                return columnas[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                EvaluacionFormato f = formatos.get(rowIndex);
                return switch (columnIndex) {
                    case 0 -> f.getTituloProyecto();
                    case 1 -> f.getModalidad();
                    case 2 -> f.getEstado();
                    case 3 -> f.getObservaciones();
                    case 4 -> "Actualizar PDF"; // texto del botón
                    default -> null;
                };
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                // solo la columna del botón es “editable” para activar el botón
                return columnIndex == 4;
            }
        });

        // renderer para el botón
        tablaFormatos.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tablaFormatos.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Colorear columna Estado (opcional)
        // …
    }

    // Renderer para mostrar un botón en la celda
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor que reacciona al clic en el botón
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener((ActionEvent e) -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                // Acción al pulsar el botón
                EvaluacionFormato formato = formatos.get(row);
                actualizarPDF(formato);
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        private void actualizarPDF(EvaluacionFormato formato) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Selecciona el nuevo PDF");
    int result = chooser.showOpenDialog(GuiActualizarFormatos.this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        File pdfFile = chooser.getSelectedFile();
        service.actualizarFormatoPdf(formato.getCodigoFormato(), pdfFile.getAbsolutePath());
        EvaluacionFormato actualizadoFormato = service.obtenerUltimaEvaluacion(formato.getCodigoFormato());

        if (actualizadoFormato == null) {
            JOptionPane.showMessageDialog(GuiActualizarFormatos.this,
                    "Este formato ha superado el límite de actualizaciones y fue eliminado.\nDebe iniciar un nuevo proceso.");
        } else {
            JOptionPane.showMessageDialog(GuiActualizarFormatos.this,
                    "Formato actualizado correctamente con: " + pdfFile.getName());
        }
        cargarDatos();
    }
}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServiceEvaluacionFormato service = new ServiceEvaluacionFormato(ServiceLocator.getInstance().getEvaluacionRepository());
            new GuiActualizarFormatos(service, "wpantoja@unicauca.edu.co").setVisible(true);
        });
    }
}
    /*
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

*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

