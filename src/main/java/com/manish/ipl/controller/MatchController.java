package com.manish.ipl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.ipl.model.Match;
import com.manish.ipl.service.MatchService;


/**
 * @author Manish
 * This controller class exposes api for Match Service.
 */
@RestController
@RequestMapping("/match")
public class MatchController {
	
	public static final Logger logger = LoggerFactory.getLogger(MatchController.class);
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	JobOperator jobOperator;
	
	@Autowired
	Job importMatchJob;
	
	@Autowired
	ApplicationContext applicationContext;

	@PostMapping(path = "/runMatchJob")
	public String launchMatchJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> paramMap = new HashMap<>();
//		paramMap.put("timeStamp", new JobParameter(System.currentTimeMillis()));
		paramMap.put("FileName", new JobParameter("MatchData.csv"));
		JobParameters jobParam = new JobParameters(paramMap);
		JobExecution jobExecution =jobLauncher.run(importMatchJob, jobParam);
//		Long executionId = jobExecution.getId();
		
//		applicationContext.me
//		JobExecution jobExecution =jobLauncher.run(importMatchJob, jobParam);
//		JobInstance ji = new JobInstance(null, null);	
		StepExecution se = new StepExecution("step1", jobExecution);
		System.out.println("Status of the step execution is - "+se.getStatus());
		System.out.println("Step execution properties - "+se.getReadCount() +", "+
		se.getWriteCount()+ ", "+se.getCommitCount()+", "+se.getRollbackCount()+", "+se.getFilterCount());
		return jobExecution.getStatus().getBatchStatus().toString();
	}
	
	/**
	 * @param city
	 * @return List of Matches
	 * 
	 * This method fetches all the matches played in a city.
	 */
	@GetMapping(path = "/getMatches/{city}")
	public ResponseEntity<List<Match>> getMatches(@Valid @PathVariable("city") final String city ){
		
//		Boolean isCityPresent = matchService.getAllCities().contains(city);
//		if(isCityPresent) {
		logger.info("info- Getting details for city: "+ city);
		logger.debug("debug- Getting details for city: "+ city);
		logger.error("error- Getting details for city: "+ city);
		logger.trace("error- Getting details for city: "+ city);
		
		List<Match> matchList = matchService.getMatchesInCity(city);
		return new ResponseEntity<List<Match>>(matchList, HttpStatus.OK);
	}
	@GetMapping(path = "/getAllMatches")
	public List<Match> getAllMatches(){
		
		 return matchService.getAllMatches();
		
	}
	
	/**
	 * @param Match
	 * @return Match
	 * 
	 * This method creates a match record in the table.
	 */
	@PostMapping("/createMatch")
	public Match createMatch(@Valid @RequestBody Match matchRecord) {		
		logger.info("Inside createMatch");
		return matchService.addMatch(matchRecord);
//		return "Match record inserted into the DB";
	}
	
	/**
	 * 
	 * @param Match
	 * @return Match
	 * 
	 * This method updates a match record in the table.
	 */
	@PutMapping("/updateMatch")
	public Match updateMatch(@RequestBody Match matchrecord) {
		return matchService.updateMatchRecord(matchrecord);
	}
	
	@DeleteMapping("/deleteMatch/{id}")
	public Match deleteMatch(@PathVariable("id") final Long id ) {
		return matchService.deleteMatch(id);
	}
}
