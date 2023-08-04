import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Registro {
    private JPanel panel1;
    private JLabel codigo;
    private JLabel cedula;
    private JLabel nombre;
    private JLabel fecha;
    private JLabel signo;
    private JTextField codigo_T;
    private JTextField cedula_T;
    private JTextField nombre_T;
    private JTextField fecha_T;
    private JComboBox signo_b;
    private JRadioButton radioButton1;
    private JRadioButton radioButton3;
    private JButton buscar_c;
    private JButton buscar_n;
    private JButton buscar_s;
    private JButton borrar;
    private JButton actualizar;
    private JButton ingresar;
    private JButton limpiar;
    PreparedStatement ps;
    Statement st;
    public Registro() {
        //No hace visible a estos dos botones
        actualizar.setEnabled(false);
        borrar.setEnabled(false);
        ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar.setEnabled(true);
                borrar.setEnabled(true);
                Connection con;

                try{
                    con = getConection();
                    PreparedStatement psConsulta = con.prepareStatement("SELECT Codigo FROM Registro WHERE Codigo = ?");
                    psConsulta.setString(1,codigo_T.getText());
                    ResultSet rsConsulta = psConsulta.executeQuery();

                    if (rsConsulta.next()) {
                        JOptionPane.showMessageDialog(null, "EL REGISTRO YA EXISTE");
                    } else {
                        ps = con.prepareStatement("INSERT INTO Registro (Codigo, Cedula,,Nombre,Fecha_n, Signo) VALUES(?,?,?,?,?,?) ");

                        ps.setString(1, codigo_T.getText());
                        ps.setString(2, cedula_T.getText());
                        ps.setString(3, nombre_T.getText());
                        ps.setString(4, fecha_T.getText());
                        // Variable para almacenar en el comboBox
                        String signoSeleccionado = signo_b.getSelectedItem().toString();
                        ps.setString(5, signoSeleccionado);
                        System.out.println(ps);//imprimo en consola para verificación

                        int res = ps.executeUpdate();

                        if (res > 0) {
                            JOptionPane.showMessageDialog(null, "Persona Guardada");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al Guardar persona");
                        }
                    }
                    con.close();//importante!!!!
                }catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigo_T.setText("");
                cedula_T.setText("");
                fecha_T.setText("");
                nombre_T.setText("");
                signo_b.setSelectedIndex(0);
            }
        });

        buscar_s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar.setEnabled(true);
                borrar.setEnabled(true);
                Connection con = null;

                try {
                    con = getConection();
                    String sql = "SELECT * FROM prueba_2.Registro WHERE Signo = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, signo_b.getSelectedItem().toString());
                    ResultSet rs = ps.executeQuery();

                    boolean encontrado = false;

                    while (rs.next()) {
                        encontrado = true;
                        nombre_T.setText(rs.getString("Nombre"));
                        cedula_T.setText(rs.getString("Cedula"));
                        codigo_T.setText(rs.getString("Codigo"));
                        fecha_T.setText(rs.getString("Fecha_n"));
                        // Recuperar el código del signo y establecerlo en el ComboBox
                        String signoSeleccionado = rs.getString("Signo");
                        signo_b.setSelectedItem(signoSeleccionado);
                    }

                    if (!encontrado) {
                        JOptionPane.showMessageDialog(null, "No se encontró la persona con el signo " + signo_b.getSelectedItem());
                    }

                } catch (Exception s) {
                    JOptionPane.showMessageDialog(null, "Error al buscar por signo: " + s.getMessage());
                } finally {
                    try {
                        if (con != null) con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        buscar_n.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar.setEnabled(true);
                borrar.setEnabled(true);
                Connection con;

                try {
                    con = getConection();
                    st = con.createStatement();
                    ResultSet rs;
                    rs = st.executeQuery("select * from prueba_2.Registro where Nombre=" + nombre_T.getText() + ";");

                    boolean encontrado = false; // variable para indicar si se encontró la persona

                    while (rs.next()) {
                        encontrado = true; // se encontró la persona
                        nombre_T.setText(rs.getString("Nombre"));
                        cedula_T.setText(rs.getString("Cedula"));
                        codigo_T.setText(rs.getString("Codigo"));
                        fecha_T.setText(rs.getString("Fecha_n"));
                        String nombreSeleccionado = rs.getString("Nombre");
                        signo_b.setSelectedItem(nombreSeleccionado);
                    }

                    if (!encontrado) { // no se encontró la persona
                        JOptionPane.showMessageDialog(null, "No se encontró la persona con el nombre " + nombre_T.getText());
                    }

                } catch (Exception s) {
                    System.err.println(s);
                }
            }
        });

        buscar_c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar.setEnabled(true);
                borrar.setEnabled(true);
                Connection con;

                try {
                    con = getConection();
                    st = con.createStatement();
                    ResultSet rs;
                    rs = st.executeQuery("select * from prueba_2.Registro where Codigo=" + codigo_T.getText() + ";");

                    boolean encontrado = false; // variable para indicar si se encontró la persona

                    while (rs.next()) {
                        encontrado = true; // se encontró la persona
                        nombre_T.setText(rs.getString("Nombre"));
                        cedula_T.setText(rs.getString("Cedula"));
                        codigo_T.setText(rs.getString("Codigo"));
                        fecha_T.setText(rs.getString("Fecha_n"));
                        String codigoSeleccionado = rs.getString("Codigo");
                        signo_b.setSelectedItem(codigoSeleccionado);
                    }

                    if (!encontrado) { // no se encontró la persona
                        JOptionPane.showMessageDialog(null, "No se encontró la persona con el codigo " + codigo_T.getText());
                    }

                } catch (Exception s) {
                    System.err.println(s);
                }
            }
        });
        actualizar.addActionListener(new ActionListener() {
            Connection con2;
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    con2 = getConection();
                    ps = con2.prepareStatement("UPDATE prueba_2.Registro SET Codigo = ?, Cedula = ?,Nombre = ?, Fecha_n = ?,Signo = ? WHERE Codigo ="+codigo_T.getText() );

                    ps.setString(1, fecha_T.getText());
                    ps.setString(2,codigo_T.getText());
                    ps.setString(3,signo_b.getSelectedItem().toString());
                    ps.setString(4,cedula_T.getText());
                    ps.setString(5,nombre_T.getText());

                    int res = ps.executeUpdate();

                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Persona Actualizada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al Actualizar persona");
                    }
                    con2.close();
                }catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                con = getConection();
                try {
                    ps = con.prepareStatement("DELETE FROM prueba_2.Registro WHERE Codigo ="+codigo_T.getText());
                    int res = ps.executeUpdate();

                    if(res > 0 ){
                        JOptionPane.showMessageDialog(null,"Se elemino con exito");
                        cedula_T.setText("");
                        nombre_T.setText("");
                        signo_b.setSelectedIndex(0);
                        codigo_T.setText("");
                        fecha_T.setText("");

                    }else{
                        JOptionPane.showMessageDialog(null,"Error, datos invalidos!! ERROR !!");
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

    }

    public static Connection getConection() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:prueba_2/" ; //Direccion, puerto y nombre de la Base de Datos
        String user = "root"; //Usuario de Acceso a MySQL
        String password = "root_bas3"; //Password del usuario
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Registro");
                frame.setContentPane(new Registro().panel1);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                frame.setSize(560, 413);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}