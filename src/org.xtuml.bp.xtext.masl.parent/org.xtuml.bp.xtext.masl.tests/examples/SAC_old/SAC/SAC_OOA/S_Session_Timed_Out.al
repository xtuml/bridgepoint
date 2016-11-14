//
// Filename: S_Session_Timed_Out.al
//
// Description: 
//
// UK Crown Copyright (c) 2010,2014. All rights reserved.
//
state SAC::Session.Session_Timed_Out () is
the_user : instance of User;

begin
   
   // report that the session has timed out
   cancel this.session_heartbeat_timer;
   this.has_timed_out := true;
   Operator~>report_user_session(this.session_id, this.user_id, this.logon_time,
                                  this.has_timed_out, (this -> R3.Workstation).workstation_hostname, this.login_name);
   
   // check if all the users session are timedout and 
   // if they are mark the user logon as suspect
   the_user := this -> R3.User;
   if (find_one (the_user -> R3.Session)(has_timed_out = false)) = null then
      // report the user login as suspect
      the_user.is_logged_on := suspect;
      Operator~>report_user(the_user.user_id, the_user.login_name, 
                            the_user.user_name, the_user.is_logged_on);
   end if;
   
end state;
