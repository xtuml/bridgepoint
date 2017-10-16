-- OOA PARSER VALIDATION FUNCTIONS
.select many s_syncs from instances of S_SYNC where ( "$U_{selected.Descrip:ParserValidateFunction}" == "TRUE" )
.for each s_sync in s_syncs
INSERT INTO S_SYNC VALUES ( ${s_sync.Sync_ID}, ${s_sync.Dom_IDdeprecated}, '${s_sync.Name}', '', '', ${s_sync.DT_ID}, ${s_sync.Suc_Pars} );
.end for
.emit to file "sql/ooa_functions.sql"