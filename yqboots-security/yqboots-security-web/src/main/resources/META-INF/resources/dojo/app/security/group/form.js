define(['dojo/_base/lang', 'baf/html/select'], function (lang, select) {
    return {
        startup: function () {
            $('#addUsers').click(lang.hitch(this, '_onAddUsers'));
            $('#removeUsers').click(lang.hitch(this, '_onRemoveUsers'));
            $('#addRoles').click(lang.hitch(this, '_onAddRoles'));
            $('#removeRoles').click(lang.hitch(this, '_onRemoveRoles'));
            $('#submit').click(lang.hitch(this, '_onSubmit'));
            $('#cancel').click(lang.hitch(this, '_onCancel'));
        },

        _onAddUsers: function () {
            select.appendSelectedOptions('selectableUsers', 'users');
            select.sort('users');
        },

        _onRemoveUsers: function () {
            select.moveSelectedOptions('users', 'selectableUsers');
            select.sort('selectableUsers');
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
            $('#users').val(select.getAllOptions('users'));
            $('#roles').val(select.getAllOptions('roles'));
            $('form').submit();
        },

        _onCancel: function (e) {
            e.preventDefault();
            window.history.back();
        }
    };
});