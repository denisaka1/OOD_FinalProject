package Model.iterator;

import Exceptions.IllegalInputException;
import Model.Product;

import java.io.*;
import java.util.*;

public class FileHandler implements Iterable<Product> {
    private long readPos;
    private long fileSize;
    private RandomAccessFile raf;

    public FileHandler(String fileName){
        try {
            raf = new RandomAccessFile(fileName, "rw");
            readPos = raf.getFilePointer();
            fileSize = raf.length();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // todo: handle file not found
        } catch (IOException e) {
            e.printStackTrace();
            // todo: handle IOException
        }
    }

    public boolean removeProduct(String serialNumber) {
        // todo: handle null itr.next()
        Iterator<Product> itr = iterator();
        boolean isRemoved = false;
        Product product;

        while(itr.hasNext() && !isRemoved) {
            product = itr.next();
            if(serialNumber.equals(product.getSerialNumber())) {
                itr.remove();
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    public LinkedHashMap<String, Product> readProducts() {

        Iterator<Product> itr = iterator();
        LinkedHashMap<String, Product> result = new LinkedHashMap<>();
        Product temp;

        while(itr.hasNext()) {
            temp = itr.next();
            result.put(temp.getSerialNumber(), temp);
        }

        return result;
    }

    @Override
    public Iterator<Product> iterator() {
        return new BinaryFileIterator();
    }

    public void writeProduct(Product product){
        byte[] temp;
        try {
            temp = Product.serialize(product);

            int productByteLength = temp.length;

            raf.seek(fileSize);
            raf.writeInt(productByteLength);
            raf.write(temp);

            fileSize = raf.length(); // resize

        } catch (IOException e) {
            // todo: handle exception
            e.printStackTrace();
        }
    }

    public void clearFile() {
        try {
            raf.setLength(0);
            readPos = 0;
            fileSize = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo: There is no need
    public void replaceProductBySerialNumber(String serialNumber, Product product) {
        // todo: handle null itr.next()

        Iterator<Product> itr = iterator();
        long prev;
        long curr;
        readPos = 0;
        while(itr.hasNext()){
            try {
                prev = readPos;
                if(serialNumber.equals(itr.next().getSerialNumber())) {
                    curr = raf.getFilePointer();

                    byte[] byteProd = Product.serialize(product);
                    int byteProdInt = byteProd.length;

                    byte[] restOfData = new byte[(int) (fileSize -
                            (curr)
                    )];

                    raf.read(restOfData); // read rest of data

                    raf.seek(prev); // move to prev

                    // write new prod
                    raf.writeInt(byteProdInt);
                    raf.write(byteProd);

                    // write rest of data
                    raf.write(restOfData);

//                    writePos = raf.getFilePointer();
                    fileSize = raf.getFilePointer();

                    break; // todo: add boolean

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class BinaryFileIterator implements Iterator<Product> {
        long read = 0;
        long prevRead;

        @Override
        public boolean hasNext() {
            return read < fileSize;
        }

        @Override
        public void remove() {
            if(read == 0)
                new IllegalInputException("Can't remove Product that hasn't being read!")
                        .showErrorMessage();
            else if(prevRead == read)
                new IllegalInputException("Can't remove twice the same object!")
                        .showErrorMessage();
            else if (fileSize < read)
                new IllegalInputException("Can't remove from an empty file!")
                        .showErrorMessage();
            else {
                try {
                    raf.seek(read);

                    // rest of file except for a product
                    byte[] data = new byte[(int) (fileSize -
                            (read)
                    )];

                    raf.read(data);
                    raf.setLength(prevRead);
                    raf.write(data);

                    read = prevRead;
                    readPos = read;
                    fileSize = raf.length();

                } catch (IOException e) {
                    // todo: error in removing
                    e.printStackTrace();
                }
            }
        }

        @Override
        public Product next() {
            Product result = null; // todo: indicates to print message to user
            if(!hasNext()) {
                new IllegalInputException("You are in the end of the iterator!")
                        .showErrorMessage();
            }
            else{
                try {
                    prevRead = read;
                    raf.seek(read);
                    int byteSize = raf.readInt();
                    byte[] arr = new byte[byteSize];
                    raf.read(arr);
                    Product product = Product.deserialize(arr);
                    read = raf.getFilePointer();
                    readPos = read;

                    result = product;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }
    }
}