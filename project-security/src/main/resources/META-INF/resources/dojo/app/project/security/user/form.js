define(['jquery'], function () {
    var addGroups = function() {debugger;
        // TODO: move selected options from #selectableGroups to #groups
        var source = $('#selectableGroups').val();
        if (!source) {
            return;
        }

        var target = $('#groups option').map(function () {
            return $(this).val();
        }).get();

        if (!target) {
            target = [];
        }

        source.forEach(function(item, index) {
            target.push(item);
        });

        alert(target.sort());
    };

    var removeGroups = function() {
        alert("TODO: remove group");
        // TODO: move selected options from #groups to #selectableGroups
    };

    var addRoles = function() {
        alert("TODO: add role");
        // TODO: move selected options from #selectableRoles to #roles
    };

    var removeRoles = function() {
        alert("TODO: remove role");
        // TODO: move selected options from #roles to #selectableRoles
    };

    return {
        startup: function () {
            $('#addGroups').click(addGroups);
            $('#removeGroups').click(removeGroups);
            $('#addRoles').click(addRoles);
            $('#removeRoles').click(removeRoles);
        }
    };
});