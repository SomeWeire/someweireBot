package com.bot.someweire.repository;

import com.bot.someweire.model.Region;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;

public interface RegionRepository extends ReactiveNeo4jRepository<Region, Long> {
  Flux<Region> findByNameLikeIgnoreCase(String name);

  Flux<Region> findByDescriptionLikeIgnoreCase(String description);
}
