package client;

import common.Common;
import common.User;

public class FrontController extends FrontControllerPattern {

    private Dispatcher dispatcher;

    public FrontController(Common stub,User loggedInUserObject)
    {
        dispatcher=new Dispatcher(stub,loggedInUserObject);
    }

    public FrontController() {
    }


    private boolean isAuthentic(){
        System.out.println("FrontController pattern in action  ");
        return true;
    }

    public void dispatchRequest(int input) throws Exception {

        if(isAuthentic()){
            dispatcher.dispatch(input);
        }
    }
}
