package com.example.students.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //traité au démarrage de l'appli
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;//injection du datasource indiqué dans application.properties
	
	//définit où sont les utilisateurs autorisées à accéder à l'appli
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();
		//stock les utilisateurs de l'appli en mémoire pour compte créer en statique pour tester petite appli
//		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
//		auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1234")).roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN");
		
		//jdbc authentication : utilisateurs sont stockés dans une base de données
		auth.jdbcAuthentication()
			.dataSource(dataSource)//indique la base de données utilisée
			.usersByUsernameQuery("SELECT username as principal, password as credentials, active from users where username=?")//permet d'identifier les utilisateurs
			.authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")//permet d'identifier les rôles des utilisateurs
			.passwordEncoder(passwordEncoder);
			//.rolePrefix("ROLE_");
	}
	
	//défini qu'elles sont les requête qui nécessites une authentification
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();//formulaire d'auth par défaut utilisé pour s'authentifier
		//http.formLogin().loginPage("/login");//utilisation d'une page login personalisée pour s'authentifier
		//http.httpBasic(); //formulaire affiché par navigateur web et envoyé par la requête http quand authification nécessaire
		
		http.authorizeRequests().antMatchers("/update**/**", "/delete**/**").hasRole("ADMIN"); //toutes les requêtes commençant par /save suivit de n'importe quoi / n'importe quoi peuvent être réalisé par un utilisateur ayant le rôle admin et étant authentifié
		//http.csrf();// à activer pour lutter contre attaque csrf mais à désactiver si on utilise des tokens type JSON web token pour gérer les attaque csrf
		//http.exceptionHandling().accessDeniedPage("/notAuthorized");//permet de personnaliser le message d'erreur de l'erreur accès non autorisé
		//http.authorizeRequests().anyRequest().permitAll(); //autorise toutes les requêtes à accéder à l'appli
		http.authorizeRequests().antMatchers("/students/**").permitAll(); //autorise tous les utilisateurs peu importe leur rôle à accéder à la route /students/quelque chose
		http.authorizeRequests().anyRequest().authenticated();//toutes les requêtes nécessites authentification. A mettre à la fin de la méthode
	}
	
	@Bean
	//encode les mots de passe
	public PasswordEncoder passwordEncoder( ) {
		return new BCryptPasswordEncoder();
	}
}
