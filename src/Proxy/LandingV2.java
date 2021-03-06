/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proxy;

import java.awt.Color;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author rpenav
 */
public class LandingV2 extends javax.swing.JFrame {

    /**
     * Creates new form RMI_App
     */
    private HTMLEditorKit kit = new HTMLEditorKit();
    private HTMLDocument doc = new HTMLDocument();
    private int chatUser;
    private final Client client;
    int cont = 1;
    
    public LandingV2(final Client client) {        
        initComponents();
        this.client = client;        
        right_sidebar.setVisible(false);        
        this.setVisible(true);
        Color containerColor = new Color(250,250,250);
        Color headerColor = new Color(223,223,216);
        Color content = new Color(244,244,238);
        getContentPane().setBackground(content);
        container.setBackground(containerColor);
        header.setBackground(headerColor);        
        right_sidebar.setBackground(content);
        left_sidebar.setBackground(content);
        jScroll.getViewport().setBackground(containerColor);
        txtuserid.setText("Bienvenido: "+client.getUser()+" -> "+client.getID());
        
        lista.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent e) {                
                if(e.getValueIsAdjusting()){
                    if(right_sidebar.isVisible()) {
                        textPanel.setText("");
                    }
                    agregarMensaje("Chateando con: "+lista.getSelectedValue().toString());
                    try {                        
                        System.out.println("id obtenido es: "+client.getServer().getUserIdByUsername(lista.getSelectedValue().toString()));
                        chatUser = (int) client.getServer().getUserIdByUsername(lista.getSelectedValue().toString());
                        ArrayList<Message> mensaje = client.getServer().getConversationFromDatabase(client.getID(),chatUser);
                        
                        for(Message msj:mensaje){                            
                            if(msj.getStart() == client.getID()){
                                
                                recibirMensaje(msj.getMessage(),""+msj.getEnd(),1);
                            } else{
                                recibirMensaje(msj.getMessage(),""+msj.getEnd(),0);
                            }
                            System.out.println("el end es: "+msj.getEnd());
                            
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(LandingV2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    right_sidebar.setVisible(true);
                }
            }
        });
        
    }
    
    public void hello(ArrayList<String> array){        
        final DefaultListModel model = new DefaultListModel();
        
        for(String u:array){
            if(!u.equals(client.getUser())){
                model.addElement(u);
            }
        }
        lista.setModel(model);
    }
    
    public int getOpenChat(){
        return this.chatUser;
    }
    
    private void agregarMensaje(String mensaje){
        try {
            textPanel.setEditorKit(kit);
            textPanel.setDocument(doc);
            String style = "<style>\n"+
                        "body{"
                        + "background:rgb(240,240,240);"+
                        "}" +
"			.left{\n" +
                                "background: rgb(165,207,231);\n" +
                                "padding: 15px;\n" +
                                "margin-right: 50px;\n" +
                                "margin-left: 10px;\n" +
                                "margin-bottom: 10px;\n" +
                                "border-radius: 30px;\n" +
                                "-moz-border-radius: 30px \n"+
                                "-webkit-border-radius: 30px \n"+                                 
                        "}\n" +
                        ".right{\n" +
                                "background: rgb(215,232,164);\n" +
                                "padding: 15px;\n" +
                                "margin-left: 50px;\n" +
                                "margin-right: 10px;\n" +
                                "margin-bottom: 10px;\n" +
                                "border-radius: 30px;\n" +
                                "-moz-border-radius: 30px \n"+
                                "-webkit-border-radius: 30px \n"+
                        "}\n" +
                    "</style>";
            kit.insertHTML(doc, doc.getLength(), style+"<b>"+mensaje+"</b><hr>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            System.out.println(ex);
        }        
    }
    
    public void paint(Message Message){                
        if(Message.getStart() == this.chatUser) {
            recibirMensaje(Message.getMessage(),""+Message.getStart(),0);
        }
    }
    
    public void recibirMensaje(String mensaje, String usuario, int tipo){        
        try {
            textPanel.setEditorKit(kit);
            textPanel.setDocument(doc);
            if(tipo == 1){
                //kit.insertHTML(doc, doc.getLength(),"<font color='green'><b>yo digo que:</b></font>" , 0, 0, null);
                String div = "<div class='left'><p>"+mensaje+"</p></div>";
                kit.insertHTML(doc, doc.getLength(),div, 0, 0,null);
            } else {
                //kit.insertHTML(doc, doc.getLength(),"<font color='blue'><b>"+usuario+" dice que:</b></font>" , 0, 0, null);
                String div = "<div class='right'><p>"+mensaje+"</p></div>";
                kit.insertHTML(doc, doc.getLength(), div, 0, 0,null);                                
            }
            
            textPanel.setAutoscrolls(true);
            
        } catch (BadLocationException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }        
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        foto = new javax.swing.JLabel();
        txtuserid = new javax.swing.JLabel();
        cerrarSesion = new javax.swing.JButton();
        right_sidebar = new javax.swing.JPanel();
        messagePanel = new javax.swing.JScrollPane();
        textPanel = new javax.swing.JTextPane();
        btnEnviarMensaje = new javax.swing.JButton();
        btnCancelarMensaje = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        mensajeAEnviar = new javax.swing.JTextArea();
        jScroll = new javax.swing.JScrollPane();
        left_sidebar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jComboBox1 = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(940, 700));
        setResizable(false);

        container.setPreferredSize(new java.awt.Dimension(824, 650));

        foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ProxyImages/user.png"))); // NOI18N

        txtuserid.setText("Mi Usuario es: ");

        cerrarSesion.setText("Salir");
        cerrarSesion.setPreferredSize(new java.awt.Dimension(78, 78));
        cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(foto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtuserid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtuserid)
                            .addComponent(foto))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        right_sidebar.setPreferredSize(new java.awt.Dimension(544, 520));

        textPanel.setEditable(false);
        messagePanel.setViewportView(textPanel);

        btnEnviarMensaje.setText("Enviar");
        btnEnviarMensaje.setPreferredSize(new java.awt.Dimension(138, 78));
        btnEnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarMensajeActionPerformed(evt);
            }
        });

        btnCancelarMensaje.setText("Cancelar");
        btnCancelarMensaje.setPreferredSize(new java.awt.Dimension(138, 78));

        mensajeAEnviar.setColumns(20);
        mensajeAEnviar.setRows(5);
        jScrollPane2.setViewportView(mensajeAEnviar);

        javax.swing.GroupLayout right_sidebarLayout = new javax.swing.GroupLayout(right_sidebar);
        right_sidebar.setLayout(right_sidebarLayout);
        right_sidebarLayout.setHorizontalGroup(
            right_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(right_sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(right_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messagePanel)
                    .addGroup(right_sidebarLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(right_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(btnCancelarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        right_sidebarLayout.setVerticalGroup(
            right_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(right_sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(right_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(right_sidebarLayout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(btnEnviarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))
                    .addGroup(right_sidebarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        left_sidebar.setMaximumSize(null);
        left_sidebar.setPreferredSize(new java.awt.Dimension(240, 518));

        jLabel2.setText("Usuarios");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Conectados", "No Conectados", "Todos" }));

        jScrollPane1.setViewportView(lista);

        javax.swing.GroupLayout left_sidebarLayout = new javax.swing.GroupLayout(left_sidebar);
        left_sidebar.setLayout(left_sidebarLayout);
        left_sidebarLayout.setHorizontalGroup(
            left_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(left_sidebarLayout.createSequentialGroup()
                .addGroup(left_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(left_sidebarLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(left_sidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(left_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(left_sidebarLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 11, Short.MAX_VALUE))))
                    .addGroup(left_sidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(left_sidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        left_sidebarLayout.setVerticalGroup(
            left_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(left_sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScroll.setViewportView(left_sidebar);

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(containerLayout.createSequentialGroup()
                        .addComponent(jScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(right_sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScroll)
                    .addComponent(right_sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarMensajeActionPerformed
        
        try {
            Message newe = new Message(client.getID(),chatUser,mensajeAEnviar.getText(),"");
            if("".equals(newe.getMessage())){
                newe.setMessage(this.client.getID()+"-"+chatUser +"-c "+cont++);
            }
            client.sendMessage(newe);            
            //kit.insertHTML(doc, doc.getLength(),"<font color='green'><b> yo digo que :</b></font>" , 0, 0, null);
            kit.insertHTML(doc, doc.getLength(), "<div class='left'<p>"+mensajeAEnviar.getText()+"</p></div>", 0, 0,null);
        } catch (BadLocationException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        mensajeAEnviar.setText("");
    }//GEN-LAST:event_btnEnviarMensajeActionPerformed

    
    private void cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionActionPerformed
        client.go();
        System.exit(0);
    }//GEN-LAST:event_cerrarSesionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarMensaje;
    private javax.swing.JButton btnEnviarMensaje;
    private javax.swing.JButton cerrarSesion;
    private javax.swing.JPanel container;
    private javax.swing.JLabel foto;
    private javax.swing.JPanel header;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScroll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel left_sidebar;
    private javax.swing.JList lista;
    private javax.swing.JTextArea mensajeAEnviar;
    private javax.swing.JScrollPane messagePanel;
    private javax.swing.JPanel right_sidebar;
    private javax.swing.JTextPane textPanel;
    private javax.swing.JLabel txtuserid;
    // End of variables declaration//GEN-END:variables
}

