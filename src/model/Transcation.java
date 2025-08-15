package model;

public abstract class Transcation {

    protected int id;
    protected int userid;
    protected double amount;
    protected String category;
    protected String date;

    public Transcation(int userid , double amount , String category , String date){
        this.userid = userid;
        this.amount = amount;
        this.category = category;
        this.date = date;

    }

    public void setId(int i){
        id = i;
    }
    public int getId(){
        return id;
    }

    public int getUserId(){
        return userid;
    }

    public double getAmount(){
        return amount;
    }

    public String getcategory(){
        return category;
    }

    public String getDate (){
        return date;
    }

    public abstract String getType();
}
