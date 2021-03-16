package com.bot.someweire.repository;

import com.bot.someweire.model.Cache;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;

public interface CacheRepository extends ReactiveNeo4jRepository<Cache, Long> {

}

