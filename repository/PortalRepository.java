package com.bot.someweire.repository;

import com.bot.someweire.model.Portail;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;

public interface PortalRepository extends ReactiveNeo4jRepository<Portail, Long> {

}
