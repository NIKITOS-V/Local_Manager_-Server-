package ru.NIKITOS_V.Writers;

import ru.NIKITOS_V.JavaInterfaces.ClassWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassSaver implements ClassWriter {
    public static final String FILE_EXTENSION = "bin";
    private final String dirName;

    public ClassSaver(String dirName){
        this.dirName = dirName;
    }

    @Override
    public void saveObject(Object object, String fileName) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(getFilePath(fileName)))))
        {
            objectOutputStream.writeObject(object);
        }
    }


    public Object openObject(String fileName) throws IOException, ClassNotFoundException {
        Object object;

        try(ObjectInputStream objectInputStream =  new ObjectInputStream(Files.newInputStream(Paths.get(getFilePath(fileName)))))
        {
            object = objectInputStream.readObject();
        }

        return object;
    }

    private String getFilePath(String fileName){
        return String.format(
                "%s/%s.%s",
                this.dirName,
                fileName,
                FILE_EXTENSION
        );
    }
}
