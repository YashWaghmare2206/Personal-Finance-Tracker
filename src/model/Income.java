package model;

public class Income extends Transcation {

        public Income(int userid , double amount , String category , String date){

            super(userid , amount , category , date);
        }

        public String getType(){
            return "Income";
        }
}
