package com.belatrix.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.belatrix.repository.LogRepository;
import com.belatrix.service.JobLoggerService;

@Service("jobLoggerService")
public class JobLoggerServiceImpl implements JobLoggerService{
	
	@Value("${joblogger.logToFile}")
	private boolean logToFile;
	
	@Value("${joblogger.logToConsole}")
	private boolean logToConsole;
	
	@Value("${joblogger.logMessage}")
	private boolean logMessage;
	
	@Value("${joblogger.logWarning}")
	private boolean logWarning;
	
	@Value("${joblogger.logError}")
	private boolean logError;
	
	@Value("${joblogger.logToDatabase}")
	private boolean logToDatabase;
	
	Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;
    
    @Autowired
	@Qualifier("logRepository")
	private LogRepository logRepository;
		
	@Override
	public void setLogToFile(boolean logToFile) {this.logToFile = logToFile;}
	@Override
	public void setLogToConsole(boolean logToConsole) {this.logToConsole = logToConsole;}
	@Override
	public void setLogMessage(boolean logMessage) {this.logMessage = logMessage;}
	@Override
	public void setLogWarning(boolean logWarning) {this.logWarning = logWarning;}
	@Override
	public void setLogError(boolean logError) {this.logError = logError;}
	@Override
	public void setLogToDatabase(boolean logToDatabase) {this.logToDatabase = logToDatabase;}


	@Override
	public int LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
		
		int initialized = 0;
		if (messageText != null && messageText.trim().length() == 0) {
			return initialized;
		}
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		File folder = null;
		FileHandler fh = null;
		ConsoleHandler ch = null;
		int limit = 10000000; // 10 Mb
		
		

		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		try {

			folder = new File("logs");
			if (!folder.exists()) {
				folder.mkdir();
			}

			ch = new ConsoleHandler();
			fh = new FileHandler(folder.getAbsolutePath() + "//Log_" + format.format(Calendar.getInstance().getTime()) + ".txt",limit, 1, true);
			
			
			Formatter formatter = new Formatter() {
				public String format(LogRecord record) {
					StringBuilder s = new StringBuilder();
					SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    
					s.append(logTime.format(cal.getTime())).append(' ');
					
					if (record.getLoggerName() != null) {
						s.append('[');
						s.append(record.getLoggerName());
						s.append("]: ");
					}
					if (record.getSourceClassName() != null) {
						if (record.getLevel().equals(Level.FINEST)) {
							String className = record.getSourceClassName();
							s.append(className.substring(Math.max(className.lastIndexOf('.') + 1, 0)));
						} else {
							s.append(record.getSourceClassName());
						}
					}
					if (record.getSourceMethodName() != null) {
						s.append(" | ");
						s.append(record.getSourceMethodName());
						s.append('(');
						Object[] parms = record.getParameters();
						
						if (parms != null) {
							for (int i = 0; i < parms.length; i++) {
								if (i != 0) {
									s.append(", ");
								}
								s.append(parms[i]);
							}
						}
						s.append(')');
					}
					if (record.getThrown() != null) {
						s.append(" | ");
						s.append(record.getThrown());
					}
					if (record.getMessage() != null) {
						s.append(": ");
						s.append(record.getMessage());
					}
					
					s.toString();
					String msg = "";
					
					if (error && logError) {
						msg+="ERROR: \t "+s.toString()+"\n";
            		}
            		if (warning && logWarning) {
            			msg+="WARNING: "+s.toString()+"\n";
            		}
            		if (message && logMessage) {
            			msg+="MESSAGE: "+s.toString()+"\n";
            		}
            		
            		if (logToDatabase) {
        				int t = 0;
        				if (message && logMessage) t = 1;
        				if (error && logError) t = 2;
        				if (warning && logWarning) t = 3;
        				
        				com.belatrix.entity.Log Log = new com.belatrix.entity.Log(new Date(),msg,message, t);
        				logRepository.save(Log);
        				
        			}

					return msg;
				}
			};
			
			ch.setFormatter(formatter);
			fh.setFormatter(formatter);
			logger.setUseParentHandlers(false);
			
			if (logToFile) {
				logger.addHandler(fh);
				logger.log(Level.INFO, messageText);
				fh.close();
				logger.removeHandler(fh);
				initialized = 1;
			}

			if (logToConsole) {
				logger.addHandler(ch);
				logger.log(Level.INFO, messageText);
				initialized = 1;
			}


		} catch (Exception e) {
			initialized = 0;
		}
		finally {
			ch.close();
			logger.removeHandler(ch);
		}
		return initialized;
		
		
		
		
		
		
	}

}
