package Model.iterator;

import Exceptions.IllegalInputException;
import Model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class FileHandler implements Iterable<Product> {

    private long writePos;
    private long readPos;
    private long fileSize;
    private RandomAccessFile raf;
    String filename;

    public FileHandler(String fileName){
        filename = fileName;
        try {
            raf = new RandomAccessFile(filename, "rw");
            writePos = raf.getFilePointer();
            readPos = writePos;
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

        while(itr.hasNext()) {
            if(serialNumber.equals(itr.next().getSerialNumber())) {
                itr.remove();
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public Iterator<Product> iterator() {
        return new BinaryFileIterator();
    }

/*    public void remove(String serialNumber) throws IOException {
        Iterator<Product> itr = iterator();
        long pos;

        while(itr.hasNext()) {
            pos = raf.getFilePointer();
            Product temp = itr.next();
            if(serialNumber.equals(temp.getSerialNumber())) {
                byte[] data = new byte[(int) (raf.length() - (serialNumber.length()))]; // rest of the file

                raf.read(data); // read rest of the file
                raf.setLength(pos);
                raf.write(data);
            }
        }
    }*/

    public void writeProduct(Product product){
        byte[] temp;
        try {
            temp = Product.serialize(product);

            int productByteLength = temp.length;

            raf.seek(writePos);
            raf.writeInt(productByteLength);
            raf.write(temp);

            fileSize = raf.length(); // resize
            writePos = raf.getFilePointer(); // resize
        } catch (IOException e) {
            // todo: handle exception
            e.printStackTrace();
        }
    }

    public void clearFile() {
        try {
            raf.setLength(0);
            writePos = 0;
            readPos = 0;
            fileSize = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceProductBySerialNumber(String serialNumber, Product product) {
        // todo: handle null itr.next()

        Iterator<Product> itr = iterator();
        while(itr.hasNext()){
            if(serialNumber.equals(itr.next().getSerialNumber())) {
                itr.remove();
                writeProduct(product);
                break;
            }
        }
    }

    private class BinaryFileIterator implements Iterator<Product> {
        long read = readPos;

        @Override
        public boolean hasNext() {
            return read < fileSize;
        }

        @Override
        public void remove() {

            if(fileSize <= read)
                new IllegalInputException("Can't remove from an empty file!")
                        .showErrorMessage();
            else{
                long currentReadPos = read; // save for later

                try {
                    raf.seek(read);
                    int sizeProductByteArr = raf.readInt();

                    // rest of file except for a product
                    byte[] data = new byte[(int) (fileSize -
                            (raf.getFilePointer() + sizeProductByteArr)
                    )];

                    raf.read(data);
                    raf.setLength(currentReadPos);
                    raf.write(data);

                    read = currentReadPos;
                    fileSize = raf.length();

                    if(writePos > fileSize)
                        writePos = fileSize;

                } catch (IOException e) {
                    // todo: error in removing
                    e.printStackTrace();
                }
            }
        }

        @Override
        public Product next() {
            if(!hasNext()) {
                new IllegalInputException("You are in the end of the iterator!")
                        .showErrorMessage();
                return null; // todo: indicates to print message to user
            }

            try {
                raf.seek(read);
                int byteSize = raf.readInt();
                byte[] arr = new byte[byteSize];

                Product product = Product.deserialize(arr);
                read = raf.getFilePointer() + 1;

                return product;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
