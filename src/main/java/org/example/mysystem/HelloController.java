package org.example.mysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text userName;

    @FXML
    private Text userRole;
    @FXML
    private TableColumn<Runner, String> address = new TableColumn<>("address");

    @FXML
    private TableColumn<Runner, String> bib = new TableColumn<>("bib");

    @FXML
    private TableColumn<Runner, String> email = new TableColumn<>("email");

    @FXML
    private TableColumn<Runner, String> name = new TableColumn<>("name");

    @FXML
    private TableColumn<Runner, String> phone = new TableColumn<>("phone");

    @FXML
    private TableColumn<Runner, String> registrationFee = new TableColumn<>("registrationFee");

    @FXML
    private TableView<Runner> runnerTable = new TableView<>();
    @FXML
    private TextField textAddress;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textName;

    @FXML
    private TextField textPhone;

    @FXML
    private TextField textRegistrationFee;
    @FXML
    private Text errorMessage;
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonDelete;
    private ObservableList<Runner> currRunners = FXCollections.observableArrayList(readDataForRunners());
    private int lastbibNum = 0;
    public void setUserName(String userName) {
        this.userName.setText(userName);
    }
    public void setUserRole(String userRole){
        this.userRole.setText(userRole);
    }

    public void switchToShowRunner(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-runners.fxml"));
        Parent root = loader.load();
        HelloController controllerB = loader.getController();
        controllerB.setUserName(userName.getText());
        controllerB.setUserRole(userRole.getText());// Pass data to ControllerB
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void switchToShowSponsor(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("show-sponsors.fxml"));
        Parent root = loader.load();
        SponsorController controllerB = loader.getController();
        controllerB.setUserName(userName.getText());
        controllerB.setUserRole(userRole.getText());// Pass data to ControllerB
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        Parent root = loader.load();
        HelloController controllerB = loader.getController();
        controllerB.setUserName(userName.getText());
        controllerB.setUserRole(userRole.getText());// Pass data to ControllerB
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void logOut(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("log-in.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private ArrayList<Runner> readDataForRunners(){
        ArrayList<Runner> temp = new ArrayList<>();
        File runners = new File("src/main/java/org/example/mysystem/myRunners.txt");
        try{
            if (runners.createNewFile()){
                System.out.println("Created new");
            } else{
                System.out.println("Already exists");
            }
            Scanner scanner = new Scanner(runners);
            int bib = 1;
            while(scanner.hasNext()){
                String[] currRunner = scanner.nextLine().split(",");
                Runner runner = new Runner(currRunner[0],currRunner[1],currRunner[2],currRunner[3],currRunner[4]);
                runner.setBib(bib + "");
                temp.add(runner);
                bib++;
            }
            lastbibNum = bib;

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }
    @FXML
    private void buttonInsert(ActionEvent event){
    if (userRole.getText().equalsIgnoreCase("admin")){
        if(textName.getText().trim().isEmpty() || textAddress.getText().trim().isEmpty() || textRegistrationFee.getText().trim().isEmpty() || textEmail.getText().trim().isEmpty() || textPhone.getText().trim().isEmpty()){
            errorMessage.setText("Please Fill all the data.");
        } else if (textRegistrationFee.getText().trim().equalsIgnoreCase("paid") || textRegistrationFee.getText().trim().equalsIgnoreCase("unpaid")){
            currRunners = FXCollections.observableArrayList(readDataForRunners());
            Runner newRunner = new Runner(textName.getText(),textEmail.getText(),textPhone.getText(),textAddress.getText(),textRegistrationFee.getText());
            newRunner.setBib(lastbibNum + "");
            runnerTable.getItems().add(newRunner);
            try {
                FileWriter writer = new FileWriter("src/main/java/org/example/mysystem/myRunners.txt",true);
                writer.write(textName.getText()+","+textEmail.getText()+","+textPhone.getText()+","+textAddress.getText()+","+textRegistrationFee.getText()+"\n");
                writer.close();
            }catch (IOException exception){
                System.out.println(exception.getMessage());
            }
            textName.clear();
            textEmail.clear();
            textPhone.clear();
            textAddress.clear();
            textRegistrationFee.clear();
            errorMessage.setText(null);
        } else {
            errorMessage.setText("Registration Fee should be (paid/not paid)");
        }
    } else {
        errorMessage.setText("You dont have permission to Insert");
    }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Runner,String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<Runner,String>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<Runner,String>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<Runner,String>("address"));
        registrationFee.setCellValueFactory(new PropertyValueFactory<Runner,String>("registrationFee"));
        bib.setCellValueFactory(new PropertyValueFactory<Runner,String>("bib"));

        runnerTable.setItems(currRunners);
        editData();
    }
    @FXML
    private void deleteData(ActionEvent event){
    if (userRole.getText().equalsIgnoreCase("admin")){
        TableView.TableViewSelectionModel<Runner> selectionModel = runnerTable.getSelectionModel();
        if (selectionModel.isEmpty()){
            errorMessage.setText("You need to select data before deleting");
        }else{
            errorMessage.setText(null);
            ObservableList<Integer> list = selectionModel.getSelectedIndices();
            Integer[] selectedIndices = new Integer[list.size()];
            selectedIndices = list.toArray(selectedIndices);

            Arrays.sort(selectedIndices);

            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                selectionModel.clearSelection(selectedIndices[i].intValue());
                runnerTable.getItems().remove(selectedIndices[i].intValue());
            }
        }
    } else {
        errorMessage.setText("You dont have permission to delete.");
    }
    }
    private void editData(){
        name.setCellFactory(TextFieldTableCell.<Runner>forTableColumn());
        name.setOnEditCommit(event ->{
            Runner runner = event.getTableView().getItems().get(event.getTablePosition().getRow());
            runner.setName(event.getNewValue());
        });

        email.setCellFactory(TextFieldTableCell.<Runner>forTableColumn());
        email.setOnEditCommit(event ->{
            Runner runner = event.getTableView().getItems().get(event.getTablePosition().getRow());
            runner.setEmail(event.getNewValue());
        });

        phone.setCellFactory(TextFieldTableCell.<Runner>forTableColumn());
        phone.setOnEditCommit(event ->{
            Runner runner = event.getTableView().getItems().get(event.getTablePosition().getRow());
            runner.setPhone(event.getNewValue());
        });

        address.setCellFactory(TextFieldTableCell.<Runner>forTableColumn());
        address.setOnEditCommit(event ->{
            Runner runner = event.getTableView().getItems().get(event.getTablePosition().getRow());
            runner.setAddress(event.getNewValue());
        });

        registrationFee.setCellFactory(TextFieldTableCell.<Runner>forTableColumn());
        registrationFee.setOnEditCommit(event ->{
            Runner runner = event.getTableView().getItems().get(event.getTablePosition().getRow());
            runner.setRegistrationFee(event.getNewValue());
        });

        bib.setCellFactory(TextFieldTableCell.<Runner>forTableColumn());
        bib.setOnEditCommit(event ->{
            Runner runner = event.getTableView().getItems().get(event.getTablePosition().getRow());
            runner.setBib(event.getNewValue());
        });
    }
}