package com.manish.ipl.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

public class BatchConfigurerImpl implements BatchConfigurer {

	@Override
	public JobRepository getJobRepository() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlatformTransactionManager getTransactionManager() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobLauncher getJobLauncher() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobExplorer getJobExplorer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
