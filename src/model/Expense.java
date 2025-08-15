package model;

public class Expense extends Transcation{

    public Expense(int userid , double amount , String category , String date){

        super(userid , amount , category , date);
    }

    public String getType(){
        return "Expense";
    }

}
