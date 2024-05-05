package com.Model;
import com.Application.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that only thing to do is to read a file or save the file and how it can handle back to the game
 * and get it translates to rows and columns
 */
public class LoadSaveFile {
    /**
     * method that take the file and load it and return 2d array list
     * @param fileName  get the file name and path from the controller
     * @param dim get the dimension of the suduko to check if the file is valid
     * @param loadFile  get the arraylist in use
     * @return return an arraylist full with numbers from the array
     */
    public ArrayList<ArrayList<String>> loadFile(String fileName, String dim, ArrayList<ArrayList<String>> loadFile){
        //start scanner
        Scanner scan=null;
        //get the size of the suduko
        int size=Integer.parseInt(dim);
        //the size of the box of the array
        size=size*size;
        //try to open the file
        try{
            //start scanning the file
            scan=new Scanner(Paths.get(fileName));
        }catch(FileNotFoundException nfe){//exception for file not found
            System.out.println("File not found");
        }catch (IOException ioe){//exception for file io
            System.out.println(ioe.getLocalizedMessage());
        }

        //string to read the line from the fiile
        String line;
        //counter to use where you are in the file
        int counter=0;
        //loop as long there more in the file
        while(scan.hasNext()) {
            //start scanning the file
            line= scan.nextLine();
            //if the file not starting as should be give an error
            if(!dim.equals(String.valueOf(line.charAt(0)))){
                SudokoView.showErrDialog();
                break;
            }
            //loop on the line of the file
            for (int j = 0; j < size; j++) {
                //set the arraylist
                loadFile.get(counter).add(j, String.valueOf(line.charAt(j+2)));
            }
            //increment the row
            counter++;

        }
        //return the arraylist
        return loadFile;


    }

    /**
     * method to save the file the chosen path
     * @param saveFile get the arraylist that hold the suduko solution
     * @param filePath get the file path
     * @throws IOException throws exception
     */
    public void saveFile(ArrayList<ArrayList<String>> saveFile,String filePath) throws IOException {
        //file writer class to write the file
        FileWriter writer = new FileWriter(filePath);
        //get the size of the suduko
        int size=(int)Math.sqrt(saveFile.size());
        //nested loop
        for (int i = 0; i < saveFile.size(); i++) {
            //start the size of the suduko
            writer.write(size+",");
            //loop to start write to the file
            for (int j = 0; j < saveFile.size(); j++) {
                //write to the file
                writer.write(saveFile.get(i).get(j));
            }
            //new line in the file
            writer.write("\n");
        }
        //close the file
        writer.close();
    }
}