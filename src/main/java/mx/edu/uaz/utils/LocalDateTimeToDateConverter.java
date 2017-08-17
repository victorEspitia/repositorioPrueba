package mx.edu.uaz.utils;


import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//import com.vaadin.data.util.converter.Converter;


public class LocalDateTimeToDateConverter implements Converter<LocalDate, Date> {

	private static final long serialVersionUID = 1L;

	@Override
	public Result<Date> convertToModel(LocalDate value, ValueContext context) {
		try {
			Instant instant = value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date res = Date.from(instant);
			return Result.ok(res);
		} catch (Exception e) {
			return Result.error("Fecha no válida");
		}
	}

	@Override
	public LocalDate convertToPresentation(Date value, ValueContext context) {
		
		if (value!= null){
			Instant instant = Instant.ofEpochMilli(value.getTime());
			LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
			return  res;
		}
		else
			return null;
		
	}

    
}