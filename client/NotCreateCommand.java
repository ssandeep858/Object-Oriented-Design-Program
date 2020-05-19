package client;

import common.Command;

public class NotCreateCommand implements Command {
   Create create;
   public NotCreateCommand(Create create){
       this.create=create;
   }




    @Override
    public void execute() {
        System.out.println("Command pattern in action");
      create.dontPerform();
    }
}
