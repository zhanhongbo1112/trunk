insert into acl_sid (id, principal, sid) values (100, FALSE, 'ROLE_USER');
insert into acl_class (id, class) values (100, 'com.yqboots.security.access.TestMenuItem');
insert into acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) values (100, 100, -10005436, null, 100, TRUE);
insert into acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) values (100, 100, 1, 100, 1, TRUE, FALSE, FALSE);