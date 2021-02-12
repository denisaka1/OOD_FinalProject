package Model;

import Model.observer.Customer;
import Model.observer.Sender;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Customer> customers = new ArrayList<>();
        Sender sender = Sender.getInstance();

        for(Customer customer : customers) {
            sender.attach(customer);
        }
    }

}
