package com.belatrix.service;

public interface JobLoggerService {
	
	public int LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception;
	public void setLogToFile(boolean logToFile);
	public void setLogToConsole(boolean logToConsole);
	public void setLogMessage(boolean logMessage);
	public void setLogWarning(boolean logWarning);
	public void setLogError(boolean logError);
	public void setLogToDatabase(boolean logToDatabase);
}
