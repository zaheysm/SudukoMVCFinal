package com.Model;

import com.Application.SudokoView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Random;

public class sudukoModel {
    /**
     * create textFiled Array
     */
    static TextField[][] textField;



    /**
     * array list to hold the numbers being entered by th player
     */
    static ArrayList<ArrayList<String>> addnum = new ArrayList<ArrayList<String>>();
    /**
     * set the difficulty of the game
     * @param percent the percent of difficulty
     * @param textField get the TextFiled
     */
    private static void createDiff(double percent, TextField textField[][]) {
        int size=(int)Math.sqrt(textField.length);
        //create tmp arraylist
        ArrayList<String> tmp = new ArrayList<>();
        //create random
        Random rand = new Random();
        //variable to get the random
        int r1;
        //loop on the array
        for (int i = 0; i < addnum.size(); i++) {
            for (int j = 0; j < addnum.size() * percent; j++) {
                //get a random number
                r1 = rand.nextInt(size * size);
                //if the number is already chosen re-choose or add to to tmp array
                if (tmp.equals(i + "" + r1)) {
                    i--;
                } else {
                    tmp.add(i + "" + r1);
                }
            }
        }
        //loop which number to open
        for (String s : tmp) {
            int num1, num2;
            num1 = Character.getNumericValue(s.charAt(0));
            num2 = Character.getNumericValue(s.charAt(1));
            textField[num1][num2].setText(addnum.get(num1).get(num2));
        }


    }

    private void craeteArraynums(int size){
        ArrayList<String> toAddnum=new ArrayList<>();
        addnum.clear();
        int sqrsize=size*size;
        for (int i = 0; i < sqrsize; i++) {
            toAddnum.add(i, " ");
            ArrayList<String> tmp = new ArrayList<>();
            for (int j = 0; j < sqrsize; j++) {
                tmp.add("");

            }
            addnum.add(i, tmp);
        }
    }

    public void emptyTextFiled(TextField textField[][]){
        for (int i = 0; i < textField.length; i++) {
            for (int j = 0; j < textField.length; j++) {
                textField[i][j].setText("");
            }
        }
    }


    /**
     * Creates method setRows of return type void in case moving from one mode to another to clear and set the mode as needed
     *
     * @return void
     */
    public static void setRows() {
        textField= SudokoView.getTextField();
        for (int i = 0; i < textField.length; i++) {
            for (int j = 0; j < textField.length; j++) {
                String s = addnum.get(i).get(j);
                System.out.println(s);

                textField[i][j].setText(s);

            }
        }
    }




    public void setDesignMode(TextField textField,int row,int col,String numToEnter){
        int size=SudokoView.getSize();
        if (addnum.isEmpty()){
            craeteArraynums(size);
        }

        if (checkRow(row,numToEnter) && checkcolumn(col,numToEnter) && checkBox(row, col,size,numToEnter)) {
            // add the number to the row
//            toAddnum.set(row, numToEnter);
            // add the row of the arrays to the column
            addnum.get(col).set(row,numToEnter);
            System.out.println(addnum);
            // set the text filed to the number eneterd
            textField.setText(numToEnter);
            numToEnter = "";
        }

    }

    public void SetPlayGame(TextField textField,int row,int col,int size,String numToEnter){
        String s = addnum.get(col).get(row);
        System.out.println(s);
        if (textField.getText().equals("")) {
            String point= SudokoView.getPoints().getText();
            System.out.println(s);
            int newpoint;
            if (!s.equals(numToEnter)) {
                textField.setText("");
                newpoint=Integer.parseInt(point)-size;
                if (newpoint<0){
                    SudokoView.getPoints().setText("0");
                }else{
                    SudokoView.getPoints().setText(Integer.toString(newpoint));
                }
            } else {
                textField.setText(numToEnter);
                newpoint=Integer.parseInt(point)+size;
                SudokoView.getPoints().setText(Integer.toString(newpoint));
            }
        }


    }

    /**
     * Creates method checkRow of return type boolean to check if the same number is already in the row
     *
     * @param row identifies the row of the number we are checking
     * @return boolean true or false if the number has been found
     */
    private static boolean checkRow(int row,String numToEnter) {
        for (int i = 0; i < addnum.size(); i++) {
            if (addnum.get(i).get(row).equals(numToEnter)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Creates method checkColumn of return type boolean to check if the same number is already in the column
     *
     * @param column is the column of the number we are checking for to identify a duplicate
     * @return boolean returns true or false if the number is found
     */
    private static boolean checkcolumn(int column,String numToEnter) {

        if (addnum.get(column).contains(numToEnter)) {
            return false;
        }

        return true;

    }

    private static boolean checkBox(int row, int col,int size,String numToEnter) {
        int newRow = row - row % size;
        int newCol = col - col % size;
        for (int i = newRow; i < newRow + size; i++) {
            for (int j = newCol; j < newCol + size; j++) {
                if (addnum.get(j).get(i).equals(numToEnter)) {
                    return false;
                }
            }

        }
        return true;
    }


    public static void setAddnum(int board[][]) {
        for (int i = 0; i < addnum.size(); i++) {
            for (int j = 0; j < addnum.size(); j++) {
                String numToEnter = Integer.toString(board[i][j]);

                addnum.get(i).set(j, numToEnter);

            }
        }
        setRows();
    }


    public static ArrayList<ArrayList<String>> getAddnum() {
        return addnum;
    }





}
