package org.steamshaper.ai.runtime.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class DataLoader extends AService {

	private boolean readFromArgs = false;
	private int argPosition = 0;

	private boolean useUserDir = false;

	private String fileName = null;

	private List<String> data = null;

	@Override
	public void run() {
		String filePath = "";
		if (isReadFromArgs()) {
			filePath = Help.me.getArgAtIndex(getArgPosition());
		} else {
		filePath = this.getFileName();
		}
		if (filePath ==null ||"".equals(filePath)) {
			log.error("File name is mandatory!");
		}
		if(isUseUserDir()){
			filePath = System.getProperty("user.dir")+"/"+filePath;
		}
		File file = new File(filePath);
		if(file.exists()&&file.canRead()){
			data = readFile(file);
			log.info("READED  ["+data.size()+"] from ["+file.getAbsolutePath()+"]");
		}else{
			log.error("File ["+file.getAbsolutePath()+"] don't exist or is not readable ");
		}

	}

	private List<String> readFile(File file) {
		List<String> list = new ArrayList<String>();
		try {
		    BufferedReader in = new BufferedReader(new FileReader(file));
		    String str;
		    while ((str = in.readLine()) != null) {
		    	log.debug("READ FROM FILE: ["+ str+"]");
		    	list.add(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
		return list;
	}

	private boolean isUseUserDir() {
		return useUserDir;
	}

	public void setUseUserDir(boolean useUserDir) {
		this.useUserDir = useUserDir;
	}

	private boolean isReadFromArgs() {
		return readFromArgs;
	}

	public void setReadFromArgs(boolean readFromArgs) {
		this.readFromArgs = readFromArgs;
	}

	private int getArgPosition() {
		return argPosition;
	}

	public void setArgPosition(int argPosition) {
		this.argPosition = argPosition;
	}

	private String getFileName() {
		return fileName;
	}

	public void setFileName(String filePath) {
		this.fileName = filePath;
	}

	public List<String> getData() {
		return data;
	}

}
