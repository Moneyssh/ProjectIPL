package com.manish.ipl.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.manish.ipl.model.Match;

@Component
public class MatchJdbcItemWriter implements ItemWriter<Match> {

    private static final String DELETE_MATCH = "DELETE FROM match_table";

    private JdbcTemplate jdbcTemplate;

    public MatchJdbcItemWriter(DataSource dataSource) {
            this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void write(List<? extends Match> items) throws Exception {
//            for(Book item : items) {
//                    int updated = jdbcTemplate.update(DELETE_BOOK,item.getId());                                                           
//            }
    	System.out.println("Step 2 writer");
    	jdbcTemplate.batchUpdate(DELETE_MATCH);
    }
}
