domain tutorial is
	service characteristics();
	service creation_and_navigation();
	service data_sets();
	service string_manipulation();
	service string_literals();
	service find_statements();
	service control_statements();
	service arrays();
	service exceptions();
	service device_output();
	service device_input();
	
	exception my_exception;
	
	object Pilot;
	object Pilot is 
		name: string;
		age: integer;
		NI: integer;
		qualified: Calendar::date_type;
	end;
	
	object Airplane;
	object Airplane is
		model: integer;
	end;
	
	object Wing;
	object Wing is
		span: real;
	end;
	
	object Wheel;
	object Wheel is
		size: real;
	end;
	
	object Airplane_Pilot_Assignment;
	object Airplane_Pilot_Assignment is
		state state_one ();
	end;
	
	type record_structure_type is structure
		int_field: integer;
		real_field: real;
		text_field: string;
		date_field: Calendar::Date;
		time_field: Calendar::Time_of_Day;
		color_field: colour;
	end;
	
	subtype octal_type is integer range(0..7);
	subtype positive is integer;
	type Time_Unit is enum (DAY, HOUR, SECOND, MILLISECOND);
	type colour is enum(blue, red, green);
	type colour_type is enum(blue, red, green);
	type my_int_array_type is array (0..20) of integer;
	type my_int_sub_type_type is integer;
	type nested_structure_type is structure
		id:integer;
	end;
	
	relationship R2 is
		Pilot conditionally foo one Airplane,
		Airplane conditionally bar one Pilot;

	relationship R3 is
		Airplane conditionally foo one Wing,
		Wing  conditionally bar one Airplane;
		
	relationship R7  is
		Wheel conditionally foo one Wing,
		Wing conditionally bar many Wheel;
		
	service Array_passing(a: in my_int_array_type, b: in my_int_array_type);
end;

domain Standard is
	exception constraint_error;
end;

domain Device_IO is
	function create_file(name: in string, overwrite: in boolean);
	function delete_file(name: in string);
	function open(name: in string, access: in AccessType, dev: in device);
	function close(dev: in device);
	function eof(dev: in device) return boolean;
	type AccessType is enum(OUT, IN);
end;

domain Calendar is
	type month_type is enum(JANUARY, FEBRUARY, MARCH, APRIL, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBR, DECEMBER);
	type Date is structure
		 month: month_type;
		 year: integer;
	end;
	type Time_of_Day is structure
	 	dummy: integer; 
	end;
	type date_type is structure 
		month: month_type;
		year: integer;
	end;
	type time_type is structure
		hour: integer;
	end;
	type time_unit_type is enum(SECOND, DAY); 
end;

domain Server is
	type fixed_size_structure_type is structure
	 	i: integer; 
	end;
end;