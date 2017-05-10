package jtablefromal;

/**
 * Created by Agrim Sachdeva on 5/9/2017.
 */

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 * @author 1bestcsharp.blogspot.com
 */
public class Java_Populate_JTable_Using_ArrayList extends javax.swing.JFrame {

    /**
     * Creates new form Java_Populate_JTable_Using_ArrayList
     */
    public Java_Populate_JTable_Using_ArrayList() {
        initComponents();

        // use the addRowToJTable
        addRowToJTable();

    }

    // create a class User and use it to populate the arraylist
    public class User {
        public int id;
        public String fname;
        public String lname;
        public int age;

        public User(int Id, String FName, String LName, int Age) {
            this.id = Id;
            this.fname = FName;
            this.lname = LName;
            this.age = Age;
        }
    }

    // create a list of users
    public ArrayList ListUsers() {
        ArrayList<User> list = new ArrayList<User>();
        User u1 = new User(1, "FNA", "LNA", 10);
        User u2 = new User(2, "FNB", "LNB", 20);
        User u3 = new User(3, "FNC", "LNC", 30);
        User u4 = new User(4, "FND", "LND", 40);
        User u5 = new User(5, "FNE", "LNE", 50);
        User u6 = new User(6, "FNF", "LNF", 60);
        list.add(u1);
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        list.add(u6);
        return list;
    }

    // added rows from arraylist to jtable
    public void addRowToJTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        ArrayList<User> list = ListUsers();
        Object rowData[] = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).id;
            rowData[1] = list.get(i).fname;
            rowData[2] = list.get(i).lname;
            rowData[3] = list.get(i).age;
            model.addRow(rowData);
        }

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Id", "First Name", "Last Name", "Age"
                }
        ));

        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

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
            java.util.logging.Logger.getLogger(Java_Populate_JTable_Using_ArrayList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Java_Populate_JTable_Using_ArrayList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Java_Populate_JTable_Using_ArrayList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Java_Populate_JTable_Using_ArrayList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Java_Populate_JTable_Using_ArrayList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}