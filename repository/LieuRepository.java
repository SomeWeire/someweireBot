package com.bot.someweire.repository;

import com.bot.someweire.model.Lieu;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LieuRepository extends ReactiveNeo4jRepository<Lieu, Long> {

  Flux<Lieu> findByNameLikeIgnoreCase(String name);

  @Query("MATCH (r:Lieu)-[:LEADS_TO]->(n:Lieu) WHERE id(n) = $id WITH r, id(n) AS __internalNeo4jId__ "
      + "RETURN r{.description, __internalNeo4jId__: id(r), .name, __nodeLabels__: labels(r), .thumb, "
      + "Lieu_IN_REGION_Region: [(r)-[__relationship__:IN_REGION]->(r_regions:Region) | r_regions{.description, __internalNeo4jId__: id(r_regions), .name, __nodeLabels__: labels(r_regions), __relationship__}]}")
  Flux<Lieu> findIncomingRoadsById(Long id);

  Flux<Lieu> findByDescriptionLikeIgnoreCase(String description);

  @Query("MATCH (n:Lieu)<-[:IN_LIEU]-(c:Character) WHERE c.userId=$userId WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{.description, __internalNeo4jId__: id(n), .name, __nodeLabels__: labels(n), .thumb, "
      + "Lieu_IN_LIEU_Cache: [(n)<-[:`IN_LIEU`]-(n_caches:`Cache`) | n_caches{__internalNeo4jId__: id(n_caches), __nodeLabels__: labels(n_caches), "
      + "Cache_IN_CACHE_Enigme: [(n_caches)<-[:`IN_CACHE`]-(n_caches_enigme:`Enigme`) | n_caches_enigme{__internalNeo4jId__: id(n_caches_enigme), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_caches_enigme)}], "
      + "Cache_IN_CACHE_Fragment: [(n_caches)<-[:`IN_CACHE`]-(n_caches_fragments:`Fragment`) | n_caches_fragments{__internalNeo4jId__: id(n_caches_fragments), .quantite, .type, __nodeLabels__: labels(n_caches_fragments)}]}]}")
  Mono<Lieu> findWithCachesByCharacterUserId(String userId);
}