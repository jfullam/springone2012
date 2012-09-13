package springone.jsr352.sample.football;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.operations.exception.JobStartException;

import jsr352.tck.utils.ServiceGateway;

import org.apache.commons.io.IOUtils;


public class DemoJob {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties jobParams = new Properties();
			jobParams.put("execution.number", "1");
			jobParams.put("readrecord.fail", "12");
			
			JobOperator jobOp = ServiceGateway.getServices().getJobOperator();
			
			InputStream jobXMLStream = DemoJob.class.getResourceAsStream("/springOneDemoJob.xml");
			String jobXML = IOUtils.toString(jobXMLStream);
			
			jobOp.start(jobXML, jobParams);
		} catch (JobStartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
