//package com.manish.ipl.fileoperation;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.configuration.JobLocator;
//import org.springframework.batch.core.configuration.JobRegistry;
//import org.springframework.batch.core.explore.JobExplorer;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.JobOperator;
//import org.springframework.batch.core.launch.NoSuchJobException;
//import org.springframework.batch.core.launch.NoSuchJobExecutionException;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.InboundChannelAdapter;
//import org.springframework.integration.annotation.Poller;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.core.MessageSource;
//import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
//import org.springframework.integration.file.remote.session.CachingSessionFactory;
//import org.springframework.integration.file.remote.session.SessionFactory;
//import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
//import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
//import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
//import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
//import org.springframework.messaging.MessageHandler;
//
//import com.jcraft.jsch.ChannelSftp.LsEntry;
//
//@Configuration
//public class SFTPInboundFileTransfer {
//	
//	@Autowired
//	private JobLauncher jobLauncher;
//	
//	@Autowired
//	private Job importMatchJob;
//	
//	@Autowired
//	private JobRepository jobRepository;
//	
//	@Autowired
//	private JobOperator jobOperator;
//	
//	@Autowired
//	private JobExplorer jobExplorer;
//	
//	@Autowired
//	private ApplicationContext applicationContext;
//	
//	@Autowired
//	private JobRegistry jobRegistry;
//	
//	private int port = 22;
//	private String host = "10.36.77.94";
//	private String user = "manish.kumar1";
//	private String password = "g4Af~#b34JA%";
//	private String remoteDirectory = "Test";
//
//	@Bean
//	public SessionFactory<LsEntry> sftpSessionFactory() {
//		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
//		factory.setHost(host);
//		factory.setPort(port);
//		factory.setUser(user);
//		factory.setPassword(password);
//		factory.setAllowUnknownKeys(true);
//		return new CachingSessionFactory<LsEntry>(factory);
//	}
//
//	@Bean
//	public SftpInboundFileSynchronizer sftpInboundFileSynchronizer() {
//		SftpInboundFileSynchronizer fileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory());
//		fileSynchronizer.setDeleteRemoteFiles(true);
//		fileSynchronizer.setRemoteDirectory(remoteDirectory);
//		fileSynchronizer.setFilter(new SftpSimplePatternFileListFilter("*.txt"));
//		return fileSynchronizer;
//	}
//
//	@Bean
//	@InboundChannelAdapter(channel = "sftpChannel", poller = @Poller(fixedDelay = "60000"))
//	public MessageSource<File> sftpMessageSource() {
//		SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(
//				sftpInboundFileSynchronizer());
//		source.setLocalDirectory(new File("sftp-inbound"));
//		source.setAutoCreateLocalDirectory(true);
//		source.setLocalFilter(new AcceptOnceFileListFilter<File>());
//		source.setMaxFetchSize(1);
//		return source;
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "sftpChannel")
//	public MessageHandler handler() {
//		return (m) -> {
//			System.out.println(m.getPayload());
//			System.out.println("Content of the payload is "+m.getPayload().toString());
//			
//			System.out.println(applicationContext.containsBeanDefinition("importMatchJob"));
//			System.out.println(applicationContext.containsBean("importMatchJob"));
//			System.out.println(applicationContext.containsLocalBean("importMatchJob"));
//			Map<String, JobParameter> paramMap = new HashMap<>();
////			paramMap.put("timeStamp", new JobParameter(System.currentTimeMillis()));
//			paramMap.put("FileName", new JobParameter("MatchData.csv"));
//			JobParameters jobParam = new JobParameters(paramMap);
//			
////			try {
////				System.out.println(jobRegistry.getJob("importMatchJob").isRestartable());
////			} catch (NoSuchJobException e1) {
////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////			}
//			JobExecution jobExecution = jobRepository.getLastJobExecution("importMatchJob", jobParam);
//			Long jobExecutionId = jobExecution.getJobId();
//			System.out.println("Value of JobExecutionId is "+ jobExecutionId);
////			JobExecution jobExecution = null;
//			try {
////				JobOperator operator =new Jobo
////				jobExecution = jobLauncher.run(importMatchJob, jobParam);
//				jobOperator.restart(jobExecutionId);
//			} catch (JobRestartException | JobInstanceAlreadyCompleteException
//					| JobParametersInvalidException | NoSuchJobExecutionException | NoSuchJobException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
////			JobInstance ji = new JobInstance(null, null);	
//			StepExecution se = new StepExecution("step1", jobExecution);
//			System.out.println("Status of the step execution is - "+se.getStatus());
//			System.out.println("Step execution properties - "+se.getReadCount() +", "+
//			se.getWriteCount()+ ", "+se.getCommitCount()+", "+se.getRollbackCount()+", "+se.getFilterCount());
////			return jobExecution.getStatus().getBatchStatus().toString();
//		};
//	}
//
//}
