import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * Ryan Bookram
 * Software Development (14877)
 * October 10, 2025
 * VMS.java
 * This class operates as a Vehicle Managment System, allowing users to store and manipulate vehicle attributes
 */
public class VMS {

    /**
     * Method: Main
     * Return: None
     * Purpose: Incorporates a GUI system to allow users to interact with the managment system
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                constructGUI();
            }
        });
    }

    /**
     * Method: constructGUI
     * Return: None
     * Purpose: Defines logic and visuals for the GUI system
     */
    public static void constructGUI() {

        VehicleManager vm = new VehicleManager();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Vehicle Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        BoxLayout layout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        GridLayout buttonLayout = new GridLayout(1, 10);
        GridLayout singleLayout = new GridLayout(1, 1);
        frame.setLayout(layout);
        frame.setMaximumSize(new Dimension(1280, 720));

        JPanel listPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        JPanel buttonPanel = new JPanel(buttonLayout);
        JPanel inputPanel = new JPanel(singleLayout);
        JPanel outputPanel = new JPanel(singleLayout);
        JPanel exitPanel = new JPanel();

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        JList<String> vehicleList = new JList<String>(listModel);
        vehicleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ScrollPane vScroll = new ScrollPane();
        vScroll.add(vehicleList);
        vScroll.setSize(1200, 500);

        JLabel infoLabel = new JLabel("Enter text below. ADD and UPDATE commands require the format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain. REMOVE and EVALUATE require valid VIN numbers. AFT (Add from texfile) requires a valid file path.");
        JLabel outputLabel = new JLabel("");

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton updateButton = new JButton("Update");
        JButton evaluateButton = new JButton("Evaluate");
        JButton addTextButton = new JButton("AFT");
        JButton exitButton = new JButton("Exit");

        JTextField inputBox = new JTextField();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    outputLabel.setText(vm.addVehicle(inputBox.getText()));
                    inputBox.setText("");
                    listModel.clear();

                    for(Vehicle vehicle : vm.getList()) {
                        listModel.addElement(vehicle.toString());
                    }
                }
            }
        });

        addTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    outputLabel.setText(vm.addFromText(inputBox.getText()));
                    inputBox.setText("");
                    listModel.clear();

                    for(Vehicle vehicle : vm.getList()) {
                        listModel.addElement(vehicle.toString());
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    try {
                        outputLabel.setText(vm.removeVehicle(Integer.parseInt(inputBox.getText())));
                        inputBox.setText("");
                        listModel.clear();

                        for(Vehicle vehicle : vm.getList()) {
                            listModel.addElement(vehicle.toString());
                        }
                    }
                    catch(Exception x) {
                        outputLabel.setText("Invalid VIN. Please enter an integer.");
                        inputBox.setText("");
                    }
                }
            }
        });

        vehicleList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    if(!vehicleList.isSelectionEmpty()) {
                        vm.getList().remove(vehicleList.getSelectedIndex());
                        outputLabel.setText("Vehicle Removed Successfully.");
                        listModel.clear();

                        for(Vehicle vehicle : vm.getList()) {
                            listModel.addElement(vehicle.toString());
                        }

                        vehicleList.clearSelection();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    outputLabel.setText(vm.updateVehicle(inputBox.getText()));
                    inputBox.setText("");
                    listModel.clear();

                    for(Vehicle vehicle : vm.getList()) {
                        listModel.addElement(vehicle.toString());
                    }
                }
            }
        });

        evaluateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inputBox.getText().isEmpty()){
                    try {
                        outputLabel.setText(vm.evaluateVehicle(Integer.parseInt(inputBox.getText())));
                        inputBox.setText("");
                    }
                    catch(Exception x) {
                        outputLabel.setText("Invalid VIN. Please enter an integer.");
                        inputBox.setText("");
                    }
                }
                else {
                    if(!vehicleList.isSelectionEmpty()) {
                        outputLabel.setText(vm.evaluateVehicle(vm.getList().get(vehicleList.getSelectedIndex()).getVinNumber()));
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() != vehicleList && !vehicleList.getBounds().contains(e.getPoint())) {
                    vehicleList.clearSelection();
                }
            }
        });

        listPanel.add(vScroll);
        infoPanel.add(infoLabel);
        buttonPanel.add(addButton);
        buttonPanel.add(addTextButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(evaluateButton);
        buttonPanel.add(inputBox);
        inputPanel.add(inputBox);
        outputPanel.add(outputLabel);
        exitPanel.add(exitButton);

        frame.add(listPanel);
        frame.add(infoPanel);
        frame.add(buttonPanel);
        frame.add(inputPanel);
        frame.add(outputPanel);
        frame.add(exitPanel);

        frame.pack();
        frame.setVisible(true);

        frame.setBounds(0, 0, 1280, 720);
    }
}