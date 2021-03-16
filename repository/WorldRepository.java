package com.bot.someweire.repository;

import com.bot.someweire.model.World;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WorldRepository extends ReactiveNeo4jRepository<World, Long> {

  Flux<World> findByNameLikeIgnoreCase(String name);

  Flux<World> findByDescriptionLikeIgnoreCase(String description);

//  @Query("MATCH (n:`Region`)-[:IN_WORLD]->(w:`World`) WHERE id(n)=$regionId WITH w, id(w) AS __internalNeo4jId__ "
//      + "RETURN w{.description, __internalNeo4jId__: id(w), .name, __nodeLabels__: labels(w)}")
//  Mono<World> getByRegion(Long regionId);

  @Query("MATCH (r:`Region`)-[:IN_WORLD]->(n:`World`) WHERE id(r)=$regionId WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{.description, __internalNeo4jId__: id(n), .name, __nodeLabels__: labels(n), "
      + "World_IN_WORLD_Region:[(n)<-[__relationship__:IN_WORLD]-(n_regions:Region) | n_regions{.description, __internalNeo4jId__: id(n_regions), .name, __nodeLabels__: labels(n_regions), "
      + "Region_IN_REGION_Lieu: [(n_regions)<-[__relationship__:`IN_REGION`]-(n_regions_lieux:Lieu) | n_regions_lieux{.thumb, .description, __internalNeo4jId__: id(n_regions_lieux), .name, __nodeLabels__: labels(n_regions_lieux), __relationship__}], __relationship__}]"
      + "}")
  Mono<World> getByRegion(Long regionId);

  @Query("MATCH (l:`Lieu`)-[:IN_REGION]->(`Region`)-[:IN_WORLD]->(w:`World`) WHERE id(l)=$lieuId WITH w, id(w) AS __internalNeo4jId__ "
      + "RETURN w{.description, __internalNeo4jId__: id(w), .name, __nodeLabels__: labels(w)}")
  Mono<World> getByLieu(Long lieuId);

  @Query("MATCH (w:World)<-[:IN_WORLD]-(r:Region)<-[:IN_REGION]-(l:Lieu)<-[:IN_LIEU]-(c:Character) WHERE c.userId=$userId return w")
  Mono<World> getByUser(String userId);
}
