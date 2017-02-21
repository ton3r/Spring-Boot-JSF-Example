package application;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import application.entity.SystemUser;
import application.repository.SystemUserRepository;
import application.service.SystemUserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

	@Configuration
	static class AccountServiceTestContextConfiguration {
		@Bean
		public SystemUserDetailsService service() {
			return new SystemUserDetailsService();
		}

		@Bean
		public SystemUserRepository accountRepository() {
			return mock(SystemUserRepository.class);
		}
	}

	@Autowired
	private SystemUserRepository repository;

	@Autowired
	private SystemUserDetailsService service;

	@Before
	public void setUp() throws Exception {
		when(repository.findByUser("test")).thenReturn(new SystemUser(1L, "test", "test", "USER"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		UserDetails details = service.loadUserByUsername("test");
		assertNotNull(details);
		assertEquals("test", details.getUsername());
	}

}
