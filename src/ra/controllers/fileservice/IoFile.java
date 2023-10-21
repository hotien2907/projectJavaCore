package ra.controllers.fileservice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ra.constant.Contant.FilePath.*;

public class IoFile<T> {
    private File ioFile;

    public IoFile() {
    }

    public IoFile(String filePath) {
        File dataDir = new File(COMMON_PATH);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        this.ioFile = new File(COMMON_PATH + filePath);
        try {
            if (!ioFile.exists()) {
                ioFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi khởi tạo file");
        }
    }

    public void saveToFile(List<T> t) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.ioFile);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(t);
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Có lỗi xảy ra khi ghi file");
        }
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(this.ioFile);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            list = (List<T>) inputStream.readObject();
        } catch (EOFException e) {
            // Bắt lỗi khi đọc hết chương trình
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

}
