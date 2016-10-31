define(['dojo/_base/lang', 'baf/html/select'], function (lang, select) {
    return {
        startup: function () {
            $('#addGroups').click(lang.hitch(this, '_onAddGroups'));
            $('#removeGroups').click(lang.hitch(this, '_onRemoveGroups'));
            $('#addRoles').click(lang.hitch(this, '_onAddRoles'));
            $('#removeRoles').click(lang.hitch(this, '_onRemoveRoles'));
        },

        _onAddGroups: function () {
            select.appendSelectedOptions('selectableGroups', 'groups');
            select.sort('groups');
        },

        _onRemoveGroups: function () {
            select.moveSelectedOptions('groups', 'selectableGroups');
            select.sort('selectableGroups');
        },

        _onAddRoles: function () {
            select.appendSelectedOptions('selectableRoles', 'roles');
            select.sort('roles');
        },

        _onRemoveRoles: function () {
            select.moveSelectedOptions('roles', 'selectableRoles');
            select.sort('selectableRoles');
        }
    };
});