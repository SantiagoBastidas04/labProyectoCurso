/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package co.unicauca.labtrabajogrado.presentation;

import co.unicauca.labtrabajogrado.domain.FormatoA;
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
 * @author edwin
 */
public class GuiFormatoEnviado extends javax.swing.JFrame {

       private JTable tablaFormatos;
    private serviceFormatoA service;
    private String emailProfesor;

    public GuiFormatoEnviado(serviceFormatoA service, String emailProfesor) {
        this.service = service;
        this.emailProfesor = emailProfesor;

        setTitle("Formatos Enviados");
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
        JLabel lblTitulo = new JLabel("Formatos enviados");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(lblTitulo, BorderLayout.WEST);

        JLabel lblCorreo = new JLabel(emailProfesor);
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
        List<FormatoA> formatos = service.listarFormatos();

        String[] columnas = {"Título", "Modalidad", "Estado", "Observaciones"};
        Object[][] datos = new Object[formatos.size()][4];

        for (int i = 0; i < formatos.size(); i++) {
            FormatoA f = formatos.get(i);
            datos[i][0] = f.getTituloProyecto();
            datos[i][1] = f.getModalidad();
            datos[i][2] = f.getEstado(); 
            datos[i][3] = ""; // campo observaciones vacío
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // solo Observaciones editable
            }
        };
        tablaFormatos.setModel(modelo);

        // Colorear columna Estado
        tablaFormatos.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                          boolean isSelected, boolean hasFocus,
                                                          int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = value != null ? value.toString() : "";
                if ("Aprobado".equalsIgnoreCase(estado)) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.WHITE);
                } else if ("Rechazado".equalsIgnoreCase(estado)) {
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

        // Evento clic en columna Título para abrir PDF
        tablaFormatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaFormatos.rowAtPoint(e.getPoint());
                int columna = tablaFormatos.columnAtPoint(e.getPoint());

                if (columna == 0) { // si clic en título
                    FormatoA f = formatos.get(fila);
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
    /**
     * Creates new form GuiFormatoEnviado
     */
    public GuiFormatoEnviado() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
 /*   
    @SuppressWarnings("unchecked")
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
            java.util.logging.Logger.getLogger(GuiFormatoEnviado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiFormatoEnviado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiFormatoEnviado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiFormatoEnviado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
            // 🔥 Crear un FormatoService de prueba
            serviceFormatoA service = new serviceFormatoA();
            // Puedes agregar datos de prueba si no tienes BD aún:
            // service.guardarFormato(new FormatoA("Proyecto 1", "Práctica Profesional", LocalDate.now(), "Director X", ...));

            // Llamar a la nueva ventana con datos simulados
            new GuiFormatoEnviado(service, "profesor@unicauca.edu.co").setVisible(true);
        }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
