SET DB_CLOSE_DELAY -1;        
;             
CREATE USER IF NOT EXISTS SA SALT '93fee9db0bcab1c6' HASH 'b32c42b22374c24e9949f39d88ae533261bd436316aea3c7c372c82cc3055bc8' ADMIN;           
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_DB337595_E8A8_44A2_A867_7B319A67A451 START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_FBA49FA5_A1E7_4BA1_A260_1B976C5AE495 START WITH 2 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_AEA8A5C3_733F_480F_8A9F_2E3D89EF52B2 START WITH 3 BELONGS_TO_TABLE;    
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_4C041B3C_9A5B_4D27_A831_77261C4C108B START WITH 1 BELONGS_TO_TABLE;    
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_ACTION(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_DB337595_E8A8_44A2_A867_7B319A67A451) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_DB337595_E8A8_44A2_A867_7B319A67A451,
    NAME VARCHAR(255),
    SPEL VARCHAR(255)
);       
ALTER TABLE PUBLIC.JPA_REPOSITORY_ACTION ADD CONSTRAINT PUBLIC.CONSTRAINT_F PRIMARY KEY(ID);  
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_ACTION;   
INSERT INTO PUBLIC.JPA_REPOSITORY_ACTION(ID, NAME, SPEL) VALUES
(1, 'TestClassInstanceAction', NULL),
(2, 'S1ToS2TransitionAction', NULL);  
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_GUARD(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_4C041B3C_9A5B_4D27_A831_77261C4C108B) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_4C041B3C_9A5B_4D27_A831_77261C4C108B,
    NAME VARCHAR(255),
    SPEL VARCHAR(255)
);        
ALTER TABLE PUBLIC.JPA_REPOSITORY_GUARD ADD CONSTRAINT PUBLIC.CONSTRAINT_E PRIMARY KEY(ID);   
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_GUARD;    
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_STATE(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_AEA8A5C3_733F_480F_8A9F_2E3D89EF52B2) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_AEA8A5C3_733F_480F_8A9F_2E3D89EF52B2,
    INITIAL BOOLEAN NOT NULL,
    KIND INTEGER,
    MACHINE_ID VARCHAR(255),
    REGION VARCHAR(255),
    STATE VARCHAR(255),
    SUBMACHINE_ID VARCHAR(255),
    INITIAL_ACTION_ID BIGINT,
    PARENT_STATE_ID BIGINT
);        
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE ADD CONSTRAINT PUBLIC.CONSTRAINT_E8 PRIMARY KEY(ID);  
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_STATE;    
INSERT INTO PUBLIC.JPA_REPOSITORY_STATE(ID, INITIAL, KIND, MACHINE_ID, REGION, STATE, SUBMACHINE_ID, INITIAL_ACTION_ID, PARENT_STATE_ID) VALUES
(1, TRUE, NULL, 'Proposal', NULL, 'S1', NULL, NULL, NULL),
(2, FALSE, NULL, 'Proposal', NULL, 'S2', NULL, NULL, NULL);      
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_STATE_DEFERRED_EVENTS(
    JPA_REPOSITORY_STATE_ID BIGINT NOT NULL,
    DEFERRED_EVENTS VARCHAR(255)
);          
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_STATE_DEFERRED_EVENTS;    
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_STATE_ENTRY_ACTIONS(
    JPA_REPOSITORY_STATE_ID BIGINT NOT NULL,
    ENTRY_ACTIONS_ID BIGINT NOT NULL
);        
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_ENTRY_ACTIONS ADD CONSTRAINT PUBLIC.CONSTRAINT_5 PRIMARY KEY(JPA_REPOSITORY_STATE_ID, ENTRY_ACTIONS_ID);              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_STATE_ENTRY_ACTIONS;      
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_STATE_EXIT_ACTIONS(
    JPA_REPOSITORY_STATE_ID BIGINT NOT NULL,
    EXIT_ACTIONS_ID BIGINT NOT NULL
);          
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_EXIT_ACTIONS ADD CONSTRAINT PUBLIC.CONSTRAINT_EF PRIMARY KEY(JPA_REPOSITORY_STATE_ID, EXIT_ACTIONS_ID);               
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_STATE_EXIT_ACTIONS;       
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_STATE_STATE_ACTIONS(
    JPA_REPOSITORY_STATE_ID BIGINT NOT NULL,
    STATE_ACTIONS_ID BIGINT NOT NULL
);        
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_STATE_ACTIONS ADD CONSTRAINT PUBLIC.CONSTRAINT_D PRIMARY KEY(JPA_REPOSITORY_STATE_ID, STATE_ACTIONS_ID);              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_STATE_STATE_ACTIONS;      
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_TRANSITION(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_FBA49FA5_A1E7_4BA1_A260_1B976C5AE495) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_FBA49FA5_A1E7_4BA1_A260_1B976C5AE495,
    EVENT VARCHAR(255),
    KIND INTEGER,
    MACHINE_ID VARCHAR(255),
    GUARD_ID BIGINT,
    SOURCE_ID BIGINT,
    TARGET_ID BIGINT
);     
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION ADD CONSTRAINT PUBLIC.CONSTRAINT_D1 PRIMARY KEY(ID);             
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_TRANSITION;               
INSERT INTO PUBLIC.JPA_REPOSITORY_TRANSITION(ID, EVENT, KIND, MACHINE_ID, GUARD_ID, SOURCE_ID, TARGET_ID) VALUES
(1, 'E1', NULL, 'Proposal', NULL, 1, 2);    
CREATE CACHED TABLE PUBLIC.JPA_REPOSITORY_TRANSITION_ACTIONS(
    JPA_REPOSITORY_TRANSITION_ID BIGINT NOT NULL,
    ACTIONS_ID BIGINT NOT NULL
);          
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION_ACTIONS ADD CONSTRAINT PUBLIC.CONSTRAINT_C PRIMARY KEY(JPA_REPOSITORY_TRANSITION_ID, ACTIONS_ID);
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.JPA_REPOSITORY_TRANSITION_ACTIONS;       
INSERT INTO PUBLIC.JPA_REPOSITORY_TRANSITION_ACTIONS(JPA_REPOSITORY_TRANSITION_ID, ACTIONS_ID) VALUES
(1, 1),
(1, 2);       
CREATE CACHED TABLE PUBLIC.STATE_MACHINE_CONTEXT(
    ID VARCHAR(255) NOT NULL,
    EVENT VARCHAR(255),
    EVENT_HEADERS VARCHAR(255),
    EXTENDED_STATE VARCHAR(255),
    HISTORY_STATES VARCHAR(255),
    STATE VARCHAR(255),
    PARENT_ID VARCHAR(255)
);       
ALTER TABLE PUBLIC.STATE_MACHINE_CONTEXT ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(ID);  
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.STATE_MACHINE_CONTEXT;   
INSERT INTO PUBLIC.STATE_MACHINE_CONTEXT(ID, EVENT, EVENT_HEADERS, EXTENDED_STATE, HISTORY_STATES, STATE, PARENT_ID) VALUES
('proposalId', NULL, 'null', 'null', '{}', 'S2', NULL);          
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_ENTRY_ACTIONS ADD CONSTRAINT PUBLIC.FKALGDCTNELPB0XRIGGIUFBFCD5 FOREIGN KEY(JPA_REPOSITORY_STATE_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;              
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_EXIT_ACTIONS ADD CONSTRAINT PUBLIC.FKNUAHUPLJ5VP27HXQDULT5Y2SU FOREIGN KEY(JPA_REPOSITORY_STATE_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;               
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_EXIT_ACTIONS ADD CONSTRAINT PUBLIC.FKLHWV3OXYP5HPRNLVS56GNYXDH FOREIGN KEY(EXIT_ACTIONS_ID) REFERENCES PUBLIC.JPA_REPOSITORY_ACTION(ID) NOCHECK;      
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE ADD CONSTRAINT PUBLIC.FK85UU4NO99EOIVTD6ELB2RP9DG FOREIGN KEY(PARENT_STATE_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;    
ALTER TABLE PUBLIC.STATE_MACHINE_CONTEXT ADD CONSTRAINT PUBLIC.FKMGGF2K51CI8K1478RD6AXA921 FOREIGN KEY(PARENT_ID) REFERENCES PUBLIC.STATE_MACHINE_CONTEXT(ID) NOCHECK;        
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_STATE_ACTIONS ADD CONSTRAINT PUBLIC.FKQQPKVNPQB8MADRAQ2L57NIAGX FOREIGN KEY(JPA_REPOSITORY_STATE_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;              
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION ADD CONSTRAINT PUBLIC.FK6JYMHCAO9W1786LDRNBDLSACU FOREIGN KEY(TARGET_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;     
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION ADD CONSTRAINT PUBLIC.FKRS9L0AYY1I7T5PJNIXKOHGRLM FOREIGN KEY(GUARD_ID) REFERENCES PUBLIC.JPA_REPOSITORY_GUARD(ID) NOCHECK;      
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE ADD CONSTRAINT PUBLIC.FKL7UW0STK3TA9K0I64VE8VIV8B FOREIGN KEY(INITIAL_ACTION_ID) REFERENCES PUBLIC.JPA_REPOSITORY_ACTION(ID) NOCHECK; 
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_DEFERRED_EVENTS ADD CONSTRAINT PUBLIC.FKOODYQP0KXBMKJTMSKJ9M79H73 FOREIGN KEY(JPA_REPOSITORY_STATE_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;            
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_ENTRY_ACTIONS ADD CONSTRAINT PUBLIC.FKP9G3IQ1NGKU1IMRSF5DNMMNWW FOREIGN KEY(ENTRY_ACTIONS_ID) REFERENCES PUBLIC.JPA_REPOSITORY_ACTION(ID) NOCHECK;    
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION ADD CONSTRAINT PUBLIC.FK4DAHKOV2DTTPLJO5MFCID5GXH FOREIGN KEY(SOURCE_ID) REFERENCES PUBLIC.JPA_REPOSITORY_STATE(ID) NOCHECK;     
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION_ACTIONS ADD CONSTRAINT PUBLIC.FKHWDL9G5S5HTJ2JCB1XRKQ6WPW FOREIGN KEY(ACTIONS_ID) REFERENCES PUBLIC.JPA_REPOSITORY_ACTION(ID) NOCHECK;           
ALTER TABLE PUBLIC.JPA_REPOSITORY_TRANSITION_ACTIONS ADD CONSTRAINT PUBLIC.FK6287NCE3O7SOY1BJDYI04HEIH FOREIGN KEY(JPA_REPOSITORY_TRANSITION_ID) REFERENCES PUBLIC.JPA_REPOSITORY_TRANSITION(ID) NOCHECK;     
ALTER TABLE PUBLIC.JPA_REPOSITORY_STATE_STATE_ACTIONS ADD CONSTRAINT PUBLIC.FK8WGWOPQVHFNB1XE5SQF2213PW FOREIGN KEY(STATE_ACTIONS_ID) REFERENCES PUBLIC.JPA_REPOSITORY_ACTION(ID) NOCHECK;    