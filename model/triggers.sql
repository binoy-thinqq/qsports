
/**
  Trigger to update bowling scorecard_bowling_entry 
**/


CREATE OR REPLACE FUNCTION fn_bowling_sc_entry_upd() RETURNS TRIGGER AS 
$BODY$
    DECLARE
		
		rec_bowlentry cricket.scorecard_bowling_entry%ROWTYPE;
    BEGIN
	raise info 'Operation: %',TG_OP;
	IF 'INSERT' = TG_OP THEN
		IF NEW.out_type = 1 		--BOWLED
			OR NEW.out_type = 7 	--LEG_BEFORE_WICKET
			OR NEW.out_type = 6 	--HIT_WICKET
			OR NEW.out_type = 3 	--CAUGHT
			THEN
			--NEW.bowler_fielder -- bowler id
			--select bowling_entry_id,overs,runs,wickets from cricket.scorecard_bowling_entry where innings_id = and match_team_player_id
			BEGIN
				select 
					* 
				into 
					rec_bowlentry 
				from  
					cricket.scorecard_bowling_entry 
				where 
					innings_id = NEW.innings_id 
					and match_team_player_id = NEW.bowler_fielder;
				IF rec_bowlentry.bowling_entry_id is not null then 
					update 
						cricket.scorecard_bowling_entry
					set
						wickets = COALESCE(wickets,0)+1,
						created_time = now(),
						updated_time = now(),
						updated_id = NEW.updated_id 
					where 
						innings_id = NEW.innings_id 
						and match_team_player_id = NEW.bowler_fielder;
				ELSE
					
					insert into cricket.scorecard_bowling_entry(
					 bowling_entry_id,     
					 match_team_player_id, 
					 innings_id,           
					 overs,                 
					 runs,                  
					 wickets,               
					 no_balls,             
					 wide,                 
					 maiden,               
					 created_id,           
					 created_time,         
					 updated_id,           
					 updated_time        
					)
					values(
					 nextval('cricket.scorecard_bowling_entry_seq'),
					 NEW.bowler_fielder,
					 NEW.innings_id,
					 0,
					 0,
					 1,
					 0,
					 0,
					 0,
					 NEW.created_id,
					 now(),
					 NEW.updated_id,
					 now()					
					);
				END IF;	
				
			EXCEPTION 
				WHEN NO_DATA_FOUND THEN
				
			END; 
		END IF;
	ELSIF 'UPDATE' = TG_OP THEN
									
		IF (NEW.out_type = 1 		--BOWLED
			OR NEW.out_type = 7 	--LEG_BEFORE_WICKET
			OR NEW.out_type = 6 	--HIT_WICKET
			OR NEW.out_type = 3 	--CAUGHT
			)
			AND(
			OLD.out_type = 1 	--BOWLED
			OR OLD.out_type = 7 	--LEG_BEFORE_WICKET
			OR OLD.out_type = 6 	--HIT_WICKET
			OR OLD.out_type = 3 	--CAUGHT
			)
			THEN
			IF OLD.bowler_fielder <> NEW.bowler_fielder THEN
				IF OLD.bowler_fielder IS NOT NULL THEN
					--Decrease or delete bowler
					BEGIN
						select 
							* 
						into 
							rec_bowlentry 
						from  
							cricket.scorecard_bowling_entry 
						where 
							innings_id = OLD.innings_id 
							and match_team_player_id = OLD.bowler_fielder;
	
						IF rec_bowlentry.wickets = 1 and rec_bowlentry.overs <= 1 THEN 
							delete from cricket.scorecard_bowling_entry
							where  innings_id = OLD.innings_id 
								and match_team_player_id = OLD.bowler_fielder;
						ELSE
		 
							update 
								cricket.scorecard_bowling_entry
							set
								wickets = case when COALESCE(wickets,0) > 0 then COALESCE(wickets,0)-1 else 0 end,
								created_time = now(),
								updated_time = now(),
								updated_id = OLD.updated_id 
							where 
								innings_id = OLD.innings_id 
								and match_team_player_id = OLD.bowler_fielder;
						END IF;
	
					EXCEPTION 
						WHEN NO_DATA_FOUND THEN
		
					END;
				END IF;
				IF NEW.bowler_fielder IS NOT NULL THEN
					--Insert or update bowler
					BEGIN
						select 
							* 
						into 
							rec_bowlentry 
						from  
							cricket.scorecard_bowling_entry 
						where 
							innings_id = NEW.innings_id 
							and match_team_player_id = NEW.bowler_fielder;
						
						IF rec_bowlentry.bowling_entry_id is not null then
							update 
								cricket.scorecard_bowling_entry
							set
								wickets = COALESCE(wickets,0)+1,
								created_time = now(),
								updated_time = now(),
								updated_id = NEW.updated_id 
							where 
								innings_id = NEW.innings_id 
								and match_team_player_id = NEW.bowler_fielder;
						ELSE
							insert into cricket.scorecard_bowling_entry(
							 bowling_entry_id,     
							 match_team_player_id, 
							 innings_id,           
							 overs,                 
							 runs,                  
							 wickets,               
							 no_balls,             
							 wide,                 
							 maiden,               
							 created_id,           
							 created_time,         
							 updated_id,           
							 updated_time         
							)
							values(
							 nextval('cricket.scorecard_batting_entry_seq'),
							 NEW.bowler_fielder,
							 NEW.innings_id,
							 0,
							 0,
							 1,
							 0,
							 0,
							 0,
							 NEW.created_id,
							 now(),
							 NEW.updated_id,
							 now()					
							);	
							
						END IF;
				
					EXCEPTION 
						WHEN NO_DATA_FOUND THEN
					END;
				END IF;
			END IF;
		ELSIF (NEW.out_type <> 1 	--BOWLED
			AND NEW.out_type <> 7 	--LEG_BEFORE_WICKET
			AND NEW.out_type <> 6 	--HIT_WICKET
			AND NEW.out_type <> 3 	--CAUGHT
			)
			AND(
			OLD.out_type = 1 	--BOWLED
			OR OLD.out_type = 7 	--LEG_BEFORE_WICKET
			OR OLD.out_type = 6 	--HIT_WICKET
			OR OLD.out_type = 3 	--CAUGHT
			)
			THEN
				IF OLD.bowler_fielder IS NOT NULL THEN
					--Decrease or delete bowler
					BEGIN
						select 
							* 
						into 
							rec_bowlentry 
						from  
							cricket.scorecard_bowling_entry 
						where 
							innings_id = OLD.innings_id 
							and match_team_player_id = OLD.bowler_fielder;
	
						IF rec_bowlentry.wickets = 1 and rec_bowlentry.overs <= 1 THEN 
							delete from cricket.scorecard_bowling_entry
							where  innings_id = OLD.innings_id 
								and match_team_player_id = OLD.bowler_fielder;
						ELSE
		 
							update 
								cricket.scorecard_bowling_entry
							set
								wickets = case when COALESCE(wickets,0) > 0 then COALESCE(wickets,0)-1 else 0 end,
								created_time = now(),
								updated_time = now(),
								updated_id = OLD.updated_id 
							where 
								innings_id = OLD.innings_id 
								and match_team_player_id = OLD.bowler_fielder;
						END IF;
	
					EXCEPTION 
						WHEN NO_DATA_FOUND THEN
		
					END;
				END IF;
		ELSIF (NEW.out_type = 1 	--BOWLED
			OR NEW.out_type = 7 	--LEG_BEFORE_WICKET
			OR NEW.out_type = 6 	--HIT_WICKET
			OR NEW.out_type = 3 	--CAUGHT
			)
			AND(
			OLD.out_type <> 1 	--BOWLED
			AND OLD.out_type <> 7 	--LEG_BEFORE_WICKET
			AND OLD.out_type <> 6 	--HIT_WICKET
			AND OLD.out_type <> 3 	--CAUGHT
			)
			THEN

				IF NEW.bowler_fielder IS NOT NULL THEN
					--Insert or update bowler
					BEGIN
						select 
							* 
						into 
							rec_bowlentry 
						from  
							cricket.scorecard_bowling_entry 
						where 
							innings_id = NEW.innings_id 
							and match_team_player_id = NEW.bowler_fielder;
						
						IF rec_bowlentry.bowling_entry_id is not null then						
							update 
								cricket.scorecard_bowling_entry
							set
								wickets = COALESCE(wickets,0)+1,
								created_time = now(),
								updated_time = now(),
								updated_id = NEW.updated_id 
							where 
								innings_id = NEW.innings_id 
								and match_team_player_id = NEW.bowler_fielder;
						ELSE
							insert into cricket.scorecard_bowling_entry(
							 bowling_entry_id,     
							 match_team_player_id, 
							 innings_id,           
							 overs,                 
							 runs,                  
							 wickets,               
							 no_balls,             
							 wide,                 
							 maiden,               
							 created_id,           
							 created_time,         
							 updated_id,           
							 updated_time        
							)
							values(
							 nextval('cricket.scorecard_bowling_entry_seq'),
							 NEW.bowler_fielder,
							 NEW.innings_id,
							 0,
							 0,
							 1,
							 0,
							 0,
							 0,
							 NEW.created_id,
							 now(),
							 NEW.updated_id,
							 now()					
							);	
							
						END IF;
				
					EXCEPTION 
						WHEN NO_DATA_FOUND THEN
					END;
				END IF;
		END IF;	
				
	ELSIF 'DELETE' = TG_OP THEN
			BEGIN
				select 
					* 
				into 
					rec_bowlentry 
				from  
					cricket.scorecard_bowling_entry 
				where 
					innings_id = OLD.innings_id 
					and match_team_player_id = OLD.bowler_fielder;
				
				IF rec_bowlentry.wickets = 1 and rec_bowlentry.overs <= 1 THEN 
					delete from cricket.scorecard_bowling_entry
					where  innings_id = OLD.innings_id 
						and match_team_player_id = OLD.bowler_fielder;
				ELSE
 
					update 
						cricket.scorecard_bowling_entry
					set
						wickets = case when COALESCE(wickets,0) > 0 then COALESCE(wickets,0)-1 else 0 end,
						created_time = now(),
						updated_time = now(),
						updated_id = OLD.updated_id 
					where 
						innings_id = OLD.innings_id 
						and match_team_player_id = OLD.bowler_fielder;
				END IF;
				
			EXCEPTION 
				WHEN NO_DATA_FOUND THEN
					
			END;
	END IF;
	return new;
    END;
$BODY$ LANGUAGE plpgsql;


create  trigger trg_update_bowling_sc_entry_ins after insert on cricket.scorecard_batting_entry 
FOR EACH ROW EXECUTE PROCEDURE fn_bowling_sc_entry_upd();

create  trigger trg_update_bowling_sc_entry_upd after update on cricket.scorecard_batting_entry 
FOR EACH ROW EXECUTE PROCEDURE fn_bowling_sc_entry_upd();

create  trigger trg_update_bowling_sc_entry_del after delete on cricket.scorecard_batting_entry 
FOR EACH ROW EXECUTE PROCEDURE fn_bowling_sc_entry_upd();
