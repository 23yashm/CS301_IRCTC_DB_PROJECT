DROP TABLE IF EXISTS train_info, train_chart, vac_seat, ticket_train, ticket_pass, berth_info, train_station_info;

CREATE TABLE train_info(
    utid SERIAL UNIQUE NOT NULL,
    train_no INTEGER NOT NULL,
    date DATE NOT NULL,
    pnr bigint NOT NULL,
    PRIMARY KEY(utid)
);

CREATE TABLE train_chart(
    utid INTEGER NOT NULL,
    ac_coach INTEGER NOT NULL,
    sl_coach INTEGER NOT NULL,
    PRIMARY KEY(utid),
    FOREIGN KEY (utid) REFERENCES train_info(utid)
);

CREATE TABLE vac_seat(
    utid INTEGER NOT NULL,
    coach_type VARCHAR(2) NOT NULL,
    coach_no INTEGER NOT NULL,
    rem_seat INTEGER NOT NULL,
    PRIMARY KEY(utid,coach_type,coach_no),
    FOREIGN KEY (utid) REFERENCES train_info(utid)
);

CREATE TABLE ticket_train(
    pnr BIGINT NOT NULL,
    coach_type VARCHAR(2) NOT NULL,
    utid INTEGER NOT NULL,
    PRIMARY KEY(pnr),
    FOREIGN KEY (utid) REFERENCES train_info(utid)
);

CREATE TABLE ticket_pass(
    pnr BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    coach_no INTEGER NOT NULL,
    berth_no INTEGER NOT NULL,
    PRIMARY KEY(pnr,coach_no,berth_no),
    FOREIGN KEY (pnr) REFERENCES ticket_train(pnr)
);

CREATE TABLE berth_info(
    coach_type VARCHAR(2) NOT NULL,
    berth_no INTEGER NOT NULL,
    berth_type VARCHAR(2) NOT NULL,
    PRIMARY KEY(coach_type,berth_no)
);

INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',1,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',2,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',3,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',4,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',5,'SL');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',6,'SU');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',7,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',8,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',9,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',10,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',11,'SL');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',12,'SU');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',13,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',14,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',15,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',16,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',17,'SL');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('AC',18,'SU');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',1,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',2,'MB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',3,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',4,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',5,'MB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',6,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',7,'SL');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',8,'SU');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',9,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',10,'MB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',11,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',12,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',13,'MB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',14,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',15,'SL');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',16,'SU');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',17,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',18,'MB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',19,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',20,'LB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',21,'MB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',22,'UB');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',23,'SL');
INSERT INTO berth_info (coach_type,berth_no,berth_type) VALUES ('SL',24,'SU');

CREATE TABLE train_station_info(
    train_no INT NOT NULL,
    train_name VARCHAR(50) NOT NULL,
    dep_station VARCHAR(50) NOT NULL,
    dep_time FLOAT NOT NULL,
    arr_station VARCHAR(50) NOT NULL,
    run_duration FLOAT NOT NULL,
    PRIMARY KEY(train_no)
);

INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12058,'Una Himachal - New Delhi Jan Shatabdi Express','Una_Himachal',5,'New_Delhi',7);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (19412,'DLPC SBIB EXP','Daulatpur_Chowk',14.41666667,'Sabarmati_BG',24.5);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12992,'Jaipur - Udaipur City Intercity Express','Chittaurgarh_Junction',14,'Udaipur_City',7.5);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12650,'YPR SAMPARK KRT ','New_Delhi',8.333333333,'Yesvantpur_JN',45.16666667);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (22692,'BANGLORE RJDHNI','New_Delhi',19.83333333,'KSR_Bangalore_CY_JN',33.5);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12046,'CDG NDLS SHT EX','Chandigarh',12.08333333,'New_Delhi',3.25);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12012,'KALKA SHTBDI','Kalka',17.75,'New_Delhi',4.083333333);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (22686,'KARNTK S KRANTI','Chandigarh',3.583333333,'Yesvantpur_JN',50.16666667);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (14012,'HSX DLI EXP','Chandigarh',3.216666667,'New_Delhi',4.366666667);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (19614,'ASR AII EXP','Amritsar_JN',17.75,'Jaipur',12.66666667);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12030,'ASR SHTBDI EXP','Amritsar_JN',16.83333333,'New_Delhi',6);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (12716,'SACHKHAND EXP','Amritsar_JN',5.5,'New_Delhi',7.25);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (22463,'RJSTHN S KRANTI','New_Delhi',22.41666667,'Jaipur',4.333333333);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (19610,'YNRK UDZ EXP','New_Delhi',0.5,'Jaipur',5.666666667);
INSERT INTO train_station_info (train_no,train_name,dep_station,dep_time,arr_station,run_duration) VALUES (19338,'DEE INDB EXP','New_Delhi',15,'Jaipur',4.5);

DROP FUNCTION IF EXISTS book_ticket;

CREATE OR REPLACE FUNCTION book_ticket(num_pass int, pass_name text[], train_no_ int, date_ date, coach_type_ text, pnr1 bigint)
RETURNS TABLE(
    pnr__ BIGINT,
    status__ INT
)
LANGUAGE plpgsql AS
$$
    DECLARE
    utid_ int;
    i int;
    c_no int;
    s_no int;
    total_seats int;
    pnr_ bigint;

    BEGIN

        PERFORM pg_advisory_lock(utid) FROM train_info T WHERE T.train_no=train_no_ and T.date=date_;

        SELECT T.utid INTO utid_ FROM train_info T WHERE T.train_no=train_no_ AND T.date=date_;

        IF utid_ IS NULL THEN
            pnr__ := pnr_;
            status__ := -1;
            RETURN NEXT;
            RETURN;
        END IF;

        SELECT T.pnr INTO pnr_ FROM train_info T WHERE T.utid = utid_ ;

        UPDATE train_info SET pnr = pnr_+1 WHERE utid=utid_ ;
        pnr_ = pnr1*100000 + pnr_;

        total_seats := (SELECT sum(rem_seat) FROM vac_seat V WHERE V.utid=utid_ AND V.coach_type=coach_type_);

        IF total_seats IS NULL OR total_seats<num_pass THEN
            pnr__ := pnr_;
            status__ := -2;
            RETURN NEXT;
            RETURN;
        END IF;

        INSERT INTO ticket_train (pnr,coach_type,utid) VALUES (pnr_,coach_type_,utid_);

        FOR i IN 1 .. num_pass LOOP
            SELECT coach_no,rem_seat INTO c_no,s_no FROM vac_seat V WHERE V.utid=utid_ AND V.coach_type=coach_type_ ORDER BY coach_no LIMIT 1;
            INSERT INTO ticket_pass (pnr,name,coach_no,berth_no) VALUES (pnr_,pass_name[i],c_no,s_no);
            IF s_no=1 THEN
                DELETE FROM vac_seat V where V.utid=utid_ and V.coach_type=coach_type_ and V.coach_no=c_no;
            ELSE
                UPDATE vac_seat SET rem_seat=s_no-1 where utid=utid_ and coach_type=coach_type_ and coach_no=c_no;
            END IF;
        END LOOP;

        pnr__ := pnr_;
        status__ := 1;
        RETURN NEXT;

    END
$$;

DROP FUNCTION IF EXISTS release_trains;

CREATE OR REPLACE FUNCTION release_trains(num_train int, train_no_ int[], date_ date[], ac_coach_ int[], sl_coach_ int[])
RETURNS TABLE(
    train_no__ INT,
    date__ DATE,
    status__ VARCHAR(10)
)
LANGUAGE plpgsql AS
$$
    DECLARE
    utid INT;
    i INT;
    j INT;

    BEGIN

        FOR i IN 1 .. num_train LOOP
            IF (SELECT count(*) FROM train_info T WHERE T.train_no=train_no_[i] AND T.date=date_[i])=0 THEN
                INSERT INTO train_info (train_no,date,pnr) VALUES (train_no_[i],date_[i],1);
                utid = (SELECT T.utid FROM train_info T WHERE T.train_no=train_no_[i] AND T.date=date_[i]);
                INSERT INTO train_chart (utid,ac_coach,sl_coach) VALUES (utid,ac_coach_[i],sl_coach_[i]);

                FOR j IN 1 .. ac_coach_[i] LOOP
                    INSERT INTO vac_seat (utid,coach_type,coach_no,rem_seat) VALUES (utid,'AC',j,18);
                END LOOP;

                FOR j IN 1 .. sl_coach_[i] LOOP
                    INSERT INTO vac_seat (utid,coach_type,coach_no,rem_seat) VALUES (utid,'SL',j,24);
                END LOOP;
       
                status__ := 'Added';
            ELSE 
                status__ := 'Already exists';
            END IF;

            train_no__ := train_no_[i] ;
            date__ := date_[i];

            RETURN NEXT;
        END LOOP;

    END
$$;