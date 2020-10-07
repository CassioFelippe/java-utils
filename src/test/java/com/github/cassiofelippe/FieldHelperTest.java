package com.github.cassiofelippe;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

import com.github.cassiofelippe.FieldHelper;

public class FieldHelperTest {

	final FieldHelper helper = new FieldHelper();

	final Instrument instrument = new Instrument(Type.Guitar, "Fender Stratocaster");
	final Musician e = new Musician("Eric", 1945 - now().getYear(), LocalDate.of(2020, 4, 1), of(2020, 4, 1, 17, 2, 30), instrument);

	@Test
    public void testGetFieldValue() throws Exception {
        assertEquals(e.name, helper.getFieldValue(e, "name"));
        assertEquals(e.age, helper.getFieldValue(e, "age"));
        assertEquals(e.created, helper.getFieldValue(e, "created"));
        assertEquals(e.lastUpdated, helper.getFieldValue(e, "lastUpdated"));
    }
	
	@Test
	public void testGetRecursiveFieldValue() throws Exception {
		assertEquals(e.instrument.type, helper.getFieldValue(e, "instrument.type"));
		assertEquals(e.instrument.model, helper.getFieldValue(e, "instrument.model"));
	}
	
	@Test
	public void testGetPrivateFieldValue() throws Exception {
		assertEquals(e.getId(), helper.getFieldValue(e, "id"));
	}
	
	@Test
	public void testSetFieldValue() throws Exception {
		final Musician m = new Musician();
		assertEquals(m.getId(), helper.getFieldValue(m, "id"));
		helper.setFieldValue(m, "id", 1730L);
		assertEquals(m.getId(), Long.valueOf(1730L));
	}
}

class Musician {
	private Long id;
	public String name;
	public Integer age;
	public LocalDate created;
	public LocalDateTime lastUpdated;
	public Instrument instrument;
	
	public Musician() {
		id = now().toEpochSecond(ZoneOffset.UTC);
	}
	
	public Musician(
		final String name,
		final Integer age,
		final LocalDate created,
		final LocalDateTime lastUpdated,
		final Instrument instrument
	) {
		this();
		this.name = name;
		this.age = age;
		this.created = created;
		this.lastUpdated = lastUpdated;
		this.instrument = instrument;
	}
	
	public Long getId() {
		return id;
	}
}

class Instrument {
	public Type type;
	public String model;
	
	public Instrument(final Type type, final String model) {
		this.type = type;
		this.model = model;
	}
}

enum Type {
	Guitar,
	Bass,
	Drums,
	Vocals
}
