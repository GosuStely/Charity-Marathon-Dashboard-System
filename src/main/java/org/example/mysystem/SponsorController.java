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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SponsorController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Text userName;

    @FXML
    private Text userRole;

    @FXML
    private TableColumn<Sponsor, String> email = new TableColumn<>("email");

    @FXML
    private TableColumn<Sponsor, String> name = new TableColumn<>("name");

    @FXML
    private TableColumn<Sponsor, String> phone = new TableColumn<>("phone");

    @FXML
    private TableColumn<Sponsor, String> registrationFee = new TableColumn<>("registrationFee");

    @FXML
    private TableView<Sponsor> runnerTable = new TableView<>();

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
    private ObservableList<Sponsor> currSponsors = FXCollections.observableArrayList(readDataForSponsors());
    public void setUserName(String userName) {
        this.userName.setText(userName);
    }
    public void setUserRole(String userRole){
        this.userRole.setText(userRole);
    }
    public void logOut(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("log-in.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    private ArrayList<Sponsor> readDataForSponsors(){
        ArrayList<Sponsor> temp = new ArrayList<>();
        File runners = new File("src/main/java/org/example/mysystem/mySponsors.txt");
        try{
            if (runners.createNewFile()){
                System.out.println("Created new");
            } else{
                System.out.println("Already exists");
            }
            Scanner scanner = new Scanner(runners);
            while(scanner.hasNext()){
                String[] currRunner = scanner.nextLine().split(",");
                Sponsor sponsor = new Sponsor(currRunner[0],currRunner[1],currRunner[2],currRunner[3]);
                temp.add(sponsor);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }
    @FXML
    private void buttonInsert(ActionEvent event){
    if (userRole.getText().equalsIgnoreCase("admin")){
        if(textName.getText().trim().isEmpty() || textRegistrationFee.getText().trim().isEmpty() || textEmail.getText().trim().isEmpty() || textPhone.getText().trim().isEmpty()){
            errorMessage.setText("Please Fill all the data.");
        } else if (!textRegistrationFee.getText().trim().equalsIgnoreCase("0")){
            currSponsors = FXCollections.observableArrayList(readDataForSponsors());
            Sponsor newSponsor = new Sponsor(textName.getText(),textEmail.getText(),textPhone.getText(),"$" + textRegistrationFee.getText());
            runnerTable.getItems().add(newSponsor);
            try {
                FileWriter writer = new FileWriter("src/main/java/org/example/mysystem/mySponsors.txt",true);
                writer.write(textName.getText()+","+textEmail.getText()+","+textPhone.getText()+",$"+textRegistrationFee.getText()+"\n");
                writer.close();
            }catch (IOException exception){
                System.out.println(exception.getMessage());
            }
            textName.clear();
            textEmail.clear();
            textPhone.clear();
            textRegistrationFee.clear();
            errorMessage.setText(null);
        } else {
            errorMessage.setText("Registration Fee should be (paid/not paid)");
        }
    } else {
        errorMessage.setText("You dont have permission to insert");
    }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Sponsor,String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<Sponsor,String>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<Sponsor,String>("phone"));
        registrationFee.setCellValueFactory(new PropertyValueFactory<Sponsor,String>("registrationFee"));

        runnerTable.setItems(currSponsors);
        editData();
    }
    @FXML
    private void deleteData(ActionEvent event){
    if (userRole.getText().equalsIgnoreCase("admin")){
        TableView.TableViewSelectionModel<Sponsor> selectionModel = runnerTable.getSelectionModel();
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
        errorMessage.setText("You dont have permission to delete");
    }
    }
    private void editData(){
        name.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
        name.setOnEditCommit(event ->{
            Sponsor sponsor = event.getTableView().getItems().get(event.getTablePosition().getRow());
            sponsor.setName(event.getNewValue());
        });

        email.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
        email.setOnEditCommit(event ->{
            Sponsor sponsor = event.getTableView().getItems().get(event.getTablePosition().getRow());
            sponsor.setEmail(event.getNewValue());
        });

        phone.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
        phone.setOnEditCommit(event ->{
            Sponsor sponsor = event.getTableView().getItems().get(event.getTablePosition().getRow());
            sponsor.setPhone(event.getNewValue());
        });

        registrationFee.setCellFactory(TextFieldTableCell.<Sponsor>forTableColumn());
        registrationFee.setOnEditCommit(event ->{
            Sponsor sponsor = event.getTableView().getItems().get(event.getTablePosition().getRow());
            sponsor.setRegistrationFee(event.getNewValue());
        });

    }
}