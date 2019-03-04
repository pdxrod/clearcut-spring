package clearcut;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  public static int CHUNK_SIZE = 5;
  private Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  // tag::readerwriterprocessor[]
  @Bean
  public FlatFileItemReader<FileItem> reader() {
          return new FlatFileItemReaderBuilder<FileItem>()
                 .name("FileItemItemReader")
                 .resource(new ClassPathResource("folders.dat"))
                 .delimited()
                 .names(new String[] {"mode", "links", "owner", "grp", "size", "month", "day", "time", "name", "vals"})
                 .fieldSetMapper(new BeanWrapperFieldSetMapper<FileItem>() {{
                          setTargetType(FileItem.class);
                  }})
                 .build();
  }

  @Bean
  public FileItemItemProcessor processor() {
          return new FileItemItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<FileItem> writer(DataSource dataSource) {
          return new JdbcBatchItemWriterBuilder<FileItem>()
                 .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                 .sql("INSERT INTO FILE_ITEM (mode, links, owner, grp, size, month, day, time, name, vals) " +
                           " VALUES (:mode, :links, :owner, :grp, :size, :month, :day, :time, :name, vals)")
                 .dataSource(dataSource)
                 .build();
  }
  // end::readerwriterprocessor[]

  // tag::jobstep[]
  @Bean
  public Job importFileItemJob(JobCompletionNotificationListener listener, Step step1) {
          log.debug( "importFileItemJob" );
          return jobBuilderFactory.get("importFileItemJob")
                 .incrementer(new RunIdIncrementer())
                 .listener(listener)
                 .flow(step1)
                 .end()
                 .build();
  }

  @Bean
  public Step step1(JdbcBatchItemWriter<FileItem> writer) {
          return stepBuilderFactory.get("step1")
                 .<FileItem, FileItem> chunk( CHUNK_SIZE )
                 .reader(reader())
                 .processor(processor())
                 .writer(writer)
                 .build();
  }
  // end::jobstep[]
}
