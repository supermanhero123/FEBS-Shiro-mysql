function updateUser() {
    var selected = $("#userTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的用户！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个用户！');
        return;
    }
    var fid = selected[0].fid;
    $.post(ctx + "userEntity/getUser", {"fid": fid}, function (r) {
        if (r.code === 0) {
            var $form = $('#user-add');
            var $deptTree = $('#deptTree');
            $form.modal();
            var user = r.msg;
            $form.find(".user_password").hide();
            $("#user-add-modal-title").html('修改用户');
            $form.find("input[name='fname']").val(user.fname).attr("readonly", true);
            $form.find("input[name='oldusername']").val(user.fname);
            $form.find("input[name='fid']").val(user.fid);
            $form.find("input[name='fcell']").val(user.fcell);
            // var roleArr = [];
            // for (var i = 0; i < user.roleIds.length; i++) {
            //     roleArr.push(user.roleIds[i]);
            // }
            // $form.find("select[name='rolesSelect']").multipleSelect('setSelects', roleArr);
            // $form.find("input[name='roles']").val($form.find("select[name='rolesSelect']").val());
            var $fisforbidden = $form.find("input[name='fisforbidden']");
            if (user.fisforbidden === '1') {
                $fisforbidden.prop("checked", true);
                $fisforbidden.parent().next().html('可用');
            } else {
                $fisforbidden.prop("checked", false);
                $fisforbidden.parent().next().html('禁用');
            }
            // $deptTree.jstree().open_all();
            // $deptTree.jstree('select_node', user.deptId, true);
            $("#user-add-button").attr("name", "update");

        } else {
            $MB.n_danger(r.msg);
        }
    });
}