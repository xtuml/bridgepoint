 state SAC::Session.Session_Heartbeat_Failed () is 
the_session_spec : instance of Session_Specification;

begin
   
   // increment the failure count and check if we have exceeded the threshold
   the_session_spec := find_one Session_Specification();
   this.session_heartbeat_failure := this.session_heartbeat_failure + 1;
   if this.session_heartbeat_failure > the_session_spec.session_heartbeat_failure_threshold then
      generate Session.session_timeout_reached() to this;
   else
      // reset the timer
      schedule this.session_heartbeat_timer generate Session.heartbeat_timeout_expired() to this 
                        delay the_session_spec.session_heartbeat_time;
   end if;
   
   
end state;
