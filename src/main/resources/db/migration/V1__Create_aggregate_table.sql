create table aggregate (
  id uuid primary key,
  aggregate_description varchar(256),
  revision int not null,
  type varchar(50) not null
)