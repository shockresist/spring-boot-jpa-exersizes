create table TEST_PARENT
(
  id      NUMBER not null,
  details VARCHAR2(500)
);

create table TEST_CHILD
(
  id        NUMBER not null,
  parent_id NUMBER not null,
  details   VARCHAR2(500)
);

create table TEST_PARENT_CHILD
(
  parent_id   NUMBER not null,
  child_id    NUMBER not null,
  child_order NUMBER not null
);

select t.*, t.rowid from TEST_PARENT t;
select t.*, t.rowid from TEST_CHILD t;
select t.*, t.rowid from TEST_PARENT_CHILD t;

begin
delete from TEST_CHILD;
delete from TEST_PARENT;
delete from TEST_PARENT_CHILD;
commit;
end;
