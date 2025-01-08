package Interfaces;

import java.io.IOException;

public interface ClassWriter {
    Object openObject(String fileName) throws IOException, ClassNotFoundException;
    void saveObject(Object object, String fileName) throws IOException;
}
