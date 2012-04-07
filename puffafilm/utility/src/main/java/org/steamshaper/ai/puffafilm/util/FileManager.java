package org.steamshaper.ai.puffafilm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public static File[] getFilesFromDirectory(String directory)
			throws IOException {
		File dir = new File(directory);
		if (dir.isDirectory()) {
			return dir.listFiles();
		} else {
			throw new IOException(directory + " is not a directory");
		}
	}

	public static File[] getFilesFromDirectory(String directory, String ext)
			throws IOException {
		File fs[] = getFilesFromDirectory(directory);
		List<File> filteredFilesList = new ArrayList<File>();
		for (File file : fs) {
			if (!file.isDirectory()) {
				String filename = file.getName();
				if (filename.endsWith(ext.trim())) {
					filteredFilesList.add(file);
				}
			}
		}
		return filteredFilesList.toArray(new File[0]);
	}

	public static List<String> readFile(File file) throws IOException,
			FileNotFoundException {
		List<String> rowContent = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new FileReader(file));
		String row = br.readLine();
		while (row != null) {
			rowContent.add(row);
			row = br.readLine();
		}
		return rowContent;
	}
}