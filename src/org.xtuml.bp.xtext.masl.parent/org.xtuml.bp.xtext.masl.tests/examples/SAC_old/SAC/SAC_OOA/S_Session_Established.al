//
// Filename: S_Session_Established.al
//
// Description: 
//
// UK Crown Copyright (c) 2010,2014. All rights reserved.
//
state SAC::Session.Session_Established () is
the_session_spec : instance of Session_Specification;
the_user : instance of User;

begin
   
   // report the session details and setup the heartbeat timer
   this.session_heartbeat_failure := 0;
   the_user := this -> R3.User;
   if the_user.is_logged_on = suspect then
      the_user.is_logged_on := logged_on;
      Operator~>report_user(the_user.user_id, the_user.login_name, the_user.user_name, the_user.is_logged_on);
   end if;
   if this.has_timed_out = true then
      this.has_timed_out := false;
      Operator~>report_user_session(this.session_id, this.user_id, this.logon_time,
                                     this.has_timed_out, (this -> R3.Workstation).workstation_hostname, the_user.login_name);
   end if;
   the_session_spec := find_one Session_Specification();
   schedule this.session_heartbeat_timer generate Session.heartbeat_timeout_expired() to this 
                        delay the_session_spec.session_heartbeat_time;

end state;
