package com.manish.ipl.fileoperation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class LocalFIleConfig {
	private String INBOUND_PATH = "C:\\Users\\manish.kumar14\\Documents\\MROM\\POCWork\\testFile\\InputFiles";
	private String OUTBOUND_PATH = "C:\\Users\\manish.kumar14\\Documents\\MROM\\POCWork\\testFile\\OutputFiles";

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job importMatchJob;
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Bean
	public MessageChannel fileInputChannel() {
		return new DirectChannel();
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "30000"))
	public MessageSource<File> fileReadingMessageSource() {
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(new File(INBOUND_PATH));
		source.setFilter(new SimplePatternFileListFilter("*.txt"));
		return source;
	}

	@Bean
	@ServiceActivator(inputChannel = "fileInputChannel")
	public MessageHandler handler() {
		return (m) -> {
			System.out.println(m.getPayload());
			System.out.println("Content of the payload is "+m.getPayload().toString());
			
			System.out.println(applicationContext.containsBeanDefinition("importMatchJob"));
			System.out.println(applicationContext.containsBean("importMatchJob"));
			System.out.println(applicationContext.containsLocalBean("importMatchJob"));
			Map<String, JobParameter> paramMap = new HashMap<>();
//			paramMap.put("timeStamp", new JobParameter(System.currentTimeMillis()));
			paramMap.put("FileName", new JobParameter("MatchData.csv"));
			JobParameters jobParam = new JobParameters(paramMap);
			
//			try {
//				System.out.println(jobRegistry.getJob("importMatchJob").isRestartable());
//			} catch (NoSuchJobException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			JobExecution jobExecution = jobRepository.getLastJobExecution("importMatchJob", jobParam);
			Long jobExecutionId = jobExecution.getJobId();
			System.out.println("Value of JobExecutionId is "+ jobExecutionId);
//			JobExecution jobExecution = null;
			try {
//				JobOperator operator =new Jobo
//				jobExecution = jobLauncher.run(importMatchJob, jobParam);
				jobOperator.restart(jobExecutionId);
			} catch (JobRestartException | JobInstanceAlreadyCompleteException
					| JobParametersInvalidException | NoSuchJobExecutionException | NoSuchJobException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			JobInstance ji = new JobInstance(null, null);	
			StepExecution se = new StepExecution("step1", jobExecution);
			System.out.println("Status of the step execution is - "+se.getStatus());
			System.out.println("Step execution properties - "+se.getReadCount() +", "+
			se.getWriteCount()+ ", "+se.getCommitCount()+", "+se.getRollbackCount()+", "+se.getFilterCount());
//			return jobExecution.getStatus().getBatchStatus().toString();
		};
	}

}
