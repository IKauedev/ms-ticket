create table tickets (
    id uuid not null,
    screening_id varchar(50) not null,
    movie_name varchar(150) not null,
    session_time timestamp with time zone not null,
    seat_number varchar(20) not null,
    customer_name varchar(120) not null,
    price numeric(10, 2) not null,
    status varchar(30) not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone not null,
    constraint pk_tickets primary key (id),
    constraint uk_ticket_screening_seat unique (screening_id, seat_number)
);

create index idx_tickets_screening_id on tickets (screening_id);
create index idx_tickets_session_time on tickets (session_time);