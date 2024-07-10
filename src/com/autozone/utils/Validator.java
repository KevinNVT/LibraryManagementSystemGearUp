package com.autozone.utils;

import java.lang.reflect.Field;

import com.autozone.annotations.MembersName;
import com.autozone.annotations.NotNull;

public class Validator {
	
	public static void validate(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
			
		for (Field field : fields) {
			field.setAccessible(true);
				
			//Not null validation
			if(field.isAnnotationPresent(NotNull.class)) {
				Object value = field.get(obj);
					
				if(value == null) {
					throw new IllegalArgumentException(field.getName() + "cannot be null");
				}
			}
			
			//Members name validation. Only letters, allows spaces, compound names or multiple surnames
			if(field.isAnnotationPresent(MembersName.class)) {
				String value = (String) field.get(obj);
				
					if(value != null && value.matches("[a-zA-Z ]+")) {
						throw new IllegalArgumentException(field.getName() + "is not a valid entry");
					}
				
				}
			
			//Validates the ISBN contains only numbers and is 13 digits long
			if(field.isAnnotationPresent(MembersName.class)) {
				String value = (String) field.get(obj);
				
					if(value != null && value.matches("[0-9]{13}")) {
						throw new IllegalArgumentException(field.getName() 
								+ "can only contain numbers and needs to be 13 digits long");
				}
			}
		}			
	}	
}
