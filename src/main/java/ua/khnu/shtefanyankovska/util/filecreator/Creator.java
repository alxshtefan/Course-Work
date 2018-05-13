package ua.khnu.shtefanyankovska.util.filecreator;

import ua.khnu.shtefanyankovska.entity.Result;
import ua.khnu.shtefanyankovska.util.MyException;

import java.io.File;
import java.util.List;

@FunctionalInterface
public interface Creator {

	public File create(List<Result> results, File f) throws MyException;

}
