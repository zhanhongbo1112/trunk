define(['jquery'], function () {
    var list2list = function (fromid, toid, moveOrAppend, isAll) {
        if (moveOrAppend.toLowerCase() == "move") {    //移动
            if (isAll == true) {    //全部移动
                $("#" + fromid + " option").each(function () {
                    //将源list中的option添加到目标list,当目标list中已有该option时不做任何操作.
                    $(this).appendTo($("#" + toid + ":not(:has(option[value=" + $(this).val() + "]))"));
                });
                $("#" + fromid).empty();    //清空源list
            }
            else if (isAll == false) {
                $("#" + fromid + " option:selected").each(function () {
                    //将源list中的option添加到目标list,当目标list中已有该option时不做任何操作.
                    $(this).appendTo($("#" + toid + ":not(:has(option[value=" + $(this).val() + "]))"));
                    //目标list中已经存在的option并没有移动,仍旧在源list中,将其清空.
                    if ($("#" + fromid + " option[value=" + $(this).val() + "]").length > 0) {
                        $("#" + fromid).get(0)
                            .removeChild($("#" + fromid + " option[value=" + $(this).val() + "]").get(0));
                    }
                });
            }
        }
        else if (moveOrAppend.toLowerCase() == "append") {
            if (isAll == true) {
                $("#" + fromid + " option").each(function () {
                    $("<option></option>")
                        .val($(this).val())
                        .text($(this).text())
                        .appendTo($("#" + toid + ":not(:has(option[value=" + $(this).val() + "]))"));
                });
            }
            else if (isAll == false) {
                $("#" + fromid + " option:selected").each(function () {
                    $("<option></option>")
                        .val($(this).val())
                        .text($(this).text())
                        .appendTo($("#" + toid + ":not(:has(option[value=" + $(this).val() + "]))"));
                });
            }
        }
    };

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