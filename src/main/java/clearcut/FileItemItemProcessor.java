package clearcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class FileItemItemProcessor implements ItemProcessor<FileItem, FileItem> {

  private static final Logger log = LoggerFactory.getLogger(FileItemItemProcessor.class);

  @Override
  public FileItem process(final FileItem fileItem) throws Exception {
    final String[] values = (fileItem.getValues());
    for( int i = 0; i < values.length; i ++ )
      values[ i ] = change(values[ i ]);

    final FileItem transformedFileItem = new FileItem(values);
    log.info("\nConverting (" + fileItem + ") into (" + transformedFileItem + ")");

    return transformedFileItem;
  }

  private String change( String value ) {
    return value.toUpperCase();
  }

}
