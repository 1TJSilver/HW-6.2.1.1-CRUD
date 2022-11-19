package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

// Stub
public class PostRepository {
  static AtomicInteger counter = new AtomicInteger(1);
  final static int NEW_POST = 0;
  List<Post> repository;
  public PostRepository(){
    this.repository = Collections.synchronizedList(new ArrayList<>());
  }
  public List<Post> all() {
    return repository;
  }

  public Optional<Post> getById(long id) {
    return repository.stream()
            .filter(x -> x.getId() == id)
            .findFirst();
  }

  public Post save(Post post) {
    if (post.getId() == NEW_POST){
      post.setId(counter.getAndIncrement());
      repository.add(post);
    } else {
      removeById(post.getId());
      repository.add(post);
    }
    return post;
  }

  public void removeById(long id) {
    Optional<Post> optional = getById(id);
    repository.remove(optional.orElseThrow(NotFoundException::new));
  }
}
