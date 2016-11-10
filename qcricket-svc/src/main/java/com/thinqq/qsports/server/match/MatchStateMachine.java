package com.thinqq.qsports.server.match;

import com.thinqq.qsports.server.core.statemachine.StateMachine;

/*
 * 
 Planned -> Started
 1. All mandatory fields filled
 2. Eleven players in the match
 Planned -> Abandoned
 No Criteria
 Started -> Completed
 1. Scorecard validation completed.
 2. Match result filled.
 Started -> Planned
 No Criteria
 Started -> Abandoned
 No Criteria
 Completed -> Approved
 1. At least one owner in each team approved
 Completed -> Rejected
 1. Reason for rejection given
 Rejected -> Completed
 1. Correction comment given
 */

public class MatchStateMachine {}
