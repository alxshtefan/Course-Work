package ua.khnu.shtefanyankovska.util.filecreator;

import ua.khnu.shtefanyankovska.entity.Result;
import ua.khnu.shtefanyankovska.util.MyException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreatorTXT implements Creator {

    @Override
    public File create(List<Result> results, File f) throws MyException {
        try (FileWriter fw = new FileWriter(f)) {

            for (Result r : results) {
                fw.write(r.toString());
            }
            fw.flush();

        } catch (IOException e) {
            throw new MyException("Error while writing txt file.", e);
        }

        return f;
    }

}
