package com.example.cachenow.es.esmapper;

import com.example.cachenow.es.esdomain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, Integer> {
    List<User> findByUsername(String username);
    List<User> findByEmail(String email);
    // 其他自定义查询方法
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findByContactContaining(String contact, Pageable pageable);
}