INSERT INTO SEC_USER (ID, USERNAME, PASSWORD, ENABLED) VALUES (1, 'admin', 'password', true);
INSERT INTO SEC_USER (ID, USERNAME, PASSWORD, ENABLED) VALUES (2, 'user', 'password', true);

INSERT INTO SEC_GROUP (ID, PATH, ALIAS, DESCRIPTION) VALUES (1, '/ADMINS', 'ADMINS', 'ADMINS');
INSERT INTO SEC_GROUP (ID, PATH, ALIAS, DESCRIPTION) VALUES (2, '/USERS', 'USERS', 'USERS');

INSERT INTO SEC_ROLE (ID, PATH, ALIAS, DESCRIPTION) VALUES (1, '/ADMINS', 'ADMINS', 'ADMINS');
INSERT INTO SEC_ROLE (ID, PATH, ALIAS, DESCRIPTION) VALUES (2, '/USERS', 'USERS', 'USERS');

INSERT INTO SEC_USER_GROUPS (USER_ID, GROUP_ID) VALUES (1, 1);
INSERT INTO SEC_USER_GROUPS (USER_ID, GROUP_ID) VALUES (1, 2);
INSERT INTO SEC_USER_GROUPS (USER_ID, GROUP_ID) VALUES (2, 2);

INSERT INTO SEC_GROUP_ROLES (GROUP_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO SEC_GROUP_ROLES (GROUP_ID, ROLE_ID) VALUES (2, 2);

INSERT INTO SEC_USER_ROLES (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO SEC_USER_ROLES (USER_ID, ROLE_ID) VALUES (1, 2);
INSERT INTO SEC_USER_ROLES (USER_ID, ROLE_ID) VALUES (2, 2);

insert into acl_sid (id, principal, sid) values (100, FALSE, '/USER');
insert into acl_sid (id, principal, sid) values (101, FALSE, '/USER/ADMIN');
insert into acl_class (id, class) values (100, 'com.yqboots.menu.core.MenuItem');
-- 46753294 (hash code of '/menu')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (100, 100, 46753294, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (100, 100, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (101, 100, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (102, 100, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (103, 100, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (104, 100, 5, 101, 16, TRUE, FALSE, FALSE);
-- 46488677 (hash code of '/dict')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (101, 100, 46488677, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (105, 101, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (106, 101, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (107, 101, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (108, 101, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (109, 101, 5, 101, 16, TRUE, FALSE, FALSE);
-- 1501879 (hash code of '/fss')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (102, 100, 1501879, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (110, 102, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (111, 102, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (112, 102, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (113, 102, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (114, 102, 5, 101, 16, TRUE, FALSE, FALSE);

-- 1260081515 (hash code of '/security/user')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (103, 100, 1260081515, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (115, 103, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (116, 103, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (117, 103, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (118, 103, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (119, 103, 5, 101, 16, TRUE, FALSE, FALSE);
-- 394872031 (hash code of '/security/group')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (104, 100, 394872031, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (120, 104, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (121, 104, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (122, 104, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (123, 104, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (124, 104, 5, 101, 16, TRUE, FALSE, FALSE);
-- -37383008 (hash code of '/security/role')
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (105, 100, 1259988502, null, 101, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (125, 105, 1, 101, 1, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (126, 105, 2, 101, 2, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (127, 105, 3, 101, 4, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (128, 105, 4, 101, 8, TRUE, FALSE, FALSE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (129, 105, 5, 101, 16, TRUE, FALSE, FALSE);
