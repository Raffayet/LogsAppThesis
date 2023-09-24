insert into my_user(username, password) values ('aleksandar@gmail.com', '123');
insert into my_user(username, password) values ('nikola@gmail.com', '123');
insert into my_user(username, password) values ('srdjan@gmail.com', '123');

--Crqlar domain tables
insert into profile(first_name, last_name, language_code, birth_date, vip_code, average_revenue_per_day,
                     last_stay_revenue_room_stay, last_stay_services_revenue, last_stay_total_revenue,
                     total_number_of_nights, total_number_of_stays, total_revenue, total_revenue_room_stays,
                     total_revenue_services)
values ('Noah', 'Lang', 'DE', '1970-03-09T02:47:14.492000', 'DDR', 405, 2230, 200, 2430, 6, 1, 2430, 2230, 200);

insert into profile(first_name, last_name, language_code, birth_date, vip_code, average_revenue_per_day,
                    last_stay_revenue_room_stay, last_stay_services_revenue, last_stay_total_revenue,
                    total_number_of_nights, total_number_of_stays, total_revenue, total_revenue_room_stays,
                    total_revenue_services)
values ('Mirko', 'Maric', 'RS', '1985-03-09T02:47:14.492000', 'DDR', 375, 2000, 150, 2150, 12, 2, 4500, 3500, 1000);

insert into profile(first_name, last_name, language_code, birth_date, vip_code, average_revenue_per_day,
                    last_stay_revenue_room_stay, last_stay_services_revenue, last_stay_total_revenue,
                    total_number_of_nights, total_number_of_stays, total_revenue, total_revenue_room_stays,
                    total_revenue_services)
values ('Hans', 'De Jong', 'NL', '1975-03-09T02:47:14.492000', 'DDR', 550, 3000, 400, 3400, 10, 2, 5500, 4700, 800);

insert into room_stay(from_day, to_day, room_id_crq, rate_plan_code_crq, amount_after_tax, room_stay_status)
values ('2023-09-19', '2023-09-25', 411, 'HPC2', 2100, 'reserved');

insert into room_stay(from_day, to_day, room_id_crq, rate_plan_code_crq, amount_after_tax, room_stay_status)
values ('2023-07-19', '2023-07-25', 411, 'HPC2', 1800, 'reserved');

insert into room_stay(from_day, to_day, room_id_crq, rate_plan_code_crq, amount_after_tax, room_stay_status)
values ('2023-06-19', '2023-06-25', 411, 'HPC2', 1800, 'reserved');

insert into room_stay(from_day, to_day, room_id_crq, rate_plan_code_crq, amount_after_tax, room_stay_status)
values ('2023-05-19', '2023-05-25', 411, 'HPC2', 1900, 'reserved');

insert into room_stay(from_day, to_day, room_id_crq, rate_plan_code_crq, amount_after_tax, room_stay_status)
values ('2023-05-19', '2023-05-25', 411, 'HPC2', 1900, 'reserved');

insert into profile_room_stays(profile_id, room_stays_id) values (1, 1);
insert into profile_room_stays(profile_id, room_stays_id) values (2, 2);
insert into profile_room_stays(profile_id, room_stays_id) values (2, 3);
insert into profile_room_stays(profile_id, room_stays_id) values (3, 4);
insert into profile_room_stays(profile_id, room_stays_id) values (3, 5);