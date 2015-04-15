# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article (
  id                        bigint not null,
  title                     varchar(255),
  article                   varchar(255),
  user_id                   bigint,
  constraint pk_article primary key (id))
;

create table user (
  id                        bigint not null,
  name                      varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  admin                     boolean,
  constraint pk_user primary key (id))
;

create sequence article_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists article;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists article_seq;

drop sequence if exists user_seq;

