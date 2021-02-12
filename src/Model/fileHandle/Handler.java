package Model.fileHandle;

public class Handler {

    private static String FILE_NAME = "products.txt";
    private String fileName;

    public Handler(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
