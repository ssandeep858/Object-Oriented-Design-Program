package client;

public class Create {
    private boolean decision;

    public void perform(){
        System.out.println("Command pattern executed ");

        decision=true;
    }

    public void dontPerform(){
        System.out.println("Command won't be performed ");
        decision=false;
    }
}
