package org.example.mysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LogInController {

    @FXML
    private TextField textPassword;

    @FXML
    private TextField textUsername;
    @FXML
    private Text logInError;
    private ArrayList<User> users = readDataForUsers();
    private String userName;
    private String userRole;
    public void switchToMainMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        Parent root = loader.load();
        HelloController controllerB = loader.getController();
        controllerB.setUserName(userName);
        controllerB.setUserRole(userRole);// Pass data to ControllerB
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void checkAccount(ActionEvent event) {
        for(User user : users){
            if (user.checkAccount(textPassword.getText(),textUsername.getText())){
                try{
                    userName = user.getUsername();
                    userRole = user.getRole();
                    switchToMainMenu(event);
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        logInError.setText("Wrong Password. Try again!");
    }
    private ArrayList<User> readDataForUsers(){
        ArrayList<User> temp = new ArrayList<>();
        File users = new File("src/main/java/org/example/mysystem/Users.txt");
        try{
            if (users.createNewFile()){
                System.out.println("Created new");
            } else{
                System.out.println("Already exists");
            }
            Scanner scanner = new Scanner(users);
            while(scanner.hasNext()){
                String[] currRunner = scanner.nextLine().split(",");
                User user = new User(currRunner[0],currRunner[1],currRunner[2]);
                temp.add(user);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }
}
