package client;

import common.Command;
import common.Common;

public class CreateCommand implements Command{
   Create create;
   public CreateCommand(Create create){
       this.create=create;
   }



    @Override
    public void execute() {

        System.out.println("New User was created using command pattern ");
        create.perform();
    }
}
