package clearcut;

import org.springframework.data.repository.CrudRepository;

import clearcut.FileItem;

public interface FileItemRepository extends CrudRepository<FileItem, Integer> {

}
