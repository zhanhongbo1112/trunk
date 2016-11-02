define(['dojo/_base/lang', 'baf/html/select'], function (lang, select) {
    return {
        startup: function () {
            $('#addGroups').click(lang.hitch(this, '_onAddGroups'));
            $('#removeGroups').click(lang.hitch(this, '_onRemoveGroups'));
            $('#addUsers').click(lang.hitch(this, '_onAddUsers'));
            $('#removeUsers').click(lang.hitch(this, '_onRemoveUsers'));
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

        _onAddUsers: function () {
            select.appendSelectedOptions('selectableUsers', 'users');
            select.sort('users');
        },

        _onRemoveUsers: function () {
            select.moveSelectedOptions('users', 'selectableUsers');
            select.sort('selectableUsers');
        },

        _onSubmit: function () {
            $('#groups').val(select.getAllOptions('groups'));
            $('#users').val(select.getAllOptions('users'));
            $('form').submit();
        },

        _onCancel: function (e) {
            e.preventDefault();
            window.history.back();
        }
    };
});