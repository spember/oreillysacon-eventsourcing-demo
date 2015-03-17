create table event (
  id uuid primary key,
  aggregate_id uuid references aggregate(id),
  revision int not null,
  date timestamp with time zone not null,
  type varchar(50) not null,
  user_id varchar(36),
  data text not null
)