define(['dojo/_base/lang', 'baf/html/select'], function (lang, select) {
    return {
        startup: function () {
            $('#addGroups').click(lang.hitch(this, '_onAddGroups'));
            $('#removeGroups').click(lang.hitch(this, '_onRemoveGroups'));
            $('#addRoles').click(lang.hitch(this, '_onAddRoles'));
            $('#removeRoles').click(lang.hitch(this, '_onRemoveRoles'));
            $('#submit').click(lang.hitch(this, '_onSubmit'));
            $('#cancel').click(lang.hitch(this, '_onCancel'));
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
        },

        _onSubmit: function () {
            $('#groups').val(select.getAllOptions('groups'));
            $('#roles').val(select.getAllOptions('roles'));
            $('form').submit();
        },

        _onCancel: function (e) {
            e.preventDefault();
            window.history.back();
        }
    };
});