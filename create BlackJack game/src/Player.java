class Player extends Human {
    
    public void handClear() {
        clear(point);
    }

    public void open(){
        System.out.println(point);
    }

    public void allOpen(){

    }

    public boolean possibleSplit() {
        return true;
    }

    public boolean possibleDoubleDown() {
        return true;
    }

    public void split(){

    }

    public int getPocketMoney() {
        return 0;
    }

    public void setPocketMoney(){

    }

    public void setBetMoney(){

    }

    public boolean isBurst() {
        return true;
    }
}
