package com.bot.someweire.repository;

import com.bot.someweire.model.Indice;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IndiceRepository extends ReactiveNeo4jRepository<Indice, Long> {

  @Query("MATCH (n:`Indice`) WHERE id(n) = $id WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{__internalNeo4jId__: id(n), __nodeLabels__: labels(n), .numero, .titre, "
      + "Indice_TO_ENIGME_Enigme: [(n)-[:TO_ENIGME]->(n_enigme:Enigme) | n_enigme{__internalNeo4jId__: id(n_enigme), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigme)}]}")
  Mono<Indice> findById(Long id);

  @Query("MATCH (n:`Indice`)-[:TO_ENIGME]->(e:Enigme) WHERE id(e) = $enigmeId AND n.numero=$numero WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{__internalNeo4jId__: id(n), __nodeLabels__: labels(n), .numero, .titre, "
      + "Indice_TO_ENIGME_Enigme: [(n)-[:TO_ENIGME]->(n_enigme:Enigme) | n_enigme{__internalNeo4jId__: id(n_enigme), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigme)}]}")
  Mono<Indice> findByEnigmeAndNumero(Long enigmeId, int numero);

  @Query("MATCH (n:`Indice`)-[:TO_ENIGME]->(e:Enigme) WHERE id(e) = $enigmeId WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{__internalNeo4jId__: id(n), __nodeLabels__: labels(n), .numero, .titre, "
      + "Indice_TO_ENIGME_Enigme: [(n)-[:TO_ENIGME]->(n_enigme:Enigme) | n_enigme{__internalNeo4jId__: id(n_enigme), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigme)}]}")
  Flux<Indice> findByEnigme(Long enigmeId);

  @Query("MATCH (c:Character)-[:HAS]->(n:`Indice`)-[:TO_ENIGME]->(e:Enigme) WHERE id(e) = $enigmeId AND c.userId=$userId WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{__internalNeo4jId__: id(n), __nodeLabels__: labels(n), .numero, .titre, "
      + "Indice_TO_ENIGME_Enigme: [(n)-[:TO_ENIGME]->(n_enigme:Enigme) | n_enigme{__internalNeo4jId__: id(n_enigme), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigme)}]}")
  Flux<Indice> findByCharacterAndEnigme(Long enigmeId, String userId);

  @Query("MATCH (c:Character) "
      + "WHERE c.userId=$userId "
      + "MATCH (e:Enigme)<-[:TO_ENIGME]-(i:Indice) "
      + "WHERE id(e)=$enigmeId AND NOT (i)<-[:HAS]-(c) "
      + "CREATE (c)-[:HAS{powered:true}]->(i) WITH i AS newIndices "
      + "MATCH (e)<-[:TO_ENIGME]-(j:Indice)<-[r:HAS]-(c) "
      + "SET r.powered=true, c.forfeitPower=false "
      + "RETURN j, newIndices")
  Flux<Indice> useForfeitPowerByCharacterAndEnigmeHasNotAllIndices(String userId, Long enigmeId);

  @Query("MATCH (e)<-[:TO_ENIGME]-(j:Indice)<-[r:HAS]-(c:Character) "
      + "WHERE c.userId=$userId AND id(e)=$enigmeId "
      + "SET r.powered=true, c.forfeitPower=false "
      + "RETURN j")
  Flux<Indice> useForfeitPowerByCharacterAndEnigmeHasAllIndices(String userId, Long enigmeId);

  @Query("MATCH (e:Enigme)<-[:TO_ENIGME]-(i:Indice)<-[r:HAS]-(n:Character) WHERE n.userId=$userId AND id(e)=$enigmeId DELETE r "
      + "return (i)")
  Flux<Indice> deleteIndicesByCharacterAndEnigme(String userId, Long enigmeId);
}
