package com.bot.someweire;

import com.bot.someweire.core.DiscordBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:world.properties")
@PropertySource("classpath:utils.properties")
@PropertySource("classpath:cache.properties")
@PropertySource("classpath:player.properties")
@PropertySource("classpath:discord.properties")
@SpringBootApplication
public class SomeweireBotApplication implements CommandLineRunner {

	@Autowired
	private DiscordBot discordBot;

	public SomeweireBotApplication () {
	}

	public static void main(String[] args) {
		SpringApplication.run(SomeweireBotApplication.class, args);
	}

	@Override
	public void run (String... args) throws Exception {
		discordBot.startBot();
	}
}