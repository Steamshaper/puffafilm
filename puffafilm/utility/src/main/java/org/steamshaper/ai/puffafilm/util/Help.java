package org.steamshaper.ai.puffafilm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;

public class Help {

	public final static Help me = new Help();

	private ApplicationContext context = null;
	private Neo4jTemplate cachedNeo4jTemplate = null;

	private WrappingNeoServerBootstrapper n4jWebAdmin = null;

	private Help() {

	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public Neo4jTemplate getNeo4jTemplate() {
		if (cachedNeo4jTemplate == null) {
			cachedNeo4jTemplate = context.getBean(Neo4jTemplate.class);
		}
		return cachedNeo4jTemplate;
	}

	public void toCleanN4JDatabase() {
		Neo4jHelper.cleanDb(this.getNeo4jTemplate());
	}

	public void toStartN4jWebAdmin() {

		n4jWebAdmin = new WrappingNeoServerBootstrapper(Help.me.getContext()
				.getBean(EmbeddedGraphDatabase.class));
		n4jWebAdmin.start();
		n4jWebAdmin.getServer();
	}

	public void toStopN4jWebAdmin() {
		if (n4jWebAdmin != null) {
			n4jWebAdmin.stop();
			n4jWebAdmin = null;
		}
	}

	public void toDeleteGraphDBFiles() {
		EmbeddedGraphDatabase egd = getContext().getBean(
				EmbeddedGraphDatabase.class);
		egd.shutdown();

		// Clear all database files
		File dbDir = new File(egd.getStoreDir());

		deleteAllFileIn(dbDir);
	}

	public void toDeleteAllFileInAFolder(String folderPath) {

		// Clear all database files
		File dir = new File(folderPath);
		if (dir.exists()) {
			deleteAllFileIn(dir);
		}

	}

	public Transaction toStartTransaction() {
		return Help.me.getNeo4jTemplate().beginTx();
	}

	public <T> T fetch(T entity) {
		return me.getNeo4jTemplate().fetch(entity);

	}

	long lastFlush = 0;

	public void flushSaveBuffer() {
		long start = System.currentTimeMillis();
		lastFlush = start;
		Transaction tx = me.toStartTransaction();
		boolean haveATimeoutWarning = false;
		try {
			duplicateCheck.clear();
			while (counter > 0 && !haveATimeoutWarning) {
				me.getNeo4jTemplate().save(saveBuffer[--counter]);
				saveBuffer[counter] = null;
				if ((System.currentTimeMillis() - start) > 10000) {
					System.err.println("Committing transaction due timeout!");
					haveATimeoutWarning = true;
				}
			}
			tx.success();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			tx.failure();
		} finally {
			tx.finish();
		}
	}

	private Object[] saveBuffer = new Object[200];
	private HashSet<Object> duplicateCheck = new HashSet<Object>(
			saveBuffer.length);
	int counter = 0;

	public void saveNode(Object node) {
		boolean isDuplicate = duplicateCheck.contains(node);
		if (!isDuplicate) {
			if (counter < saveBuffer.length) {
				saveBuffer[counter++] = node;
				duplicateCheck.add(node);
			}
			if (counter == saveBuffer.length) {
				this.flushSaveBuffer();
			}
			if (isRunningOnLowMemory()) {
				System.err.println("Low memory CATCHED cleaning garbage!");
				System.gc();
				flushSaveBuffer();
				System.gc();
			}
		} else {
			// System.err.println("Found duplicate! SKIP");
		}
		printStatus();
	}

	long timer = System.currentTimeMillis();

	private void printStatus() {
		long now = System.currentTimeMillis();
		if ((now - timer) > 5000) {
			timer = now;
			System.err.println("Free Memory ["
					+ (Runtime.getRuntime().freeMemory() / 1048576)
					+ "] MB object in buffer [" + counter
					+ "] last  buffer flush ["
					+ ((System.currentTimeMillis() - lastFlush) / 1000)
					+ "] secs");
		}
	}

	public boolean isRunningOnLowMemory() {
		if ((Runtime.getRuntime().freeMemory() * 100 / Runtime.getRuntime()
				.maxMemory()) <= 5) {
			System.err.println("Low Memory warning!!!");
			return true;
		}
		return false;
	}

	private void deleteAllFileIn(File directory) {
		// Get all BCS files
		File[] fLogs = directory.listFiles();

		// Delete all files
		for (int i = 0; i < fLogs.length; i++) {
			if (fLogs[i].isDirectory()) {
				deleteAllFileIn(fLogs[i]);
			} else {

				fLogs[i].delete();
			}
		}
		directory.delete();

	}

	public void toShutdownNeo4jDB() {
		me.getContext().getBean(EmbeddedGraphDatabase.class).shutdown();

	}

	private String[] argsHolder;

	public void storeArgs(String[] args) {
		argsHolder = args;

	}

	public String getArgAtIndex(int index) {
		if (index >= argsHolder.length) {
			return null;
		}
		return argsHolder[index];
	}

	public void toStoreOutput(String destination, List<String> content)
			throws IOException {
		StringBuilder out = new StringBuilder();
		for (String row : content) {
			out.append(row + "\r\n");
		}
		File outputFile = new File(destination);
		FileOutputStream fos = new FileOutputStream(outputFile);
		fos.write(out.toString().getBytes());
		fos.flush();
		fos.close();
	}

	public boolean existArgForName(String argsName) {
		if(argsName==null||"".equals(argsName)){
			return false;
		}
		for(String arg : argsHolder){
			if(argsName.equals(arg)){
				return true;
			}
		}
		return false;

	}

}
