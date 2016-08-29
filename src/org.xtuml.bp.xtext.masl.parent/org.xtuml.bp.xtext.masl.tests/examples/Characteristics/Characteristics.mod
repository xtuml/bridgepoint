domain Standard is
	exception constraint_error; 
	type record_structure_type is integer;
	type Time_Unit is enum (MILLISECOND, SECOND, MINUTE, HOUR, DAY);
	type octal_type is integer range 0..7;
	
	object Pilot;
	object Pilot is
		age: integer; 
	end;
	service characteristicsExample();
end domain;