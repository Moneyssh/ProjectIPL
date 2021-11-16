package com.manish.ipl.batch;



import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.manish.ipl.config.JpaConfig;
import com.manish.ipl.data.MatchInput;
import com.manish.ipl.model.Match;
/**
 * 
 *
 *This is a configuration class for the batch job.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private final String[] FIELD_NAMES = { "id", "city", "date", "player_of_match", "venue", "neutral_venue", "team1",
			"team2", "toss_winner", "toss_decision", "winner", "result", "result_margin", "eliminator", "method",
			"umpire1", "umpire2" };

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobRegistry jobRegistry;

	
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
	    JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
	    postProcessor.setJobRegistry(jobRegistry);
	    return postProcessor;
	}
//	@Autowired
//	public JpaConfig jpaConfig;
	@Bean
	@StepScope
	public FlatFileItemReader<MatchInput> reader() {
		return new FlatFileItemReaderBuilder<MatchInput>().name("MatchDataReader")
				.resource(new ClassPathResource("MatchData.csv")).delimited().names(FIELD_NAMES)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
					{
						setTargetType(MatchInput.class);
					}
				}).build();
	}

	@Bean
	@StepScope
	public MatchDataProcessor processor() {
		return new MatchDataProcessor();
	}
//TODO-Use JPA writer and try to implement transactions
	@Bean("JdbcBatchItemWriter1")
	public JdbcBatchItemWriter<Match> writer1(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Match>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO match_table (id, city, match_date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, match_result, result_margin, umpire1, umpire2) VALUES (:id, :city, :matchDate, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :matchResult, :resultMargin, :umpire1, :umpire2)")
				.dataSource(dataSource).build();
	}
	
	@Bean("JdbcBatchItemWriter3")
	public JdbcBatchItemWriter<Match> writer3(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Match>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO match_table (id, city, match_date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, match_result, result_margin, umpire1, umpire2) VALUES (:id, :city, :matchDate, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :matchResult, :resultMargin, :umpire1, :umpire2)")
				.dataSource(dataSource).build();
	}
	
//	@Bean("JdbcBatchItemWriter2")
//	public JdbcBatchItemWriter<Match> writer2(DataSource dataSource) {
//		return new JdbcBatchItemWriterBuilder<Match>()
////				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
////				.sql("INSERT INTO match_table (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)")
//				.sql("DELETE FROM match_table")
//				.dataSource(dataSource).build();
//	}
//	@Bean
//	public JpaItemWriter<Match> jpaWriting(){
//		JpaItemWriter<Match> jpaMatchWriter = new JpaItemWriter<>();
//		jpaMatchWriter.setEntityManagerFactory(jpaConfig.entityManagerFactory());
//		return jpaMatchWriter;
//	}
	
	@Bean
	public Job importMatchJob(JobCompletionNotificationListener listener, @Qualifier("step1") Step step1, @Qualifier("step2")  Step step2) {
	  return jobBuilderFactory.get("importMatchJob")
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .start(step1).on("COMPLETED")
	    .stopAndRestart(step2)
//	    .start(new Decider()).on("START").to(step2)
//	    .flow(step1)
//	    .start(step1)
	    .end()
	    .build();
	}

	@Bean("step2")
	public Step step2(@Qualifier("JdbcBatchItemWriter1") JdbcBatchItemWriter<Match> writer) {
//	public Step step1(JpaItemWriter<Match> writer) {
		
	  return stepBuilderFactory.get("step1")
	    .<MatchInput, Match> chunk(10)
	    .reader(reader())
	    .processor(processor())
	    .writer(writer)
	    .build();
	}
	
	@Bean("step1")
	public Step step1(MatchJdbcItemWriter writer) {
//	public Step step1(JpaItemWriter<Match> writer) {
		
	  return stepBuilderFactory.get("step2")
	    .<MatchInput, Match> chunk(10)
	    .reader(reader())
//	    .processor(processor())
	    .writer(writer)
	    .build();
	}
}
