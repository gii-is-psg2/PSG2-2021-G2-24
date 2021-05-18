/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following
 * services provided by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary
 * set up time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning
 * that we don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable,
 * which uses autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is
 * executed in its own transaction, which is automatically rolled back by
 * default. Thus, even if tests insert or otherwise change database state, there
 * is no need for a teardown or cleanup script.
 * <li>An {@link org.springframework.context.ApplicationContext
 * ApplicationContext} is also inherited and can be used for explicit bean
 * lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ReservaServiceTests {

	@Autowired
	protected ReservaService reservaSer;

	@Test
	void shouldFindReservas() {
		Iterable<Reserva> reservaIt = this.reservaSer.findAll();
		List<Reserva> reservas = new ArrayList<Reserva>();

		for (Reserva reserva : reservaIt) {
			reservas.add(reserva);
		}
		Reserva reserva = EntityUtils.getById(reservas, Reserva.class, 1);
		assertThat(reserva.getPet().getName()).isEqualTo("Leo");
		assertEquals(reserva.getRoom().getId(), 1);
		assertThat(reserva.getOwner().getFirstName()).isEqualTo("George");
		assertThat(reserva.getOwner().getLastName()).isEqualTo("Franklin");
		assertEquals(reserva.getId(), 1);
		assertThat(reserva.getStartDate()).isEqualTo("2013-01-01");
		assertThat(reserva.getEndingDate()).isEqualTo("2013-01-03");

	}
	/*
	@Test
	void shouldbebooked() {
		Iterable<Reserva> reservaIt = this.reservaSer.findAll();
		List<Reserva> reservas = new ArrayList<Reserva>();

		for (Reserva reserva : reservaIt) {
			reservas.add(reserva);
		}
		Reserva reserva = EntityUtils.getById(reservas, Reserva.class, 1);
		assertTrue(!reservaSer.alreadyBooked(reserva));
	}*/

	@Test
	void shouldfinduser() {
		Authorities auth = this.reservaSer.getAuthority("admin1");
		assertThat(auth.getUser().getUsername()).isEqualTo("admin1");
	}

	@Test
	void shoulddeletereserva() {
		Reserva reserva = reservaSer.getReservaById(1).get();
		this.reservaSer.delete(reserva);
		for (Reserva res : reservaSer.findAll()) {
			assertNotSame(res.getId(), reserva.getId());
		}
	}

}