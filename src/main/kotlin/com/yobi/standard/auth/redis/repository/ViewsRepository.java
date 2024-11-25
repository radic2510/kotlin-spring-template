package com.yobi.standard.auth.redis.repository;


import com.yobi.standard.auth.redis.entity.Views;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewsRepository extends CrudRepository<Views, String> {

}
