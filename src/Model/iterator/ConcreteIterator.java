package Model.iterator;

import Model.Product;

import java.io.*;
import java.util.Iterator;

public class ConcreteIterator implements Iterator<Product> {

    ObjectInputStream read;
    ObjectOutputStream write;

    public ConcreteIterator(String fileName) {


        try {
            read = new ObjectInputStream(new FileInputStream(fileName));
            write = new ObjectOutputStream(new FileOutputStream(fileName));
        } catch (IOException e) {
            // todo: handle the exception so it will print to GUI
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        boolean canRead = false;

        try {
            if(read.available() != 0)
                canRead = true;

        } catch (IOException e) {
            // todo: handle the exception so it will print to GUI
            e.printStackTrace();
        }

        return canRead;
    }

    @Override
    public Product next() {
        if(!hasNext())
            return null;

        try {
            return (Product)read.readObject();
        } catch (IOException e) {
            // todo: handle if can't read from object - not supposed to happen
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            // todo: handle if class Product doesn't exist
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void remove() {
        // todo: choose the best option for remove item from file
    }

    public void write(Product product) {
        try {
            write.writeObject(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endIterator() {
        try {
            read.close();
            write.close();
        } catch (IOException e) {
            // todo: handle the exception if can't close a file
            // todo: happens if the file is not open
            e.printStackTrace();
        }

    }
}
