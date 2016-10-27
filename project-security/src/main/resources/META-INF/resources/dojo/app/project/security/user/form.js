define(['jquery'], function () {
    var addAllGroups = function() {
        alert("TODO: add all groups");
        // TODO: move all options from #selectableGroups to #groups
    };

    var addGroups = function() {
        alert("TODO: add group");
        // TODO: move selected options from #selectableGroups to #groups
    };

    var removeAllGroups = function() {
        alert("TODO: remove all groups");
        // TODO: move all options from #groups to #selectableGroups
    };

    var removeGroups = function() {
        alert("TODO: remove group");
        // TODO: move selected options from #groups to #selectableGroups
    };

    var addAllRoles = function() {
        alert("TODO: add all roles");
        // TODO: move all options from #selectableRoles to #roles
    };

    var addRoles = function() {
        alert("TODO: add role");
        // TODO: move selected options from #selectableRoles to #roles
    };

    var removeAllRoles = function() {
        alert("TODO: remove all roles");
        // TODO: move all options from #roles to #selectableRoles
    };

    var removeRoles = function() {
        alert("TODO: remove role");
        // TODO: move selected options from #roles to #selectableRoles
    };

    return {
        startup: function () {
            $('#addAllGroups').click(addAllGroups);
            $('#addGroups').click(addGroups);
            $('#removeAllGroups').click(removeAllGroups);
            $('#removeGroups').click(removeGroups);
            $('#addAllRoles').click(addAllRoles);
            $('#addRoles').click(addRoles);
            $('#removeAllRoles').click(removeAllRoles);
            $('#removeRoles').click(removeRoles);
        }
    };
});