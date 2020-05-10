package main.resources.utilities.validations;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

public class Validations {

    public static boolean numeric(JFXTextField textField){
        if(!textField.getText().matches("[0-9]{15}")){
            return false;
        }
        return true;
    }

    public static boolean isEmpty(JFXTextField textField){
        if (textField.getText().isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isEmpty(JFXPasswordField textField){
        if (textField.getText().isEmpty()){
            return true;
        }
        return false;
    }

}
