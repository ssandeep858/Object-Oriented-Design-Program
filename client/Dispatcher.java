package client;

import common.Common;
import common.User;

public class Dispatcher extends FrontController{
    private CustomerView customerView;
    private AdminView adminView;

    public Dispatcher(Common stub,User loggedInUserObject)
    {
        customerView =new CustomerView(stub,loggedInUserObject);
        adminView=new AdminView(stub,loggedInUserObject);
    }

    public Dispatcher() {
    }

    public void dispatch(int input) throws Exception {
        if(input==1)
        {
            customerView.show();
        }
        if(input==2)
        {
            adminView.show();
        }

    }

}
