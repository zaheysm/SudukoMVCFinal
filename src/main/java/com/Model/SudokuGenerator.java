package com.Model;

import java.util.*;

/**
 * class that generate the random arrays to use in the suduko
 */
public class SudokuGenerator {
    /**
     * the size of the suduko
     */
    int size;
    /**
     * size if the raw
     */
    int sizeSqr;

    /**
     * constructor that get the size of the suduko
     * @param size the size of the suduko
     */
    public SudokuGenerator(int size){
        //initialize the size variable
        this.size=size;
        //initialize the raw size
        sizeSqr=size*size;
        //array to use for the shuffle
        arrShuffle=new int[sizeSqr][sizeSqr];
    }

    /**
     * method to return the array
     * @return return the array
     */
    public  int[][] getArrShuffle() {
        return arrShuffle;
    }

    /**
     * initialize the array
     */
    private  int arrShuffle[][];

    /**
     * method that will generate the array
     */
    public  void generate()
    {
        //initialize variable numarr,n
        int numarr,box=1;
        //nested loop
        for(int i=0;i<sizeSqr;i++)
        {
            //initialize k with n
            numarr=box;
            for(int j=0;j<sizeSqr;j++)
            {
                //as long as we are in the same raw
                if(numarr<=sizeSqr){
                    //add numarr to the array
                    arrShuffle[i][j]=numarr;
                    //increment numarr
                    numarr++;
                    //if bigger than the array
                }else{
                    //initialize numarr with 1
                    numarr=1;
                    //add numarr to the array
                    arrShuffle[i][j]=numarr;
                    //increment numarr
                    numarr++;
                }
            }
            //jump to the next box
            box=numarr+size;
            //in case it's the end of the row
            if(numarr==sizeSqr+1)
                //jump to next box
                box=size+1;

            if(box>sizeSqr)
                //jump to next box
                box=(box%sizeSqr)+1;
        }
    }

    /**
     * randomaise the array
     * @param check the array if 1 for rows and 0 for columns
     */
    public void generateRandom(int check){
        //initialize variables
        int rand1,rand2,max=size-1,min=0;
        //create a random
        Random rand= new Random();
        for(int i=0;i<size;i++)
        {
            //There are three groups.So we are using for loop three times.
            rand1=rand.nextInt(max-min+1)+min;
            //This while is just to ensure rand1 is not equal to rand2.
            do{
                //create rand from 1 to max possible
                rand2=rand.nextInt(max-min+1)+min;
            }while(rand1==rand2);//keep loop if the same
            //change the max and min size
            max+=size;min+=size;
            //check is global variable.
            //We are calling random_gen two time from the main func.
            //Once it will be called for columns and once for rows.
            if(check==1)
                //calling a function to interchange the selected rows.
                shuffle_row(rand1,rand2);
            else if(check==0)
                shuffle_col(rand1,rand2);
        }
    }

    /**
     * method to shuffle the row
     * @param rand1 get the random 1 number
     * @param rand2 get the random 2 number
     */
    public void shuffle_row(int rand1,int rand2){
        int temp;//k1 and k2 are two rows that we are selecting to interchange.
        for(int j=0;j<sizeSqr;j++)
        {
            temp=arrShuffle[rand1][j];
            arrShuffle[rand1][j]=arrShuffle[rand2][j];
            arrShuffle[rand2][j]=temp;
        }
    }
    /**
     * method to shuffle the column
     * @param rand1 get the random 1 number
     * @param rand2 get the random 2 number
     */
    public void shuffle_col(int rand1,int rand2){
        int temp;
        for(int j=0;j<sizeSqr;j++)
        {
            temp=arrShuffle[j][rand1];
            arrShuffle[j][rand1]=arrShuffle[j][rand2];
            arrShuffle[j][rand2]=temp;
        }
    }





}

